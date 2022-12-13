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
  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map, scan);
    Point start = null;
    LinkedList<State> starts = new LinkedList<>();
    for(Point p : map.getAllPoints())
    {
      if (map.get(p)=='S') start = p;
      if (map.get(p)=='S') starts.add(new SS(p,0));
      if (map.get(p)=='a') starts.add(new SS(p, 0));
      
    }

    SS ss = (SS)Search.search(new SS(start, 0));
    System.out.println("Part 1: " + ss.steps);

    System.out.println("Starts: " + starts.size());

    ss = (SS)Search.searchM(starts);
    System.out.println("Part 2: " + ss.steps);

  }


  public class SS extends State
  {
    Point loc;
    int steps;

    public SS(Point loc, int steps)
    {
      this.loc = loc;
      this.steps = steps;
    }

    public double getCost(){return steps;}
    public boolean isTerm()
    {
      return map.get(loc)=='E';

    }

    public String toString(){return loc.toString(); }

    public List<State> next()
    {
      List<State> lst = new LinkedList<>();
      for(Point n : map.getAdj(loc, false))
      {
        char z = map.get(n);
        boolean allow = false;
        if ((z=='E') && (map.get(loc) >= 'y'))allow=true;
        if (map.get(loc)=='S') allow=true;
        if ((z>='a') && (z<= map.get(loc))) allow=true;
        if ((z>='a') && (z-1 <= map.get(loc))) allow=true;

        if (allow)
        {
          lst.add(new SS(n, steps+1));
        }

      }

      return lst;

    }
  }

}
