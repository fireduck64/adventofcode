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
    TreeSet<Long> v = new TreeSet<>();

    int pre=25;
    boolean p1=true;
    long p1_v=0;
    List<Long> input = In.numbers(scan);
    for(long x : input)
    {
      if (pre > 0)
      {
        v.add(x);
      }
      else
      {
        if (!check(v, x))
        {
          if (p1)
          {
            System.out.println("Part 1: " + x);
            p1=false;
            p1_v=x;
          }
        }
        else
        {
          v.add(x);

        }

        
      }
      pre--;

    }

    for(int i=0; i<input.size(); i++)
    for(int j=i+1; j<input.size(); j++)
    {
      long sum = 0;
      long high=0;
      long low=69213187032307L;
      for(int k=i; k<=j; k++)
      {
        long n = input.get(k);
        sum += input.get(k);
        high = Math.max(high, n);
        low = Math.min(low, n);
      }
      if (sum == p1_v)
      {
        System.out.println("Part 2: " + (low+high));
      }


    }

  }

  public boolean check(Set<Long> v, long a)
  {
    for(long b : v)
    {
      if (b < a)
      {
        long c = a - b;
        if (v.contains(c)) return true;

      }

    }
    return false;
    

  }

}
