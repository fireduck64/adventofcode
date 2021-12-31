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
    List<String> input = In.lines(scan);

    {
      Ecomp e = new Ecomp(input);
      e.regs.put("a",7L);
      System.out.println("Ops: " + e.exec());
      System.out.println("Part 1: " );
      System.out.println(e.regs);
    }
    for(long v=6; v<=12; v++)
    {
      System.out.println("Input: " + v);
      Ecomp e = new Ecomp(input);
      e.regs.put("a",v);
      e.exec();
      System.out.println("Part 2: " );
      System.out.println(e.regs);
 
    }

  }

}
