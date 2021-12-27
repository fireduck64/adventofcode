
import java.util.*;

import com.google.common.collect.TreeMultimap;

public class SearchWorst
{

  public static class SearchCtx
  {
    private TreeMap<StateSort, State> queue=new TreeMap<>();
    private HashMap<String, Double> visited=new HashMap<String,Double>(8192, 0.5f);
    private LinkedList<State> terms=new LinkedList<>();
    private double best_term=1e15;
    private volatile boolean has_terms=false;

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
        double cost = me.getKey().cost;
        State s = me.getValue();


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
            ctx.enqueue(new StateSort(n.getCost() + n.getEstimate(), rnd.nextLong()), n);
          }
        }

      }


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
    ctx.enqueue(new StateSort(start.getCost(), 0), start);
    LinkedList<ParaSearchThread> threads = new LinkedList<>();
    for(int i=0; i<64; i++)
    {
      ParaSearchThread t= new ParaSearchThread(ctx);
      t.start();
      threads.add(t);
    }
    for(ParaSearchThread t : threads)
    {
      t.join();
    }

    return ctx.getBestTermVal();

  }


  public static State search(State start)
  {
    //TreeMultimap<Double, State> queue = TreeMultimap.create();
    TreeMap<StateSort, State> queue = new TreeMap<>();
    long sort_idx = 0;

    HashSet<String> visited = new HashSet<>();

    Random rnd = new Random();
    queue.put(new StateSort(start.getCost(), sort_idx), start);
    sort_idx++;

    int count = 0;
    State found_term = null;
    while(!queue.isEmpty())
    {
      count++;
      
      //Map.Entry<Double, Collection<State> > me =  queue.asMap().firstEntry();

      //Iterator<State> I = me.getValue().iterator();
      //State s = I.next();
      //I.remove();
      State s = queue.pollFirstEntry().getValue();

      if (count % 10000 == 0)
      {
        System.out.println(String.format("Search %d %f (q:%d) - %s", count, s.getCost(), queue.size(), s));
      }
      if (s.isTerm())
      {
        if ((found_term == null) || (s.getCost() > found_term.getCost()))
        {
          found_term = s;
        }
        
        continue; // no next after term
      }

      String hash = s.getHash();

      if (!visited.contains(hash))
      {
        visited.add(hash);

        for(State n : s.next())
        {
          queue.put(new StateSort(n.getCost() + n.getEstimate(), sort_idx), n);
          sort_idx++;
          //queue.put(n.getCost() + n.getEstimate() + rnd.nextDouble()/1e6, n);
        }
      }

    }

    return found_term;

  }

  public static class StateSort implements Comparable<StateSort>
  {
    private double cost;
    private long idx;

    public StateSort(double cost, long idx)
    {
      this.cost = cost;
      this.idx = idx;

    }
    public int compareTo(StateSort ss)
    {
      if (cost < ss.cost) return -1;
      if (cost > ss.cost) return 1;
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
