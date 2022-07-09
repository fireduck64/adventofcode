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

      comp.writeVal(1,12);
      comp.writeVal(2,2);
      comp.exec();
      System.out.println("Part 1: " + comp.readCode(0));
    }

    for(int i=0;i<=99;i++)
    for(int j=0; j<=99; j++)
    {
      IntComp comp = new IntComp(line);

      comp.writeVal(1,i);
      comp.writeVal(2,j);
      comp.exec();
      if (comp.readCode(0) == 19690720L)
      {
        int v = 100 * i + j;
        System.out.println("Part 2: " + v);

      }
      

    }

  }

}
