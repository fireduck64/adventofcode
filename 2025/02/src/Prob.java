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
    List<String> input = Tok.en(scan.nextLine(), ",");

    long cnt = 0;
    for(String seg : input)
    {
      List<Long> vals = Tok.enl(seg, "-");
      long low = vals.get(0);
      long high = vals.get(1);


      for(long x = low; x<=high; x++)
      {
        if (testVal(x)) cnt+=x;

      }

    }
		return "" + cnt;
  }

  public boolean testVal(long x)
  {
    String str = "" + x;
    for(int i =0; i<str.length(); i++)
    {
      for(int j=i+1; j<str.length(); j++)
      {
        String sub = str.substring(i,j);
        if (sub.length() == 0) E.er("" + str + " " + i + " " + j);
        String dub = "" + sub + sub;
        if (str.equals(dub))
        {
          return true;
        }
        //if (str.contains(dub)) return true;

      }

    }

    return false;
  } 

  public boolean testVal2(long x)
  {
    String str = "" + x;
    for(int i =0; i<str.length(); i++)
    {
      for(int j=i+1; j<str.length(); j++)
      {
        String sub = str.substring(i,j);
        if (sub.length() == 0) E.er("" + str + " " + i + " " + j);
        String dub = "" + sub + sub;
        while(dub.length() < str.length()) dub = dub + sub;
        if (str.equals(dub))
        {
          return true;
        }
        //if (str.contains(dub)) return true;

      }

    }

    return false;
  } 


  public String Part2(Scanner scan)
    throws Exception
  {
	  List<String> input = Tok.en(scan.nextLine(), ",");

    long cnt = 0;
    for(String seg : input)
    {
      List<Long> vals = Tok.enl(seg, "-");
      long low = vals.get(0);
      long high = vals.get(1);


      for(long x = low; x<=high; x++)
      {
        if (testVal2(x)) cnt+=x;

      }

    }
		return "" + cnt;
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
