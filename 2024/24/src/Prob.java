import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  public class Node
  {
    boolean value;
    boolean is_set;

    List<String> src;
    String op;

    public Node(boolean val)
    {
      this.value = val;
      this.is_set=true;
    }

    public Node(String a, String b, String op)
    {
      src=new ArrayList<>();
      src.add(a);
      src.add(b);
      this.op = op;

      is_set=false;
    }
  }

  TreeMap<String, Node> map;

  public void reset(long x, long y)
  {
    for(Node n : map.values())
    {
      if (n.op != null)
      {
        n.is_set=false;
      }
      else
      {
        n.value=false;
      }
    }

    int idx =0;
    while(x > 0)
    {
      long val = x % 2;
      if (val == 1)
      {
        String s =getName('x', idx);
        map.get(s).value=true;
      }
      x = x/2;
      idx++;
    }
    idx =0;
    while(y > 0)
    {
      long val = y % 2;
      if (val == 1)
      {
        String s =getName('y', idx);
        map.get(s).value=true;
      }
      y = y/2;
      idx++;
    }
  }

  public String getName(char letter, int idx)
  {
    String s = "" + idx;
    while(s.length() < 2) s="0" + s;
    return "" + letter + s;
  }

  public void readInput(Scanner scan)
  {
    map = new TreeMap<>();
    while(true)
    {
      String line = scan.nextLine();
      if (line.trim().length() == 0) break;

      List<String> tok = Tok.en(line.replace(":",""), " ");
      String name = tok.get(0);
      boolean val = false;

      if (tok.get(1).equals("1")) val=true;
      map.put(name, new Node(val));

    }

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();

      List<String> tok = Tok.en(line.replace(":",""), " ");
      String a = tok.get(0);
      String op = tok.get(1);
      String b = tok.get(2);
      String name = tok.get(4);

      map.put(name, new Node(a,b,op));

    }

  }

  public boolean resolve(String name, TreeSet<String> stack)
    throws Exception
  {
    if (stack.contains(name)) throw new Exception("nope");


    Node n = map.get(name);
    if (n.is_set) return n.value;

    stack.add(name);
    boolean a = resolve(n.src.get(0), stack);
    boolean b = resolve(n.src.get(1), stack);

    String op = n.op;
    if (op.equals("AND"))
    {
      n.value = a && b;
      n.is_set=true;
    }
    else if (op.equals("OR"))
    {
      n.value = a || b;
      n.is_set=true;
    }
    else if (op.equals("XOR"))
    {
      n.value = a ^ b;
      n.is_set=true;
    }
    else
    {
      E.er(op);
    }
    stack.remove(name);
    return n.value;
  }

  public long getZ()
    throws Exception
  {
    long v = 0;

    for(int i=45; i>=0; i--)
    {
      String s = getName('z', i);

      if (map.containsKey(s))
      {
        v = v << 1;

        boolean b = resolve(s, new TreeSet<String>());
        if (b) v=v+1L;
      }

    }
    return v;


  }

  public String Part1(Scanner scan)
    throws Exception
  {
    readInput(scan);


		return "" + getZ();
  }



  public String Part2(Scanner scan)
    throws Exception
  {
    readInput(scan);

    TreeMap<String, Double> err_cnt=new TreeMap<>();

    int depth=7;
    System.out.println("Error depth: " + depth);
    for(int i=0; i<100000; i++)
    {
      long x = getInputRand();
      long y = getInputRand();
      reset(x,y);

      long z = getZ();
      long e = x+y;
      //System.out.println("z " + z + " e " + e);

      //System.out.println(getErrorBits(e,z));

      merge(err_cnt, getErrorNodes(getErrorBits(e,z), depth));

   }

    TreeMap<Double, String> pri_map = new TreeMap<>();

    for(String n : err_cnt.keySet())
    {
      double cnt = rnd.nextDouble() + err_cnt.get(n);
      if ((!n.startsWith("x")) && (!n.startsWith("y")))
        pri_map.put(cnt, n);
    }

    while(pri_map.size() > 20)
    {
      pri_map.pollFirstEntry();
    }

    System.out.println(pri_map);


    ArrayList<String> usable = new ArrayList<>();
    usable.addAll(pri_map.values());

    TreeSet<String> used=new TreeSet<>();
    while(pri_map.size() > 8)
    {
      pri_map.pollFirstEntry();
    }
    TreeSet<String> as=new TreeSet<String>();
    as.addAll(pri_map.values());
    String ans = as.toString();
    ans=ans.replace(" ","");
    System.out.println(ans);
    System.out.println(usable);

    System.out.println("Attemping fix with: " + usable.size());

    attemptFix(usable,0, used, 4);
    System.out.println("Tried: " + fix_tries);
    
		return "" + ans;
  }

  long fix_tries;

  public void attemptFix(ArrayList<String> usable, int idx, TreeSet<String> used, int swaps_left)
  {
    if (swaps_left == 0)
    {
      fix_tries++;
      try
      {
        for(int i=0; i<100; i++)
        {
          long x = getInputRand();
          long y = getInputRand();
          reset(x,y);

          long z = getZ();
          long e = x+y;
          if (e != z) return;
        }
      }
      catch(Exception e)
      {
        return;
      }

      // winner
      TreeSet<String> win = new TreeSet<>();
      System.out.println("WIN: " + win.toString().replace(" ",""));
      return;
    }

    for(int i = idx; i<usable.size(); i++)
    for(int j = i+1; j<usable.size(); j++)
    {
      String a = usable.get(i);
      String b = usable.get(j);

      if (!a.equals(b))
      if (!used.contains(a))
      if (!used.contains(b))
      {
        Node n_a = map.get(a);
        Node n_b = map.get(b);

        used.add(a);
        used.add(b);
        map.put(b,n_a);
        map.put(a,n_b);

        attemptFix(usable, i+1, used, swaps_left-1);

        used.remove(a);
        used.remove(b);
        map.put(a,n_a);
        map.put(b,n_b);

      }

    }

  }

  public TreeSet<Integer> getErrorBits(long a, long b)
  {
    TreeSet<Integer> res = new TreeSet<>();
    int bit =0;

    while((a >0) || (b>0))
    {

      if (a % 2 != b % 2)
      {
        res.add(bit);
      }

      a = a/2;
      b = b/2;
      bit++;

    }
    return res;

  }
  public TreeMap<String, Double> getErrorNodes(TreeSet<Integer> bits, int layers)
  {
    TreeMap<String, Double> res=new TreeMap<>();
    for(int bit : bits)
    {
      String name = getName('z', bit);
      merge(res, getSource(name, layers, 1.0));
    }

    return res;
    

  }

  // Add a to x
  void merge(Map<String, Double> x, Map<String, Double> add)
  {
    for(Map.Entry<String, Double> me : add.entrySet())
    {
      String k = me.getKey();
      if (!x.containsKey(k)) x.put(k, 0.0);

      x.put(k , x.get(k) + me.getValue());
    }

  }
  public Map<String, Double> getSource(String node, int layers, double fault)
  {
    if (layers == 0) return ImmutableMap.of(node, fault);

    TreeMap<String, Double> res = new TreeMap<>();

    res.put(node, fault);
    Node n = map.get(node);
    if (n.op != null)
    {
      merge(res, getSource(n.src.get(0), layers-1, fault/2.0));
      merge(res, getSource(n.src.get(1), layers-1, fault/2.0));
    }
    return res;

  }


  public long getInputRand()
  {
    long v = rnd.nextLong();
    if (v < 0) v = -v;
    v = v % 17592186044416L;
    return v;
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
