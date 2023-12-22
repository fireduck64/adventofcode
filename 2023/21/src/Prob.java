import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Character> input = new Map2D<>(' ');
  Map2D<Character> garden = null;
  Map2D<Character> p1t = new Map2D<>('.');

  int max_step=100;

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(input, scan);
    
    Point start = null;
    for(Point p : input.getAllPoints())
    {
      if (input.get(p) == 'S')
      {
        start = p;
      }
    }

    garden = input.copy();
    garden.set(start, '.');

    markUnreachable(garden, start);

    Map2D<Character> p1 = new Map2D<Character>(' ');
    for(Point p : input.getAllPoints())
    {
      p1.set(p, input.get(p));
    }

    p1.set(start, 'O');
    long last=1;


    for(int i=0; i<max_step; i++)
    {
      if (i>10)
      {
        if ((i - 65) % 131 == 0)
        if (checkCleanEdge(p1, start))
        {
          System.out.println(i + " " + p1.getCounts().get('O') + " " + p1.low_x + " " + p1.high_x);
          //double area = (p1.high_x - p1.low_x + 1L) * (p1.high_y - p1.low_y + 1L);
          double area = getArea(p1, start);
          double ratio = area/p1.getCounts().get('O');

          System.out.println("Clean at: " + i + " " + p1.getCounts().get('O') + " " + getArea(p1, start));
          System.out.println("data: " + i + " " + p1.getCounts().get('O'));

          p1.print();
        }

      }
      p1 = step(p1);

    }
    //System.exit(-1);

    System.out.println(p1.getCounts());

    // All the above to say we are filled diamond at the final step
    {
      long fs = 26501365;
      //long fs = 1899;
      //long fs=1113;

      long final_area = getAreaStep(fs);
      System.out.println("Final area: " + final_area);
      long garden_size = 131;


      /*long included_area=0;
      for(int i=-1000; i<=1000; i++)
      for(int j=-1000; j<=1000; j++)
      {
        // fs is odd, therefor 0,0 (both even) is not included
        int mod_sum=(Math.abs(i) % 2) + (Math.abs(j) % 2);

        if (mod_sum == 1)
        {
          if (pointInside(fs, new Point(i,j))) included_area++;
        }
      }*/

      long rocks = 0;
      for(Point p : garden.getAllPoints())
      { // Determine how many times this is in the big form
        System.out.print('.');

        if (garden.get(p) != '#') continue;

        Point offset = p.add(start.mult(-1));

        /*AtomicLong counter = new AtomicLong();
        Search.search(new FRocks(fs, offset, counter));

        rocks += counter.get();
        System.out.println("Rock " + p + " at offset " + offset + " " + counter.get());*/


        // Go up
        Point n = offset;
        TreeSet<Long> y_set = new TreeSet<>();
        while(pointInside(fs, n))
        {
          y_set.add(n.y);
          n = new Point(offset.x, n.y - garden_size);
        }
        n = offset;
        while(pointInside(fs, n))
        {
          y_set.add(n.y);
          n = new Point(offset.x, n.y + garden_size);
        }

        /*if (y_set.size() > 0)
        {
          System.out.println( p.toString() + " y set: " + y_set.size());
          System.out.println(y_set);
        }*/

        for(long y : y_set)
        {
          long left_x = -(fs - Math.abs(y));
          long right_x = fs - Math.abs(y); 
          //System.out.println("Range: " + left_x + " " + right_x);

          long pos_x = offset.x;
          long count = 0;

          
          long mod_sum=(Math.abs(pos_x) % 2) + (Math.abs(y) % 2);
          if (mod_sum != 1)
          {
            pos_x = pos_x + garden_size;

          }

          if (pointInside(fs, new Point(pos_x, y))) count++;
          long dist_right = right_x - pos_x;
          long count_right = dist_right / (garden_size * 2);
          count+=count_right;

          long dist_left = pos_x - left_x;
          long count_left = dist_left / (garden_size * 2);

          count += count_left;

          rocks += count;
        }

      }
      long answer = final_area - rocks;
      System.out.println();
      System.out.println("Area: " + final_area);
      System.out.println("Rocks: " + rocks);
      System.out.println("P2: " + answer);

    }

  }

  public class FRocks extends Flood
  {
    final long fs;
    final Point loc;
    AtomicLong counter;

    public FRocks(long fs, Point p, AtomicLong counter)
    {
      this.fs = fs;
      this.loc = p;
      this.counter = counter;
    }

    public String toString(){return loc.toString(); }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (pointInside(fs, loc))
      {
        boolean include=false; 
        // fs is odd, therefor 0,0 (both even) is not included
        long mod_sum=(Math.abs(loc.x) % 2) + (Math.abs(loc.y) % 2);
        if (mod_sum == 1) include=true;
 
        if (include) counter.incrementAndGet();


        LinkedList<Point> n_lst = new LinkedList<>();

        n_lst.add( loc.add(new Point(0, 131)) );
        n_lst.add( loc.add(new Point(0, -131)) );
        n_lst.add( loc.add(new Point(131,0)) );
        n_lst.add( loc.add(new Point(-131,0)) );

        for(Point n : n_lst)
        {
          lst.add(new FRocks(fs, n, counter));
        }

      }
      return lst;

    }

  }

  public boolean pointInside(long fs, Point p)
  {
    if (Math.abs(p.x) + Math.abs(p.y) <= fs) return true;

    return false;

  }

  public long getAreaStep(long step)
  {
    return 1L + 2*step + step*step;
  }

  public long getArea(Map2D<Character> in, Point start)
  {
    Point high_x = start; // E
    Point high_y = start; // S
    Point low_x = start; // W
    Point low_y = start; // N

    Point center = new Point( low_y.x, high_x.y);
    System.out.println("Start: " + start + " c: " + center);
     
    for(Point p : in.getAllPoints())
    {
      if (p.x > high_x.x) high_x=p;
      if (p.x < low_x.x) low_x=p;
      if (p.y > high_y.y) high_y=p;
      if (p.y < low_y.y) low_y=p;
    }

    Map2D<Character> zone = new Map2D<Character>(' ');
    paintArea(zone,
      low_y, low_x, new Point(-1, 1),
      low_y, high_x, new Point(1, 1));

    //zone.print();
    paintArea(zone,
      low_x, high_y, new Point(1, 1),
      high_x, high_y, new Point(-1, 1));

    //zone.print();

    return zone.getCounts().get('O');

  }

  public void paintArea(Map2D<Character> area_map, 
    Point start_left, Point end_left, Point step_left, 
    Point start_right, Point end_right, Point step_right)
  {
    int step_count = (int)((end_left.x - start_left.x) / step_left.x);

    for(int i=0; i<=step_count; i++)
    {
      Point l = start_left.add(step_left.mult(i));
      Point r = start_right.add(step_right.mult(i));

      Point loc = l;
      while(loc.x <= r.x)
      {
        area_map.set(loc, 'O');
        loc = loc.add(new Point(2,0));
      }
    }

    

  }


  public boolean checkCleanEdge(Map2D<Character> in, Point start)
  {
    Point high_x = start; // E
    Point high_y = start; // N
    Point low_x = start; // W
    Point low_y = start; // S
     
    for(Point p : in.getAllPoints())
    {
      if (p.x > high_x.x) high_x=p;
      if (p.x < low_x.x) low_x=p;
      if (p.y > high_y.y) high_y=p;
      if (p.y < low_y.y) low_y=p;
    }

    // N to E
    if (!checkLine(in, high_y, high_x, new Point(1,-1))) return false;

    // E to S
    if (!checkLine(in, high_x, low_y, new Point(-1,-1))) return false;

    // S to W
    if (!checkLine(in, low_y, low_x, new Point(-1,1))) return false;

    // W to N
    if (!checkLine(in, low_x, high_y, new Point(1,1))) return false;

    return true;
  }


  public boolean checkLine(Map2D<Character> in, Point start, Point end, Point step)
  {
    int step_count_x = (int)((end.x - start.x) / step.x);
    int step_count_y = (int)((end.y - start.y) / step.y);
    if (step_count_x != step_count_y) return false;

    Point loc = start;
    for(int i=1; i<step_count_x; i++)
    {
      Point c = start.add(step.mult(i));
      if (in.get(c)!='O') return false;
    }
    return true;



  }

  public Map2D<Character> step(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>(' ');

    for(Point p : in.getAllPoints())
    {
      if (in.get(p)=='O')
      {
        for(Point q : garden.getAdj(p, false))
        {
          if (garden.get(mapToGarden(q)) == '.')
          {
            if (out.get(q) != 'O')
            {
              out.set(q, 'O');
            }
          }
        }
      }
    }
    return out;
  }
  public Point mapToGarden(Point p)
  {

    long x = p.x;
    long y = p.y;

    while (x < 0) x+=garden.high_x+1L;
    while (y < 0) y+=garden.high_y+1L;

    while (x > garden.high_x) x-=garden.high_x+1L;
    while (y > garden.high_y) y-=garden.high_y+1L;

    return new Point(x,y);

  }

  public void markUnreachable(Map2D<Character> in, Point start)
  {
    Search.search(new F(start, in));

    for(Point p : in.getAllPoints())
    {
      if (in.get(p) == '.') in.set(p, '#');
      if (in.get(p) == ',') in.set(p, '.');
    }
  }

  public class F extends Flood
  {
    final Point loc;
    final Map2D<Character> map;
    public F(Point loc, Map2D<Character> in)
    {
      this.loc = loc;
      this.map = in;

    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      map.set(loc, ',');

      for(Point p : map.getAdj(loc, false))
      {
        if (map.get(p) == '.') lst.add(new F(p, map));
      }


      return lst;

    }

    public String toString(){return loc.toString();}

  }


}
