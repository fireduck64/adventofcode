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

  Random rnd=new Random();
  TreeMap<String, Reaction> reactions = new TreeMap();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line=line.replace(",","");
      Reaction r = new Reaction(line);
      reactions.put(r.out_type, r);

    }

    { // Part 1

      TreeeMap<String> junk = new TreeeMap<>();
      junk.add("FUEL", -1L);
      reactFlush(junk);
      System.out.println("Part 1: " + -junk.getL("ORE"));
    }

    
    { // Part 2
      long ore_limit=1000000000000L;
      long upper=ore_limit;
      long lower=0L;
      TreeeMap<String> junk = new TreeeMap<>();
      while(Math.abs(upper-lower) > 1)
      {
        long mid = (upper + lower + 1) / 2;
        junk.clear();
        junk.add("FUEL", -1L * mid);
        reactFlush(junk);
        long ore = -junk.getL("ORE");
        if (ore > ore_limit)
        {
          upper=mid;
        }
        else
        {
          lower=mid;
        }
      }
      System.out.print("Part 2: ");
      System.out.println(lower);

    }


  }

  public void reactFlush(TreeeMap<String> junk)
  {

    while(true)
    {
      int actions =0;
      for(String t : reactions.keySet())
      {
        if (junk.getL(t) < 0L)
        if (!t.equals("ORE"))
        {
          react(t, junk);
          actions++;
        }
      }
      if (actions==0) return;

    }

    

  }

  public class Reaction
  {
    long out_qty;
    String out_type;

    TreeMap<String, Long> inputs=new TreeMap<>();

    public Reaction(String line)
    {
      List<String> lst = Tok.en(line, " ");
      int pos = 0;
      while(true)
      {
        String n = lst.get(pos);
        if (n.equals("=>"))
        {
          pos++;
          break;
        }
        long count = Long.parseLong(n);
        String type = lst.get(pos+1);
        inputs.put(type, count);
        pos+=2;
      }
      out_qty = Long.parseLong(lst.get(pos));
      out_type = lst.get(pos+1);

    }

  }

  public void react(String thing, TreeeMap<String> inv)
  {
    long v = inv.getL(thing);
    if (v >= 0) return;
    if (thing.equals("ORE")) return;

    v = Math.abs(v);

    Reaction r = reactions.get(thing);
    long mult = v / r.out_qty;
    if (v % r.out_qty != 0) mult++;

    inv.add(thing, r.out_qty * mult);
    for(String t : r.inputs.keySet())
    {
      inv.add(t, r.inputs.get(t) * mult * -1L);
    }

  }

}
