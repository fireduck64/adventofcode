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


  boolean p2=false;

  public Prob(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(input, scan);

    for(int i=0; i<14; i++)
    {
      System.out.println("Time: " + i);
      getMapAtTime(i).print();
    }

    SS p1 = (SS) Search.search(new SS(0, new Point(1,0),0));
    System.out.println("Part 1: " + p1.steps);

    p2=true;
    SS p2 = (SS) Search.searchPara(new SS(0, new Point(1,0),1));
    System.out.println("Part 1: " + p1.steps);
    System.out.println("Part 2: " + p2.steps);


  }

  TreeMap<Integer, Map2D<Character> > time_maps = new TreeMap<>();

  public synchronized Map2D<Character> getMapAtTime(int n)
  {
    if (n==0) return input;

    if (time_maps.containsKey(n)) return time_maps.get(n);

    Map2D<Character> m = new Map2D<Character>(' ');
    for(Point p : input.getAllPoints())
    {
      char z = input.get(p);
      m.set(p, '.');
      if (z=='#') m.set(p,'#');
      if (z=='E') m.set(p,'E');
    }

    long y_sz = input.high_y - input.low_y - 1;
    long x_sz = input.high_x - input.low_x - 1;

    for(Point p : input.getAllPoints())
    {
      char z = input.get(p);
      Point dir = null;
      Point pp= null;
      if (z=='^')
      {
        long loc = (p.y - 1 -n);
        while (loc < 0) loc+=y_sz;
        pp = new Point(p.x, loc+1);
      }
      if (z=='v')
      {
        long loc = (p.y - 1 + n);
        while (loc >= y_sz ) loc-=y_sz;
        pp = new Point(p.x, loc+1);
      }
      if (z=='<')
      {
        long loc = (p.x - 1 -n);
        while (loc < 0) loc+=x_sz;
        pp = new Point(loc+1, p.y);

      }
      if (z=='>')
      {
        long loc = (p.x - 1 +n);
        while (loc >= x_sz ) loc-=x_sz;
        pp = new Point(loc+1, p.y);

      }
      if (pp != null)
      {
        m.set(pp, z);
      }

    }

    

    time_maps.put(n, m);
    return m;


  }


  public class SS extends State
  {
    int steps;
    Point loc;
    int phase;
    public SS(int steps, Point loc, int phase)
    {
      this.steps = steps;
      this.loc = loc;
      this.phase = phase;

      if ((phase == 1) && (loc.y == input.high_y))
      {
        this.phase=2;
      }
      if ((phase==2) && (loc.y == 0))
      {
        this.phase=3;
      }

    }
     // phase 1 - to goal
     // phase 2 - return
     // phase 3 - to goal again

    public boolean isTerm()
    {
      if (phase == 0)
      {
        return (loc.y == input.high_y);
      }
      if (phase == 3)
      {
        return (loc.y == input.high_y);

      }
      return false;  

    }

    public double getCost()
    {
      return steps;
    }

    public String toString()
    {

      return loc.toString() + " " + steps + " " + phase;
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      Map2D<Character> m = getMapAtTime(steps+1);
      /*m = m.copy();
      m.set(loc, 'E');
      System.out.println(loc);
      System.out.println(steps);
      m.print();*/

      m = getMapAtTime(steps+1); 

      // Stay
      {
        if (m.get(loc)=='.')
        {
          lst.add(new SS(steps+1, loc, phase));
        }

      }
      for(Point p : m.getAdj(loc, false))
      {
        if (m.get(p) == '.')
        {
          lst.add(new SS(steps+1, p, phase));
        }
      }

      return lst;
    }

  }

}
