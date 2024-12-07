import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  // V must be before K
  TreeMap<Integer, TreeSet<Integer> > b4=new TreeMap<>();

  // V must be after K
  TreeMap<Integer, TreeSet<Integer> > af=new TreeMap<>();

  ArrayList<ArrayList<Integer> > test_lines = new ArrayList<>();

  public void readInput(Scanner scan)
  {
    b4.clear();
    af.clear();
    while(true)
    {
      String line = scan.nextLine();
      if(line.trim().length() == 0) break;

      List<Integer> lst = Tok.ent(line,"|");
      int a = lst.get(0);
      int b = lst.get(1);
      add(b4, b, a);
      add(af, a, b);

    }

    test_lines.clear();
    while(scan.hasNextLine())
    {
      test_lines.add ( Tok.ent(scan.nextLine(), ",") );

    }

  }

  public void add(TreeMap<Integer, TreeSet<Integer>> map, int a, int b)
  {
    create(map, a);

    map.get(a).add(b);
  }
  public void create(TreeMap<Integer, TreeSet<Integer>> map, int a)
  {
    if (!map.containsKey(a)) map.put(a, new TreeSet<Integer>());

  }
  public boolean checkList(ArrayList<Integer> lst)
  {
    for(int i = 0; i<lst.size(); i++)
    {
      TreeSet<Integer> before = new TreeSet<>();
      before.addAll(lst.subList(0, i));
      TreeSet<Integer> after = new TreeSet<>();
      after.addAll(lst.subList(i+1, lst.size()));

      int v = lst.get(i);
      create(b4,v);
      create(af,v);

      for(int n : b4.get(v))
      {
        //if (!before.contains(n)) return false;
        if (after.contains(n)) return false;
      }


      for(int n : af.get(v))
      {
        if (before.contains(n)) return false;
        //if (!after.contains(n)) return false;
      }
    }
    //System.out.println("Correct: " + lst);
    return true;


  }

  public String Part1(Scanner scan)
    throws Exception
  {
    readInput(scan);
    int sum  =0;
    for(ArrayList<Integer> lst : test_lines)
    {
      if (checkList(lst))
      {
        sum += lst.get(lst.size()/2);
      }
      else
      { 
      }

    }
    
		return "" + sum;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
	  int sum  =0;

    for(ArrayList<Integer> lst : test_lines)
    {
      if (checkList(lst))
      {
        //sum += lst.get(lst.size()/2);
      }
      else
      { 
        TreeSet<Integer> to_add=new TreeSet<>();
        to_add.addAll(lst);
        ArrayList<Integer> cor = correct(new ArrayList<>(), to_add);

        sum += cor.get(lst.size()/2);
      }

    }

    TreeSet<Integer> all = new TreeSet<>();
    for(List<Integer> lst : test_lines) all.addAll(lst);
    all.addAll(b4.keySet());
    all.addAll(af.keySet());

    System.out.println("All order: " + correct(new ArrayList<>(), all));

    
		return "" + sum;
  }

  public ArrayList<Integer> correct(ArrayList<Integer> cur_lst, TreeSet<Integer> to_add)
  {
    if (!checkList(cur_lst))
    {
      return null;

    }
    if (to_add.size() == 0)
    {
      return cur_lst;
    }

    int next = to_add.first();
    TreeSet<Integer> to_add2 = new TreeSet<>();
    to_add2.addAll(to_add);
    to_add2.remove(next);

    for(int i=0; i<=cur_lst.size(); i++)
    {
      ArrayList<Integer> lst2 = new ArrayList<>();
      lst2.addAll(cur_lst);

      lst2.add(i, next);

      ArrayList<Integer> r = correct(lst2, to_add2);
      if (r != null) return r;

    }
    return null;


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
