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

  public Prob(Scanner scan)
  {
    List<String> lines = In.lines(scan);
    {
      Net n = new Net(lines);

      long ops = n.exec();
      System.out.println("Executed " + ops);

      System.out.println("Part 1: " + n.mul_call);
    }


    /*{

      Net n = new Net(lines);
      n.regs.put("a",1L);
      long ops = n.exec();
      System.out.println("Executed " + ops);
      System.out.println("Part 2: " + n.getReg("h"));
    }*/
    System.out.println("Part 2: " + countComposit(109300, 126300, 17));
  }

  public int countComposit(int start, int end, int by)
  {
    int primes=0;
    for (int i=start; i<=end; i+=by)
    {
      if (!checkPrime(i)) primes++;

    }
    return primes;

  }

  // 126300-109300
  public boolean checkPrime(int v)
  {
    int m = (int)Math.ceil(Math.sqrt(v));
    for(int i=2; i<=m; i++)
    {
      if (v % i == 0) return false;
    }
    return true;

  }

}
