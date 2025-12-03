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
    int count =0;
    int dial = 50;
    for(String line : In.lines(scan))
    {
      char z = line.charAt(0);
      int n = Integer.parseInt(line.substring(1));

      if (z == 'L') dial-=n;
      if (z == 'R') dial+=n;

      while (dial >= 100) dial-=100;
      while (dial < 0) dial+=100;

      if (dial == 0) count++;
      //System.out.println("After " + line + " " + dial);

    }
		return "" + count;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
	  int count =0;
    int dial = 50;
    for(String line : In.lines(scan))
    {
      char z = line.charAt(0);
      int n = Integer.parseInt(line.substring(1));

      for(int i =0 ;i<n; i++)
      {
        if (z == 'L') dial-=1;
        if (z == 'R') dial+=1;
        if (dial == 100) count++;
        if (dial == 0) count++;
        while (dial >= 100) { dial-=100;}
        while (dial < 0) { dial+=100;}
      }


      //System.out.println("After " + line + " " + dial);

    }
		return "" + count;
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
