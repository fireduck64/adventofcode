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
    long sum = 0;
    for(String line : In.lines(scan))
    {
      List<Long> lst = Tok.enl(line.replace(":",""), " ");
      long target = lst.get(0);
      List<Long> vals = lst.subList(1, lst.size());
      sum += check(target, vals);

    }
    
		return "" + sum;
  }

  public long check(long target, List<Long> vals)
  {
    if (checkRec(target, vals, 1, vals.get(0)))
    {
      return target;
    }
    return 0L;

  }
  public boolean checkRec(long target, List<Long> vals, int idx, long in)
  {
    if (idx == vals.size()) 
    {
      return (target == in);
    }

    if (checkRec(target, vals, idx+1, in + vals.get(idx))) return true;
    if (checkRec(target, vals, idx+1, in * vals.get(idx))) return true;
    return false;



  }

  public long check2(long target, List<Long> vals)
  {
    if (checkRec2(target, vals, 1, vals.get(0)))
    {
      return target;
    }
    return 0L;

  }
  public boolean checkRec2(long target, List<Long> vals, int idx, long in)
  {
    if (idx == vals.size()) 
    {
      return (target == in);
    }

    if (checkRec2(target, vals, idx+1, in + vals.get(idx))) return true;
    if (checkRec2(target, vals, idx+1, in * vals.get(idx))) return true;
    if (checkRec2(target, vals, idx+1, Long.parseLong("" + in + "" + vals.get(idx)))) return true;
    return false;



  }



  public String Part2(Scanner scan)
    throws Exception
  {
	    long sum = 0;
    for(String line : In.lines(scan))
    {
      List<Long> lst = Tok.enl(line.replace(":",""), " ");
      long target = lst.get(0);
      List<Long> vals = lst.subList(1, lst.size());
      sum += check2(target, vals);

    }
    
		return "" + sum;
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
