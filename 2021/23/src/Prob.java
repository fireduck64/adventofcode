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

  Map2D<Character> input = new Map2D<Character>('#');
  Map2D<Character> target = new Map2D<Character>('#');

  public Prob(Scanner scan)
    throws Exception
  {

    {
      MapLoad.loadMap(input, new Scanner(new FileInputStream("input")));
      //input.print();

      MapLoad.loadMap(target, new Scanner(new FileInputStream("target")));

      //target.print();

      //SS sol = (SS)Search.search(new SS(0, input.copy()));
      SS sol = (SS)Search.searchPara(new SS(0, input.copy()));
      if (sol == null) System.out.println("No solution");
      System.out.println("Part 1: " + sol.getCost());
    }
    {
      input = new Map2D<Character>('#');
      target = new Map2D<Character>('#');
      MapLoad.loadMap(input, new Scanner(new FileInputStream("input2")));
      //input.print();

      MapLoad.loadMap(target, new Scanner(new FileInputStream("target2")));

      //target.print();

      //SS sol = (SS)Search.search(new SS(0, input.copy()));
      SS sol = (SS)Search.searchPara(new SS(0, input.copy()));
      if (sol == null) System.out.println("No solution");
      System.out.println("Part 2: " + sol.getCost());


    }

  }

  public class SS extends State
  {
    long cost;
    Map2D<Character> map;

    public SS(long cost, Map2D<Character> map)
    {
      this.cost = cost;
      this.map = map;

    }


    public boolean isTerm()
    {
      for(Point p : target.getAllPoints())
      {
        char z = target.get(p);
        if (isBeast(z))
        {
          if (map.get(p) != z) return false;
        }

      }
      return true;
    }
    public String toString()
    {
      return "\n" + map.getPrintOut();
    }
    public double getCost()
    {
      return cost;
    }

    @Override
    public double getEstimate()
    {
      long est=0;

      for(Point p : map.getAllPoints())
      {
        char z = map.get(p);
        if (isBeast(z))
        {
          // if not in home
          if (target.get(p) != z)
          {
            Point t = null;
            if (z=='A') t=new Point(3,2);
            if (z=='B') t=new Point(5,2);
            if (z=='C') t=new Point(7,2);
            if (z=='D') t=new Point(9,2);
            est += getMoveCostEst(z, p, t);

          }


        }

      }
      return est;

    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      // Moves to hallway
      for(Point p : map.getAllPoints())
      {
        char z = map.get(p);
        // Am I home already?
        /*if (target.get(p)==z)
        {
          continue;
        }*/
        if (isBeast(z))
        {
          Set<Point> moves = validMoves(p, map);
          if (!isValidHall(p))
          {
            for(Point m : moves)
            {
              if (isValidHall(m))
              {
                Map2D<Character> copy = map.copy();
                copy.set(p, '.');
                copy.set(m, z);
                lst.add(new SS(cost + getMoveCost(z, p, m), copy));
              }

            }
          }
          else
          { // in hall
            for(Point m : moves)
            {
              if (isGoodHomeMove(m, z, map))
              {
                Map2D<Character> copy = map.copy();
                copy.set(p, '.');
                copy.set(m, z);
                lst.add(new SS(cost + getMoveCost(z, p, m), copy));
              }
            }
          }
        }

      }

      return lst;

    }

  }

  public boolean isGoodHomeMove(Point m, char z, Map2D<Character> map)
  {
    if (target.get(m) != z) return false;
    for(int i=-3; i<=3; i++)
    {
      char o = map.get(m.x, m.y+i);
      if (isBeast(o))
      {
        if (o != z) 
        {
          return false;
        }
      }
    }
    return true;

  }
  public boolean isBeast(char z)
  {
    if ('A' <= z)
    if (z <= 'Z')
      return true;
    return false;
  }
  public long getMoveCostEst(char z, Point a, Point b)
  {
    long base = 1;
    if (z=='B') base=10;
    if (z=='C') base=100;
    if (z=='D') base=1000;
    long d=0;
    // To hall
    d+=Math.abs(a.y - 1);
    // across hall
    d+=Math.abs(a.x - b.x);
    // Into room
    d+=Math.abs(b.y - 1);

    return d*base;

  }
  public long getMoveCost(char z, Point a, Point b)
  {
    long base = 1;
    if (z=='B') base=10;
    if (z=='C') base=100;
    if (z=='D') base=1000;

    return a.getDistM(b) * base;

  }

  public boolean isValidHall(Point p)
  {
    if (p.y != 1) return false;
    if (target.get(p) != '.') return false;
    if (target.get(p.x, p.y+1) != '#') return false; //no blocking doors

    return true;
  }
  public Set<Point> validMoves(Point start, Map2D<Character> map)
  {
    TreeSet<Point> v_set = new TreeSet<>();

    LinkedList<Point> queue=new LinkedList<>();
    queue.addAll( map.getAdj(start,false));

    while(queue.size() > 0)
    {
      Point q = queue.pollFirst();
      if (map.get(q) == '.')
      {
        if (!v_set.contains(q))
        {
          v_set.add(q);
          queue.addAll(map.getAdj(q, false));
        }
      }
    }
    return v_set;

  }

}
