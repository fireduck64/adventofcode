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
    Map2D<Character> input = new Map2D<>('_');

    MapLoad.loadMap(input, scan);

    Map2D<Character> m = input;

    while(true)
    {
      String h1 = m.getHashState();

      m = update(m);

      String h2 = m.getHashState();

      if (h2.equals(h1)) break;

      System.out.println(m.getPrintOut(null));
    }

    int filled=0;
    for(Point p : m.getAllPoints())
    {
      char z = m.get(p.x, p.y);

      if (z=='#') filled++;
    }
    System.out.println(m.getPrintOut(null));

    System.out.println(filled);
    


  }

  public Map2D<Character> update(Map2D<Character> input)
  {
    Map2D<Character> out = new Map2D<>('_');
    for(Point p : input.getAllPoints())
    {
      int adj_filled = 0;
      char z = input.get(p.x, p.y);
      if (z=='.') out.set(p.x,p.y,z);
      else
      {

        for(int i=-1; i<=1; i++)
        for(int j=-1; j<=1; j++)
        {

          boolean fin=false;
          int m =1;
          // For part one, remove this while
          while(!fin)
          {
            if ((i != 0) || (j != 0))
            {
              char f = input.get(p.x+i*m, p.y+j*m);
              if (f=='_') { fin=true; }
              if (f=='L') { fin=true; }
              if (f=='#') { adj_filled++; fin=true; }
            }
            else
            {
              fin=true;
            }
            m++;
          }
        }
      }
      //System.out.println("" + p + " " + adj_filled);
      if ((z =='L') && (adj_filled == 0)) 
      {
        out.set(p.x,p.y,'#');
      }
      else if ((z == '#') && (adj_filled >= 5)) 
      {
        out.set(p.x,p.y,'L');
      }
      else
      {
        out.set(p.x,p.y,z);
      }
    }
    return out;

  }

}
