import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> pad = new Map2D<Character>('.');
  Map2D<Character> robo = new Map2D<Character>('.');

  public static final double allow_skew=0.0;

  public String Part1(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(pad, new Scanner(new FileInputStream("pad")));
    MapLoad.loadMap(robo, new Scanner(new FileInputStream("robot")));
    long sum = 0;

    for(String line : In.lines(scan))
    {
      cost_len_map = new HashMap<>();

      target_code=line;
      SS fin = (SS) (Search.search(new SS()).get(0));

      System.out.println(fin.input);
      int len = fin.input.length();
      int n = Integer.parseInt(line.substring(0,3));
      System.out.println(" " + len + " " + n);
      sum += len*n;

    }

		return "P1 Sol " + sum;
  }

  HashMap<Integer, Integer> cost_len_map;
  HashMap<String, Integer> state_cost_map;

  public String Part2(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(pad, new Scanner(new FileInputStream("pad")));
    MapLoad.loadMap(robo, new Scanner(new FileInputStream("robot")));
    long sum = 0;

    for(String line : In.lines(scan))
    {
      
      String path = "A" + line;
      p2r=26;
    
      long len = 0L;
      for(int i=0; i<path.length()-1; i++)
      {
        len += recRobotGetCost(p2r, path.charAt(i), path.charAt(i+1), pad, robo);
      }

      int n = Integer.parseInt(line.substring(0,3));
      System.out.println(" " + len + " " + n);
      sum += len*n;
      /*target_code=line;
      p2r=1;
      int steps=26;
      target_code = line;
      List<SS2> next_states=new LinkedList<SS2>();
      SS2 start_state = new SS2(new Keypad(pad));
      start_state.input = line;
      next_states.add(start_state);

      for(int i=0; i<steps; i++)
      {
        System.out.println("Pass " + i + " input size: " + next_states.size());
        //System.out.println("Searching for : " + target_code);
        LinkedList<State> fins = new LinkedList<>();
        for(SS2 ns : next_states)
        {
          Keypad k = new Keypad(pad);
          if (i > 0) k = new Keypad(robo);
          target_code = ns.input;
          cost_len_map = new HashMap<>();
          state_cost_map = new HashMap<>();
          fins.addAll(Search.search(new SS2(k)));
        }
        System.out.println("Solutions: " + fins.size());
        double min_cost = fins.get(0).getCost();
        for(State s : fins)
        {
          min_cost = Math.min(min_cost, s.getCost());
        }
        next_states.clear();
        for(State s : fins)
        {
          if (s.getCost() <= min_cost + allow_skew)
          {
            next_states.add( (SS2) s);
          }
        }
        System.out.println("Sol Best filter: " + fins.size());
       

      }

        // final filter
        double min_cost = next_states.get(0).getCost();
        SS2 fin = null;

        for(SS2 s : next_states)
        {
          min_cost = Math.min(min_cost, s.getCost());
        }
        for(SS2 s : next_states)
        {
          if (s.getCost() <= min_cost)
          {
            fin = s;
          }
        }
       
      System.out.println(fin.input);
      int len = fin.input.length();
      int n = Integer.parseInt(line.substring(0,3));
      System.out.println(" " + len + " " + n);
      sum += len*n;*/

    }

		return "P2 Sol " + sum;

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
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

  String target_code="";

  public class SS extends State
  {
    Keypad k;
    Robot r1;
    Robot r2;
    Robot r3;
    String input;

    public SS()
    {
      input="";
      k=new Keypad(pad);
      r1=new Robot(k);
      r2=new Robot(r1);
      r3=new Robot(r2);
    }

    public SS(SS old, char next)
    {
      input = old.input;
      input += next;

      k=old.k.copy();
      r1=old.r1.copy(k);
      r2=old.r2.copy(r1);
      r3=old.r3.copy(r2);
      r3.forcePress(next);

    }
    public String toString()
    {
      return "" + k.output + "/" + k.cursor + "/" + r1.cursor +"/" + r2.cursor;
    }


    public double getCost()
    {
      return input.length();
    }
    public boolean isTerm()
    {
      return k.output.equals(target_code);
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();


      if (!target_code.startsWith(k.output))
      {
        return lst;
      }
      if (pad.get(k.cursor) == '.') return lst;
      if (robo.get(r1.cursor) == '.') return lst;
      if (robo.get(r2.cursor) == '.') return lst;
      if (robo.get(r3.cursor) == '.') return lst;

      int cost = input.length();
      int match = k.output.length();
      if (cost_len_map.containsKey(cost))
      {
        if (cost_len_map.get(cost) > match) return lst; 
      }
      cost_len_map.put(cost, match);

      String in="<>v^A";
      for(char z : Str.stolist(in))
      {
        lst.add(new SS(this, z));
      }

      return lst;
      

    }

  }

  int p2r=3;

  public class SS2 extends State
  {
    Keypad k;

    ArrayList<Robot> robots;
    String input;

    public SS2(Keypad k)
    {
      input="";
      this.k=k;
      robots = new ArrayList<>();
      Thing last = k;
      for(int i=0; i<p2r; i++)
      {
        Robot r = new Robot(last);
        last = r;
        robots.add(r);
      }
    }

    public SS2(SS2 old, char next)
    {
      input = old.input;
      input += next;

      k=old.k.copy();
      Thing last = k;

      robots = new ArrayList<>();
      for(int i=0; i<p2r; i++)
      {
        Robot r = old.robots.get(i).copy(last);
        last = r;
        robots.add(r);
      }

      robots.get(p2r-1).forcePress(next);

    }
    public String toString()
    {
      return input;

      /*StringBuilder sb = new StringBuilder();

      int leno=12;
      if (input.length() < 12)
      {
        sb.append(input);
      }
      else
      {
        sb.append(input.substring(input.length()-12));
      }
      sb.append(input);

      sb.append("/");
      sb.append(k.output);
      sb.append("/");
      sb.append(k.cursor);
      for(Robot r : robots)
      {
        sb.append("/");
        sb.append(r.cursor);

      }
      return sb.toString();*/
    }

    public String getState()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("/");
      sb.append(k.output);
      sb.append("/");
      sb.append(k.cursor);
      for(Robot r : robots)
      {
        sb.append("/");
        sb.append(r.cursor);

      }
      return sb.toString();

    }


    public double getCost()
    {
      return input.length();
    }
    public boolean isTerm()
    {
      return k.output.equals(target_code);
    }

    boolean check()
    {

      if (!target_code.startsWith(k.output))
      {
        return false;
      }
      if (k.pad_map.get(k.cursor) == '.') return false;
      for(Robot r : robots)
      {
        if (robo.get(r.cursor) == '.') return false;
      }

      return true;


    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      int cost = input.length();
      int match = k.output.length();
      synchronized(cost_len_map)
      {
        if (cost_len_map.containsKey(cost))
        {
          if (cost_len_map.get(cost) > match) return lst; 
        }
        cost_len_map.put(cost, match);
      }
      synchronized(state_cost_map)
      {
        String state_str = getState();
        if (state_cost_map.containsKey(state_str))
        {
          if (state_cost_map.get(state_str) < cost) return lst;
          state_cost_map.put(state_str, Math.min(state_cost_map.get(state_str), cost) );
        }
        else
        {
          state_cost_map.put(state_str, cost);
        }

      }

      String in="<>v^A";
      for(char z : Str.stolist(in))
      {
        SS2 s2 = new SS2(this, z);
        if (s2.check())
        {
          lst.add(s2);
        }
      }

      return lst;
      

    }

  }

 


  public interface Thing
  {
    void move(Point d);
    void press();
  }

  public class Keypad implements Thing
  {
    Point cursor;
    String output;
    Map2D<Character> pad_map;

    public Keypad(Map2D<Character> pad_map)
    {
      this.pad_map = pad_map;
      cursor = pad_map.getAllPoints('A').get(0);
      output="";
    }

    public void move(Point d)
    {
      cursor = cursor.add(d);
    }
    public void press()
    {
      output += pad_map.get(cursor);
    }

    Keypad copy()
    {
      Keypad k2 = new Keypad(pad_map);
      k2.cursor = cursor;
      k2.output = output;

      return k2;

    }

  }

  public class Robot implements Thing
  {
    Point cursor;
    Thing under;

    public Robot(Thing under)
    {
      cursor = robo.getAllPoints('A').get(0);
      this.under =under;
    }

    public void move(Point d)
    {
      cursor = cursor.add(d);
    }

    public void forcePress(char z)
    {
      cursor = robo.getAllPoints(z).get(0);
      press();
    }
    public void press()
    {
      char z = robo.get(cursor);

      if (z == 'A')
      {
        under.press();
      }
      else
      {
        Point d = Nav.getDir(z);
        under.move(d);
      }
    }

    Robot copy(Thing new_under)
    {
      Robot r2 = new Robot(new_under);
      r2.cursor = cursor;

      return r2;
    }

  }

  // Get the cost of going from from to to

  HashMap<String, Long> cost_memo=new HashMap<>();
  long recRobotGetCost(int layers, char from, char to, Map2D<Character> mapo, Map2D<Character> next_map)
  {
    // top layer, just press the damn button
    if (layers==0) return 1;


    Point a= mapo.getAllPoints(from).get(0);
    Point b= mapo.getAllPoints(to).get(0);
    
    String key = "" + layers + "/" + from + " " + to + " " + a + " " + b;
    if (cost_memo.containsKey(key))
    {
      return cost_memo.get(key);

    }

    // Upper layer is going to move on this layer from a to b and then press b.
    // Assume upper layer is starting on 'A'

    long best_cost = 100000000000L;

    for(String path : getPaths(a,b,mapo))
    {
      long cost = 0;
      for(int i=0; i<path.length()-1; i++)
      {
        cost += recRobotGetCost(layers-1, path.charAt(i), path.charAt(i+1), next_map, next_map);
      }

      best_cost = Math.min(cost, best_cost);

    }
    cost_memo.put(key, best_cost);

    return best_cost;


  }

  public List<String> getPaths(Point a, Point b, Map2D<Character> mapo)
  {
    paths_found = new LinkedList<>();

    Search.search(new PathSearch("A", a, b, mapo));

    return paths_found;


  }

  LinkedList<String> paths_found;
  public class PathSearch extends Flood
  {
    String path;
    Point loc;
    Point dest;
    Map2D<Character> mapo;

    public PathSearch(String path, Point loc, Point dest, Map2D<Character> mapo)
    {
      this.path=path;
      this.loc = loc;
      this.mapo = mapo;
      this.dest = dest;

    }

    public String toString(){return path;}

    public List<State> next()
    {
      if (loc.equals(dest))
      {
        paths_found.add(path+"A");
      }

      LinkedList<State> lst = new LinkedList<>();

      for(Point n : mapo.getAdj(loc, false))
      {
        if (mapo.get(n) != '.')
        if (n.getDistM(dest) < loc.getDistM(dest))
        {
          char sym = Nav.getDirSym( n.add(loc.mult(-1)));
          lst.add(new PathSearch(path + sym, n, dest, mapo));
        }

      }
      return lst;

    }



  }





}
