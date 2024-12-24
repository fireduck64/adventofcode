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
    public Node copy()
    {
      Node n2 = new Node(value);
      n2.value = value;
      n2.is_set = is_set;
      n2.op = op;
      if (op != null)
      {
        n2.src = ImmutableList.copyOf(src);
      }
      return n2;
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

    int depth=20;
    System.out.println("Error depth: " + depth);
    for(int i=0; i<20000; i++)
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

    System.out.println(pri_map.size());
    while(pri_map.size() > 60)
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

    /*
    System.out.println("Attemping fix with: " + usable.size());

    attemptFix(usable,0, used, 4);
    System.out.println("Tried: " + fix_tries);
    
		return "" + ans;*/

    System.out.println("Starting parallel search with: " + usable.size());
    SS fin = (SS) Search.searchPara(new SS(usable, new TreeSet<String>(), new TreeSet<String>(), 4, map));

    return "" + fin.used.toString().replace(" ", "");

  }

  public class SS extends State
  {
    List<String> usable;
    Set<String> used;
    Set<String> swaps;
    int swaps_left;
    Map<String, Node> smap;

    long score;
    boolean score_run=false;

    public SS(List<String> usable, Set<String> used, Set<String> swaps, int swaps_left, Map<String, Node> smap_in)
    {
      this.usable = usable;
      this.used = used;
      this.swaps = swaps;
      this.swaps_left = swaps_left;
      this.smap = new TreeMap<>();

      for(Map.Entry<String, Node> me : smap_in.entrySet())
      {
        smap.put(me.getKey(), me.getValue().copy());
      }

    }
    protected void swap(String a, String b)
    {
      Node n_a = smap.get(a);
      Node n_b = smap.get(b);

      smap.put(a,n_b);
      smap.put(b,n_a);

    }
    public void updateScore()
    {
      long errors = 0;
      for(int i=0; i<50; i++)
      {
        long x = getInputRand();
        long y = getInputRand();
        reset(x,y);

        try
        {
          long z = getZ();
          long e = x+y;
          errors += getErrorBits(z,e).size();
        }
        catch(Exception e)
        {
          errors+=1000L;
        }
      }

      score=errors;
      //System.out.println("SS: " + toString() + " " + score);

      
      score_run =true;


    }
    public boolean isTerm()
    {
      if (!score_run) updateScore();
      return (score == 0L);
    }

    public double getCost()
    {
      if (!score_run) updateScore();
      return score;
    }
    public String toString()
    {
      TreeSet<String> uu = new TreeSet<>();
      uu.addAll(swaps);
      return uu.toString().replace(" ", "");
    }
    public boolean resolve(String name, TreeSet<String> stack)
      throws Exception
    {
      if (stack.contains(name)) throw new Exception("nope");


      Node n = smap.get(name);
      if (n.is_set) return n.value;

      stack.add(name);
      boolean a = this.resolve(n.src.get(0), stack);
      boolean b = this.resolve(n.src.get(1), stack);

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

        if (smap.containsKey(s))
        {
          v = v << 1;

          boolean b = this.resolve(s, new TreeSet<String>());
          if (b) v=v+1L;
        }

      }
      return v;


    }


    public void reset(long x, long y)
    {
      for(Node n : smap.values())
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
          smap.get(s).value=true;
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
          smap.get(s).value=true;
        }
        y = y/2;
        idx++;
      }
    }

    public List<State> next()
    {
      if (!score_run) updateScore();
      LinkedList<State> lst = new LinkedList<>();
      if (swaps_left == 0) return lst;

      for(String a : usable)
      for(String b : usable)
      {

        if (!a.equals(b))
        if (!used.contains(a))
        if (!used.contains(b))
        {
          TreeSet<String> u2 = new TreeSet<>();
          u2.addAll(used);
          u2.add(a);
          u2.add(b);

          TreeSet<String> s2 = new TreeSet<>();
          s2.addAll(swaps);
          TreeSet<String> ssss = new TreeSet<>();
          ssss.add(a);
          ssss.add(b);
          s2.add(ssss.toString());
          SS ss = new SS(usable, u2, s2, swaps_left-1, smap);
          ss.swap(a,b);
          ss.updateScore();
          if (ss.score < score) lst.add(ss);

        }

      }

      return lst;

    }

  }

  // A - expected
  // B - found
  // returns error bits and expected value
  public TreeMap<Integer, Boolean> getErrorBits(long a, long b)
  {
    TreeMap<Integer, Boolean> res = new TreeMap<>();
    int bit =0;

    while((a >0) || (b>0))
    {

      if (a % 2 != b % 2)
      {
        boolean e = false;
        if (a%2 == 1) e=true;
        res.put(bit, e);
      }

      a = a/2;
      b = b/2;
      bit++;

    }
    return res;

  }
  public TreeMap<String, Double> getErrorNodes(TreeMap<Integer,Boolean> bits, int layers)
  {
    TreeMap<String, Double> res=new TreeMap<>();
    for(int bit : bits.keySet())
    {
      String name = getName('z', bit);
      merge(res, getSource(name, layers, bits.get(bit), 1.0));
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
  public Map<String, Double> getSource(String node, int layers, boolean expected, double fault)
  {
    TreeMap<String, Double> res = new TreeMap<>();
    Node n = map.get(node);
    if (n.value == expected) return res;

    if (layers == 0) return ImmutableMap.of(node, fault);

    res.put(node, fault);
    if (n.op != null)
    {

      String a_n = n.src.get(0);
      String b_n = n.src.get(1);
      boolean a = map.get(a_n).value;
      boolean b = map.get(b_n).value;

      if (n.op.equals("OR"))
      {
        // we wanted true, both must be false
        if (expected)
        {
          merge(res, getSource(a_n, layers-1, expected, fault/2.0));
          merge(res, getSource(b_n, layers-1, expected, fault/2.0));
        }

        // we wanted false, blame any true
        if (!expected)
        {
          if (!b)
          { // blame a completely
            merge(res, getSource(a_n, layers-1, expected, fault));
          }
          else if (!a)
          { // blame b completely
            merge(res, getSource(b_n, layers-1, expected, fault));
          }
          else
          {
            //blame both
            merge(res, getSource(a_n, layers-1, expected, fault/2.0));
            merge(res, getSource(b_n, layers-1, expected, fault/2.0));
          }
        }
      }
      else if (n.op.equals("AND"))
      {
        // we wanted true, blame any false
        if (expected)
        {
          if (b)
          { // blame a completely
            merge(res, getSource(a_n, layers-1, expected, fault));
          }
          else if (a)
          { // blame b completely
            merge(res, getSource(b_n, layers-1, expected, fault));
          }
          else
          {
            //blame both
            merge(res, getSource(a_n, layers-1, expected, fault/2.0));
            merge(res, getSource(b_n, layers-1, expected, fault/2.0));
          }
        }

        // we wanted false, blame all
        if (!expected)
        {
            //blame both
            merge(res, getSource(a_n, layers-1, expected, fault/2.0));
            merge(res, getSource(b_n, layers-1, expected, fault/2.0));
        }
      }
      else if (n.op.equals("XOR"))
      {
        // blame both equally
        merge(res, getSource(a_n, layers-1, expected, fault/2.0));
        merge(res, getSource(b_n, layers-1, expected, fault/2.0));

      }


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
