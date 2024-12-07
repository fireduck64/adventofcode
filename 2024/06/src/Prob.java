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
    Map2D<Character> map = new Map2D<Character>('e');
    MapLoad.loadMap(map, scan);

    Point start = map.getAllPoints('^').get(0);
    Point dir = new Point(0, -1); // up



    TreeSet<Point> visit = new TreeSet<>();

    Point loc = start;
    visit.add(loc);
    while(true)
    {
      Point n = loc.add(dir);
      if (map.get(n) == '#')
      {
        dir = Nav.turnRight(dir);
      }
      else if (map.get(n) == 'e')
      {
        break;
      }
      else if ((map.get(n) == '.') || (map.get(n) == '^'))
      {
        loc = loc.add(dir);
        visit.add(loc);
      }
    }
		return "" + visit.size();
  }

  

  public String Part2(Scanner scan)
    throws Exception
  {
    Map2D<Character> map = new Map2D<Character>('e');
    MapLoad.loadMap(map, scan);

    Point start = map.getAllPoints('^').get(0);
    Point dir = new Point(0, -1); // up

    TreeSet<Point> visit = new TreeSet<>();

    Point loc = start;
    visit.add(loc);
    while(true)
    {
      Point n = loc.add(dir);
      if (map.get(n) == '#')
      {
        dir = Nav.turnRight(dir);
      }
      else if (map.get(n) == 'e')
      {
        break;
      }
      else if ((map.get(n) == '.') || (map.get(n) == '^'))
      {
        loc = loc.add(dir);
        visit.add(loc);
      }
    }

    dir = new Point(0, -1); // up
    int loops = 0;

    // The obstical has to be along the dudes path so only check those
    for(Point p : visit)
    //for(Point p : map.getAllPoints('.'))
    {
      if (map.get(p) == '.')
      {
        map.set(p, '#');
        if (checkLoop(map, start, dir))
        {
          loops++;
        }
        map.set(p, '.');
        System.out.print(".");
      }

    }
    System.out.println();

		return "" + loops;
 }

 public boolean checkLoop(Map2D<Character> map, Point start, Point dir)
 {

    Point loc = start;
    HashSet<String> visit = new HashSet<>(2048,0.5f);

    while(true)
    {
      Point n = loc.add(dir);
      if (map.get(n) == '#')
      {
        dir = Nav.turnRight(dir);
      }
      else if (map.get(n) == 'e')
      {
        return false;
      }
      else if ((map.get(n) == '.') || (map.get(n) == '^'))
      {
        loc = n;
      }
      else
      {
        E.er();
      }
      String key = "" + loc +"|"+dir;
      if (visit.contains(key)) return true;
      visit.add(key);
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
