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

  Map2D<Character> map = new Map2D<>('#');

  TreeMap<Double, Integer> sizes = new TreeMap<>();
  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map, scan);

    int low_sum = 0;
    for(Point p : map.getAllPoints())
    {
      char v = map.get(p);
      boolean not_low=false;
      for(Point q : map.getAdj(p, false))
      {
        char w = map.get(q);
        if (w !='#')
        {
          if (w <= v)
          {
            not_low=true;
          } 
        }

      }
      if (!not_low)
      {
        int risk = 1 + Integer.parseInt("" + v);

        low_sum +=risk;
        int size = getBasinSize(p);
        sizes.put( size + rnd.nextDouble(), size);

      }
    }
    System.out.println("Part 1: " + low_sum);
    while(sizes.size() > 3)
    {
      sizes.  pollFirstEntry();
    }
    long sum = 1;
    for(int v : sizes.values()) sum*=v;
    System.out.println("Part 2: " + sum);
  }

  int getBasinSize(Point start)
  {
    HashSet<Point> basin_points = new HashSet<>();
    LinkedList<Point> check = new LinkedList<>();
    check.add(start);

    while(check.size() > 0)
    {
      Point p = check.removeFirst();

      char v = map.get(p);
      if (v == '9') continue;
      if (v == '#') continue;

      if (!basin_points.contains(p))
      {
        check.addAll( map.getAdj(p, false));
        basin_points.add(p);

      }


    }

    return basin_points.size();
    

  }

  

}
