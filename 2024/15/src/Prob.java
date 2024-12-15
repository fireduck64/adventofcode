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
    Map2D<Character> map = new Map2D<Character>(' ');
    MapLoad.loadMap(map, scan);

    String inst = In.oneString(scan);

    map.print();
    System.out.println(inst);
    doInstructions(map, inst);
		return "" + getScore(map);
  }

  public long getScore(Map2D<Character> map)
  {
    long sum = 0;

    for(Point p : map.getAllPoints('O'))
    {
      sum += p.y * 100 + p.x;
    }

    for(Point p : map.getAllPoints('['))
    {
      sum += p.y * 100 + p.x;
    }

    return sum;

  }
  public Map2D<Character> doInstructions2(Map2D<Character> map, String inst)
  {
    Point robot = null;
    for(char z : Str.stolist(inst))
    {
      System.out.print('.');
      //if (robot == null) 
      robot = map.getAllPoints('@').get(0);

      Point dir = Nav.getDir(z);
      map = tryMove(robot, dir, map);

    }
    System.out.println();
    return map;
  }

  public Map2D<Character> tryMove(Point robot, Point dir, Map2D<Character> map)
  {
    Map2D<Character> m2 = map.copy();
    if (moveRec(robot.add(dir), dir, m2, '@'))
    {
      m2.set(robot, '.');
      return m2;
    }
    
    return map; 

  }
  public boolean moveRec(Point step, Point dir, Map2D<Character> map, char in_char)
  {
    char z = map.get(step);

    Point left = null;
    Point right = null;

    if (z == '#') return false;
    else if (z == '.') 
    {
      map.set(step, in_char);
      return true;
    }
    else if (z == '[')
    {
      if (dir.y != 0)
      {
        left = step;
        right = step.add(new Point(1, 0));
      }
      else
      {
        if (moveRec(step.add(dir), dir, map, '['))
        {
          map.set(step, in_char);
          return true;
        }
        return false;
      }
    }
    else if (z == ']')
    {
      if (dir.y != 0)
      {
        right = step;
        left = step.add(new Point(-1, 0));
      }
      else
      {
        if (moveRec(step.add(dir), dir, map, ']'))
        {
          map.set(step, in_char);
          return true;
        }
        return false;

      }
    }
    else
    {
      E.er();
    }

    if (moveRec(left.add(dir), dir, map, '['))
    if (moveRec(right.add(dir), dir, map, ']'))
    {
      map.set(left, '.');
      map.set(right, '.');
      map.set(step, in_char);
      return true;

    }
    return false;

  }

  public void doInstructions(Map2D<Character> map, String inst)
  {
    Point robot = null;
    for(char z : Str.stolist(inst))
    {
      if (robot == null) robot = map.getAllPoints('@').get(0);
      Point dir = Nav.getDir(z);

      Point write = null;

      int step=1;
      while(true)
      {
        Point n = robot.add(dir.mult(step));

        if (map.get(n) == '#')
        {
          break;
        }
        else if (map.get(n) == '.')
        {
          write = n;
          break;
        }
        else if (map.get(n) == 'O')
        {
          step++;
        }

      }
      if (write != null)
      {
        map.set(write, 'O');
        Point r2 = robot.add(dir);
        map.set( r2, '@');
        map.set(robot, '.');

        robot = r2;
      }

    }

  }

  public String Part2(Scanner scan)
    throws Exception
  {
    Map2D<Character> map = new Map2D<Character>(' ');
    MapLoad.loadMap(map, scan);

    Map2D<Character> m2 = new Map2D<>(' ');

    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      if (z=='O')
      {
        m2.set(p.x*2, p.y, '[');
        m2.set(p.x*2+1, p.y, ']');
      }
      else if (z == '@')
      {
        m2.set(p.x*2, p.y, '@');
        m2.set(p.x*2+1, p.y, '.');
      }
      else
      {
        m2.set(p.x*2, p.y, z);
        m2.set(p.x*2+1, p.y, z);

      }
    }
    map = m2;


    String inst = In.oneString(scan);

    map.print();
    System.out.println(inst);
    map = doInstructions2(map, inst);
    map.print();
		return "" + getScore(map);

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
