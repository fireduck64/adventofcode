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
  long gen_mod=2147483647L;
  long low_mod = 65536;

  long mult_a = 16807;
  long mult_b = 48271;

  // Sample
  long start_a = 65;
  long start_b = 8921;

  public Prob(Scanner scan)
  {
    start_a = 65;
    start_b = 8921;
    System.out.println("Sample p1: " + countMatches(5));
    System.out.println("Sample p1: " + countMatches(40000000L));
    start_a = 722;
    start_b = 354;
    System.out.println("p1: " + countMatches(40000000L));
    
    start_a = 65;
    start_b = 8921;
    System.out.println("sample p2: " + countMatches2(5000000L));
    
    start_a = 722;
    start_b = 354;
    System.out.println("p2: " + countMatches2(5000000L));

  }

  public long countMatches(long cycles)
  {
    long a = start_a;
    long b = start_b;
    long matches = 0;

    for(long c = 0; c<cycles; c++)
    {
      a = (a * mult_a) % gen_mod;
      b = (b * mult_b) % gen_mod;
      //system.out.println(" " + a + " " + b);

      if (a % low_mod == b % low_mod)
      {
        matches++;
      }

    }

    return matches;

  }
  public long countMatches2(long cycles)
  {
    long a = start_a;
    long b = start_b;
    long matches = 0;

    for(long c = 0; c<cycles; c++)
    {
      a = (a * mult_a) % gen_mod;
      while( a % 4 != 0)
      {
        a = (a * mult_a) % gen_mod;
      }

      b = (b * mult_b) % gen_mod;
      while (b % 8 != 0)
      {
        b = (b * mult_b) % gen_mod;

      }
      //system.out.println(" " + a + " " + b);

      if (a % low_mod == b % low_mod)
      {
        matches++;
      }

    }

    return matches;

  }


}
