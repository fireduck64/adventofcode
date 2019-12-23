import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  TreeMap<String, Long> inv = new TreeMap<>();
  TreeMap<String, Reaction> reaction_map = new TreeMap<>();
  long max_ore = 1000000000000L;

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      parseLine(scan.nextLine());
    }

    getItem(1L, "FUEL");

    System.out.println(-inv.get("ORE"));

    long one_fuel = -inv.get("ORE");

    long start = max_ore / one_fuel;

    System.out.println(rec(0, start*2));
    
  }

  public long rec(long min, long max)
  {
    System.out.println(" " + min + " " + max);

    if (Math.abs(max-min) <= 1) return min;

    inv.clear();
    long mid = ((max + min) + 0) / 2;
    getItem(mid, "FUEL");
    long ore = -inv.get("ORE");

    if (ore <= max_ore)
    {
      return rec(mid, max);
    }
    else
    {
      return rec(min, mid);
    }

  }
 
  public void getItem(long amt, String name)
  {
    recordInv(name, -amt);
    if (name.equals("ORE"))
    {
      return;
    }
    
    long needed = -inv.get(name);

    if (needed <= 0) return;

    Reaction r = reaction_map.get(name);
    long multiplier = needed / r.result.amt ;
    while (multiplier * r.result.amt < needed) multiplier+=1;


    for(Comp c : r.parts)
    {
      getItem(c.amt * multiplier, c.name);
    }

    long sum = multiplier * r.result.amt;
    recordInv(name, sum);

  }

  public void recordInv(String name, long amt)
  {
    if (inv.containsKey(name))
    {
      inv.put(name, inv.get(name) + amt);
    }
    else
    {
      inv.put(name, amt);
    }
  }

  public void parseLine(String line)
  {
    line = line.replaceAll("=>", "|");
    line = line.replaceAll(",", "");
    
    StringTokenizer stok = new StringTokenizer(line, "|");
    String parts = stok.nextToken();
    String result = stok.nextToken();

    Scanner scan = new Scanner(parts);

    LinkedList<Comp> part_list = new LinkedList<>();
    while(scan.hasNext())
    {
      part_list.add(new Comp(scan));
    }

    Comp res = new Comp(new Scanner(result));

    Reaction r = new Reaction();
    r.result = res;
    r.parts = part_list;

    if (reaction_map.containsKey(res.name))
    {
      System.out.println("Duplicate: " + res.name);
    }

    reaction_map.put(res.name, r);

    System.out.println(r);

    
  }


  public class Comp
  {
    public long amt;
    public String name;

    public Comp(Scanner scan)
    {
      amt = scan.nextInt();
      name = scan.next();
    }

    public String toString()
    {
      return "" + amt + " " + name;
    }
  }

  public class Reaction
  {
    Comp result;
    LinkedList<Comp> parts;

    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(result.toString());
      sb.append(" <=");

      for(Comp c : parts)
      {
        sb.append(" ");
        sb.append(c.toString());
      }

      return sb.toString();
    }
  }

}
