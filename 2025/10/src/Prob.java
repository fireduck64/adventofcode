import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.RatNum;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Optimize;
import java.math.BigInteger;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    long p1 = 0;
    while(scan.hasNextLine())
    {
      Machine m = new Machine(scan.nextLine());
      p1 += getPress(m, m.getDefaultState(), 0);
    }
		return "" + p1;
  }

  public int getPress(Machine m, BitSet state, int press_idx)
  {
    if (state.equals(m.on_mode)) return 0;

    int low = 1000000;
    for(int b_idx = press_idx; b_idx < m.buttons.size(); b_idx++)
    {
      BitSet n = m.pressButton(b_idx, state);

      low = Math.min( getPress(m, n, b_idx+1) + 1, low);
    }

    return low;


  }
  
  HashMap<String, Integer> memo=new HashMap<>();
  public int getPressJolt(Machine m, List<Integer> state, int press_idx)
  {
    if (state.equals(m.voltages)) return 0;

    for(int i=0; i<state.size(); i++)
    {
      if (state.get(i) > m.voltages.get(i)) return 1000000;
    }
    String key = "" + state + " " + press_idx;
    System.out.println(key + " " + m.voltages);
    if (memo.containsKey(key)) return memo.get(key);

    int low = 1000000;
    for(int b_idx = press_idx; b_idx < m.buttons.size(); b_idx++)
    {
      List<Integer> n = m.joltButton(b_idx, state);
      low = Math.min( getPressJolt(m, n, b_idx) + 1, low);
    }

    memo.put(key, low);

    return low;
  }

  public class SS extends State
  {
    Machine m;
    List<Integer> state;
    int cost;
    int last_b;
    
    public SS(Machine m, List<Integer> state, int cost, int last_b)
    {
      this.m = m;
      this.state = state;
      this.cost = cost;
      this.last_b = last_b;
    }
    public String toString()
    {
      return "" + state;
    }
    public boolean isTerm()
    {
      return (state.equals(m.voltages));
    }
    public double getCost()
    {
      return cost;
    }
    @Override
    public double getEstimate()
    {
      int max = 0;
      for(int i=0; i<state.size(); i++)
      {
        max = Math.max( Math.abs(state.get(i) - m.voltages.get(i)), max);
      }
      return max;
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      for(int i=0; i<state.size(); i++)
      {
        if (state.get(i) > m.voltages.get(i)) return lst;
      }
      for(int b_idx = last_b; b_idx < m.buttons.size(); b_idx++)
      {
        List<Integer> n = m.joltButton(b_idx, state);

        lst.add(new SS(m, n, cost+1, last_b));
      }

      return lst;

    }

  }


  public String Part2(Scanner scan)
    throws Exception
  {
    long p2 = 0;
    while(scan.hasNextLine())
    {
      memo.clear();

      Machine m = new Machine(scan.nextLine());
      ArrayList<Integer> start = new ArrayList<>();
      for(int i=0; i<m.voltages.size(); i++) start.add(0);
      //SS sol = (SS) Search.searchPara(new SS(m, start, 0, 0));

      //p2 += sol.cost;
      p2 += solveZ3(m);
    }
		return "" + p2;

  }

  public int solveZ3(Machine m)
  {
    Context ctx = new Context();

    Optimize s = ctx.mkOptimize();
    Expr press = ctx.mkIntConst(ctx.mkSymbol("press"));


    ArrayList<Expr> b = new ArrayList<>();
    Expr add = ctx.mkInt(0);
    for(int i=0; i<m.buttons.size(); i++)
    {
      b.add(ctx.mkIntConst(ctx.mkSymbol("b"+i)));
      add = ctx.mkAdd(add, b.get(i));

      // b# >= 0
      s.Add( ctx.mkGe(b.get(i), ctx.mkInt(0)));
    }
    // press = b0 + b1 + ...
    s.Add( ctx.mkEq(press, add));


    for(int i=0; i<m.voltages.size(); i++)
    {
      Expr sum = ctx.mkInt(0);

      for(int b_idx =0; b_idx<m.buttons.size(); b_idx++)
      {
        if (m.buttons.get(b_idx).contains(i))
        {
          sum = ctx.mkAdd( b.get(b_idx), sum);
        }
      }
      // voltage_i = sum of all buttons that can act on it
      s.Add( ctx.mkEq( ctx.mkInt(m.voltages.get(i)), sum)); 

    }
    // Least presses
    s.MkMinimize(press);

    if (s.Check() == Status.SATISFIABLE )
    {
      long p  = ClownMath.z3getO(s, press);
      return (int) p;
    }
    else
    {
      System.out.println("impossible");
    }

    return -1;


  }

  public class Machine
  {
    int light_count;
    BitSet on_mode;
    List<Integer> voltages;
    List<TreeSet<Integer> > buttons;
    public Machine(String line)
    {
      // [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}

      String lights = line.substring(1, line.indexOf(']'));
      //System.out.println(lights);
      String butt_str = line.substring(line.indexOf(']')+1, line.indexOf('{')).trim();
      //System.out.println(butt_str);
      String volt_str = line.substring(line.indexOf('{')+1, line.indexOf('}')).trim();
      //System.out.println(volt_str);

      light_count=lights.length();
      on_mode = new BitSet(light_count);
      for(int i=0; i<lights.length(); i++)
      {
        char z = lights.charAt(i);
        if (z == '#') on_mode.set(i);
      }
      buttons=new ArrayList<>();

      for(String b : Tok.en(butt_str, " "))
      {
        b=b.trim();
        b=b.replace("(","").replace(")","");
        TreeSet<Integer> v = new TreeSet<>();
        v.addAll(Tok.ent(b, ","));
        buttons.add(v);
      }
      voltages=new ArrayList<>();
      voltages.addAll(Tok.ent(volt_str, ","));

    }
    public BitSet getDefaultState()
    {
      return new BitSet(light_count);
    }
    public BitSet pressButton(int b_idx, BitSet prev)
    {
      BitSet n_state = new BitSet(light_count);

      n_state.or(prev);

      TreeSet<Integer> b_set = buttons.get(b_idx);
      for(int b : b_set)
      {
        n_state.flip(b);
      }
      return n_state;
    }
    public List<Integer> joltButton(int b_idx, List<Integer> jolt)
    {
      ArrayList<Integer> n_jolt = new ArrayList<>();
      n_jolt.addAll(jolt);

      TreeSet<Integer> b_set = buttons.get(b_idx);
      for(int b : b_set)
      {
        n_jolt.set(b, n_jolt.get(b) + 1);
      }
      return n_jolt;
    }

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
