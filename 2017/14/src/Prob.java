import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Integer> map=new Map2D<Integer>(0);

  public Prob(Scanner scan)
  {
    String input = scan.nextLine();

    for(int i=0; i<128; i++)
    {
      String bin = Knot.hashbin(input + "-" + i);
      for(int j=0; j<128; j++)
      {
        if (bin.charAt(j) == '1')
        {
          map.set(i,j,1);
        }
        else
        {
          //map.set(i,j,0);
        }
      }
    }
    System.out.println("Part 1: " + map.getCounts().get(1));

    int groups = 0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p) == 1)
      {
        mark(p);
        groups++;
      }

    }
    System.out.println("Part 2: " + groups);


  }

  public void mark(Point start)
  {
    LinkedList<Point> queue = new LinkedList<>();
    queue.add(start);

    while(queue.size() > 0)
    {
      Point p = queue.pollFirst();
      if (map.get(p) == 1)
      {
        map.set(p, 0);
        queue.addAll( map.getAdj(p, false));
      }

    }

  }

}
