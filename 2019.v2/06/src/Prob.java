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
  TreeMap<String, TreeSet<String> > orbits = new TreeMap<>();
  TreeMap<String, String> rev = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      List<String> lst = Tok.en(scan.nextLine(), ")");
      String a = lst.get(0);
      String b = lst.get(1);
      addOrbit(a,b);
    }

    int p1 = 0;
    for(String a : rev.keySet())
    {
      p1 += countOrbits(a);
    }
    System.out.println("Part 1: " + p1);

    int cost = (int)(Math.round(Search.search(new SS(0, "YOU")).getCost()) - 2.0);
    System.out.println("Part 2: " + cost);

  }

  public void addOrbit(String a, String b)
  {
    if (!orbits.containsKey(a))
      orbits.put(a, new TreeSet<String>());
    orbits.get(a).add(b);

    rev.put(b,a);

  }
  public int countOrbits(String a)
  {
    int cnt = 0;
    if (rev.containsKey(a))
    {
      cnt++;
      cnt+=countOrbits(rev.get(a));
    }

    return cnt;
  }

  public class SS extends State
  {
    public SS(int cost, String loc)
    {
      this.cost = cost;
      this.loc = loc;

    }
    int cost;
    String loc;
    public double getCost(){return cost;}

    public boolean isTerm(){return loc.equals("SAN");}
    public String toString(){return loc;}
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      if (rev.containsKey(loc))
      {
        lst.add(new SS(cost+1, rev.get(loc)));
      }
      if (orbits.containsKey(loc))
      {
      for(String b : orbits.get(loc))
      {
        lst.add(new SS(cost+1, b));

      }
      }
      return lst;

    }

  }

}
