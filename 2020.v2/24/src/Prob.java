import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  // Used for both token expansion
  // and neighbor list
  TreeMap<String, Point> dirs = new TreeMap<>();

  public Prob(Scanner scan)
  {
    dirs.put("w",new Point(-2,0));
    dirs.put("nw",new Point(-1,1));
    dirs.put("ne",new Point(1,1));
    dirs.put("e",new Point(2,0));
    dirs.put("se",new Point(1,-1));
    dirs.put("sw",new Point(-1,-1));


    Map2D<Character> tiles=new Map2D<>('.');


    for(String line : In.lines(scan))
    {
      Point c = new Point(0,0);
      while(line.length() > 0)
      {
        for(String d : dirs.keySet())
        {
          if (line.startsWith(d))
          {
            line = line.substring(d.length());

            c = c.add(dirs.get(d));

          }

        }

      }
      if (tiles.get(c) == '.') tiles.set(c, 'x');
      else tiles.set(c, '.');

    }

    System.out.println("Part 1: " + tiles.getCounts().get('x'));
    for(int i=0; i<100; i++)
    {
      tiles = sim(tiles);
    }
    System.out.println("Part 2: " + tiles.getCounts().get('x'));

  }

  public Map2D<Character> sim(Map2D<Character> in)
  {
    TreeSet<Point> all_points=new TreeSet<>();

    for(Point p : in.getAllPoints())
    {
      all_points.add(p);
      for(Point delta : dirs.values())
      {
        all_points.add(p.add(delta));
      }
    }

    Map2D<Character> out = new Map2D<Character>('.');

    for(Point p : all_points)
    {
      int count = 0;
      for(Point delta : dirs.values())
      {
        if (in.get(p.add(delta)) == 'x') count++;

      }
      if (in.get(p) == '.')
      {
        if (count == 2) out.set(p, 'x');
      }
      else
      {
        if (count == 1) out.set(p, 'x');
        if (count == 2) out.set(p, 'x');
      }

    }
    return  out;


  }




}
