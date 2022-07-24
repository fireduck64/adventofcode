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
  String line;

  public Prob(Scanner scan)
  {
    line = scan.nextLine();

    for(int x=0; x<50; x++)
    for(int y=0; y<50; y++)
    {
      if (getTractor(x,y)) map.set(x,y,'#');
      else map.set(x,y,'.');

    }
    map.print();
    System.out.println("Part 1: " + map.getCounts().get('#'));

    Point start = scanRight();
    System.out.println("Scan start: " + start);
    Point p2 = p2Seek(start);
    System.out.println(p2);
    long v = p2.x * 10000L + p2.y;
    System.out.println("Part 2: " + v);
  }

  public boolean getTractor(long x, long y)
  {
    IntComp comp;
    comp = new IntComp(line);
    comp.input.add(x);
    comp.input.add(y);
    comp.exec();
    long v = comp.output.poll();
    return (v==1L);
  }

  public Point p2Seek(Point start)
  {
    TreeMap<Double, Point> queue = new TreeMap<Double, Point>();

    for(int i=0; i<1100; i++)
    for(int j=0; j<1100; j++)
    {
      Point p = new Point(i,j).add(start);
      double dist = p.getDist2(new Point(0,0)) + rnd.nextDouble()/1e3;
      queue.put(dist, p);
    }
    while(!queue.isEmpty())
    {
      Point p = queue.pollFirstEntry().getValue();
      if (check(p)) return p;
    }

    return null;

  }

  public boolean check(Point p)
  {
    ArrayList<Point> lst = new ArrayList<>();
    lst.add(p);
    lst.add(p.add(new Point(0,99)));
    lst.add(p.add(new Point(99,0)));
    lst.add(p.add(new Point(99,99)));
    for(Point q : lst)
    {
      if (!getTractor(q.x, q.y)) return false;
    }
    return true;

  }

  public Point scanRight()
  {
    long top_y = 0;
    long x = 5;
    long bottom_y = top_y;

    while(bottom_y - top_y < 100)
    {

      // find top
      while(!getTractor(x,top_y))
      {
        top_y++;
      }
      bottom_y=top_y;
      // find buttom
      while(getTractor(x, bottom_y))
      {
        bottom_y++;
      }
      //System.out.println("Span at " + x + " is " + top_y + " to " + bottom_y); 
      x++;

    }
    return new Point(x, top_y);
    



  }

}
