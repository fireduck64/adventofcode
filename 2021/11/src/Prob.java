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

  Map2D<Integer> map= new Map2D<Integer>(-1);

  public Prob(Scanner scan)
  {
    MapLoad.loadMapInt(map, scan);
    
    //long step1 = 0;
    
    //for(int i=0; i<100; i++)
    int step=0;
    while(true)
    {
      step++;
      long cnt = step();
      if (cnt == 100)
      {
        System.out.println(step);
        break;
      } 
    }

  }

  public long step()
  {
    long total_flash = 0;
    // add power
    for(Point p : map.getAllPoints())
    {
      map.set(p, map.get(p) + 1);
    }
    while(true)
    {
      int flash = 0;
      for(Point p : map.getAllPoints())
      {
        if (map.get(p) > 9)
        {
          flash++;
          map.set(p,-2); // already flashed
          for(Point q : map.getAdj(p, true))
          {
            int v = map.get(q);
            if (v >= 0)
            {
              map.set(q, map.get(q) + 1);
            }
          }
        }
      }
      total_flash += flash;

      if (flash == 0) break;
    }

    for(Point p : map.getAllPoints())
    {
      int v = map.get(p);
      if (v == -2)
      {
        map.set(p, 0);
      }
    }

    return total_flash;

  }

}
