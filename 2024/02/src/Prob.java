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
    int safe = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<Integer> lst = Tok.ent(line, " ");
      if (check(lst)) safe++;
    }
		return "" + safe;
  }

  boolean check(List<Integer> lst)
  {
      boolean inc = true;
      boolean dec = true;
      boolean diff = true;
      for(int i=0; i<lst.size()-1; i++)
      {
        int a = lst.get(i);
        int b = lst.get(i+1);
        if (a < b) inc=false;
        if (a > b) dec = false;
        int d = Math.abs(a-b);
        if (d < 1) diff=false;
        if (d > 3) diff = false;
        

      }

      if (diff)
      {
        if (inc || dec)
        {
          return true;

        }

      }
      return false;
   
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    int safe = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<Integer> lst = Tok.ent(line, " ");

      int s = 0;

      if (check(lst)) s++;

      for(int i=0; i<lst.size(); i++)
      {
        LinkedList<Integer> l2 = new LinkedList<>();
        l2.addAll(lst);
        l2.remove(i);
        if (check(l2)) s++;

      }

      
      if (s > 0) safe++;
    }
		return "" + safe;
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
