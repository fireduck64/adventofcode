
import java.util.*;

import com.google.common.collect.TreeMultimap;

public class Search
{

  public static State search(State start)
  {
    //TreeMultimap<Double, State> queue = TreeMultimap.create();
    TreeMap<StateSort, State> queue = new TreeMap<>();
    long sort_idx = 0;

    HashSet<String> visited = new HashSet<>();

    Random rnd = new Random();
    queue.put(new StateSort(start.getCost(), sort_idx), start);
    sort_idx++;

    int count = 0;
    while(!queue.isEmpty())
    {
      count++;
      
      //Map.Entry<Double, Collection<State> > me =  queue.asMap().firstEntry();

      //Iterator<State> I = me.getValue().iterator();
      //State s = I.next();
      //I.remove();
      State s = queue.pollFirstEntry().getValue();

      if (count % 10000 == 0)
      {
        System.out.println(String.format("Search %d %f - %s", count, s.getCost(), s));
      }
      if (s.isTerm())
      {
        System.out.println("Search solution: " + s);
        return s;
      }

      String hash = s.getHash();

      if (!visited.contains(hash))
      {
        visited.add(hash);

        for(State n : s.next())
        {
          queue.put(new StateSort(n.getCost() + n.getEstimate(), sort_idx), n);
          sort_idx++;
          //queue.put(n.getCost() + n.getEstimate() + rnd.nextDouble()/1e6, n);
        }
      }

    }

    return null;

  }

  public static class StateSort implements Comparable<StateSort>
  {
    private double cost;
    private long idx;

    public StateSort(double cost, long idx)
    {
      this.cost = cost;
      this.idx = idx;

    }
    public int compareTo(StateSort ss)
    {
      if (cost < ss.cost) return -1;
      if (cost > ss.cost) return 1;
      if (idx < ss.idx) return -1;
      if (idx > ss.idx) return 1;

      return 0;

    }

    @Override
    public int hashCode()
    {
      E.er();
      return 0;
    }

    @Override
    public boolean equals(Object o)
    {
      E.er();
      return false;

    }
  }


}
