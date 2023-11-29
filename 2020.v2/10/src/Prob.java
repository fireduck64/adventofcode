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
  ArrayList<Long> al=new ArrayList<>();

  public Prob(Scanner scan)
  {
    TreeSet<Long> adapters = new TreeSet<>();
    adapters.addAll( In.numbers(scan));

    al.addAll(adapters);

    long joltage = 0L;

    int d1 = 0;
    int d3 = 0;

    for(long v : adapters)
    {
      long d = v - joltage;
      if (d == 1) d1++;
      if (d == 3) d3++;

      joltage = v;

    }
    d3++;
    System.out.println("Part 1: " + d1*d3);


    long p2 = 0;
    for(int i=0; i<al.size(); i++)
    {
      p2+=getOptions(i, 0L);
    }
    System.out.println("Part 2: " + p2);


  }


  HashMap<String, Long> memo = new HashMap<>(1024,0.5f);

  public long getOptions(int idx, long jolt)
  {
    String key = "" + idx +"," + jolt;

    if (memo.containsKey(key)) return memo.get(key);

    if (idx == al.size())
    {
      if (canConnect(jolt, al.get(idx-1)+3)) return 1L;
      //if (jolt + 3L >= al.get(idx-1)) return 1L;
      return 0L;
    }

    long sum = 0;
    if (canConnect(jolt, al.get(idx)))
    //if (jolt + 3L >= al.get(idx))
    {
      long j2 = al.get(idx);
      for(int j = idx+1; j<=al.size(); j++)
      {
        sum += getOptions(j, j2);
      }
    }

    memo.put(key, sum);


    return sum;
  }

  public boolean canConnect(long src, long adapter)
  {
    if (src < adapter)
    {
      if (src + 3L >= adapter) return true;

    }
    return false;

  }

}
