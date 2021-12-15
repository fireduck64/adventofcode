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
    Jumpy j = new Jumpy(scan);
    j.run();

    System.out.println(j.regs);
    long high = -1000000;
    for(long v : j.regs.values())
    {
      high = Math.max(v, high);
    }
    System.out.println("Part 1: " + high);
    System.out.println("Part 2: " + j.highest_val);

  }

}
