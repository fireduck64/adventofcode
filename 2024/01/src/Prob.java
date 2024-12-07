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
    ArrayList<Integer> in = In.ints(scan);
    LinkedList<Integer> left = new LinkedList<>();
    LinkedList<Integer> right = new LinkedList<>();

    for(int i=0; i<in.size(); i+=2)
    {
      left.add(in.get(i));
    }

    for(int i=1; i<in.size(); i+=2)
    {
      right.add(in.get(i));
    }

    Collections.sort(left);
    Collections.sort(right);

    int diff = 0;
    while(left.size()>0)
    {
      int a = left.pollFirst();
      int b = right.pollFirst();
      diff += Math.abs(a-b);

    }

		return "" + diff;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    ArrayList<Integer> in = In.ints(scan);
    LinkedList<Integer> left = new LinkedList<>();
    LinkedList<Integer> right = new LinkedList<>();

    for(int i=0; i<in.size(); i+=2)
    {
      left.add(in.get(i));
    }

    for(int i=1; i<in.size(); i+=2)
    {
      right.add(in.get(i));
    }

    Collections.sort(left);
    Collections.sort(right);

    long score = 0;
    for(int i=0; i<left.size(); i++)
    {
      int a = left.get(i);
      int count =0;
      for(int j=0; j<right.size(); j++)
      {
        if (right.get(j) == a) count++;

      }
      score += a * count;


    }


		return "" + score;
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
