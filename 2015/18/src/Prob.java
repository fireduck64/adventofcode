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

  Map2D<Character> mapo = new Map2D<Character>('.');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(mapo, scan);

    Map2D<Character> p1 = mapo.copy();

    for(int i=0; i<100; i++)
    {
      p1 = sim(p1);
    }
    System.out.println("Part 1 - " + p1.getCounts());

    Map2D<Character> p2 = mapo.copy();
    setCorners(p2);

    for(int i=0; i<100; i++)
    {
      p2 = sim(p2);
      setCorners(p2);
    }
    System.out.println("Part 2 - " + p2.getCounts());


  }
  public void setCorners(Map2D<Character> in)
  {
    in.set(0,0,'#');
    in.set(0,in.high_y, '#');
    in.set(in.high_x,in.high_y, '#');
    in.set(in.high_x,0, '#');

  }

  public Map2D<Character> sim(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>('.');

    for(Point p : in.getAllPoints())
    {
      int count = 0;
      for(Point n : in.getAdj(p, true))
      {
        if (in.get(n)=='#') count++;
      }
      if(in.get(p)=='#')
      {
        if ((count >= 2) && (count <=3)) out.set(p, '#');
        else out.set(p,'.');
      }
      else
      {
        if (count == 3) out.set(p, '#');
        else out.set(p, '.');
      }

    }

    return out;

  }

}
