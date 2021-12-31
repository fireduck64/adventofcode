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
  Map2D<Character> mapo = new Map2D<>('#');

  TreeSet<Character> wires = new TreeSet<>();
  Point start_loc;

  boolean part2=false;
  

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(mapo, scan);
    mapo.print();


    for(char z : mapo.getCounts().keySet())
    {
      if (z >='0')
      if (z <='9')
      {
        wires.add(z);
      }
    }
    for(Point p : mapo.getAllPoints())
    {
      if (mapo.get(p)=='0') start_loc = p;
    }
    System.out.println("Wires needed: " + wires);

      SS fin1 = (SS) Search.search(new SS(0, ImmutableSet.of('0'), start_loc));
      part2=true;
      SS fin2 = (SS) Search.search(new SS(0, ImmutableSet.of('0'), start_loc));
      System.out.println("Part 1: " + fin1.getCost());
      System.out.println("Part 2: " + fin2.getCost());


  }

  public class SS extends State
  {
    int cost;
    TreeSet<Character> touched=new TreeSet<>();
    Point loc;

    public SS(int cost, Collection<Character> touched, Point loc)
    {
      this.cost = cost;
      this.touched.addAll(touched);
      this.loc = loc;
    }

    public String toString()
    {
      return loc.toString() + "/" + touched;
    }

    public boolean isTerm()
    {
      if (part2)
      {
        return ((loc.equals(start_loc) && (touched.size() == wires.size())));

      }
      else
      {
        return (touched.size() == wires.size());
      }
    }
    public double getCost(){return cost; }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      for(Point n : mapo.getAdj(loc, false))
      {
        char z = mapo.get(n);
        if (z != '#')
        {
          TreeSet<Character> t2 = new TreeSet<>();
          t2.addAll(touched);
          if (z != '.')
          {
            t2.add(z);
          }
          lst.add(new SS(cost+1, t2, n));
        }
      }

      return lst;
    }

  }



}
