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
  TreeMap<String, Valve> map=new TreeMap<>();

  TreeSet<String> openable = new TreeSet<>();

  public Prob(Scanner scan)
    throws Exception
  {
    for(String line : In.lines(scan))
    {
      Valve v = new Valve(line);

      map.put(v.id, v);
      if (v.flow_rate > 0) openable.add(v.id);
    }

    // Nonsense A-star solution (worked)
    //SS p1 = (SS) Search.search(new SS(0, 0, "AA", new TreeSet<String>()));
    //System.out.println(p1.released);

    System.out.println("Making travel map");
    for(String s : map.keySet())
    {
      getCostMap(s);
      //System.out.println("Cost map: " + s + " " + getCostMap(s));
    }

    // Pretending I'm not an idiot recursive solution
    System.out.println("Part 1: " + rec(1, 0, "AA", new TreeSet<String>()));
    System.out.println("Part 2: " + rec(0, 4, "AA", new TreeSet<String>()));

    // Nonsense A-star solution to p2 (kinda worked on sample - super slow)
    //System.out.println(rec(new SS2(4, 0, "AA", "AA", new TreeSet<String>(),0)));
    //SS2 p2 = (SS2) Search.searchPara(new SS2(4, 0, "AA", "AA", new TreeSet<String>(),0));
    //System.out.println(p2.released);
  }

 
  HashMap<String, Integer> mm=new HashMap<>();
  

  // Treating each worker as a phase.
  // So phase 0 is person, phase 1 is elephant
  // For p1, start on phase 1.
  public int rec(int phase, int time, String loc, TreeSet<String> opened)
  {

    if (phase==2) return 0;
    
    String memo = "" + phase + " " + time + " " + loc + " " + opened;
    if (mm.containsKey(memo)) return mm.get(memo);

    int best =0;

    // chill

    best = Math.max(best, rec(phase+1, 4, "AA", opened));

    if (time < 30)
    {

      // move and open

      // To hell with all the walking around,
      // we walk directly to a valve and open it
      // only if we haven't opened it
      // and it actually has flow
      for(String s : openable)
      {
        if (!opened.contains(s))
        {
          int walk_cost = getCostMap(loc).get(s);
          int total_time = walk_cost + 1;
          if (time + total_time < 30)
          {
            int rem_time = 30 - (time + total_time);
            int r_add = map.get(s).flow_rate * rem_time;
            TreeSet<String> o2 = new TreeSet<>();
            o2.addAll(opened);
            o2.add(s);
             
            int v = r_add + rec(phase, time + total_time, s, o2);

            best = Math.max(best, v);
          }

        }

      }
    }
   
    mm.put(memo, best);
    return best;

  }

  public class Valve
  {
    String id;
    TreeSet<String> tunnels=new TreeSet<>();
    int flow_rate;

    public Valve(String line)
    {
      id = Tok.en(line, " ").get(1);
      flow_rate = Tok.ent(line, "=;").get(0);

      List<String> lst = Tok.en(line.replace(",",""), " ");

      for(int i=9; i<lst.size();i++)
      {
        tunnels.add(lst.get(i));
      }

      //System.out.println("" + id + " " + flow_rate + " " + tunnels);
    }

  }

  public class SS extends State
  {
    int time;
    int released;
    String loc;
    TreeSet<String> opened=new TreeSet<>();

    public SS(int time, int released, String loc, Set<String> opened)
    {
      this.time = time;
      this.released = released;
      this.loc = loc;
      this.opened.addAll(opened);

    }

    public double getCost()
    {
      return time * 1.0 - (released / 10000000.0) ;
    }

    public boolean isTerm()
    {
      return (time==30);
    }

    public String toString()
    {
      return "" + time + " " + loc + " " + opened;
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      if (time < 30)
      {
        // chill
        {
          lst.add(new SS(time+1, released, loc, opened));
        }
      
        // walk
        for(String path : map.get(loc).tunnels)
        {
          lst.add(new SS(time+1, released, path, opened));
        }

        // open
        if (!opened.contains(loc))
        if (map.get(loc).flow_rate > 0)
        {
          int time_rem = 30 - (time + 1);
          int r_new = time_rem * map.get(loc).flow_rate;
          int r = released + r_new;
          TreeSet<String> o2 = new TreeSet<>();
          o2.addAll(opened);
          o2.add(loc);
          lst.add(new SS(time+1, r, loc, o2));
          
        }

      }


      return lst;


    }





  }
  public class SS2 extends State
  {
    int time;
    int released;
    String loc1;
    String loc2;
    TreeSet<String> opened=new TreeSet<>();
    int move;

    public SS2(int time, int released, String loc1, String loc2, Set<String> opened, int move)
    {
      this.time = time;
      this.released = released;
      this.loc1 = loc1;
      this.loc2 = loc2;
      this.opened.addAll(opened);
      this.move = move;

    }

    public double getCost()
    {
     // return time * 1.0 - (released / 10000000.0);
      return move* 1.0 - (released / 1e6);
    }

    public boolean isTerm()
    {
      return (time==30);
    }

    public String toString()
    {
      LinkedList<String> loc = new LinkedList<>();
      loc.add(loc1);
      loc.add(loc2);
      Collections.sort(loc);
      int m = move%2;
      
      return time + " " + loc1 + " " + loc2 + " " + opened + " " + move;
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      if (time < 30)
      {
        // chill
        {
          if (move % 2 == 0)
          {
            lst.add(new SS2(time, released, loc1, loc2, opened, move+1));
          }
          else
          {
            lst.add(new SS2(time+1, released, loc1, loc2, opened, move+1));
          }
        }
    
        if (opened.size() < openable.size())
        {
          // walk
          if (move % 2 == 0)
          {
          for(String path : map.get(loc1).tunnels)
          {
            lst.add(new SS2(time, released, path, loc2, opened, move+1));
          }
          }
          else
          {
            for(String path : map.get(loc2).tunnels)
            {
              lst.add(new SS2(time+1, released, loc1, path, opened, move+1));
            }

          }

          // open
          if (move % 2 == 0)
          {
            if (!opened.contains(loc1))
            if (map.get(loc1).flow_rate > 0)
            {
              int time_rem = 30 - (time + 1);
              int r_new = time_rem * map.get(loc1).flow_rate;
              int r = released + r_new;
              TreeSet<String> o2 = new TreeSet<>();
              o2.addAll(opened);
              o2.add(loc1);
              lst.add(new SS2(time, r, loc1, loc2, o2, move+1));
              
            }
          }
          else
          {
            if (!opened.contains(loc2))
            if (map.get(loc2).flow_rate > 0)
            {
              int time_rem = 30 - (time + 1);
              int r_new = time_rem * map.get(loc2).flow_rate;
              int r = released + r_new;
              TreeSet<String> o2 = new TreeSet<>();
              o2.addAll(opened);
              o2.add(loc2);
              lst.add(new SS2(time+1, r, loc1, loc2, o2, move+1));
              
            }

          }
        }

      }


      return lst;


    }

  }


  TreeMap<String, TreeMap<String, Integer> > cost_map=new TreeMap<>();
  public TreeMap<String, Integer> getCostMap(String start)
  {
    if (cost_map.containsKey(start)) return cost_map.get(start);

    TreeMap<String, Integer> m = new TreeMap<>();
    m.put(start, 0);

    int added=1;
    while(added>0)
    {
      added=0;
      TreeSet<String> keys = new TreeSet<>();
      keys.addAll(m.keySet());
      for(String s : keys)
      {
        int c = m.get(s);
        Valve v = map.get(s);
        for(String t : v.tunnels)
        {
          if ((!m.containsKey(t)) || (m.get(t) > c+1))
          {
            added++;
            m.put(t, c+1);
          }

        }
      }

    }
    cost_map.put(start, m);
    return m;


  }
}
