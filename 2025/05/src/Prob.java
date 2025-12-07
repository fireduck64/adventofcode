import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();


  LinkedList<Point> fresh_ranges;
 


  public String Part1(Scanner scan)
    throws Exception
  {
    fresh_ranges = new LinkedList<>();

    while(true)
    {
      String line = scan.nextLine();
      if (line.trim().length()==0) break;

      List<Long> lst = Tok.enl(line, "-");
      fresh_ranges.add(new Point(lst.get(0), lst.get(1)));
    }

    int p1=0;
    while(scan.hasNextLong())
    {
      long v = scan.nextLong();

      boolean good=false;
      for(Point p : fresh_ranges)
      {
        if (p.x <= v)
        if (v <= p.y)
        {
          good=true;
        }
      }
      if (good) p1++;

    }
		return "" + p1;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    fresh_ranges = new LinkedList<>();

    while(true)
    {
      String line = scan.nextLine();
      if (line.trim().length()==0) break;

      List<Long> lst = Tok.enl(line, "-");
      fresh_ranges.add(new Point(lst.get(0), lst.get(1)));
      if (lst.get(1) < lst.get(0)) E.er();
    }
    System.out.println("ranges: " + fresh_ranges.size());
    //System.out.println(fresh_ranges);

    TreeSet<Point> pset=new TreeSet<>();
    pset.addAll(fresh_ranges);

    while(true)
    {
      TreeSet<Point> rm_set = new TreeSet<>();
      TreeSet<Point> add_set = new TreeSet<>();
      for(Point a : pset)
      for(Point b : pset)
      {
        //if (add_set.size() == 0)
        if (!a.equals(b))
        {
          if (a.x <= b.x)
          if (b.x <= a.y)
          {

            rm_set.add(a);
            rm_set.add(b);
            Point n = new Point(Math.min(a.x, b.x), Math.max(a.y, b.y));
            //System.out.println("Merging: "  + a + " " + b + " " + n);
            add_set.add(n);
          }

          /*TreeMap<Double, String> order = new TreeMap<>();
          Random rnd = new Random();
          order.put(b.x + rnd.nextDouble(), "B");
          order.put(b.y + rnd.nextDouble(), "B");
          order.put(a.x + rnd.nextDouble(), "A");
          order.put(a.y + rnd.nextDouble(), "A");
          String str = "";
          for(String s : order.values()) str = str + s;
          //if (a.compareTo(b) < 0)
          if (
            str.equals("ABAB") || 
            str.equals("ABBA") || 
            str.equals("BAAB") ||
            str.equals("BABA") ||
            str.equals("ABA") 
            )
          {
            rm_set.add(a);
            rm_set.add(b);
            Point n = new Point(Math.min(a.x, b.x), Math.max(a.y, b.y));
            System.out.println("Merging: " + str + " "  + a + " " + b + " " + n);
            add_set.add(n);
          }*/
          /*if (str.equals("AABB"))
          {
            if (a.y + 1 == b.x)
            {
              rm_set.add(a);
              rm_set.add(b);
              Point n = new Point(Math.min(a.x, b.x), Math.max(a.y, b.y));
              System.out.println("Merging: " + a + " " + b + " " + n);
              add_set.add(n);
            }
          }*/
        }
      }
      if (rm_set.size() == 0) break;
      pset.removeAll(rm_set);
      pset.addAll(add_set);
    }

    long p2=0;
    for(Point p : pset)
    {
      p2 += p.y - p.x + 1L;
    }

		return "" + p2;
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
