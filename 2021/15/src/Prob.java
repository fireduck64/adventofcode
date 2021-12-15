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

  Map2D<Integer> map = new Map2D<Integer>(1000000000);
  public Prob(Scanner scan)
  {
    MapLoad.loadMapInt(map, scan);

    SS p1 = (SS)Search.search(new SS(new Point(0,0), 0));
    System.out.println(p1);
    System.out.println("Part 1: " + p1.getCost());

    List<Point> lst = map.getAllPoints();
    long x_sz = map.high_x + 1;
    long y_sz = map.high_y + 1;
    for(int i=0; i<5; i++)
    for(int j=0; j<5; j++)
    {
      if (i + j > 0)
      for(Point p : lst)
      {
        int risk_delta = i+j;
        int r = map.get(p) + risk_delta;
        while (r > 9) r-=9;
        map.set(p.x + i * x_sz, 
                p.y + j * y_sz, 
                r);


      }
    }

    SS p2 = (SS)Search.search(new SS(new Point(0,0), 0));
    System.out.println(p2);
    System.out.println("Part 2: " + p2.getCost());
  }


  public class SS extends State
  {
    int cost;
    Point p;

    public SS(Point p, int cost)
    {
      this.p = p;
      this.cost = cost;

    }
    public boolean isTerm()
    {
      return (p.x == map.high_x) && (p.y == map.high_y);
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<State>();
      for(Point q : map.getAdj(p, false))
      {
        lst.add(new SS(q, cost + map.get(q)));

      }

      return lst;
    }

    @Override
    public double getEstimate()
    {
      return Math.abs(p.x - map.high_x) + Math.abs(p.y - map.high_y);
    }

    public double getCost()
    {
      return cost;
    }
    public String toString()
    {
      return p.toString();
    }


  }
}
