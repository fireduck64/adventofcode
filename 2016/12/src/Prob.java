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
   Ecomp e = new Ecomp(input);
   e.exec();
   System.out.println("Part 1:" );
   System.out.println(e.regs);

   e.reset();
   e.regs.put("c",1L);
   e.exec();
   System.out.println("Part 2:" );
   System.out.println(e.regs);
  }

}
