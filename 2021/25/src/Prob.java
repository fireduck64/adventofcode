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
  Map2D<Character> mapo = new Map2D<>('.');

  long max_x=0;
  long max_y=0;
  int move_count=0;

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(mapo, scan);

    max_x = mapo.high_x+1;
    max_y = mapo.high_y+1;
    mapo.print();

    int steps=0;
    while(true)
    {
      move_count=0;
      mapo = step(mapo);
      steps++;
      if (move_count==0) break;

    }
    System.out.println("Steps: " + steps);

  }


  public Map2D<Character> step(Map2D<Character> in)
  {
    
    Map2D<Character> out = new Map2D<Character>('.');
    for(Point p : in.getAllPoints())
    {
      Point dir = null;
      char z = in.get(p);
      if (z=='>')
      {
        dir=new Point(1,0);
        Point n = p.add(dir);
        Point n2 = new Point(n.x % max_x, n.y%max_y);
        if (in.get(n2)=='.')
        {
          out.set(n2, z);
          move_count++;
        }
        else
        {
          out.set(p, z);
        }
      }
      if (z=='v')
      {
        out.set(p, z);
      }
    }

    in = out;
    out = new Map2D<Character>('.');
    for(Point p : in.getAllPoints())
    {
      Point dir = null;
      char z = in.get(p);
      if (z=='v')
      {
        dir=new Point(0,1);
        Point n = p.add(dir);
        Point n2 = new Point(n.x % max_x, n.y%max_y);
        if (in.get(n2)=='.')
        {
          out.set(n2, z);
          move_count++;
        }
        else
        {
          out.set(p, z);
        }
      }
      if (z=='>')
      {
        out.set(p,z);
      }

    }

    return out;



  }

}
