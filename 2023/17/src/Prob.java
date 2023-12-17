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

  Map2D<Character> input = new Map2D<Character>(' ');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(input, scan);

    SS p1 = (SS)Search.search(new SS(new Point(0,0), 0, Nav.E, 0));

    System.out.println("Part 1: " + p1.getCost());
    SS2 p2 = (SS2)Search.searchM(
     ImmutableList.of(
        new SS2(new Point(0,0), 0, Nav.E, 0),
        new SS2(new Point(0,0), 0, Nav.S, 0))
    );

    System.out.println("Part 2: " + p2.getCost());

  }

  // Search state object for part 2
  public class SS2 extends State
  {
    final Point loc;
    final int cost;
    final Point dir;
    final int dir_moves;
    public SS2(Point loc, int cost, Point dir, int dir_moves)
    {
      this.loc = loc;
      this.cost = cost;
      this.dir = dir;
      this.dir_moves = dir_moves;

    }

    public boolean isTerm()
    {
      if (dir_moves >= 4)
      if (dir_moves <= 10)
      if (loc.x == input.high_x)
      if (loc.y == input.high_y)
      {
        return true;
      }
      return false;


    }
    public String toString()
    {
      return "" + loc + " " + dir + " " + dir_moves;

    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (dir_moves > 10) return lst;

      // forward

      if (dir_moves <= 9)
      {
        Point np = loc.add(dir);
        int c = getCostP(np);
        if (c >= 0)
        {
        lst.add(new SS2(np, cost+c, dir, dir_moves+1));
        }
      }
      if (dir_moves >= 4)
      {
      { // left
        Point nd = Nav.turnLeft(dir);
        Point np = loc.add(nd);

        int c = getCostP(np);
        if (c >= 0)
        {
          lst.add(new SS2(np, cost+c, nd, 1));
        }

      }
      { // right
        Point nd = Nav.turnRight(dir);
        Point np = loc.add(nd);

        int c = getCostP(np);
        if (c >= 0)
        {
          lst.add(new SS2(np, cost+c, nd, 1));
        }

      }
      }

      return lst;



    }
    public double getCost()
    {
      return cost;
    }

  }

  // Search state object for part 1
  public class SS extends State
  {
    final Point loc;
    final int cost;
    final Point dir;
    final int dir_moves;
    public SS(Point loc, int cost, Point dir, int dir_moves)
    {
      this.loc = loc;
      this.cost = cost;
      this.dir = dir;
      this.dir_moves = dir_moves;

    }

    public boolean isTerm()
    {
      if (dir_moves <= 3)
      if (loc.x == input.high_x)
      if (loc.y == input.high_y)
      {
        return true;
      }
      return false;


    }
    public String toString()
    {
      return "" + loc + " " + dir + " " + dir_moves;

    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (dir_moves > 3) return lst;

      // forward

      if (dir_moves <= 2)
      {
        Point np = loc.add(dir);
        int c = getCostP(np);
        if (c >= 0)
        {
        lst.add(new SS(np, cost+c, dir, dir_moves+1));
        }
      }
      { // left
        Point nd = Nav.turnLeft(dir);
        Point np = loc.add(nd);

        int c = getCostP(np);
        if (c >= 0)
        {
          lst.add(new SS(np, cost+c, nd, 1));
        }

      }
      { // right
        Point nd = Nav.turnRight(dir);
        Point np = loc.add(nd);

        int c = getCostP(np);
        if (c >= 0)
        {
          lst.add(new SS(np, cost+c, nd, 1));
        }

      }

      return lst;



    }
    public double getCost()
    {
      return cost;
    }

  }

  public int getCostP(Point p)
  {
    char z = input.get(p);

    if (z == ' ') return -1;

    return (int)(z - '0');

  }
}
