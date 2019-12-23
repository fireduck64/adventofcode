import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Map2D<Integer> map = new Map2D<Integer>(-1);

  TreeMap<Integer, Character> print_map=new TreeMap<>();

  IntComp compy;
  Random rnd = new Random();

  public Prob(Scanner scan)
  {
    print_map.put(-1,'?');
    print_map.put(0,'.');
    print_map.put(1,'#');
    print_map.put(2,'O');
    print_map.put(8,'D');

    compy = new IntComp(scan.nextLine());


    long x=0;
    long y=0;
    long dx=0;
    long dy=0;
    long moves = 0;

    long ox=0;
    long oy=0;


    while(true)
    {

      long dir = rnd.nextInt(4) + 1;

      dx=0; dy=0;
      if (dir == 1) dy=-1;
      if (dir == 2) dy=1;
      if (dir == 3) dx=-1;
      if (dir == 4) dx=1;

      compy.input_queue.add(dir);
      compy.execute();
      long r = compy.output_queue.poll();
      if (r == 0)
      {
        map.set(x+dx, y+dy, 1); //wall
      }
      if (r == 1)
      {
        map.set(x+dx, y+dy, 0); //open
        x+=dx;
        y+=dy;
      }
      if (r == 2)
      {
        map.set(x+dx, y+dy, 2); //oxygen
        x+=dx;
        y+=dy;

        ox=x;
        oy=y;
      }

      moves++;

      if (moves % 1000 == 0)
      {
        int prev = map.get(x,y);
        map.set(x,y,8);
        System.out.println(map.getPrintOut(print_map));
        map.set(x,y,prev);
      }
      if (moves > 10000000) break;
    }

    long o2_cost = findCost(0,0,ox,oy);
    System.out.println("O2 Cost: " + o2_cost);
    long fill_time = getFillTime();
    System.out.println("Fill time: " + fill_time);
  }


  public long findCost(long start_x, long start_y, long dest_x, long dest_y)
  {
    HashSet<String> visited = new HashSet<>();
    TreeMap<Double, Point> queue=new TreeMap<>();

    queue.put(0.0, new Point(start_x, start_y));

    while(true)
    {
      Map.Entry<Double, Point> me = queue.pollFirstEntry();

      long cost = (long) Math.floor(me.getKey());
      Point p = me.getValue();
      if (!visited.contains(p.toString()))
      {
        visited.add(p.toString());

        if ((p.x == dest_x) && (p.y == dest_y)) return cost;

        for(int dx=-1; dx<=1; dx++)
        for(int dy=-1; dy<=1; dy++)
        if (Math.abs(dx) + Math.abs(dy) == 1)
        {
          Point n = new Point(p.x+dx, p.y+dy);
          int v = (map.get(p.x+dx, p.y+dy));
          if ((v == 0) || (v==2))
          {
            queue.put( cost + 1 + rnd.nextDouble(), n);
          }
        }
      }

    }
  }

  public long getFillTime()
  {
    int time = 0;
    while(true)
    {
      LinkedList<Point> fill_points = new LinkedList<>();

      for(Map.Entry<Long, Long> me : map.getAllPoints())
      {
        long x = me.getKey(); long y = me.getValue();
        int v = map.get(x,y);
        if (v == 2)
        {
          for(int dx=-1; dx<=1; dx++)
          for(int dy=-1; dy<=1; dy++)
          if (Math.abs(dx) + Math.abs(dy) == 1)
          {
            Point n = new Point(x+dx, y+dy);
            int v2 = (map.get(x+dx, y+dy));
            if (v2 == 0)
            {
              fill_points.add(n);
            }
          } 

        }
      }
      

      if (fill_points.size() ==0) return time;
      time++;

      for(Point p : fill_points)
      {
        map.set(p.x,p.y,2);
      }


    }

  }

  public class Point
  {
    long x;
    long y;

    public Point(long x, long y){
      this.x = x;
      this.y = y;
    }

    public String toString()
    {
      return String.format("(%d %d)", x,y);
    }
  }

}
