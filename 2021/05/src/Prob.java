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

  Map2D<Integer> map = new Map2D<Integer>(0);

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {   
      String line=scan.nextLine();
      line = line.replace(" -> ",",");
      line = line.replace(",", " ");
      Scanner s2 = new Scanner(line);
      int x1 = s2.nextInt();
      int y1 = s2.nextInt();
      int x2 = s2.nextInt();
      int y2 = s2.nextInt();

      System.out.println("" + x1 + " " + y1 + " " + x2 + " " + y2);

      {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int n = Math.max(Math.abs(dy), Math.abs(dx));
        if (dx != 0)
        {
          dx = dx/Math.abs(dx);
        }
        if (dy != 0)
        {
          dy = dy/Math.abs(dy);
        }
        for(int i =0 ;i<=n; i++)
        {
          int x = x1 + i*dx;
          int y = y1 + i*dy;
          map.set(x,y, map.get(x,y)+1);
        }
      }
      
    }

    int cnt = 0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p) > 1) cnt++;
    }

    System.out.println(map.getPrintOut(null));
    System.out.println(cnt);

  }

}
