import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;


import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.StringSymbol;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Expr;


public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
  
    long sum = 0;
    while(scan.hasNextLine())
    {
      sum += runPhase(scan);
    }
    
		return "" + sum;
  }

  Point a;
  Point b;

  public long runPhase(Scanner scan)
  {
    a = readButton(scan);
    b = readButton(scan);
    Point prize = readButton(scan);
    
    if (scan.hasNextLine())
    {
      scan.nextLine();
    }

    int cost = rec(new Point(0,0), 0, prize);
    if (cost >= 0) return cost;

    return 0;
    

  }

  public int rec(Point loc, int mode, Point target)
  {
    if (loc.equals(target)) return 0;
    if (loc.x > target.x) return -1;
    if (loc.y > target.y) return -1;

    int low = 10000000;

    if (mode == 0)
    {
      {
        int cost = rec(loc.add(a), 0, target);
        if (cost >= 0)
        {
          cost += 3;
          low = Math.min(low, cost);
        }
      }
    }

    {
      int cost = rec(loc.add(b), 1, target);
      if (cost >= 0)
      {
        cost += 1;
        low = Math.min(low, cost);
      }

    }

    if(low < 10000000)
    {
      return low;
    }
    return -1;

  }

  public Point readButton(Scanner scan)
  {
    String line = scan.nextLine();
    List<Integer> lst = Tok.ent(line.replace(","," ").replace("+", " ").replace("="," "), " ");

    if(lst.size() != 2) E.er();
    return new Point(lst.get(0), lst.get(1));

  }

  public String Part2(Scanner scan)
    throws Exception
  {
  
    long sum = 0;
    while(scan.hasNextLine())
    {
      sum += runPhase2(scan);
      //System.out.println(sum);
    }
		return "" + sum;
  }
  long lcm;
  public long runPhase2(Scanner scan)
    throws Exception
  {
    a = readButton(scan);
    b = readButton(scan);
    Point prize = readButton(scan);

    prize = new Point(prize.x + 10000000000000L, prize.y + 10000000000000L);
    
    if (scan.hasNextLine())
    {
      scan.nextLine();
    }

    return solve(prize);
  }

  public long solve(Point target)
  {
    Context ctx = new Context();
    Solver s = ctx.mkSolver();
    Expr ma = ctx.mkIntConst(ctx.mkSymbol("ma"));
    Expr mb = ctx.mkIntConst(ctx.mkSymbol("mb"));

    // a.x * ma + b.x * mb = target.x
    s.add( ctx.mkEq(
      ctx.mkAdd( ctx.mkMul(ctx.mkInt(a.x), ma), ctx.mkMul(ctx.mkInt(b.x), mb)),
      ctx.mkInt(target.x)
    ));
    s.add( ctx.mkEq(
      ctx.mkAdd( ctx.mkMul(ctx.mkInt(a.y), ma), ctx.mkMul(ctx.mkInt(b.y), mb)),
      ctx.mkInt(target.y)
    ));

    //System.out.println(s.check());
    if (s.check() == Status.SATISFIABLE )
    {
      long s_ma  = ClownMath.z3get(s, ma);
      long s_mb  = ClownMath.z3get(s, mb);


      return s_ma * 3L + s_mb;
    }
    return 0L;
    

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
