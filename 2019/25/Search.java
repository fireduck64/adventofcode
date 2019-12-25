
import java.util.*;

public class Search
{

  public static State search(State start)
  {
    TreeMap<Double, State> queue = new TreeMap<>();;
    HashSet<String> visited = new HashSet<>();
    Random rnd = new Random();
    queue.put(0.0, start);

    int count = 0;
    while(!queue.isEmpty())
    {
      count++;

      State s = queue.pollFirstEntry().getValue();
      if (count % 1000 == 0)
      {
        System.out.println(String.format("Search %d - %s", count, s));
      }
      System.out.println(String.format("Search %d - %s", count, s));
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
          queue.put(n.getCost() + n.getEstimate() + rnd.nextDouble(), n);
        }
      }

    }

    return null;

  }


}
