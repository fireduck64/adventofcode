import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map=null;
  LinkedList<String> fins=new LinkedList<>();

  public String Part1(Scanner scan)
    throws Exception
  {
  
    map = new Map2D<Character>(' ');
    MapLoad.loadMap(map, scan);

    int sum = 0;
    for(Point p : map.getAllPoints('0'))
    {
      sum += getScore(p);
    }

		return "" + sum;
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
      
      if (map.get(loc)=='9')
      {
        fins.add(this.toString());
      }
      LinkedList<State> lst = new LinkedList<>();
      
      for(Point n : map.getAdj(loc, false))
      {
        if (map.get(loc) + 1 == map.get(n))
        {
          lst.add(new FF(n));
        }
      }
      return lst;
    }

    public boolean isTerm()
    {
      //if (map.get(loc)=='9') return true;
      return false;
    }

  }
  public class FF2 extends Flood
  {
    Point loc;
    String path;
    public FF2(Point loc, String path)
    {
      this.loc = loc;
      this.path = path;
      this.path += "/" + loc;
    }
    public String toString()
    {
      return "" + loc + "/" + path;
    }
    public List<State> next()
    {
      
      if (map.get(loc)=='9')
      {
        fins.add(this.toString());
      }
      LinkedList<State> lst = new LinkedList<>();
      
      for(Point n : map.getAdj(loc, false))
      {
        if (map.get(loc) + 1 == map.get(n))
        {
          lst.add(new FF2(n,path));
        }
      }
      return lst;
    }

    public boolean isTerm()
    {
      //if (map.get(loc)=='9') return true;
      return false;
    }

  }


  public int getScore2(Point start)
  {
    fins.clear();

    Search.search(new FF2(start,""));

    return fins.size();

  }
  public int getScore(Point start)
  {
    fins.clear();

    Search.search(new FF(start));

    return fins.size();

  }

  public String Part2(Scanner scan)
    throws Exception
  {
  
    map = new Map2D<Character>(' ');
    MapLoad.loadMap(map, scan);

    int sum = 0;
    for(Point p : map.getAllPoints('0'))
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
