import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map;
  Map2D<Boolean> marked;
  TreeMap<Point, Map2D<Character>> fence_zone;
  public String Part1(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>(' ');
    marked = new Map2D<Boolean>(false);

    MapLoad.loadMap(map, scan);

    long sum = 0;
    for(Point p : map.getAllPoints())
    {
      sum += getScore(p);
    }

		return "" + sum;
  }

  long getScore(Point p)
  {
    if (marked.get(p)) return 0L;

    area =0;
    fence=0;
    fence_zone = new TreeMap<>();
    Search.search(new FF(p));

    return area*fence;

  }
  long getScore2(Point p)
  {
    if (marked.get(p)) return 0L;

    area =0;
    fence=0;
    fence_zone = new TreeMap<>();
    Search.search(new FF(p));

    int sides = 0;
    for(Map2D<Character> fmap : fence_zone.values())
    {
      //fmap.print();
      for(Point q : fmap.getAllPoints())
      {
        if (fmap.get(q) == '#')
        {
          sides++;
          Search.search(new FF2(q, fmap));
        }

      }

    }

    

    return area*sides;

  }


  long area=0;
  long fence=0;

  public class FF2 extends Flood
  {
    Point loc;
    Map2D<Character> fmap;

    public FF2(Point loc, Map2D<Character> fmap)
    {
      this.loc = loc;
      this.fmap = fmap;
    }
    public String toString()
    {
      return "" + loc;
    }

    public List<State> next()
    {
      char v = fmap.get(loc);
      if (v != '#') E.er("" + v);

      fmap.set(loc, ' ');
      List<State> lst = new LinkedList<>();

      for(Point n : fmap.getAdj(loc, false))
      {
        if (fmap.get(n) == v)
        {
          lst.add(new FF2(n, fmap));
        }
      }
      return lst;

    }



  }

  public class FF extends Flood
  {
    Point loc;
    public FF(Point loc)
    {
      this.loc = loc;
    }
    public String toString()
    {
      return "" + loc;
    }

    public List<State> next()
    {
      char v = map.get(loc);
      marked.set(loc, true);
      area++;
      List<State> lst = new LinkedList<>();

      for(Point n : map.getAdj(loc, false))
      {
        if (map.get(n) == v)
        {
          lst.add(new FF(n));
        }
        else
        {
          Point dir = loc.add(n.mult(-1));
          if (!fence_zone.containsKey(dir))
          {
            fence_zone.put(dir, new Map2D<Character>(' '));
          }
          fence_zone.get(dir).set(n, '#');
          fence++;
        }
      }
      return lst;

    }

  }

  public String Part2(Scanner scan)
    throws Exception
  {
	  map = new Map2D<Character>(' ');
    marked = new Map2D<Boolean>(false);

    MapLoad.loadMap(map, scan);

    long sum = 0;
    for(Point p : map.getAllPoints())
    {
      sum += getScore2(p);
    }

		return "" + sum;
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
