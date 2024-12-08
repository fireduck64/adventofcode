import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map;

  public String Part1(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>(' ');

    MapLoad.loadMap(map, scan);

    TreeSet<Point> nodes = new TreeSet<>();
    for(char k : map.getCounts().keySet())
    {
      if (k != '.')
      {
        nodes.addAll(findNodes(k));
      }
    }
		return "" + nodes.size();
  }

  public String Part2(Scanner scan)
    throws Exception
  {
	  map = new Map2D<Character>(' ');

    MapLoad.loadMap(map, scan);

    TreeSet<Point> nodes = new TreeSet<>();
    for(char k : map.getCounts().keySet())
    {
      if (k != '.')
      {
        nodes.addAll(findNodes2(k));
      }
    }
		return "" + nodes.size();
  }

  TreeSet<Point> findNodes(char z)
  {
    TreeSet<Point> set = new TreeSet<>();

    ArrayList<Point> points = new ArrayList<>();
    points.addAll( map.getAllPoints(z));

    for(int i=0; i<points.size(); i++)
    for(int j=0; j<points.size(); j++)
    {
      if (i != j)
      {
        Point a = points.get(i);
        Point b = points.get(j);
        Point delta = a.add(b.mult(-1));

        Point c = a.add(delta);
        if (map.get(c) != ' ') set.add(c);
      }
    }

    return set;

  }


  TreeSet<Point> findNodes2(char z)
  {
    TreeSet<Point> set = new TreeSet<>();

    ArrayList<Point> points = new ArrayList<>();
    points.addAll( map.getAllPoints(z));

    for(int i=0; i<points.size(); i++)
    for(int j=0; j<points.size(); j++)
    {
      if (i != j)
      {
        Point a = points.get(i);
        Point b = points.get(j);
        Point delta = a.add(b.mult(-1));

          long  mult = 0;
        while(true)
        {
          Point c = a.add(delta.mult(mult));
          if (map.get(c) == ' ') break;
          set.add(c);
          mult++;

        }
      }

    }

    return set;

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
