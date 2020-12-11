import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Star default_star = null;
  Map2D<Star> initial = new Map2D<Star>(default_star);

  public Prob(Scanner scan)
  {
    while(scan.hasNext())
    {
      int x,y,dx,dy;

      x = scan.nextInt();
      y = scan.nextInt();
      dx = scan.nextInt();
      dy = scan.nextInt();

      initial.set(x,y, new Star(dx, dy) );
    }

    Map2D<Star> m = initial;

    Map2D<Star> tight = initial;
    long spread=10000000L;
    int time=0;

    for(int i=1; i<100000; i+=1)
    //int i=10634;
    {
      m = advance(initial, i);

      long s = Math.abs(m.high_x - m.low_x) + Math.abs(m.high_y - m.low_y);
      if (s <= spread)
      {
        tight = m;
        spread =s;
        time = i;
      }

    }
    System.out.println( tight.getPrintOut(null) );
    System.out.println("Time: " + time + " " + spread);

    

  }

  Map2D<Star> advance( Map2D<Star> input, int time)
  {
    Map2D<Star> out = new Map2D<Star>(default_star);

    for(Point p : input.getAllPoints())
    {
      Star s = input.get(p.x, p.y);

      out.set( p.x + time * s.dx, p.y + time*s.dy, s);

    }

    return out;
  }


  public class Star
  {
    final int dx;
    final int dy;

    public Star(int dx, int dy)
    {
      this.dx = dx;
      this.dy = dy;
    }


    public String toString(){return "#";}
  }

}
