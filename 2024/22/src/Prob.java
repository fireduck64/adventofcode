import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    long sum = 0L;

    for(long n : In.longs(scan))
    {
      for(int i=0; i<2000; i++)
      {
        n = evolve(n);
      }
      sum +=n;
    }

		return "" + sum;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    Map<String, Long> total = new HashMap<>();

    for(long n : In.longs(scan))
    {
      Map<String, Long> map = getPrices(n);
      //System.out.println(map.size());
      //System.out.println(map);

      for(Map.Entry<String, Long> me : map.entrySet())
      {
        String key = me.getKey();
        if (!total.containsKey(key)) total.put(key, 0L);
        total.put(key, total.get(key) + me.getValue());

      }

    }
    long best =0L;
    for(long v : total.values())
    {
      best = Math.max(v, best);

    }

		return "" + best;
  }

  Map<String,Long> getPrices(long secret)
  {
    LinkedList<Long> seq=new LinkedList<>();
    HashMap<String, Long> out = new HashMap<>();
    HashSet<Long> visit = new HashSet<>();
    long last = secret;
    for(int i=0; i<2000; i++)
    //while(true)
    {
      long next = evolve(last);
      long diff = ((next % 10) - (last % 10));
      seq.add(diff);
      while(seq.size() > 4) seq.pollFirst();

      if (seq.size() == 4)
      {
        String key = seq.toString();
        if (!out.containsKey(key))
        {
          out.put(key, next % 10L);
          //System.out.println(out.size());
        }

        // I was accidentally solving for a harder problem of all sequences
        if (out.size() == 40951) break;
        if (visit.contains(next)) break;
        visit.add(next);

      }

      last=next;
    }
    return out;


  }

  long evolve(long in)
  {
    long secret = in;
    long mult = secret*64L;

    secret = mult ^ secret;
    secret = secret % 16777216L;

    long div = secret / 32L;
    secret = div ^ secret;
    secret = secret % 16777216L;

    long m3 = secret * 2048L;

    secret = m3 ^ secret;
    secret = secret % 16777216L;
    

    return secret;
  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
