import java.math.BigInteger;

import java.util.*;

import com.microsoft.z3.RatNum;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Solver;



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

  public static long z3get(Solver s, Expr sym)
  {
    Expr v = s.getModel().getConstInterp(sym);

    if (v instanceof IntNum)
    {
      IntNum vi = (IntNum) v;
      return vi.getInt64();

    }
    else
    {
      RatNum rn = (RatNum) v;
      return (long) Math.round(Double.parseDouble(rn.toString()));

    }


  }


}

