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
    long total = 0;
    long total2 = 0;

    while(scan.hasNextLong())
    {
      long mass = scan.nextLong();
      long fuel = mass / 3 - 2;
      total += fuel;
      total2 += findFuel(mass);
    }

    System.out.println("Part 1: " + total);
    System.out.println("Part 2: " + total2);

  }

  public long findFuel(long mass)
  {
    long fuel = mass / 3 - 2;
    if (fuel <=0L) return 0L;

    return fuel + findFuel(fuel);

  }

}
