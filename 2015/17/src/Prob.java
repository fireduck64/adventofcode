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
  ArrayList<Integer> jugs=new ArrayList<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextInt())
    {
      jugs.add(scan.nextInt());

    }
    System.out.println("Jugs: " + jugs.size());

    System.out.println("Part 1: " + rec(0, 150, 0));
    System.out.println("Part 2: " + count.firstEntry().getValue());
    System.out.println(count);
  }

  TreeMap<Integer, Long> count=new TreeMap<>();

  public long rec(int idx, int rem, int used)
  {
    if (rem < 0) return 0L;
    if (idx == jugs.size())
    {
      if (rem == 0)
      { 
        if (!count.containsKey(used)) count.put(used, 0L);
        count.put(used, count.get(used) + 1L);
        return 1L;
      }
      return 0L;
    }

    long s = 0;
    s += rec(idx+1, rem, used);
    s += rec(idx+1, rem - jugs.get(idx), used+1);

    return s;

  }

}
