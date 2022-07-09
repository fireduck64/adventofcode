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
    String line = scan.nextLine();
    {
      IntComp comp = new IntComp(line);
      comp.input.add(1L);

      comp.exec();
      System.out.println("Part 1: " + comp.output.peekLast());
    }
    {
      IntComp comp = new IntComp(line);
      comp.input.add(5L);

      comp.exec();
      System.out.println("Part 2: " + comp.output.peekLast());
    }



  }

}
