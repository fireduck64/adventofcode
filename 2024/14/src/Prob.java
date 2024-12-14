import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();


    int x_max = 101;
    int y_max = 103;

    //int x_max = 11;
    //int y_max = 7;
  public class Robot
  {
    Point loc;
    Point vel;

    public Robot(String line)
    {
      List<Integer> lst = Tok.ent(line.replace("=", " ").replace(",", " "), " ");

      if (lst.size() != 4) E.er();
      loc = new Point(lst.get(0), lst.get(1));
      vel = new Point(lst.get(2), lst.get(3));

    }
    public void sim()
    {
      long x = loc.x + vel.x;
      long y = loc.y + vel.y;

      while (x >= x_max) 
      {
        x-=x_max;
      }
      while(x < 0)
      {
        x+=x_max;
      }
      while(y >= y_max)
      {
        y-=y_max;
      }
      while(y < 0)
      {
        y+=y_max;
      }
      loc = new Point(x,y);

    }

  }

  ArrayList<Robot> robots;

  public void readBots(Scanner scan)
  {
    robots= new ArrayList<>();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      Robot r = new Robot(line);
      robots.add(r);
    }
  }

  public String Part1(Scanner scan)
    throws Exception
  {
    readBots(scan);

    for(int t=0; t<100; t++)
    {
      for(Robot r : robots)
      {
        r.sim();
      }
    }

    Map2D<Integer> map = new Map2D<Integer>(0);

    for(Robot r : robots)
    {
      map.set( r.loc, map.get(r.loc) + 1);
    }
    long sum = 1;

    //map.copyRange(0,0,x_max/2-1,y_max/2-1).print();
    //map.copyRange(0,y_max/2+1,x_max/2-1,y_max).print();
    //map.copyRange(x_max/2+1,y_max/2+1,x_max,y_max).print();
    //map.copyRange(x_max/2+1,0,x_max,y_max/2-1).print();

    sum *= getCount(map.copyRange(0,0,x_max/2-1,y_max/2-1));
    sum *= getCount(map.copyRange(0,y_max/2+1,x_max/2-1,y_max));
    sum *= getCount(map.copyRange(x_max/2+1,y_max/2+1,x_max,y_max));
    sum *= getCount(map.copyRange(x_max/2+1,0,x_max,y_max/2-1));

		return "" + sum;
  }

  public long getCount(Map2D<Integer> m )
  {
    int sum = 0;
    for(Point p : m.getAllPoints())
    {
      sum += m.get(p);
    }
    return sum;

  }

  public String Part2(Scanner scan)
    throws Exception
  {
    readBots(scan);

    long t = 0;
    while(true)
    {
      for(Robot r : robots)
      {
        r.sim();
      }
      t++;
      Map2D<Integer> map = new Map2D<Integer>(0);

      for(Robot r : robots)
      {
        map.set( r.loc, map.get(r.loc) + 1);
      }

      for(Point p : map.getAllPoints())
      {
        flood_count=0;
        // if there are 50 connected maybe it is a tree or something.
        // I don't know.
        Search.search(new FF(p, map));
        if (flood_count > 50)
        {
          map.print();
          System.out.println("Time: " + t);
          System.out.println("count: " + flood_count);
          return "";
          
        }
      }
    }

 
  }
  
  int flood_count;

  public class FF extends Flood
  {
    Point loc;
    Map2D<Integer> m;
    public FF(Point loc, Map2D<Integer> m)
    {
      this.loc = loc;
      this.m = m;

    }
    public String toString()
    {
      return loc.toString();
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (m.get(loc) == 0) return lst;

      flood_count++;

      for(Point p : m.getAdj(loc, true))
      {
        if (m.get(p) > 0)
        {
          lst.add(new FF(p, m));
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
