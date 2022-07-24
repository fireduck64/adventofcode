import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Long> map=new Map2D<Long>(0L);

  Map<Long,Character> display_map = ImmutableMap.of(
    0L, ' ',
    1L, '#',
    2L, 'O',
    3L, '-',
    4L, '*');



  public Prob(Scanner scan)
    throws Exception
  {
    String line = scan.nextLine();

    {
      map=new Map2D<Long>(0L);
      IntComp comp = new IntComp(line);
      comp.exec();
      while(comp.output.size() > 0)
      {
        long x = comp.output.poll();
        long y = comp.output.poll();
        long v = comp.output.poll();
        map.set(new Point(x,y), v);

      }
      System.out.println(map.getPrintOut(display_map));
      System.out.println("Part 1: " + map.getCounts().get(2L));
    }
    {
      map=new Map2D<Long>(0L);
      IntComp comp = new IntComp(line);
      comp.writeVal(0L, 2L); // free play
      long score=0;

      while(!comp.term)
      {
        comp.exec();
        while(comp.output.size() > 0)
        {
          long x = comp.output.poll();
          long y = comp.output.poll();
          long v = comp.output.poll();
          if (x==-1)
          {
            score = v;
          }
          else
          {
            map.set(new Point(x,y), v);
          }
        }
        Point ball = find(4L);
        Point paddle = find(3L);
        long dir = 0;
        if (paddle.x < ball.x) dir=1;
        if (paddle.x > ball.x) dir=-1;
        comp.input.add(dir);
        //System.out.println(map.getPrintOut(display_map));

      }
      System.out.println("Part 2: " + score);

    }

  }
  public Point find(long v)
  {
    for(Point p : map.getAllPoints())
    {
      if (map.get(p)==v) return p;

    }
    return null;
  }
}
