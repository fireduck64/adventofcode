import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  IntComp compy;
  IntComp compy_base;

  Map2D<Integer> map = new Map2D<>(-1);
  TreeMap<Integer, Character> print_map = new TreeMap<>();

  public Prob(Scanner scan)
  {

    print_map.put(0, '.');
    print_map.put(1, '#');
    print_map.put(-1, ' ');
    String line = scan.nextLine();

    compy_base = new IntComp(line);

    int pulled = 0;
    TreeMap<Double, Point> queue = new TreeMap<>();
    queue.put(0.0, new Point(0,0));

    HashSet<Point> checked = new HashSet<>();
    
    int box = 99;

    while(queue.size() > 0)
    {
      Point p = queue.pollFirstEntry().getValue();


      if (p.x >= 0)
      if (p.y >= 0)
      if (!checked.contains(p))
      {
        checked.add(p);
        if (getPoint(p) == 1)
        {
          if ((p.x + p.y) % 100 == 0)
          {
          System.out.println("On beam: " + p);
          }
          if (getPoint(new Point(p.x, p.y+box)) == 1)
          if (getPoint(new Point(p.x+box, p.y)) == 1)
          if (getPoint(new Point(p.x+box, p.y+box)) == 1)
          {
            long ans = p.x * 10000 + p.y;
            System.out.println("Answer: " + ans);
            break;

          }

          for(int dx=-1; dx<=3; dx++)
          for(int dy=-1; dy<=3; dy++)
          if (Math.abs(dx) + Math.abs(dy) != 0)
          {
            Point n = new Point(p.x + dx, p.y+dy);
            queue.put( n.x*n.x + n.y*n.y + rnd.nextDouble(), n);
          }
        }
      }
    }

    //System.out.println(map.getPrintOut(print_map));

  }


  public int getPoint(Point o)
  {
    if (map.get(o.x, o.y) >= 0) return map.get(o.x, o.y);


        compy = new IntComp(compy_base);
        compy.input_queue.add(o.x);
        compy.input_queue.add(o.y);
        compy.execute();

        long pull =   compy.output_queue.poll();

        map.set(o.x, o.y, (int)pull);

      return (int)pull;

  }

}
