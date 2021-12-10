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

  ArrayList<Point> points = new ArrayList<>();
  HashMap<Point, Integer> map_color=new HashMap<>();
    
  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<Integer> lst = Tok.ent(line,",");
      Point p = new Point( lst.get(0), lst.get(1), lst.get(2), lst.get(3) );
      points.add(p);
    }

    int color=0;
    for(Point p : points)
    {
      if (!map_color.containsKey(p))
      {
        color++;
        int v = paint(p, color);
      }
      
    }
    System.out.println("Part 1: " + color);

  }

  public int paint(Point start, int color)
  {
    HashSet<Point> my_points = new HashSet<>();
    LinkedList<Point> check = new LinkedList<>();

    check.add(start);

    while(check.size() > 0)
    {
      Point p = check.pollFirst();

      if (!my_points.contains(p))
      {
        my_points.add(p);
        for(Point q : points)
        {
          if (getDist(p,q) <= 3)
          {
            check.add(q);
          }
        }
      }
    }
    for(Point p : my_points)
    {
      map_color.put(p, color);
    }
    return my_points.size();

  }

  public int getDist(Point a, Point b)
  {
    int d = 0;
    d += Math.abs(a.x - b.x);
    d += Math.abs(a.y - b.y);
    d += Math.abs(a.z - b.z);
    d += Math.abs(a.w - b.w);
    return d;
  }

}
