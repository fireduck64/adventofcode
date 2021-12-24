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
  //String model="13579246899999";
  //String model="99999999999999";
  List<String> inputs;

  public Prob(Scanner scan)
  {

    inputs = In.lines(scan);
    TreeMap<String, Long> vals=new TreeMap<String, Long>();
    reset(vals);

    String model = getBest(vals, 0);
    System.out.println("Part 1: " + model);
    
    reset(vals);
    memo.clear();
    String model2 = getBest2(vals, 0);
    System.out.println("Part 2: " + model2);

  }

 
  HashMap<String, String> memo = new HashMap<>(8192, 0.5f); 
  public String getBestMemo(TreeMap<String, Long> vals, int exec_pos)
  {
    String state="" + exec_pos + ":" + vals.toString();
    if (memo.containsKey(state))
    {
      return memo.get(state);
    }
    //System.out.println(state);
    String res = getBest(vals, exec_pos);
    memo.put(state, res);
    return res;
  }

  public String getBest(TreeMap<String, Long> vals, int exec_pos)
  {
    if (exec_pos == inputs.size())
    {
      long z = vals.get("z");
      if (z==0) return "";
      return null;
    }

    String line = inputs.get(exec_pos);
    if (line.startsWith("inp"))
    {
      String reg = Tok.en(line, " ").get(1);
      for(long i=9; i>0; i--)
      {
        long e = i;
        // Wild guess that the first number has to be 9
        TreeMap<String, Long> v2= new TreeMap<>();
        v2.putAll(vals);
        v2.put(reg, e);
        String sol = getBestMemo(v2, exec_pos+1);
        if (sol != null) return "" + e + sol;
      }
      return null;

    }
    else
    {
      doInst(vals, line);
      return getBest(vals, exec_pos+1);
    }


  }


  public String getBestMemo2(TreeMap<String, Long> vals, int exec_pos)
  {
    String state="" + exec_pos + ":" + vals.toString();
    if (memo.containsKey(state))
    {
      return memo.get(state);
    }
    //System.out.println(state);
    String res = getBest2(vals, exec_pos);
    memo.put(state, res);
    return res;
  }

  public String getBest2(TreeMap<String, Long> vals, int exec_pos)
  {
    if (exec_pos == inputs.size())
    {
      long z = vals.get("z");
      if (z==0) return "";
      return null;
    }

    String line = inputs.get(exec_pos);
    if (line.startsWith("inp"))
    {
      String reg = Tok.en(line, " ").get(1);
      for(long i=1; i<=9; i++)
      {
        long e = i;
        // Wild guess that the first number has to be 9
        if (exec_pos < 5) e = 9;
        TreeMap<String, Long> v2= new TreeMap<>();
        v2.putAll(vals);
        v2.put(reg, e);
        String sol = getBestMemo2(v2, exec_pos+1);
        if (sol != null) return "" + e + sol;
      }
      return null;

    }
    else
    {
      doInst(vals, line);
      return getBest2(vals, exec_pos+1);
    }


  }

  public void reset(TreeMap<String, Long> vals)
  {
    vals.put("w",0L);
    vals.put("x",0L);
    vals.put("y",0L);
    vals.put("z",0L);
  }


  public class InData
  {
    int pos =0;
    String data;
    public InData(String in)
    {
      data = in;

    }
    long read()
    {
      int v = Integer.parseInt("" + data.charAt(pos));
      pos++;
      return v;
    }

  }

  public boolean doInst(TreeMap<String, Long> vals, String inst)
  {
    List<String> tok = Tok.en(inst, " ");
    String cmd = tok.get(0);
    String a = tok.get(1);
    long b = 0;
    if (tok.size()==3)
    {
      String b_str = tok.get(2);
      if (vals.containsKey(b_str))
      {
        b = vals.get(b_str);
      }
      else
      {
        b = Long.parseLong(b_str);
      }
    }

    if (cmd.equals("add"))
    {
      vals.put(a, vals.get(a) + b);
    }
    else if (cmd.equals("mul"))
    {
      vals.put(a, vals.get(a) * b);
    }
    else if (cmd.equals("div"))
    {
      if (b==0) return false;
      vals.put(a, vals.get(a) / b);
    }
    else if (cmd.equals("mod"))
    {
      if (b<=0) return false;
      if (vals.get(a) < 0) return false;
      vals.put(a, vals.get(a) % b);
    }
    else if (cmd.equals("eql"))
    {
      long res = 0;
      long av = vals.get(a);
      if (av == b) res=1;
      vals.put(a, res);
    }
    else
    {
      E.er();
    }
    return true;
  }
}
