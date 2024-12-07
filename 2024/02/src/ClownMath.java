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


}

