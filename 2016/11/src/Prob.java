import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  // 1 chip to 1 RTG left on floor
  // must carry 1 to 2 things on elevator
  // elevator stops on each floor


  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    TreeMap<Integer, TreeSet<String> > start_items = new TreeMap<>();
    // Fork reading that input
    for(int i=0; i<=4; i++) start_items.put(i, new TreeSet<>());

    // first letter is type (generator or chip)
    // second letter is flavor
    start_items.get(1).add("GP");
    start_items.get(1).add("CP");

    start_items.get(2).add("GC"); //cobalt
    start_items.get(2).add("GU"); //curium
    start_items.get(2).add("GR");
    start_items.get(2).add("GL"); //plutonium

    start_items.get(3).add("CC"); //cobalt
    start_items.get(3).add("CU"); //curium
    start_items.get(3).add("CR");
    start_items.get(3).add("CL"); //plutonium

    SS fin1 = (SS) Search.search(new SS(0, 1, start_items));
    System.out.println("Part 1: " + fin1.getCost());
    System.out.println(fin1);

    start_items.get(1).add("GE");
    start_items.get(1).add("CE");

    start_items.get(1).add("GD");
    start_items.get(1).add("CD");

    SS fin2 = (SS) Search.search(new SS(0, 1, start_items));
    System.out.println("Part 1: " + fin1.getCost());
    System.out.println(fin1);
    System.out.println("Part 2: " + fin2.getCost());
    System.out.println(fin2);
  }

  public class SS extends State
  {
    int cost;
    int ele_loc;
    // Maps floors to items
    // 0 - elevator items
    TreeMap<Integer, TreeSet<String> > items = new TreeMap<>();

    public SS(int cost, int ele_loc, TreeMap<Integer, TreeSet<String> > in)
    {
      this.cost = cost;
      this.ele_loc = ele_loc;
      for(int i=0; i<=4; i++)
      {
        items.put(i, new TreeSet<>());
        items.get(i).addAll(in.get(i));
      }
    }
    public String toString()
    {
      return "" + ele_loc + " " + items;
    }

    public boolean isTerm()
    {
      // Everything on 4th floor
      for(int floor = 0; floor<=3; floor++)
      {
        if (items.get(floor).size() != 0) return false;
      }
      return true;
    }
    public double getCost()
    {
      return cost;
    }
    @Override
    public double getEstimate()
    {
      double est =0.0;
      // Although we can move two items on the elevator
      // we need to come back down with at least one
      // so we are saying estimate of at least 1 step per
      // item floor
      for(int f =1; f<=3; f++)
      {
        int d = Math.abs(4-f);
        est += d * items.get(f).size();
        

      }
      // That last step we can carry two things
      // across all three floor changes
      est=est-3.0;
      est = Math.max(est, 0.0);
      return est;
    }
    public boolean isValid()
    {
      for(int f=1; f<=4; f++)
      {
        TreeSet<String> eff = new TreeSet<>();
        eff.addAll(items.get(f));

        // If we have the elevator with us
        if (f == ele_loc) eff.addAll(items.get(0));

        if (!isSafe(eff))
        {
          return false;
        }
      }
      //System.out.println("Valid: " + toString());
      return true;
    }

    public List<State> next()
    {
      LinkedList<State> lst=new LinkedList<State>();
      if (!isValid()) return lst;

      // Actions

      // move elevator
      // Elevator can't move empty
      if (items.get(0).size() > 0)
      if (isSafe(items.get(0))) // parts are elevator safe
      {

        for(int f=1; f<=4; f++)
        {
          if (Math.abs(ele_loc - f) == 1)
          {
            SS ns = new SS(cost+1, f, items);
            lst.add(ns);
          }
        }
      }

      // shift items

      // Unload elevator
      for(String i : items.get(0))
      {
        SS ns = new SS(cost, ele_loc, items);
        ns.items.get(0).remove(i);
        ns.items.get(ele_loc).add(i);
        lst.add(ns);
      }
      // Load elevator
      for(String i : items.get(ele_loc))
      {
        SS ns = new SS(cost, ele_loc, items);
        ns.items.get(ele_loc).remove(i);
        ns.items.get(0).add(i);
        if (ns.items.get(0).size() <= 2)
        {
          lst.add(ns);
        }
      }


    

      return lst;

    }

    

  }

  public boolean isSafe(Set<String> set)
  {
    boolean has_gen=false;
    for(String s : set)
    {
      if (s.charAt(0) == 'G') has_gen=true;
    }

    if (!has_gen) return true;

    for(String s : set)
    {
      if (s.charAt(0) == 'C')
      {
        char f = s.charAt(1);
        if (!set.contains("G" + f))
        {
          // chip unsafe - no gen to power it
          return false;
        }
      }
    }
    return true;

  }

}
