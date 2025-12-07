import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> input;
  public String Part1(Scanner scan)
    throws Exception
  {
    input =new Map2D<>(' ');
    MapLoad.loadMap(input, scan);

    Point start = null;
    for(Point p : input.getAllPoints())
    {
      if (input.get(p) == 'S') start = p;
    }
		return "" + flowDown(start);
  }

  public int flowDown(Point start)
  {
    char z = input.get(start);
    if (z == 'S')
    {
      return flowDown( new Point(start.x, start.y+1));

    }
    if (z == ' ')
    {
      return 0;
    }
    if (z == '|')
    {
      return 0;
    }
    if (z == '.')
    {
      input.set(start, '|');
      return flowDown( new Point(start.x, start.y+1));
    }
    if (z == '^')
    {
      return 1 + flowDown( new Point(start.x-1, start.y))
               +  flowDown(new Point(start.x+1, start.y));

    }
    E.er("z: " + z);
    return -1;

  }

  HashMap<Point, Long> memo=new HashMap<>();
  public long qDown(Point start)
  {
    if (memo.containsKey(start)) return memo.get(start);
    char z = input.get(start);
    if (z == 'S')
    {
      return qDown( new Point(start.x, start.y+1));
    }
    if (z == ' ')
    {
      return 1;
    }
    if (z == '|')
    {
      return 0;
    }
    if (z == '.')
    {
      return qDown( new Point(start.x, start.y+1));
    }
    if (z == '^')
    {
      long n = qDown( new Point(start.x-1, start.y))
               + qDown( new Point(start.x+1, start.y));
      memo.put(start, n);
      return n;

    }
    E.er("z: " + z);
    return -1;

  }


  public String Part2(Scanner scan)
    throws Exception
  {
    memo.clear();
    input =new Map2D<>(' ');
    MapLoad.loadMap(input, scan);

    Point start = null;
    for(Point p : input.getAllPoints())
    {
      if (input.get(p) == 'S') start = p;
    }
		return "" + qDown(start);

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
