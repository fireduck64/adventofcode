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

    int p1 = 0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p) == '@')
      {
        int rolls=0;
        for(Point q : map.getAdj(p, true))
        {
          if ((map.get(q) == '@') || (map.get(q) == 'x')) rolls++;
        }

        if (rolls < 4)
        {
          map.set(p, 'x');
          p1++;
        }
      }

    }

    //map.print();

		return "" + p1;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>(' ');
    MapLoad.loadMap(map, scan);

    int p1 = 0;

    while(true)
    {
      boolean change=false;
      for(Point p : map.getAllPoints())
      {
        if (map.get(p) == '@')
        {
          int rolls=0;
          for(Point q : map.getAdj(p, true))
          {
            if ((map.get(q) == '@') || (map.get(q) == 'x')) rolls++;
          }

          if (rolls < 4)
          {
            map.set(p, '.');
            p1++;
            change=true;
          }
        }
      }
      if (!change) break;
    }

    //map.print();

		return "" + p1;

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
