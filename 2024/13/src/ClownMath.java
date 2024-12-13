
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.RatNum;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import java.math.BigInteger;
import java.util.*;

public class ClownMath
{

  // Get the least common multiple
  // aka, the smallest X where each number in the input times some integer equals X
  public static BigInteger lcm(Collection<BigInteger> in)
  {
    LinkedList<BigInteger> list = new LinkedList<>();
    list.addAll(in);

    if (list.size() == 0) return null;
    BigInteger x = list.pop();

    while(list.size() > 0)
    {
      BigInteger a = list.pop();
      x = lcm(x, a);
    }

    return x;

  }

  public static BigInteger lcm(BigInteger a, BigInteger b)
  {
    return a.multiply(b).divide( a.gcd(b) );

  }

  public static BigInteger lcm_l(Collection<Long> in)
  {
    LinkedList<BigInteger> bi = new LinkedList<>();

    for(Long l : in)
    {
      bi.add(BigInteger.valueOf(l));
    }
    return lcm(bi);

  }

  public static long z3Solve()
  {
    Context ctx = new Context();
    Solver s = ctx.mkSolver();

    // Can also be ct.mkRealConst for floating
    Expr ma = ctx.mkIntConst(ctx.mkSymbol("ma"));
    Expr mb = ctx.mkIntConst(ctx.mkSymbol("mb"));
    Point a = new Point(45,20);
    Point b = new Point(23,64);
    Point target = new Point(2702, 3836);

    // a.x * ma + b.x * mb = target.x
    s.add( ctx.mkEq(
      ctx.mkAdd( ctx.mkMul(ctx.mkInt(a.x), ma), ctx.mkMul(ctx.mkInt(b.x), mb)),
      ctx.mkInt(target.x)
    ));
    // a.y * ma + b.y * mb = target.y
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

  public static long z3get(Solver s, Expr sym)
  {
    Expr v = s.getModel().getConstInterp(sym);

    if (v instanceof IntNum)
    {
      IntNum vi = (IntNum) v;
      return vi.getInt64();

    }
    else if (v instanceof RatNum)
    {
      RatNum rn = (RatNum) v;
      return (long) Math.round(Double.parseDouble(rn.toDecimalString(10).replace("?","")));
    }
    else
    {
      E.er("Unknown type: " + v);
      return 0L;
    }


  }


}
