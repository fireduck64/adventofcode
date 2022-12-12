
import java.util.*;

import com.google.common.collect.TreeMultimap;
import com.google.common.collect.ImmutableList;
import java.util.concurrent.atomic.AtomicLong;
import duckutil.MultiAtomicLong;
import duckutil.PeriodicThread;

public class Search
{
  public static final long print_every=10000;

  public static class SearchCtx
  {
    private TreeMap<StateSort, State> queue=new TreeMap<>();
    private HashMap<String, Double> visited=new HashMap<String,Double>(8192, 0.5f);
    private LinkedList<State> terms=new LinkedList<>();
    private double best_term=1e15;
    private volatile boolean has_terms=false;
    protected volatile State recent_state=null;
    protected MultiAtomicLong states = new MultiAtomicLong();

    public boolean checkVisit(State state, double cost)
    {
      String hash = state.getHash();
      synchronized(visited)
      {
        if ((!visited.containsKey(hash)) || (visited.get(hash) > cost))
        {
          visited.put(hash, cost);
          return true;
        }
        return false;
      }
    }

    public Map.Entry<StateSort, State> dequeue()
      throws Exception
    {
      synchronized(queue)
      {
        int tries=0;
        while(true)
        {
          Map.Entry<StateSort, State> me = queue.pollFirstEntry();
          if (me!=null) return me;
          if (has_terms) return null;
          if (tries > 20) return null;
          
          queue.wait(500L);
          tries++;
        }

      }

    }

    public void enqueue(StateSort sort, State state)
    {
      synchronized(queue)
      {
        queue.put(sort, state);
        queue.notifyAll();
      }
    }
    public double getBestTerm()
    {
      synchronized(terms)
      {
        return best_term;
      }
    }
    public State getBestTermVal()
    {
      State best = null;
      double cost=0.0;
      synchronized(terms)
      {
        for(State t : terms)
        {
          if ((best == null) || (t.getCost() < cost))
          {
            best = t;
            cost = t.getCost();

          }

        }
      }
      return best;
    }
    public void addTerm(State t)
    {
      synchronized(terms)
      {
        terms.add(t);
        best_term = Math.min( best_term, t.getCost());
        has_terms=true;
      }
      synchronized(queue)
      {
        queue.notifyAll();
      }
    }
  }

  public static void runPara(SearchCtx ctx)
    throws Exception
  {
      Random rnd = new Random();

      while(true)
      {

        Map.Entry<StateSort, State> me = ctx.dequeue();
        if (me == null)
        {
          return;
        }
        ctx.states.add(1L);
        double cost = me.getKey().cost;
        State s = me.getValue();
        ctx.recent_state=s;


        if (s.isTerm())
        {
          ctx.addTerm(s);
          System.out.println("Search solution: " + s);
          return;
        }
        
        if (ctx.getBestTerm() < cost) return;

        if (ctx.checkVisit(s, s.getCost()))
        {

          for(State n : s.next())
          {
            ctx.enqueue(new StateSort(n.getCost() + n.getEstimate(), n.getLean(), rnd.nextLong()), n);
          }
        }

      }


  }
  public static class ParaMetricThread extends PeriodicThread
  {
    SearchCtx ctx;
    private long last_count=0L;
    public ParaMetricThread(SearchCtx ctx)
    {
      super(2000);
      this.ctx = ctx;

    }
    public void runPass()
    {
      long s = ctx.states.sum();
      if (s - last_count > print_every)
      {
        int queue_size = 0;
        synchronized(ctx.queue)
        {
          queue_size = ctx.queue.size();
        }
        System.out.println(getReport(s,queue_size, ctx.recent_state));

        last_count += print_every;
      }

    }


  }
  public static String getReport(long count, int queue_size, State st)
  {
    return String.format("  States: %d {q:%d} {c:%f e:%f l:%f} - %s", count, queue_size, st.getCost(), st.getEstimate(), st.getLean(), st.toString());
  }
  
  public static class ParaSearchThread extends Thread
  {
    SearchCtx ctx;
    public ParaSearchThread(SearchCtx ctx)
    {
      this.ctx = ctx;
    }

    public void run()
    {
      try
      {
        runPara(ctx);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }

    }
  }

  public static State searchPara(State start)
    throws Exception
  {
    SearchCtx ctx = new SearchCtx();
    ctx.enqueue(new StateSort(start.getCost(), start.getLean(), 0), start);
    LinkedList<ParaSearchThread> threads = new LinkedList<>();
    for(int i=0; i<64; i++)
    {
      ParaSearchThread t= new ParaSearchThread(ctx);
      t.start();
      threads.add(t);
    }
    ParaMetricThread metric = new ParaMetricThread(ctx);
    metric.start();
    for(ParaSearchThread t : threads)
    {
      t.join();
    }
    metric.halt();

    return ctx.getBestTermVal();

  }

  public static State searchM(List<State> starts)
  {
    //TreeMultimap<Double, State> queue = TreeMultimap.create();
    TreeMap<StateSort, State> queue = new TreeMap<>();
    long sort_idx = 0;

    HashSet<String> visited = new HashSet<>();

    Random rnd = new Random();
    for(State s : starts)
    {
      queue.put(new StateSort(s.getCost(), s.getLean(), sort_idx), s);
      sort_idx++;
    }

    int count = 0;
    while(!queue.isEmpty())
    {
      count++;
      
      //Map.Entry<Double, Collection<State> > me =  queue.asMap().firstEntry();

      //Iterator<State> I = me.getValue().iterator();
      //State s = I.next();
      //I.remove();
      State s = queue.pollFirstEntry().getValue();

      if (count % print_every == 0)
      {
        System.out.println(getReport(count, queue.size(), s));
      }
      if (s.isTerm())
      {
        System.out.println("Search solution: " + s);
        return s;
      }

      String hash = s.getHash();

      if (!visited.contains(hash))
      {
        visited.add(hash);

        for(State n : s.next())
        {
          queue.put(new StateSort(n.getCost() + n.getEstimate(), n.getLean(), sort_idx), n);
          sort_idx++;
          //queue.put(n.getCost() + n.getEstimate() + rnd.nextDouble()/1e6, n);
        }
      }

    }

    return null;

  }


  public static State search(State start)
  {
    return searchM(ImmutableList.of(start));
  }

  public static class StateSort implements Comparable<StateSort>
  {
    private double cost;
    private double lean;
    private long idx;

    
    public StateSort(double cost, double lean, long idx)
    {
      this.cost = cost;
      this.lean = lean;
      this.idx = idx;

    }
    public int compareTo(StateSort ss)
    {
      if (cost < ss.cost) return -1;
      if (cost > ss.cost) return 1;
      if (lean < ss.lean) return -1;
      if (lean > ss.lean) return 1;
      if (idx < ss.idx) return -1;
      if (idx > ss.idx) return 1;

      return 0;

    }

    @Override
    public int hashCode()
    {
      E.er();
      return 0;
    }

    @Override
    public boolean equals(Object o)
    {
      E.er();
      return false;

    }
  }


}
