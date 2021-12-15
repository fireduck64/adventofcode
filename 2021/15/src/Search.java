
import java.util.*;

import com.google.common.collect.TreeMultimap;

public class Search
{

  public static State search(State start)
  {
    //TreeMultimap<Double, State> queue = TreeMultimap.create();
    TreeMap<Double, State> queue = new TreeMap<>();


    HashSet<String> visited = new HashSet<>();

    Random rnd = new Random();
    queue.put(0.0, start);

    int count = 0;
    while(!queue.isEmpty())
    {
      count++;
      
      //Map.Entry<Double, Collection<State> > me =  queue.asMap().firstEntry();

      //Iterator<State> I = me.getValue().iterator();
      //State s = I.next();
      //I.remove();
      State s = queue.pollFirstEntry().getValue();

      if (count % 100000 == 0)
      {
        System.out.println(String.format("Search %d - %s", count, s));
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
          queue.put(n.getCost() + n.getEstimate() + rnd.nextDouble()/1e6, n);
        }
      }

    }

    return null;

  }


}
