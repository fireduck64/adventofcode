import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  TreeMap<String, TreeSet<String> > conn;

  public String Part1(Scanner scan)
    throws Exception
  {
    readInput(scan);

    TreeSet<String> included= new TreeSet<>();

    int count = 0;

    /*for(String n : conn.keySet())
    {
      if (n.startsWith("t"))
      if (!included.contains(n))
      {
        flood_set=new TreeSet<>();
        Search.search(new FF(n));
        System.out.println(flood_set);

        if (flood_set.size() >= 3) count++;

        included.addAll(flood_set);

      }

    }*/

    count = findSets().size();

		return "" + count;
  }

  public TreeSet<String> findSets()
  {
    TreeSet<String> found = new TreeSet<>();

    for(String a : conn.keySet())
    {
    for(String b : conn.keySet())
    {
    if (conn.get(a).contains(b))
    for(String c : conn.keySet())
    {
      if (conn.get(a).contains(c))
      {
      TreeSet<String> g = new TreeSet<>();
      g.add(a);
      g.add(b);
      g.add(c);

      if (g.size() == 3)
      {
        int links = 0;
        int t = 0;
        for(String d : g)
        for(String e : g)
        {
          if (d.startsWith("t")) t++;
          if (!d.equals(e))
          {
            if (conn.get(d).contains(e)) links++;
          }

        }
        if (links == 6)
        if (t > 0)
        {
          found.add(g.toString());
        }
      }
      }

    }
    }
    }
    return found;

  }


  public void findSet(String src)
  {
    TreeSet<String> group = new TreeSet<>();

    //System.out.println(conn.get(src).size());
    ArrayList<String> pot = new ArrayList<>();
    pot.add(src);
    pot.addAll(conn.get(src));

    recGroupCheck(pot, group, 0); 


  }
  TreeSet<String> p2_best_set = null;
  public void recGroupCheck(ArrayList<String> potentials, TreeSet<String> members, int idx)
  {
    if (idx == potentials.size())
    {
      if (p2_best_set != null)
      if (members.size() <= p2_best_set.size()) 
        return;

      if (checkGroup(members))
      {
        p2_best_set = new TreeSet<>();
        p2_best_set.addAll(members);
      }
      
      return;

    }

    recGroupCheck(potentials, members, idx+1);
    members.add(potentials.get(idx));
    recGroupCheck(potentials, members, idx+1);
    members.remove(potentials.get(idx));


  }
  public boolean checkGroup(TreeSet<String> members)
  {
    for(String a : members)
    for(String b : members)
    {
      if (!a.equals(b))
      {
        if (!conn.get(a).contains(b)) return false;

      }

    }
    return true;

  }

  TreeSet<String> flood_set;
  public class FF extends Flood
  {
    String node;
    public FF(String node)
    {
      this.node = node;

    }
    public String toString(){return node;}

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      flood_set.add(node);

      for(String s : conn.get(node))
      {
        lst.add(new FF(s));

      }


      return lst;

    }


  }

  public void readInput(Scanner scan)
  {
    conn = new TreeMap<>();
    for(String line : In.lines(scan))
    {
      List<String> lst = Tok.en(line, "-");

      addLink(lst.get(0), lst.get(1));
      addLink(lst.get(1), lst.get(0));

    }

  }
  public void addLink(String a, String b)
  {
    if (!conn.containsKey(a))
    {
      conn.put(a, new TreeSet<>());

    }
    conn.get(a).add(b);


  }



  public String Part2(Scanner scan)
    throws Exception
  {
    readInput(scan);

    p2_best_set = null;

    for(String n : conn.keySet())
    {
      findSet(n);
    }
    String ans = p2_best_set.toString();
    ans=ans.replace(" ","");
		return "" + ans;
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
