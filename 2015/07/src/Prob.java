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

  TreeMap<String, Gate> gates=new TreeMap<>();
  TreeMap<String, Integer> gate_cache= new TreeMap<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      List<String> parts = Tok.en(line, " ");
      if (parts.get(0).equals("NOT"))
      {
        String src = parts.get(1);
        String g = parts.get(3);
        gates.put(g, new GateNot(src) );

      }
      else if (parts.get(1).equals("OR"))
      {
        String s1 = parts.get(0);
        String s2 = parts.get(2);
        String g = parts.get(4);
        gates.put(g, new GateOr(s1,s2));

      }
      else if (parts.get(1).equals("AND"))
      {
        String s1 = parts.get(0);
        String s2 = parts.get(2);
        String g = parts.get(4);
        gates.put(g, new GateAnd(s1,s2));
      }
      else if (parts.get(1).equals("RSHIFT"))
      {
        String s1 = parts.get(0);
        int val = Integer.parseInt(parts.get(2));
        String g = parts.get(4);
        gates.put(g, new GateRShift(s1, val));
      }
      else if (parts.get(1).equals("LSHIFT"))
      {
        String s1 = parts.get(0);
        int val = Integer.parseInt(parts.get(2));
        String g = parts.get(4);
        gates.put(g, new GateLShift(s1, val));
      }

      else if (parts.get(1).equals("->"))
      {
        String g = parts.get(2);
        try
        {
          int val = Integer.parseInt(parts.get(0));
          gates.put(g, new GateDirect(val));
        }
        catch(NumberFormatException e)
        {
          String s1 = parts.get(0);
          gates.put(g, new GateRef(s1));

        }

      }
      else
      {
        E.er(parts.toString());
      }

    }
    System.out.println( getGateV("a"));
    int p1_a = getGateV("a");
    System.out.println("Part 1: " + p1_a);

    gate_cache.clear();
    gates.put("b", new GateDirect(p1_a));
    int p2_a = getGateV("a");
    System.out.println("Part 2: " + p2_a);

  }


  public abstract class Gate
  {
   
    public abstract int getValueInt();
  }

  public int getGateV(String s)
  {
    try
    {
      int v = Integer.parseInt(s);
      return v;
    }
    catch(Exception e)
    {
      
    }
    if (gate_cache.containsKey(s))
    {
      return gate_cache.get(s);
    }

    Gate g= gates.get(s);
    if (g == null) E.er("No gate: " + s);
    int v = g.getValueInt();
    gate_cache.put(s, v);
    return v;
  }

  public class GateDirect extends Gate
  {
    int val;
    public GateDirect(int v)
    {
      this.val=v;
    }

    public int getValueInt() { return val; }
  }
  public class GateRef extends Gate
  {
    String src;
    public GateRef(String src)
    {
      this.src = src;
    }
    public int getValueInt() {return getGateV(src);}
  }
  
  public class GateNot extends Gate
  {
    String src;
    public GateNot(String src)
    {
      this.src = src;
    }
    public int getValueInt() {return ~getGateV(src);}
  }
  
  public class GateAnd extends Gate
  {
    String s1;
    String s2;
    public GateAnd(String s1, String s2)
    {
      this.s1 = s1;
      this.s2 = s2;
    }
    public int getValueInt()
    {
      return getGateV(s1) & getGateV(s2);
    }

  }
  public class GateOr extends Gate
  {
    String s1;
    String s2;
    public GateOr(String s1, String s2)
    {
      this.s1 = s1;
      this.s2 = s2;
    }
    public int getValueInt()
    {
      return getGateV(s1) | getGateV(s2);
    }

  }

  public class GateLShift extends Gate
  {
    String src;
    int slide;
    public GateLShift(String src,int slide)
    {
      this.src = src;
      this.slide = slide;
    }
    public int getValueInt()
    {
      return (getGateV(src) << slide) % 65536;
    }
  }

   public class GateRShift extends Gate
  {
    String src;
    int slide;
    public GateRShift(String src,int slide)
    {
      this.src = src;
      this.slide = slide;
    }
    public int getValueInt()
    {
      return (getGateV(src) >> slide) % 65536;
    }
  }


}
