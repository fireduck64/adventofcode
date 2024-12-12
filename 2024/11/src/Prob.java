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
    List<Long> lst = new LinkedList<>();
    String line = scan.nextLine();
    lst.addAll( Tok.enl(line, " "));

    for(int i=0; i<25; i++)
    {
      lst = process(lst);
    }

     
		return "" + lst.size();
  }

  public List<Long> process(List<Long> in)
  {
    LinkedList<Long> out = new LinkedList<>();

    for(long v : in)
    {
      String s = "" + v;
      if (v == 0L)
      {
        out.add(1L);
      }
      else if (s.length() % 2 == 0)
      {
        long a = Long.parseLong(s.substring(0, s.length()/2));
        long b = Long.parseLong(s.substring(s.length()/2));
        out.add(a);
        out.add(b);
      }
      else
      {
        out.add(v * 2024L);
      }

    }

    return out;

  }

  public String Part2(Scanner scan)
    throws Exception
  {
    List<Long> lst = new LinkedList<>();
    String line = scan.nextLine();
    lst.addAll( Tok.enl(line, " "));

    long sum = 0;
    for(long v : lst )
    {
      sum += getCount(v, 75);

    }
    System.out.println("Memo items: " + memo.size());

		return "" + sum;

  }
  
  HashMap<String, Long> memo = new HashMap<>();

  public long getCount(long v, int levels)
  {
    if (levels == 0) return 1L;

      String s = "" + v;
      String key = s +"/" + levels;
      if (memo.containsKey(key))
      {
        return memo.get(key);
      }
      if (v == 0L)
      {
        return getCount(1L, levels-1);
      }
      else if (s.length() % 2 == 0)
      {
        long a = Long.parseLong(s.substring(0, s.length()/2));
        long b = Long.parseLong(s.substring(s.length()/2));

        long t = getCount(a, levels-1) + getCount(b, levels-1);
        memo.put(key,t);
        return t;
      }
      else
      {
        return getCount(v*2024L, levels-1);
      }


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
