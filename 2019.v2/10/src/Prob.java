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

    {
      int best =0;
      Point best_loc = null;
      for(Point p : map.getAllPoints())
      {
        if (map.get(p) == '#')
        {
          int seen = countSeen(p);
          if (seen > best)
          {
            best = Math.max(best, seen);
            best_loc = p;
          }
        }
      }
      System.out.println("Part 1: " + best);
      System.out.println(best_loc);

      Point n = laserBall(best_loc);
      System.out.println("Part 2: " + (n.x * 100 + n.y));
      System.out.println(n);
   

    }
  }

  public int countSeen(Point base)
  {
    TreeSet<Double> angles = new TreeSet<>();

    for(Point p : map.getAllPoints())
    {
      if (map.get(p) == '#')
      {
        if (!p.equals(base))
        {
          Point delta = p.sub(base);
          angles.add(Math.atan2(delta.y, delta.x));
        }

      }
    }

    return angles.size();

  }
  public Point laserBall(Point base)
  {
    int count=0;
    while(true)
    {
      TreeMap<Double, Point> close=new TreeMap<>();
      for(Point p : map.getAllPoints())
      {
        if (map.get(p) == '#')
        {
          if (!p.equals(base))
          {
            Point delta = p.sub(base);
            double angle = Math.atan2(delta.y, delta.x)*180.0/Math.PI;
            angle=angle+90.0;
            if (angle < 0.0) angle+=360.0;
            if (angle > 360.0) angle-=360.0;
            if (close.containsKey(angle))
            {
              if (base.getDist2(p) < base.getDist2(close.get(angle)))
              {
                close.put(angle, p);
              }
            }
            else
            {
              close.put(angle, p);
            }
          }
        }
      }
      if (close.size() == 0) return null;

      
      while(close.size()>0)
      {
        Point p = close.pollFirstEntry().getValue();
        map.set(p,'x');
        count++;
        //System.out.println("Laser " + count + " " + p);
        if (count==200) return p;

      }



    }

  }

}
