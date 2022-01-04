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
    String line = scan.nextLine().replace(",","").replace(".","");
    List<Integer> vals = Tok.ent(line, " ");
    System.out.println(vals);
    Point target = new Point(vals.get(1), vals.get(0));
    System.out.println("Target: " + target);

    int next_y=2;
    Point diag = new Point(+1,-1);
    Point pos = new Point(1,1);

    Map2D<Integer> m = new Map2D<Integer>(0);

    int i = 1;
    long v = 20151125;
    while(true)
    {
      //m.set(pos, i);
      if (pos.equals(target))
      {
        System.out.println("Sol: " + v);
        break;
      }
      if (i % 10000 == 0) System.out.println(pos.toString() + " - " + v);

      i++;
      v = (v * 252533) % 33554393;
      if (pos.y == 1)
      {
        pos = new Point(1, next_y);
        next_y++;
      }
      else
      {
        pos = pos.add(diag);
      }

      //if (i>9) break;

    }

    //m.print();



  }

}
