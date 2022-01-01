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

  TreeMap<String, TreeMap<String, Integer> > dmap = new TreeMap<>();
  TreeSet<String> all=new TreeSet<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      int dist = Tok.ent(line, " ").get(0);
      List<String> parts = Tok.en(line, " ");
      addDist(parts.get(0), parts.get(2), dist);
    }
    System.out.println("Locations: " + all.size());

    SS fin = (SS) Search.search(new SS(null, 0, ImmutableSet.of()));
    System.out.println("Part 1: " + fin.getCost());

    SS fin2 = (SS) SearchWorst.search(new SS(null, 0, ImmutableSet.of()));
    System.out.println("Part 2: " + fin2.getCost());

  }

  public class SS extends State
  {
    String loc;
    int cost;
    TreeSet<String> visit;

    public SS(String loc, int cost, Set<String> visit)
    {
      this.loc = loc;
      this.cost = cost;
      this.visit = new TreeSet<String>();
      this.visit.addAll(visit);
    }
    public String toString()
    {
      return loc + "/" + visit.toString();
    }
    public double getCost()
    {
      return cost;
    }
    public List<State> next()
    {
      List<State> lst = new LinkedList<State>();
      if (loc == null)
      {
        for(String n : all)
        {
          lst.add(new SS(n, 0, ImmutableSet.of(n)));

        }

      }
      else
      {
        for(String n : all)
        {
          if (!visit.contains(n))
          {
            int c2 = cost + dmap.get(loc).get(n);
            TreeSet<String> v2 = new TreeSet<>();
            v2.addAll(visit);
            v2.add(n);
            lst.add(new SS(n, c2, v2));
          }

        }
      }
      return lst;
    }
    public boolean isTerm()
    {
      return (visit.size() == all.size());
    }


  }

  public void addDist(String a, String b, int dist)
  {
    all.add(a);
    all.add(b);
    addDist2(a,b,dist);
    addDist2(b,a,dist);
  }
  public void addDist2(String a, String b, int dist)
  {
    if (!dmap.containsKey(a)) dmap.put(a, new TreeMap<String, Integer>());

    dmap.get(a).put(b,dist);

  }
    



}
