import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  IntComp compy;

  Map2D<Long> map;

  public Prob(Scanner scan)
  {
    compy = new IntComp(scan.nextLine());
    map = new Map2D<>(0L);
    
    HashSet<Long> painted=new HashSet<>();

    map.set(0,0,1L);

    long x=0;
    long y=0;
    long dx=0;
    long dy=-1;
    while(!compy.term)
    {
      compy.input_queue.add(map.get(x,y));
      compy.execute();

      painted.add(x*1000000L+y);
      long color = compy.output_queue.poll();
      map.set(x,y,color);

      long turn = compy.output_queue.poll();

      if (turn ==0) //left
      {
        if (dy==-1)
        {
          dy=0;
          dx=-1;
        }
        else if (dy==1)
        {
          dy=0;
          dx=1;
        }
        else if (dx==1)
        {
          dy=-1;
          dx=0;
        }
        else if (dx==-1)
        {
          dy=1;
          dx=0;
        }
      }
      if (turn ==1) //right
      {
        if (dy==-1)
        {
          dy=0;
          dx=1;
        }
        else if (dy==1)
        {
          dy=0;
          dx=-1;
        }
        else if (dx==1)
        {
          dy=1;
          dx=0;
        }
        else if (dx==-1)
        {
          dy=-1;
          dx=0;
        }
      }

      x+=dx;
      y+=dy;

    }

    TreeMap<Long, Character> print_map = new TreeMap<>();
    print_map.put(0L,' ');
    print_map.put(1L,'#');
    

    System.out.println("Painted: " + painted.size());

    System.out.println(map.getPrintOut(print_map));


  }

}
