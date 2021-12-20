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
  Map2D<Character> map = new Map2D<Character>(' ');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map, scan);
    Point start = findStart();
    System.out.println(start);
    System.out.println("Part 1: " + follow(start));

  }

  public Point findStart()
  {
    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      if ((z=='|') && (p.y==0)) return p;

    }
    return null;
  }

  public String follow(Point start)
  {
    Point dir = new Point(0,1); //down
    Point loc = start;
    String path = "";
    int steps=0;

    while(true)
    {
      char z = map.get(loc);
      if ((z=='|') || (z=='-'))
      {
        // no change
      }
      else if (z==' ')
      {
        System.out.println("Part 2: " + steps);
        return path;
      }
      else if (z=='+')
      {
        // change if we have to
        char next = map.get(loc.add(dir));
        if (next != ' ')
        { // no change, continue

        }
        else
        {
          for(Point look : turnHead(dir))
          {
            char n = map.get(loc.add(look));
            if (n != ' ')
            {
              dir=look;
            }
          }

        }
      }
      else
      {
        if (('A' <= z) && (z <= 'Z'))
        {
          // no dir change
          path = path + z;
        }
        else
        {
          E.er("" + z);
        }

      }

      steps++;
      loc = loc.add(dir);
    }


  }

  public List<Point> turnHead(Point dir)
  {
    if (dir.x == 0)
    {
      return ImmutableList.of( new Point(-1,0), new Point(1,0));
    }

    if (dir.y == 0)
    {
      return ImmutableList.of( new Point(0,-1), new Point(0,1));
    }
    E.er();
    return null;
  }


}
