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
    List<String> lines = In.lines(scan);
    Clocko c = new Clocko(lines);

    c.run();
    System.out.println(c.vals);

    int p1 = 0;
    for(int i=0; i<6; i++)
    {
      int cycle = 20 + (i * 40);

      p1 += cycle * c.vals.get(cycle);

    }
    System.out.println(p1);

    c.map.print();



  }

}
