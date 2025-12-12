import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  TreeMap<String, TreeSet<String> > links;



  public String Part1(Scanner scan)
    throws Exception
  {
    memo.clear();
    links = new TreeMap<>();
    for(String line : In.lines(scan))
    {
      line = line.replace(":","");
      List<String> lst = Tok.en(line, " ");
      String name = lst.get(0);
      links.put(name, new TreeSet<String>());
      for(int i=1; i<lst.size(); i++)
      {
        links.get(name).add(lst.get(i));
      }

    }
		return "" + countPaths("you", "out");
  }

  HashMap<String, Long> memo = new HashMap<>();
  public long countPaths(String loc,String target)
  {
    if (loc.equals(target)) return 1L;

    String key="" + loc + "/" + target;
    if (memo.containsKey(key)) return memo.get(key);

    long count = 0;
    if (links.containsKey(loc))
    for(String s : links.get(loc))
    {
      count += countPaths(s, target);
    }

    memo.put(key, count);
    return count;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    memo.clear();
    links = new TreeMap<>();
    for(String line : In.lines(scan))
    {
      line = line.replace(":","");
      List<String> lst = Tok.en(line, " ");
      String name = lst.get(0);
      links.put(name, new TreeSet<String>());
      for(int i=1; i<lst.size(); i++)
      {
        links.get(name).add(lst.get(i));
      }
    }

    long a = countPaths("svr", "dac") * countPaths("dac", "fft") * countPaths("fft", "out");
    long b = countPaths("svr", "fft") * countPaths("fft", "dac") * countPaths("dac", "out");
    long p2 = a + b;
		return ""+p2 ;
  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample1");
      new Prob("sample2");
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
