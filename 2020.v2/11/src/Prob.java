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
    Map2D<Character> input = new Map2D<Character>('.');

    MapLoad.loadMap(input, scan);

    HashSet<String> states = new HashSet<>();

    Map2D<Character> m = input;
    while(true)
    {
      m = sim(m);
      String h = m.getHashState();
      if (states.contains(h)) break;

      states.add(h);
    }
    System.out.println("Part 1: " + m.getCounts());

    states.clear();
    m = input;
    while(true)
    {
      m = sim2(m);
      String h = m.getHashState();
      if (states.contains(h)) break;

      states.add(h);
    }
    System.out.println("Part 2: " + m.getCounts());


  }


  public Map2D<Character> sim(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>('.');

    for(Point p : in.getAllPoints())
    {
      char z = in.get(p);
      //Default same
      out.set(p, z);
      int c = 0;
      for(Point q : in.getAdj(p, true))
      {
        if (in.get(q)=='#') c++;
      }
      if (z=='L')
      {
        if (c==0)
        {
          out.set(p, '#');
        }
      }
      if (z=='#')
      {
        if (c>=4)
        { 
          out.set(p, 'L');
        }
      }
    }
    return out;
  }

  public Map2D<Character> sim2(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>('.');

    for(Point p : in.getAllPoints())
    {
      char z = in.get(p);
      //Default same
      out.set(p, z);
      int c = 0;
      for(Point q : getAdj2(p,in))
      {
        if (in.get(q)=='#') c++;
      }

      if (z=='L')
      {
        if (c==0)
        {
          out.set(p, '#');
        }
      }
      if (z=='#')
      {
        if (c>=5)
        { 
          out.set(p, 'L');
        }
      }
    }
    return out;
  }

  public List<Point> getAdj2(Point p, Map2D<Character> in)
  {
    LinkedList<Point> out = new LinkedList<>();
    for(int x=-1; x<=1; x++)
    for(int y=-1; y<=1; y++)
    {
      int d = Math.abs(x) + Math.abs(y);
      if (d > 0)
      {
        Point delta = new Point(x,y);
        int mult=1;
        while(true)
        {
          Point q = p.add(delta.mult(mult));
          if (q.x < in.low_x) break;
          if (q.y < in.low_y) break;
          if (q.x > in.high_x) break;
          if (q.y > in.high_y) break;

          if (in.get(q) != '.')
          {
            out.add(q);
            break;
          }
          
          mult++;
        }
      }

    }

    return out;


  }

}
