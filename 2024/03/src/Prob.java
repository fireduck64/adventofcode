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
    String line = In.oneString(scan);
    {

      int idx=0;
      while(true)
      {
        int n = line.indexOf("mul", idx);
        if (n == -1) break;
        idx = n;

        long v = processMul(line, idx);
        sum += v;

        idx+=1;

      }
    }

		return "" + sum;
  }

  public int processMul(String line, int idx)
  {
    int end = Math.min(idx+50, line.length());
    String sub = line.substring(idx, end);
    int open = sub.indexOf("(");
    if (open != 3) return 0;
    int close = sub.indexOf(")");
    if (close < open) return 0;

    String nums = sub.substring(open+1, close);

    return extractNumbers(nums);

    /*List<Integer> lst = Tok.ent(nums, ",");
    if (lst.size() == 2)
    if (lst.get(0) > 0)
    if (lst.get(0) < 1000)
    if (lst.get(1) > 0)
    if (lst.get(1) < 1000)
    {
      System.out.println(nums + " - " + sub + " " + lst);
      return lst.get(0) * lst.get(1);
    }

    return 0;*/

  }
  public int extractNumbers(String str)
  {
    StringBuilder a = new StringBuilder();
    StringBuilder b = new StringBuilder();

    int int_a = 0;
    int int_b = 0;
    int state = 0;
    for(int idx=0; idx<str.length(); idx++)
    {
      char z = str.charAt(idx);
      if (state == 0)
      {
        if (z == ',')
        {
          if (a.length() == 0) return 0;
          if (a.length() > 3) return 0;
          int_a = Integer.parseInt(a.toString());
          state=1;
        }
        else
        {
          if (z < '0') return 0;
          if (z > '9') return 0;
          a.append(z);
        }
      }
      else if (state == 1)
      {
          if (z < '0') return 0;
          if (z > '9') return 0;
          b.append(z);
      }
    }
    if (state != 1) return  0;

    if (b.length() == 0) return 0;
    if (b.length() > 3) return 0;

    int_b = Integer.parseInt(b.toString());
    //System.out.println(str);

    return int_a * int_b;

  }


  public String Part2(Scanner scan)
    throws Exception
  {
	
    long sum = 0;
    String line = In.oneString(scan);
    {

      int idx=0;
      while(true)
      {
        int n = line.indexOf("mul", idx);
        if (n == -1) break;
        idx = n;

        int do_idx = line.lastIndexOf("do()", idx);
        int dont_idx = line.lastIndexOf("don't()", idx);

        boolean enabled=true;

        /*if (do_idx <0)
        {
          if (dont_idx >= 0)
          {
            enabled=false;
          }
        }*/
        if (dont_idx > do_idx)
        {
          enabled=false;
        }
        //System.out.println("" + do_idx + " " + dont_idx + " " + enabled);

        if (enabled)
        {
          long v = processMul(line, idx);
          sum += v;
        }

        idx+=1;

      }
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
