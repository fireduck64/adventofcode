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

  Map2D<Long> map=new Map2D<>(0L);
  IntComp comp=null;

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();
    
    {
      map=new Map2D<>(0L);
      comp = new IntComp(line);
      robotDrive();
    
      System.out.println("Part 1: " + map.getAllPoints().size());
    }
 
    {
      map=new Map2D<>(0L);
      comp = new IntComp(line);
      map.set(0,0,1L);
      robotDrive();
    
      System.out.println("Part 2: " + map.getAllPoints().size());
      TreeMap<Long, Character> conv = new TreeMap<>();
      conv.put(0L, ' ');
      conv.put(1L, '#');
      System.out.println(map.getPrintOut(conv));
    }


  }

  public void robotDrive()
  {

    Point dir = new Point(0,-1); // North
    Point loc = new Point(0,0);

    while(!comp.term)
    {
      comp.input.add( map.get(loc) );
      comp.exec();

      long color = comp.output.poll();
      map.set(loc, color);

      long turn = comp.output.poll();
      if (turn == 0l)
      {
        dir=Nav.turnLeft(dir);
      }
      else
      {
        dir=Nav.turnRight(dir);
      }
      loc = loc.add(dir);
    }


  }
}
