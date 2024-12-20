import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map;
  Map2D<Integer> end_cost_map;
  Map2D<Integer> start_cost_map;
  int max_cost=100000;
  int target=100;

  public String Part1(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>('#');
    MapLoad.loadMap(map, scan);
    Point start = map.getAllPoints('S').get(0);

    max_cost = 1000000;
    int no_cost=0;
    {
      Map<String, State> nocheat_map = Search.search(new SS(start, 0, 0, null));
      System.out.println("" + nocheat_map.size());
      for(State s : nocheat_map.values())
      {
        SS fin = (SS) s;
        no_cost = fin.cost;

      }
    }

    max_cost = no_cost - target;

    System.out.println("No cheat cost: " + no_cost);
    System.out.println("Max cost: " + max_cost);

    Map<String, State> cheat_map = Search.search(new SS(start, 0, 1, null));

    int g=0;
    for(State s : cheat_map.values())
    {
      SS fin = (SS) s;
      int save = no_cost - fin.cost;
      if (save >= target)
      {
        g++;

      }

    }

		return "P1 Over target: " + g;
  }

  public class SS extends State
  {
    int cheat_count=0;
    Point loc;
    int cost;
    String cheat_pos;
    public SS(Point loc, int cost, int cheat_count, String cheat_pos)
    {
      this.loc = loc;
      this.cost = cost;
      this.cheat_count = cheat_count;
      this.cheat_pos = cheat_pos;
    }

    public double getCost()
    {
      return cost;
    }
    public String toString()
    {
      return "" + loc + "/" + cheat_count + "/" + cheat_pos;
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (cost > max_cost) return lst;

      for(Point n : map.getAdj(loc, false))
      {
        char z = map.get(n);
        if ((z == 'E') || (z == '.'))
        {
          lst.add(new SS(n, cost+1, cheat_count, cheat_pos));
        }
        if ((z == '#') && (cheat_count > 0))
        {
          String pos = "" + loc + "/" + n;
          lst.add(new SS(n, cost+1, cheat_count-1, pos));
        }
      }
      return lst;

    }
    public boolean isTerm()
    {
      if (map.get(loc) == 'E') return true;
      return false;
    }

  }
  public class SS2 extends State
  {
    Point loc;
    int cost;
    int cheat_rem;
    String cheat_start;
    String cheat_end;
    public SS2(Point loc, int cost, int cheat_rem, String cheat_start, String cheat_end)
    {
      this.loc = loc;
      this.cost = cost;
      this.cheat_rem = cheat_rem;
      this.cheat_start = cheat_start;
      this.cheat_end = cheat_end;
    }

    public double getCost()
    {
      return cost;
    }
    public String toString()
    {
      return "" + loc + "/" + cheat_start + "/" + cheat_end;
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (cost >= max_cost) return lst;

      for(Point n : map.getAdj(loc, false))
      {
        char z = map.get(n);

        boolean in_cheat = (cheat_start != null) && (cheat_end == null);

        if ((z == 'E') || (z == '.') || (z=='S'))
        {
          // normal step

          if (in_cheat)
          {
            // step in cheat
            if (cheat_rem > 0)
            if (z != 'E')
            { 
              lst.add(new SS2(n, cost+1, cheat_rem-1, cheat_start, cheat_end));
            }

            // end cheat
            //if (map.get(loc) == '#')
            {
              String end = "" + n;
              lst.add(new SS2(n, cost+1, 0, cheat_start, end));
            }

          }
          else
          {
            lst.add(new SS2(n, cost+1, cheat_rem, cheat_start, cheat_end));
          }
        }
        if (z == '#')
        {
          if (cheat_rem > 0)
          {
            // start cheat
            if (cheat_start == null)
            {
              String start = "" + loc;
              lst.add(new SS2(n, cost+1, cheat_rem-1, start, null));
            }
            else
            {
              // continue in cheat
              lst.add(new SS2(n, cost+1, cheat_rem-1, cheat_start, null));
            }
          }
        }
      }
      return lst;

    }
    public boolean isTerm()
    {
      if (map.get(loc) == 'E') return true;
      return false;
    }

  }


  public String Part2(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>('#');
    MapLoad.loadMap(map, scan);
    Point start = map.getAllPoints('S').get(0);
    Point end = map.getAllPoints('E').get(0);

    start_cost_map = new Map2D<Integer>(1000000);
    end_cost_map = new Map2D<Integer>(1000000);
    Search.search(new FF(end, 0, end_cost_map));
    Search.search(new FF(start, 0, start_cost_map));

    int no_cost=end_cost_map.get(start);

    System.out.println("No cheat cost: " + no_cost);

    HashSet<String> opts=new HashSet<>();

    for(Point a : map.getAllPoints())
    {
      char aa = map.get(a);
      if ((aa=='.') || (aa=='S'))
      {
        for(Point b : map.getAllPoints())
        {
          char bb = map.get(b);
          if ((bb == '.') || (bb == 'E'))
          if (a.getDistM(b) <= 20)
          {
            int d = (int) a.getDistM(b);
            int cost = start_cost_map.get(a) + d + end_cost_map.get(b);
            int save = no_cost - cost;
            if (save >= target)
            {
              //System.out.println("Save: " + save);
              opts.add("" + a +"/" + b);
            }

          }

        }

      }

    }
    for(String s: opts)
    {
       //System.out.println(s);
    }

    return "" + opts.size();

  }

  

  public class FF extends Flood
  {
    Point loc;
    int cost;
    Map2D<Integer> cost_map;
    public FF(Point loc, int cost, Map2D<Integer> cost_map)
    {
      this.loc = loc;
      this.cost = cost;
      this.cost_map = cost_map;

    }
    public String toString(){return "" + loc; }
    public double getCost(){return cost; }

    public List<State> next()
    {
      cost_map.set(loc, cost);
      List<State> lst = new LinkedList<>();

      //if (map.get(loc) != '#')
      {
        for(Point n : map.getAdj(loc,false))
        {
          char z = map.get(n);
          if ((z == '.') || (z == 'S') || (z =='E'))
          {
            lst.add(new FF(n, cost+1, cost_map));
          }
        }
      }

      return lst;

    }

  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    /*if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }*/
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
