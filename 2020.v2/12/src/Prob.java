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

  public Prob(Scanner scan)
  {
    Point dir = Nav.getDir('E');
    Point loc = new Point(0,0);
    Point way = new Point(10,-1);
    Point loc2 = new Point(0,0);


    List<String> lines = In.lines(scan);

    for(String line : lines)
    {
      char z = line.charAt(0);
      int dist = Integer.parseInt( line.substring(1));

      if (z == 'L')
      {
        while(dist >= 90)
        {
          dir = Nav.turnLeft(dir);
          way = Nav.turnLeft(way);
          dist -= 90;
        }
      }
      else if (z == 'R')
      {
        while(dist >= 90)
        {
          dir = Nav.turnRight(dir);
          way = Nav.turnRight(way);
          dist -= 90;
        }
      }
      else if (z=='F')
      {
        loc = loc.add(dir.mult(dist));
        loc2 = loc2.add(way.mult(dist));
      }
      else
      {
        Point m = Nav.getDir(z);

        loc = loc.add(m.mult(dist));
        way = way.add(m.mult(dist));
      }
    }

    long p1 = Math.abs(loc.x) + Math.abs(loc.y);

    System.out.println("Part 1: " + p1 + " " + loc);

    long p2 = Math.abs(loc2.x) + Math.abs(loc2.y);

    System.out.println("Part 2: " + p2 + " " + loc2);
  }

}
