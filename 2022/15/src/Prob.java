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

  TreeMap<Point, Point> sensors = new TreeMap<>();
  TreeSet<Point> points = new TreeSet<Point>();
  long low_x=1000000;
  long high_x=0;

  public Prob(Scanner scan)
  {
    Map2D<Character> map = new Map2D<>('.');
    for(String line : In.lines(scan))
    {
      List<Long> num = Tok.enl(line, "=, :");
      Point sensor = new Point(num.get(0), num.get(1));
      Point beacon = new Point(num.get(2), num.get(3));

      low_x = Math.min(low_x, sensor.x);
      low_x = Math.min(low_x, beacon.x);
      high_x = Math.max(high_x, beacon.x);
      high_x = Math.max(high_x, sensor.x);

      points.add(sensor);
      points.add(beacon);


      sensors.put(sensor, beacon);

    }

    /*long p1=0;
    for(long x = low_x - 10000000; x<=high_x+10000000; x++)
    {
      Point p = new Point(x, 2000000L);

      //Point p = new Point(x, 10L);
      if (!points.contains(p))
      {
      for(Map.Entry<Point, Point> me : sensors.entrySet())
      {
        Point s = me.getKey();
        Point b = me.getValue();
        long dist = s.getDistM(b);
        long d = p.getDistM(s);
        if (d <= dist) 
        { 
          p1++;
          break;
        }

      }
      }


    }
    System.out.println(p1);*/

    for(Map.Entry<Point, Point> me : sensors.entrySet())
    {
        Point s = me.getKey();
        Point b = me.getValue();
        long dist = s.getDistM(b);
        scanRing(s, dist);
    }

  }

  // Theory being that the right point is on the edge of one of the sensor ranges
  // so we just walk around the damn thing
  public void scanRing(Point s, long ring)
  {
    long count = ring+1;
    LinkedList<Point> dirs = new LinkedList<>();
    dirs.add(new Point(1,1)); // down right
    dirs.add(new Point(-1,1)); // down left
    dirs.add(new Point(-1,-1)); // up left
    dirs.add(new Point(1, -1)); //up right
    
    Point cur = s.add(new Point(0, -ring-1));

    for(Point dir : dirs)
    for(long step =0; step<count; step++)
    {
      cur = cur.add(dir);
      if (!pointImpossible(cur))
      {
        System.out.println(cur);
      }
    }


  }

  public boolean pointImpossible(Point p)
  {
    if (p.x <= 0) return true;
    if (p.y <= 0) return true;
    if (p.x > 4000000) return true;
    if (p.y > 4000000) return true;

    
      if (points.contains(p)) return true;

      for(Map.Entry<Point, Point> me : sensors.entrySet())
      {
        Point s = me.getKey();
        Point b = me.getValue();
        long dist = s.getDistM(b);
        long d = p.getDistM(s);
        if (d <= dist) 
        { 
          return true;
        }

      }
      return false;
 
  }

}
