import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  TreeSet<String> towels=new TreeSet<>();
  ArrayList<String> labels = new ArrayList<>();

  public void read(Scanner scan)
  {
    towels.clear();
    towels.addAll(Tok.en(scan.nextLine().replace(" ",""), ","));

    scan.nextLine();
    labels.clear();

    labels.addAll(In.lines(scan));

  }
  public String Part1(Scanner scan)
    throws Exception
  {
    long good = 0;

    read(scan);

    for(String lbl : labels)
    {
      if (check(lbl)) good++;


    }
		return "" + good;
  }

  public boolean check(String in)
  {
    if (towels.contains(in)) return true;
    if (in.length() == 0) return true;

    for(String t : towels)
    {
      if (in.startsWith(t))
      {
        if (check(in.substring(t.length()))) return true;
      }

    }


    return false;

  }

  HashMap<String, Long> memo=new HashMap<>();

  public long check2(String in)
  {
    if(memo.containsKey(in)) return memo.get(in);
    long sum = 0;
    //if (towels.contains(in)) sum++;
    if (in.length() == 0) sum++;
    else
    {
      for(String t : towels)
      {
        if (in.startsWith(t))
        {
          sum += check2(in.substring(t.length()));
        }
      }
    }

    memo.put(in, sum);

    return sum;

  }


  public String Part2(Scanner scan)
    throws Exception
  {
		long good = 0;

    read(scan);

    for(String lbl : labels)
    {
      //System.out.println(lbl + " " + check2(lbl));
      good += check2(lbl);


    }
    return "" +good;
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
