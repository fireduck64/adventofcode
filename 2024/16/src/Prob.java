import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map;

  Map2D<Integer> visits;

  public String Part1(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>('#');
    MapLoad.loadMap(map, scan);

    Point start= map.getAllPoints('S').get(0);

    SS fin = (SS) Search.search(new SS(start, Nav.E, 0, ImmutableSet.of())).get(0);

		return "" + fin.cost;
  }

  public class SS extends State
  {
    public Point loc;
    public Point dir;
    int cost;
    TreeSet<Point> path=new TreeSet<>();

    public SS(Point loc, Point dir, int cost, Set<Point> path)
    {
      this.loc = loc;
      this.dir = dir;
      this.cost = cost;

      this.path = new TreeSet<>();
      this.path.addAll(path);
      this.path.add(loc);

    }
    public String toString()
    {
      return "" + loc + "/" + dir;
    }

    public double getCost()
    {
      return cost;
    }

    public List<State> next()
    {

      List<State> lst = new LinkedList<>();

      lst.add(new SS(loc, Nav.turnRight(dir), cost+1000, path));
      lst.add(new SS(loc, Nav.turnLeft(dir), cost+1000, path));

      Point n = loc.add(dir);
      if (map.get(n) != '#')
      {
        lst.add(new SS(n, dir, cost+1, path));
      }


      return lst;

    }
    public boolean isTerm()
    {
      if (map.get(loc) == 'E') return true;
      return false;
    }

  }

  public String Part2(Scanner scan)
    throws Exception
  {
    map = new Map2D<Character>('#');
    MapLoad.loadMap(map, scan);

    Point start= map.getAllPoints('S').get(0);

    List<State> sols = Search.search(new SS(start, Nav.E, 0, ImmutableSet.of()));
    TreeSet<Point> ok=new TreeSet<>();
    System.out.println("solutions: " + sols.size());

    for(State s : sols)
    {
      SS ss = (SS) s;

      // I thought it was all the tiles that are in ALL of the solutions
      // not the set of tiles that are in any of the solutions

      //if (ok.size() == 0)
      //{
        ok.addAll(ss.path);
      /*}
      else
      {
        LinkedList<Point> to_remove=new LinkedList<>();
        for(Point p : ok)
        {
          if (!ss.path.contains(p))
          {
            to_remove.add(p);
          }

        }
        ok.removeAll(to_remove);

      }
      */

    }

		return "" + ok.size();
  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
