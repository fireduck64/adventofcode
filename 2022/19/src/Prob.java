import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  TreeMap<Integer, Blueprint> prints= new TreeMap();

  public Prob(Scanner scan)
  {
    long p1 = 0;
    long p2 = 1;
    for(String line : In.lines(scan))
    {
      Blueprint bp = new Blueprint(line);

      prints.put(bp.id, bp);

      System.out.println("bp : " + bp.id);

      /*int v = 0;
      while(true)
      {

        TreeMap<String, Integer> needed = new TreeMap<>();
        needed.put("ore", -32); // starting miner
        needed.put("clay", 0);
        needed.put("obs", 0);
        needed.put("geo", v);

        if (planMe(bp, needed, 24, 24))
        {
          System.out.println("plan success" + " " + v);
        }
        else
        {
          v--;
          break;
        }
        v++;
      }*/
      mm.clear();
      blurst.clear();
      int v = rec(32, new State(), bp);
      //int v = findBest(bp);
      System.out.println(v);
      p2 *= v;
      p1 += bp.id * v;


    }
    System.out.println(p1);
    System.out.println(p2);
  }


  // Nothing has ever been more wrong than whatever the fuck this is
  public boolean planMe(Blueprint bp, TreeMap<String, Integer> needed, int time, int end_time )
  {
    if (time==0)
    {
      System.out.println("" + needed + " " + time);
      for(int v : needed.values())
      {
        if (v > 0) return false;
      }
      return true;
    }
    int rem_time = end_time - time - 1;
    for(int v : needed.values())
    {
      if (v > (time * time + 1 / 2)) return false;
    }
    if (planMe(bp, needed, time-1, end_time)) return true;


    for(String t : bp.costs.keySet())
    {
      Cost c = bp.costs.get(t);
      TreeMap<String, Integer> n2 = new TreeMap<>();
      n2.putAll(needed);
      // This new robot will robot until the end
      n2.put(t, n2.get(t) - rem_time);

      for(String tt : c.mats.keySet())
      {
        n2.put(tt, n2.get(tt) + c.mats.get(tt));

      }
      if (planMe(bp, n2, time-1, end_time)) return true;

    }

    
    return false;
  }

  public int findBest(Blueprint bp)
  {
    LinkedList<State> in = new LinkedList<>();
    LinkedList<State> out = new LinkedList<>();
    in.add(new State());

    for(int i=0; i<32; i++)
    {
      expandPhase(i, in, out, bp);

      System.out.println("Time: " + i + " s: " + out.size());
      in=out;
      out=new LinkedList<>();


    }
    int b=0;
    for(State s : in)
    {
      b = Math.max(s.mats.get("geo"), b);
    }
    return b;

  }

  public void expandPhase(int time, List<State> in, List<State> out, Blueprint bp)
  {
    ArrayList<State> t_out=new ArrayList<>();
    HashSet<String> h_out = new HashSet<>();
    for(State s : in)
    {
    
    LinkedList<String> build_order = new LinkedList<String>();
    build_order.add("geo");
    build_order.add("obs");
    build_order.add("clay");
    build_order.add("ore");
    boolean bigb=true;

      // Decide what to build
      for(String t : build_order)
      {
        Cost c = bp.costs.get(t);
        if ((s.has(c)) && bigb)
        {
          State s2 = new State(s);
          int v = s2.mine();
          s2.robots.put(t, s2.robots.get(t) + 1);
          s2.spend(c);

          String h = HUtil.getHash(s2.toString());
          if (!h_out.contains(h))
          {
            t_out.add(s2);
          }
          bigb=false;
        }

      }
      {

        // nothing
        State s2 = new State(s);
        int v = s2.mine();
           String h = HUtil.getHash(s2.toString());
          if (!h_out.contains(h))
          {
            t_out.add(s2);
          }
      }
    }

    TreeSet<Integer> rem_list=new TreeSet<>();
    
    for(int i=0; i<t_out.size();i++)
    {
      boolean include=true;
      /*for(int j=0; j<t_out.size(); j++)
      {
        if (i != j)
        {
          if (t_out.get(j).isBetterThan(t_out.get(i)))
          {
            include=false;
          }
        }

      }*/
      if (include)
      {
        out.add(t_out.get(i));
      }

    }


  }

  
  HashMap<String, Integer> mm=new HashMap<>();

  TreeMap<Integer, Integer> blurst=new TreeMap<>();

  public int rec(int time, State s, Blueprint bp)
  {
    if (time==0) return 0;

    //System.out.println("" + time + " " + s.toString());
    if (blurst.containsKey(time))
    {
      if (blurst.get(time) > s.robots.get("geo")+1) return 0;
      blurst.put(time, Math.max(s.robots.get("geo"), blurst.get(time)));
    }
    else
    {
      blurst.put(time, s.robots.get("geo"));
    }

    String memo = null;
    boolean do_memo= (time % 5 == 0);
    if (do_memo)
    {
      memo = HUtil.getHash("" + time + " " + bp.id + " " + s.toString());

      if (mm.containsKey(memo)) return mm.get(memo);
    }

    int best = 0;

    // Decide what to build
    for(String t : bp.costs.keySet())
    {
      Cost c = bp.costs.get(t);
      if (s.has(c))
      {
        State s2 = new State(s);
        int v = s2.mine();
        s2.robots.put(t, s2.robots.get(t) + 1);
        s2.spend(c);

        best = Math.max(best, v + rec(time-1, s2, bp));
      }

    }
    {
      // nothing
      State s2 = new State(s);
      int v = s2.mine();
      best = Math.max(best,v + rec(time-1, s2, bp));
    }

    if (do_memo)
    {
      mm.put(memo, best);
    }

    return best;
  }



  public class State
  {
    TreeMap<String, Integer> mats=new TreeMap<>();
    TreeMap<String, Integer> robots = new TreeMap<>();

    public State()
    {
      mats.put("ore", 0);
      mats.put("clay", 0);
      mats.put("obs", 0);
      mats.put("geo", 0);

      robots.put("ore", 1);
      robots.put("clay", 0);
      robots.put("obs", 0);
      robots.put("geo", 0);
    }
    public State(State src)
    {
      mats.putAll(src.mats);
      robots.putAll(src.robots);
    }
    public String toString()
    {
      return mats.toString() + "/" + robots.toString();
    }

    // If s doesn't beat us in anything, true
    public boolean isBetterThan(State s)
    {
      /*if (robots.get("geo") > s.robots.get("geo")) return true;
      if (robots.get("obs") > s.robots.get("obs")) return true;
      if (robots.get("clay") > s.robots.get("clay")) return true;
      if (robots.get("ore") > s.robots.get("ore")) return true;*/

      //return false;

      for(String m : mats.keySet())
      {
        if (s.mats.get(m) > mats.get(m)) return false;
      }
      for(String m : robots.keySet())
      {
        if (s.robots.get(m) > robots.get(m)) return false;
      }
      return true;

    }
    public int mine()
    {
      int geo = 0;
      for(String s : robots.keySet())
      {
        int n = robots.get(s);
        if (n > 0)
        {
          if (s.equals("geo"))
          {
            geo=n;
          }
          else
          {
            mats.put(s, mats.get(s) + n);
          }
        }
      }
      return geo;
    }
    public boolean has(Cost c)
    {
      for(String s : c.mats.keySet())
      {
        int needed = c.mats.get(s);
        if (mats.get(s) < needed) return false;
      }
      return true;

    }
    public void spend(Cost c)
    {
      for(String s : c.mats.keySet())
      {
        int needed = c.mats.get(s);
        mats.put(s, mats.get(s) - needed);
      }
    }

  }

  public class Cost
  {
    TreeMap<String, Integer> mats=new TreeMap<>();

  }

  public class Blueprint
  {
    int id;

    TreeMap<String, Cost> costs = new TreeMap<>();

    public Blueprint(String line)
    {
      List<Integer> nums = Tok.ent(line, " :");
      if (nums.size() != 7) E.er();
      id = nums.get(0);

    Cost c_ore = new Cost();
    Cost c_clay = new Cost();
    Cost c_obs = new Cost();
    Cost c_geo = new Cost();
      c_ore.mats.put("ore", nums.get(1));
      c_clay.mats.put("ore", nums.get(2));
      c_obs.mats.put("ore", nums.get(3));
      c_obs.mats.put("clay", nums.get(4));
      c_geo.mats.put("ore", nums.get(5));
      c_geo.mats.put("obs", nums.get(6));

      costs.put("ore", c_ore);
      costs.put("clay", c_clay);
      costs.put("obs", c_obs);
      costs.put("geo", c_geo);



    }

  }
}
