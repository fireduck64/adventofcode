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
    int total=0;
    int ribbon=0;
    for(String line : In.lines(scan))
    {
      List<Integer> lst = Tok.ent(line, "x");
      Collections.sort(lst);
      int a = lst.get(0);
      int b = lst.get(1);
      int c = lst.get(2);

      int sz = 3*a*b + 2*a*c + 2*b*c;
      total += sz;

      ribbon += 2*a + 2*b + a*b*c;

    }

    System.out.println("Part 1: " + total);
    System.out.println("Part 2: " + ribbon);
  }

}
