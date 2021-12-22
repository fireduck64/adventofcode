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
    String line = scan.nextLine().replace(",","");
    Point pos = new Point(0,0);
    Point dir = new Point(0,-1);
    TreeSet<Point> visit=new TreeSet<>();
    visit.add(pos);
    Point part2=null;

    for(String s : Tok.en(line, " "))
    {
      if (s.charAt(0)=='R') dir = Nav.turnRight(dir);
      else dir = Nav.turnLeft(dir);

      int steps = Integer.parseInt(s.substring(1));
      for(int t=0; t<steps; t++)
      {
        pos = pos.add(dir);

        if (visit.contains(pos))
        if (part2 == null)
        {
          part2 = pos;
        }
        visit.add(pos);
      }

    }
    Point origin=new Point(0,0);
    System.out.println(pos);
    System.out.println("Part 1: " + pos.getDistM(origin));

    System.out.println("Part 2: " + part2.getDistM(origin));

  }

}
