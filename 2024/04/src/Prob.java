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
    Map2D<Character> map = new Map2D<Character>('.');
    MapLoad.loadMap(map, scan);

    String xmas="XMAS";
    int count =0;

    for(Point p : map.getAllPoints())
    {
      for(Point dir : map.getAdj(new Point(0,0), true))
      {
        if (check(map, p, dir, xmas)) count++;
      }
    }

    
		return "" + count;
  }

  public int check2(Map2D<Character> map, Point start, List<List<Point>> opts)
  {
    if (map.get(start) != 'A') return 0;

    int ph=0;

    for(List<Point> opt : opts)
    {
      Point a = opt.get(0);
      Point b = opt.get(1);
      if (map.get(start.add(a)) == 'M')
      if (map.get(start.add(b)) == 'S')
      {
        ph++;
      }

      if (map.get(start.add(b)) == 'M')
      if (map.get(start.add(a)) == 'S')
      {
        ph++;
      }
    }
    if (ph == 2) return 1;
    if (ph == 3) return 3;
    if (ph > 3) E.er("" + ph);

    return 0;

  }
  public boolean check(Map2D<Character> map, Point start, Point dir, String search)
  {
    for(int i=0; i<search.length(); i++)
    {
      char z = search.charAt(i);
      if (map.get(start.add(dir.mult(i))) != z) return false;

    }
    return true;

  }

  public String Part2(Scanner scan)
    throws Exception
  {
    Map2D<Character> map = new Map2D<Character>('.');
    MapLoad.loadMap(map, scan);

    int count=0;

    ArrayList<List<Point> > options = new ArrayList<>();

    options.add(ImmutableList.of(new Point(-1,-1), new Point(1,1)));
    options.add(ImmutableList.of(new Point(-1,1), new Point(1,-1)));
    //options.add(ImmutableList.of(new Point(0,-1), new Point(0,1)));
    //options.add(ImmutableList.of(new Point(1,0), new Point(-1,0)));


    for(Point p : map.getAllPoints())
    {
      {
        count += check2(map, p, options);
      }
    }

    
		return "" + count;
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
