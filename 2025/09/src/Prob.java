import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    TreeSet<Point> input = new TreeSet<>();
    while(scan.hasNextLine())
    {
      List<Long> lst = Tok.enl(scan.nextLine(), ",");

      input.add(new Point(lst.get(0), lst.get(1)));
    }
    long p1 = 0;
    for(Point a : input)
    for(Point b : input)
    {
      long dx = Math.abs(a.x - b.x) + 1;
      long dy = Math.abs(a.y - b.y) + 1;
      long area = dx * dy;
      p1 = Math.max(area, p1);
    }
		return "" + p1;
  }
  Map2D<Character> map;
  TreeSet<Long> all_x;
  TreeSet<Long> all_y;

  public String Part2(Scanner scan)
    throws Exception
  {
    ArrayList<Point> input = new ArrayList<>();
    while(scan.hasNextLine())
    {
      List<Long> lst = Tok.enl(scan.nextLine(), ",");

      input.add(new Point(lst.get(0), lst.get(1)));
    }
    point_memo.clear();

    map = new Map2D<>('.');
    all_x = new TreeSet<Long>();
    all_y = new TreeSet<Long>();
    for(Point p : input)
    {
      all_x.add(p.x);
      all_y.add(p.y);
    }
    System.out.println("Sizes: " + all_x.size() + " " + all_y.size());
    //System.out.println("X: " + all_x);
    //System.out.println("Y: " + all_y);

    for(int i=0; i<input.size(); i++)
    {
      int j = i +1;
      j = j % input.size();
      Point a = input.get(i);
      Point b = input.get(j);

      if (a.x != b.x)
      if (b.y != a.y)
      {
        E.er();
      }
      Point dir = null;
      if (b.x > a.x) dir = new Point(1,0);
      if (b.x < a.x) dir = new Point(-1,0);
      if (b.y > a.y) dir = new Point(0,1);
      if (b.y < a.y) dir = new Point(0,-1);
      Point cur = a;
      while(!cur.equals(b))
      {
        cur = cur.add(dir);
        map.set(cur, 'X');
      }
      map.set(a, '#');
      map.set(b, '#');
    }
    long p2 = 0;
    for(Point a : input)
    for(Point b : input)
    if (a.x < b.x)
    //if (a.y < b.y)
    {
      
      long dx = Math.abs(a.x - b.x) + 1;
      long dy = Math.abs(a.y - b.y) + 1;
      long area = dx * dy;

      long low_x = Math.min(a.x, b.x);
      long low_y = Math.min(a.y, b.y);
      long high_x = Math.max(a.x, b.x);
      long high_y = Math.max(a.y, b.y);

      //Point mid = new Point((low_x + high_x) / 2, (low_y + high_y) / 2);


      Point low = new Point(low_x, low_y);
      Point high = new Point(high_x, high_y);
      //if (!low.equals(a)) E.er();
      //if (!high.equals(b)) E.er();

      if (area > p2)
      {

        TreeSet<Point> corners = getAllTestPoints(low, high);

        /*new TreeSet<Point>();
        corners.add(new Point(low_x+1, low_y+1));
        corners.add(new Point(low_x+1, high_y-1));
        corners.add(new Point(high_x-1, low_y+1));
        corners.add(new Point(high_x-1, high_y-1));*/

        boolean ok=true;
        for(Point p : corners)
        {
          //if (Search.search(new SS(0, p))!= null)
          if (!isPointInside(p))
          {
            ok=false;
            break;
          } 
        }
        if (ok)
        {
          p2 = Math.max(area, p2);
          System.out.println("better " + p2 + " - " + point_memo.size());
        }
      }

    }

 
		return "final " + p2;
  }

  public TreeSet<Point> getAllTestPoints(Point low, Point high)
  {
    TreeSet<Point> set = new TreeSet<>();

    for(long x : all_x.subSet(low.x, high.x))
    for(long y : all_y.subSet(low.y, high.y))
    {
      set.add(new Point(x+1, y+1));
    }
    //System.out.println(set);

    return set;

  }

  HashMap<Point, Boolean> point_memo=new HashMap<>(8192, 0.5f);

  public boolean isPointInside(Point start)
  {
    if (point_memo.containsKey(start))
    {
      return point_memo.get(start);
    }
    Point cur = start;
    Point dir = new Point(0, -1);
    int crosses=0;
    while(cur.y > 0)
    {
      char z = map.get(cur);
      if (z == '#') E.er("" + cur);
      if (z != '.') crosses++;
      cur = cur.add(dir);
    }
    boolean inside = (crosses % 2 == 1);

    point_memo.put(start, inside);
    return inside;

  }

  public class SS extends State
  {
    Point loc;
    int cost;
    public SS(int cost, Point loc)
    {
      this.cost = cost;
      this.loc = loc;

    }
    public boolean isTerm()
    {
      return (loc.equals(new Point(0,0)));
    }
    public double getCost()
    {
      return cost;
    }
    public String toString()
    {
      return loc.toString();
    }

    public List<State> next()
    {

      LinkedList<State> lst = new LinkedList<>();
      if (loc.x < 0) return lst;
      if (loc.y < 0) return lst;
      if (loc.x > map.high_x) return lst;
      if (loc.y > map.high_y) return lst;
      if (map.get(loc) != '.') return lst;

      for(Point p : map.getAdj(loc, false))
      {
        if (map.get(p) == '.')
        {
          lst.add(new SS(cost+1, p));
        }
      }
      return lst;
    }
    @Override
    public double getEstimate()
    {
      return loc.getDistM(new Point(0,0));
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

}
