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

    long v=0;
    while(true) 
    {
      System.out.println("Running: " + v);
      Ecomp e = new Ecomp(input);

      e.regs.put("a",v);
      long ret = e.exec();
      if (ret == 0)
      {
        System.out.println("Solution: " + v);
        break;
      }
      v++;

    }

  }

}
