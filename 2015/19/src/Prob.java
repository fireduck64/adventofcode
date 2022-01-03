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
  TreeMap<String, TreeSet<String> > xform = new TreeMap<>();
  TreeMap<String, TreeSet<String> > rform = new TreeMap<>();
  String input_line;
  int max_drop = 0;

  public Prob(Scanner scan)
    throws Exception
  {
   
    for(String line : In.lines(scan))
    {
      if (line.contains("=>"))
      {
        List<String> parts = Tok.en(line, " ");
        String from = parts.get(0);
        String to = parts.get(2);

        if (!xform.containsKey(from)) xform.put(from, new TreeSet<>());
        xform.get(from).add(to);

        if (!rform.containsKey(to)) rform.put(to, new TreeSet<>());
        rform.get(to).add(from);

        max_drop = Math.max(max_drop, to.length() - from.length());

      }
      else
      {
        if (line.trim().length() > 0)
        {
          input_line=line;
        }
      }

    }

    System.out.println("Part 1: " + transform(input_line).size());
    System.out.println(rform);

    Set<String> fing = ImmutableSet.of("e");
    //System.out.println(getOptions("e","C",8));
    //System.out.println(getOptions("e","Ti",8));
    SS fin = (SS) Search.searchPara(new SS(0, input_line));
    System.out.println(fin);
    System.out.println("Part 2: " + fin.getCost());

  }

  public class SS extends State
  {
    int cost;
    String v;

    public SS(int cost, String v)
    {
      this.cost = cost;
      this.v = v;
    }
    
    @Override
    public double getEstimate()
    {
      return (v.length() -1 + 1.0) / max_drop;
    }
    public double getCost()
    {
      return cost;
    }
    public double getLean()
    {
      return v.length();
    }
    public boolean isTerm()
    {
      return v.equals("e");
    }
    public String toString()
    {
      return v;
    }

    public List<State> next()
    {
      List<State> lst = new LinkedList<State>();

      // This is complete bullshit
      // look ahead of 20 did it
      // wtf
      // Only generating states for first 20 chars of string
      // this way we generate insane numbers of states less
      // but a pathological case could break this, i think
      for(int i = 0; i<Math.min(v.length(),20); i++)
      {
        String pre = v.substring(0, i);
        String sub = v.substring(i);
        for(String r : rform.keySet())
        {
          if (sub.startsWith(r))
          for(String x : rform.get(r))
          {
            String n = pre + x + sub.substring(r.length()); 
            lst.add(new SS(cost+1, n));

          }

        }
      }

      return lst;
    }
  }


  HashMap<String, TreeMap<String, Integer>> memo = new HashMap<>();

  // Maps result string to cost
  public TreeMap<String, Integer> getOptions(String in, String req, int depth)
  {
    String key = in + "/" + req + "/" + depth;
    synchronized(memo)
    {
      if (memo.containsKey(key)) return memo.get(key);
    }


    TreeMap<String, Integer> opt = new TreeMap<>();
    if (in.equals(req))
    {
      opt.put(in, 0);
    }
    if (depth <= 0) return opt;
    if (xform.containsKey(in))
    {
      for(String x : xform.get(in))
      {
        if (x.startsWith(req))
        {
          opt.put(x, 1);
        }
        else
        {
        String nx = readSymbol(x);
        String rem_x = x.substring(nx.length());
        for(Map.Entry<String, Integer> me : getOptions(nx, req, depth-1).entrySet())
        {
          String s = me.getKey() + rem_x;
          int v = me.getValue();
          if ((!opt.containsKey(s)) || (opt.get(s) > v))
          {
            opt.put(s, v+1);
          }
        }
        }

      }

    }

    synchronized(memo)
    {
      memo.put(key, opt);
    }

    return opt;
  }


  
  public TreeSet<String> transform(String in)
  {
    TreeSet<String> out = new TreeSet<>();

    for(int i=0; i<in.length(); i++)
    {
      String start = in.substring(0,i);
      String rem = in.substring(i);
      for(int j=1; j<=2; j++)
      {
        if (rem.length() >= j)
        {
          String sub = rem.substring(0,j);
          if (xform.containsKey(sub))
          {
            for(String x : xform.get(sub))
            {
              String n = start + x + rem.substring(j);
              out.add(n);

            }

          }

        }
      }

    }
    return out;

  }

  public String readSymbol(String in)
  {
    String s = "" + in.charAt(0);
    if (in.length() > 1)
    if (in.charAt(1) >= 'a')
    if (in.charAt(1) <= 'z')
    {
      s+=in.charAt(1);
    }
    return s;

  }



}
