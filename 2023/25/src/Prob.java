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

  TreeMap<String, TreeSet<String> > conns=new TreeMap<>();

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    int wires=0;
    for(String line : In.lines(scan))
    {
      line = line.replace(":", "");
      List<String> tok = Tok.en(line, " ");
      String a = tok.get(0);
      for(int i=1; i<tok.size(); i++)
      {
        connect(a, tok.get(i));
        connect(tok.get(i), a);
        wires++;
      }
    }
    System.out.println("Wires: " + wires);
  
    System.out.println("Doing wire counts");
    for(String n : conns.keySet())
    {
      Search.search(new WC(new LinkedList<Wire>(), n));
    }

    TreeMap<Double, Wire> count_map = new TreeMap<>();
    for(Wire w : wire_counts.keySet())
    {
      count_map.put(wire_counts.get(w) + rnd.nextDouble(), w);

    }

    for(Map.Entry<Double, Wire> me : count_map.entrySet())
    {
      System.out.println(me);
    }

    TreeMap<String, Set<String> > map = copyConns();

    for(int i=0; i<3; i++)
    {
      Wire w = count_map.pollLastEntry().getValue();
      System.out.println("Removing wire: " + w);

      map.get(w.a).remove(w.b);
      map.get(w.b).remove(w.a);

    }

    HashSet<String> marked = new HashSet<>();
    Search.search(new F(conns.firstKey(), marked, map));

    System.out.println(marked.size());
    int p1 = marked.size() * (conns.size() - marked.size());

    System.out.println("Part 1: " + p1);


  }
  public void connect(String a, String b)
  {
    if (!conns.containsKey(a))
    {
      conns.put(a, new TreeSet<String> ());
    }
    conns.get(a).add(b);
  }

  TreeMap<String, Set<String> > copyConns()
  {
    TreeMap<String, Set<String> > m = new TreeMap<>();

    for(Map.Entry<String, TreeSet<String> > me : conns.entrySet())
    {
      m.put(me.getKey(), new TreeSet<String>());
      m.get(me.getKey()).addAll(me.getValue());
    }
    return m;
  }

  public class Wire implements Comparable<Wire>
  {
    final String a;
    final String b;

    public Wire(String a, String b)
    {
      if (a.compareTo(b) < 0)
      {
        this.a = a;
        this.b = b;
      }
      else
      {
        this.a = b;
        this.b = a;
      }
    }

    public String toString()
    {
      return "W{" + a + "." + b + "}";

    }

    public int compareTo(Wire o)
    {
      if (a.compareTo(o.a) < 0) return 1;
      if (a.compareTo(o.a) > 0) return -1;

      if (b.compareTo(o.b) < 0) return 1;
      if (b.compareTo(o.b) > 0) return -1;

      return 0;

    }
    
  }

  public class F extends Flood
  {
    Set<String> marked;
    String loc;
    Map<String, Set<String> > map;


    public F(String loc, Set<String> marked, Map<String, Set<String> > map)
    {
      this.loc = loc;
      this.marked = marked;
      this.map = map;

    }
    public String toString()
    {
      return loc;
    }

    public List<State> next()
    {
      List<State> lst = new LinkedList<>();
      marked.add(loc);

      if (map.containsKey(loc))
      for(String s : map.get(loc))
      {
        lst.add(new F(s, marked, map));

      }
      return lst;
    }
  }

  TreeMap<Wire, Integer> wire_counts=new TreeMap<>();

  public class WC extends State
  {
    List<Wire> path;
    String loc;

    public WC(List<Wire> path, String loc)
    {
      this.path = path;
      this.loc = loc;
    }

    public boolean isTerm(){return false;}

    public double getCost(){return path.size(); }
    public String toString(){return loc;}

    public List<State> next()
    {
      for(Wire w : path)
      {
        if (!wire_counts.containsKey(w))
        {
          wire_counts.put(w, 1);
        }
        else
        {
          wire_counts.put(w, wire_counts.get(w) + 1);
        }
      }
      LinkedList<State> lst = new LinkedList<>();
      
      for(String n : conns.get(loc))
      {
        LinkedList<Wire> nl = new LinkedList<>();
        nl.addAll(path);
        nl.add(new Wire(loc, n));
        lst.add(new WC(nl, n));
      }

      Collections.shuffle(lst);

      return lst;
    }
  }
}
