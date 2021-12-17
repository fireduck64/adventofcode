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
  int t_min_x = 34;
  int t_max_x = 67;
  int t_min_y = -215;
  int t_max_y = -186;


  /*int t_min_x = 20;
  int t_max_x = 30;
  int t_min_y = -10;
  int t_max_y = -5;*/


  int high_y;

  public Prob(Scanner scan)
  {

    System.out.println(launch(6,9));
    
    int t_high_y=0;
    int wins=0;
    for(int dy=-2500; dy<2500; dy++)
    for(int dx=-100; dx<100; dx++)
    {
      high_y=0;
      if (launch(dx,dy))
      {
        wins++;
        t_high_y = Math.max(t_high_y, high_y);

      }

    }
    System.out.println(t_high_y);
    System.out.println(wins);
    

  }

  public boolean launch(int dx, int dy)
  {
    int x =0;
    int y=0;
    while(y >= t_min_y)
    {
      if (t_min_x <= x)
      if (x <= t_max_x)
      if (t_min_y <= y)
      if (y <= t_max_y)
      {
        return true;
      }

      x=x+dx;
      y=y+dy;
      //System.out.println(new Point(x,y));
      high_y = Math.max(high_y, y);

      if (dx > 0) dx--;
      else if (dx < 0) dx++;
      dy--;
    }
    return false;

  }

}
