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

  Map2D<Character> input = new Map2D<Character>(' ');

  TreeSet<Point> choke_point = new TreeSet<>();
  TreeMap<Integer, Point> choke_map = new TreeMap<>();
  TreeSet<Point> dead_ends = new TreeSet<>();

  public Prob(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(input, scan);

    TreeSet<Point> check_points = new TreeSet<>();
    {
      HashSet<Point> visited=new HashSet<>();
      visited.add(new Point(1,0));

      long p1 = getSteps(
        new Point(1,0), 
        visited
      );
      p1--;
      System.out.println("P1: " );
      System.out.println(p1);
    }

    for(Point p : input.getAllPoints())
    {
      if (input.get(p)=='v') 
      {
        check_points.add(p);
        input.set(p, '.');
      }

      if (input.get(p)=='>')
      {
        check_points.add(p);
        input.set(p, '.');
      }
      if (input.get(p)=='.')
      {
        check_points.add(p);
      }
    }

    findVerts();

    //input.print();

    /*for(Point p : check_points)
    {
      if (checkChoke(p))
      {
        System.out.println("Choke point: " + p);
        choke_point.add(p);

        DistS ss = (DistS) Search.search(new DistS(new Point(1,0), input, p, 0));
        int cost = ss.cost;
        System.out.println("  " + cost + " " + p);
        choke_map.put(cost, p);

      }

    }
    System.out.println("Total choke points: " + choke_point.size());
    */
   
    /*{
      SS p1 = (SS) Search.searchPara(new SS(new Point(1,0), ImmutableSet.of(new Point(1,0))));
      System.out.println("Search: " );
      System.out.println(best.visited.size()-1);
    }*/

    
    /*{
      ArrayList<Point> chokes=new ArrayList<>();
      chokes.addAll(choke_map.values());
      long p1=0;

      HashSet<Point> visited=new HashSet<>();
      for(int i=0; i<chokes.size() - 1; i++)
      {
        Point a = chokes.get(i);
        Point b = chokes.get(i+1);
        visited.add(a);

        long step = getSteps(a, visited, b);
        System.out.println("From " + a + " to " + b + " steps: " + step);
        p1 += step;

      }
      System.out.println("Choke list: " );
      System.out.println(p1);
    }*/

    { // recursive with verticies
      HashSet<Point> visited=new HashSet<>();
      visited.add(new Point(1,0));

      long p1 = getStepsV(
        new Point(1,0), 
        visited
      );
      p1--;
      System.out.println("P2 v: " );
      System.out.println(p1);
    }

    /*{ // regular recursive
      HashSet<Point> visited=new HashSet<>();
      visited.add(new Point(1,0));

      long p1 = getSteps(
        new Point(1,0), 
        visited
      );
      p1--;
      System.out.println("P2: " );
      System.out.println(p1);
    }*/
  }

  TreeMap<Point, Long> best_map = new TreeMap<>();


  public boolean checkChoke(Point p)
  {
    Map2D<Character> map = input.copy();
    map.set(p, '#');

    Map2D<Character> reach = new Map2D<Character>(' ');

    Search.search(new F(new Point(1,0), map, reach));

    if (reach.high_y != input.high_y) return true;

    { // not a choke by maybe a dead end?
      for(Point n : input.getAdj(p, false))
      {
        if (input.get(n)=='.')
        if (reach.get(n)!='X')
        {
          System.out.println("Dead end: " + p);
          dead_ends.add(p);

        }

      }

    }

    return false;

  }

  public class DistS extends State
  {
    Point loc;
    Map2D<Character> map;
    Point dest;
    int cost;
    public DistS(Point loc, Map2D<Character> map, Point dest, int cost)
    {
      this.loc = loc;
      this.map = map;
      this.dest = dest;
      this.cost = cost;

    }

    public double getCost(){return cost;}
    public String toString(){return "" + loc; }

    public boolean isTerm()
    {
      if (loc.equals(dest)) return true;
      return false;
    }

    public List<State> next()
    {
      char z = map.get(loc);

      LinkedList<State> lst = new LinkedList<>();

      TreeSet<Point> n_lst = new TreeSet<>();
      if (z == '>')
      {
        n_lst.add( loc.add(Nav.E));
      }
      if (z == 'v')
      {
        n_lst.add( loc.add(Nav.S) );
      }
      if (z == '.')
      {
        n_lst.addAll( map.getAdj(loc, false) );
      }

      for(Point n : n_lst)
      {
        char nz = map.get(n);
        if ((nz == '.') || (nz == '>') || (nz == 'v'))
        {
          lst.add(new DistS(n, map, dest, cost+1));
        }
      }
      return lst;
    }

  }


  public class F extends Flood
  {
    Point loc;
    Map2D<Character> reach;
    Map2D<Character> map;
    public F(Point loc, Map2D<Character> map, Map2D<Character> reach)
    {
      this.loc = loc;
      this.reach = reach;
      this.map = map;

    }
    public String toString(){return "" + loc; }

    @Override
    public boolean isTerm()
    {
      /*if (loc.y == input.high_y) 
      {
        reach.set(loc, 'X');
        return true;
      }*/
      return false;
    }

    public List<State> next()
    {
      reach.set(loc, 'X');
      char z = map.get(loc);

      LinkedList<State> lst = new LinkedList<>();

      TreeSet<Point> n_lst = new TreeSet<>();
      if (z == '>')
      {
        n_lst.add( loc.add(Nav.E));
      }
      if (z == 'v')
      {
        n_lst.add( loc.add(Nav.S) );
      }
      if (z == '.')
      {
        n_lst.addAll( map.getAdj(loc, false) );
      }

      for(Point n : n_lst)
      {
        char nz = map.get(n);
        if ((nz == '.') || (nz == '>') || (nz == 'v'))
        {
          lst.add(new F(n, map, reach));
        }
      }
      return lst;

     


    }

  }

  public long getSteps(Point loc, Set<Point> visited, Point dest)
  {
    
    if (loc.equals(dest)) return 0L;

    SS ss = new SS(loc, visited);
    long best = 0;
    boolean sol=false;
    
    for(Point p : ss.nextP())
    {
      if (!dead_ends.contains(p))
      if (!visited.contains(p))
      {
        visited.add(p);
        long v = getSteps(p, visited, dest);
        visited.remove(p);
        if (v >= 0)
        {
          sol=true;
          best = Math.max(best, v);
        }
      }
    }
    if (sol) return 1L+best;

    return -1L;

  }

  


  public HashMap<String, Long> stepv_memo=new HashMap<>();

  public long getStepsV(Point loc, Set<Point> visited)
  {
     
    if (loc.y == input.high_y) return 1L;
    String key = HUtil.getHash("" + loc + "/" + visited.toString());
    if (stepv_memo.containsKey(key))
    {
      return stepv_memo.get(key);
    }


    long best = 0;
    boolean sol=false;

    for(Map.Entry<Point, Integer> me : vert_edge.get(loc).entrySet())
    {
      Point p = me.getKey();
      
      if (!visited.contains(p))
      {
        visited.add(p);
        long v = getStepsV(p, visited);
        visited.remove(p);
        if (v >= 0)
        {
          sol=true;
          best = Math.max(best, v + me.getValue());
        }
      }

    }
    long ans = -1L;
    if (sol) return ans=best;

    //stepv_memo.put(key, ans);
    return ans;


  }




  public long getSteps(Point loc, Set<Point> visited)
  {
    
    char z = input.get(loc);
    if ((z == '>') || (z == 'v') || (choke_point.contains(loc)))
    {
      long sz = visited.size();
      if (best_map.containsKey(loc))
      {
        if (best_map.get(loc) >= sz) return -1L;
      }
      else
      {
        best_map.put(loc, sz);
      }
    }
    if (loc.y == input.high_y) return 1L;

    SS ss = new SS(loc, visited);
    long best = 0;
    boolean sol=false;
    
    for(Point p : ss.nextP())
    {
      if (!visited.contains(p))
      {
        visited.add(p);
        long v = getSteps(p, visited);
        visited.remove(p);
        if (v >= 0)
        {
          sol=true;
          best = Math.max(best, v);
        }
      }

    }
    if (sol) return 1L+best;

    return -1L;

  }

  SS best = null;

  public class SS extends State
  {
    final Point loc;
    final Set<Point> visited;
    public SS(Point loc, Set<Point> visited)
    {
      this.loc = loc;
      this.visited = visited;

    }

    public boolean isTerm()
    {
      if (loc.y == input.high_y)
      {
        if( best == null) best = this;
        if (best.visited.size() < visited.size()) best=this;

      }
      //return loc.y == input.high_y;

      return false;

    }
    public String toString()
    {
      /*char z = input.get(loc);
      if ((z == '>') || (z == 'v'))
      {
        return loc.toString();
      }*/

      return loc + "/" + visited;
    }

    public double getCost()
    {
      return 0.0;
      //return 1e6 - visited.size();
    }

    public List<Point> nextP()
    {
      char z = input.get(loc);

      LinkedList<Point> lst = new LinkedList<>();

      TreeSet<Point> n_lst = new TreeSet<>();
      if (z == '>')
      {
        n_lst.add( loc.add(Nav.E));
      }
      if (z == 'v')
      {
        n_lst.add( loc.add(Nav.S) );
      }
      if (z == '.')
      {
        n_lst.addAll( input.getAdj(loc, false) );
      }

      for(Point n : n_lst)
      {
        char nz = input.get(n);
        if (!visited.contains(n))
        if ((nz == '.') || (nz == '>') || (nz == 'v'))
        {
          lst.add(n);
        }
      }
      return lst;

    }

    public List<State> next()
    {
      char z = input.get(loc);

      LinkedList<State> lst = new LinkedList<>();

      TreeSet<Point> n_lst = new TreeSet<>();
      if (z == '>')
      {
        n_lst.add( loc.add(Nav.E));
      }
      if (z == 'v')
      {
        n_lst.add( loc.add(Nav.S) );
      }
      if (z == '.')
      {
        n_lst.addAll( input.getAdj(loc, false) );
      }

      for(Point n : n_lst)
      {
        char nz = input.get(n);
        if (!visited.contains(n))
        if ((nz == '.') || (nz == '>') || (nz == 'v'))
        {
          TreeSet<Point> nv = new TreeSet<>();
          nv.addAll(visited);
          nv.add(n);
          lst.add(new SS(n, nv));

        }
      }

      return lst;



    }


  }


  TreeSet<Point> verts=new TreeSet<>();
  TreeMap<Point, TreeMap<Point, Integer> > vert_edge=new TreeMap<>();

  public void findVerts()
  {
    for(Point p : input.getAllPoints())
    {
      int opt_count = 0;

      char z = input.get(p);
      if ((z == '.') || (z == '>') || (z=='v'))
      {

        for(Point n : input.getAdj(p, false))
        {

          if (input.get(n) == ' ') opt_count+=2; // empty space
          if (input.get(n) == '.') opt_count++;
          if (input.get(n) == 'v') opt_count++;
          if (input.get(n) == '>') opt_count++;
        }
        if (opt_count>=3) verts.add(p);
      }
    }

    for(Point a : verts)
    {
      vert_edge.put(a, new TreeMap<Point, Integer>());
      Search.search(new VertFlood(a, a, 0));
      //System.out.println("Vert: " + a + " " + vert_edge.get(a));
    }
    System.out.println("Total verticies: " + verts.size());
  }

  public class VertFlood extends State
  {
    Point src;
    Point loc;
    int cost;

    public VertFlood(Point src, Point loc, int cost)
    {
      this.src = src;
      this.loc = loc;
      this.cost = cost;

    }

    public double getCost(){return cost; }
    public boolean isTerm()
    {
      return false;
    }
    public String toString(){return "" + loc;}

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (cost > 0)
      if (verts.contains(loc))
      {
        vert_edge.get(src).put(loc, cost);
        return lst;
      }

      for(Point n : input.getAdj(loc, false))
      {
        char z = input.get(n);
        if ((z == '.') || (z == '>') || (z=='v'))
        {
          lst.add(new VertFlood(src, n, cost+1));
        }
      }
      return lst;
    }

  }

}
