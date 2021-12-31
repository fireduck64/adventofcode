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
    String line = scan.nextLine();

    { // Part 1

      Map2D<Integer> map = new Map2D<Integer>(0);
      Point loc = new Point(0,0);
      map.set(loc, 1);
      for(char z : Str.stolist(line))
      {
        loc = loc.add(getDir(z));
        map.set(loc, map.get(loc) + 1);
      }
      long p1 = 0;
      for(long v : map.getCounts().values()) p1+=v;
      System.out.println("Part 1: " + p1);
    }
    { // Part 2

      Map2D<Integer> map = new Map2D<Integer>(0);
      Point loc_a = new Point(0,0);
      Point loc_b = new Point(0,0);
      map.set(loc_a, 2);
      int n=0;
      for(char z : Str.stolist(line))
      {
        if (n % 2 == 0)
        {
          loc_a = loc_a.add(getDir(z));
          map.set(loc_a, map.get(loc_a) + 1);
        }
        else
        {
          loc_b = loc_b.add(getDir(z));
          map.set(loc_b, map.get(loc_b) + 1);

        }
        n++;
      }
      long p2 = 0;
      for(long v : map.getCounts().values()) p2+=v;
      System.out.println("Part 2: " + p2);
    }

  }

  public Point getDir(char z)
  {
    if (z=='^') return new Point(0,-1);
    if (z=='v') return new Point(0,1);
    if (z=='>') return new Point(-1,0);
    if (z=='<') return new Point(1,0);
    E.er();
    return null;
  }
}
