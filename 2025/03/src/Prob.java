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
    List<String> lines = In.lines(scan);
    int p1=0;
    for(String line : lines)
    {
      int best = 0;
      for(int i=0; i<line.length(); i++)
      for(int j=i+1; j<line.length(); j++)
      {
        String n = "" + line.charAt(i) + line.charAt(j);
        int v = Integer.parseInt(n);
        best = Math.max(best, v);

      }
      p1 += best;

    }

		return "" + p1;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
	  List<String> lines = In.lines(scan);
    long p2=0;
    for(String line : lines)
    {
      p2 += rec(line, 0, 12);
    }

		return "" + p2;
  }

  HashMap<String, Long> memo = new HashMap<>();

  public long rec(String line, int idx, int rem)
  {
    if (rem == 0) return 0;
    if (idx == line.length()) return -1L;

    String key = line + "/" + idx + "/" + rem;
    if (memo.containsKey(key)) return memo.get(key);

    // Use index
    int cur = Integer.parseInt("" + line.charAt(idx));

    long best = -1L;
    long v1 = rec(line, idx+1, rem-1);
    if (v1 >= 0)
    {
      if (v1 == 0) v1 = cur;
      else
      {
        v1 = Long.parseLong("" + cur + "" + v1);
      }
      best = Math.max(v1, best);
    }

    long v2 = rec(line, idx+1, rem);
    if (v2 >= 0)
    {
      best=Math.max(v2, best);
    }

    memo.put(key, best);

    return best;

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
