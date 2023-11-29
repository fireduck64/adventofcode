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

  ArrayList<Inst> insts = new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      insts.add(new Inst(line));
    }

    Calc c = new Calc(insts);
    c.run();
    System.out.println("Part 1: " + c.acc);

    for(int idx = 0; idx<insts.size(); idx++)
    {
      ArrayList<Inst> i2 = new ArrayList<Inst>();
      i2.addAll(insts);
      i2.set(idx, i2.get(idx).swap());

      Calc c2 = new Calc(i2);
      if (c2.run()==0)
      {
        System.out.println("Part 2: " + c2.acc);

      }
      

    }


  }

  

}
