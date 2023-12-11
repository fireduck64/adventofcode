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

  Map2D<Character> input = new Map2D<Character>('.');
  TreeSet<Long> pop_x = new TreeSet<>();
  TreeSet<Long> pop_y = new TreeSet<>();
  ArrayList<Point> star_lst = new ArrayList<>();
  TreeMap<Point, Integer> star_map = new TreeMap<>();

  TreeMap<Integer, TreeMap<Integer, Long> > cost_map = new TreeMap<>();

  long empty_cost = 2;

  public Prob(Scanner scan)
  {

    MapLoad.loadMap(input, scan);

    input.print();

    for(Point p : input.getAllPoints())
    {
      if (input.get(p) == '#')
      {
        pop_x.add(p.x);
        pop_y.add(p.y);

        star_map.put(p, star_lst.size());
        star_lst.add(p);

      }

    }

    long p1 = 0;
    empty_cost = 2;
    for(int i=0; i<star_lst.size(); i++)
    {
      cost_map.put(i, new TreeMap<Integer, Long>());
      System.out.println("STAR " + i);
      Search.search(new SS(star_lst.get(i), star_lst.get(i), 0, null));

      for(int j=i+1; j<star_lst.size(); j++)
      {
        //SS answer = (SS)Search.search(new SS(star_lst.get(i), 0, star_lst.get(j)));
        //p1 += (int) answer.getCost();
        p1 += cost_map.get(i).get(j);

      }
    }
    System.out.println("Part 1: " + p1);

    long p2 = 0;
    cost_map.clear();
    empty_cost = 1000000;
    for(int i=0; i<star_lst.size(); i++)
    {
      cost_map.put(i, new TreeMap<Integer, Long>());
      System.out.println("STAR " + i);
      Search.search(new SS(star_lst.get(i), star_lst.get(i), 0, null));

      for(int j=i+1; j<star_lst.size(); j++)
      {
        //SS answer = (SS)Search.search(new SS(star_lst.get(i), 0, star_lst.get(j)));
        //p1 += (int) answer.getCost();
        p2 += cost_map.get(i).get(j);

      }
    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);



    

  }

  public class SS extends State
  {

    Point loc;
    long cost;
    Point target;
    Point start;
    public SS(Point start, Point loc, long cost, Point target)
    {
      this.start = start;
      this.loc = loc;
      this.cost = cost;
      this.target = target;
    }
    public double getCost(){return cost;}
    public List<State> next()
    {
      if (star_map.containsKey(loc))
      {
        int o_idx = star_map.get(loc);
        int s_idx = star_map.get(start);
        cost_map.get(s_idx).put(o_idx, cost);

      }
      LinkedList<State> lst = new LinkedList<>();
      for(Point q : input.getAdj(loc, false))
      {
        if (q.x >= input.low_x)
        if (q.x <= input.high_x)
        if (q.y <= input.high_y)
        if (q.y >= input.low_y)
        {


          long c = cost+empty_cost;
          if (pop_x.contains(q.x))
          if (pop_y.contains(q.y))
          {
            c = cost+1;
          }
          lst.add(new SS(start, q, c, target));
        }

      }
      return lst;

    }
    public String toString(){
      return loc.toString();
    }

    /** is the end state we are looking for */
    public boolean isTerm()
    {
      if (target != null)
      {
        return loc.equals(target);
      }
      else
      {
        return cost_map.size() == star_lst.size();

      }
    }

  } 

}
