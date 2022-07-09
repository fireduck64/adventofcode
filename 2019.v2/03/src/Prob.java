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
    String wire_1_str = scan.nextLine();
    String wire_2_str = scan.nextLine();

    Map2D<Integer> wire_1 = getMap(wire_1_str);
    Map2D<Integer> wire_2 = getMap(wire_2_str);

    HashSet<Point> points_1 = new HashSet<>();
    points_1.addAll(wire_1.getAllPoints());

    {

      Point best = new Point(1000000,1000000);

      for(Point b : wire_2.getAllPoints())
      {
        if (points_1.contains(b))
        {
          if (b.getDistM(new Point(0,0)) < best.getDistM(new Point(0,0)))
          {
            best =b;
          }
        }
      }
      System.out.println("Part 1:");
      System.out.println("   " + best + " " + best.getDistM(new Point(0,0)));
    }

    {
      int best_cost = 10000000;
      Point best = new Point(1000000,1000000);

      for(Point b : wire_2.getAllPoints())
      {
        if (points_1.contains(b))
        {
          int cost = wire_1.get(b) + wire_2.get(b);
          if (cost < best_cost)
          {
            best = b;
            best_cost = cost;
          }
        }
      }
      System.out.println("Part 2:");
      System.out.println("   " + best + " " + best_cost);
 
    }

  }

  // Up = +y
  // Right = +x
  public Map2D<Integer> getMap(String wire_str)
  {
    Map2D<Integer> map = new Map2D<Integer>(0);

    Point pos = new Point(0,0);
    List<String> moves = Tok.en(wire_str, ",");

    int cost=0;

    for(String move : moves)
    {
      int steps = getSteps(move);
      Point dir = getDir(move);
      for(int i=0; i<steps; i++)
      {
        cost++;
        pos = pos.add(dir);
        if (map.get(pos) == 0)
        {
          map.set(pos, cost);
        }
      }

    }

    return map;


  }

  public Point getDir(String move)
  {
    char d = move.charAt(0);
    if (d=='U') return new Point(0,1);
    if (d=='D') return new Point(0,-1);
    if (d=='R') return new Point(1,0);
    if (d=='L') return new Point(-1,0);

    return null;
  }
  public int getSteps(String move)
  {
    return Integer.parseInt(move.substring(1));
  }

}
