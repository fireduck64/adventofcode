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

  // West is -x
  // East is +x
  // North is -y
  // South is +y

  Random rnd=new Random();
  Map2D<Character> map = new Map2D<Character>('W');

  // This coordinate system allows us to map hex grid movements
  // onto a normal x,y grid.  Distances are not correct from
  // a geometric sense, but who cares.
  Point NW=new Point(-1,-1);
  Point W=new Point(-2,0);
  Point SW=new Point(-1,1);

  Point NE=new Point(1,-1);
  Point E=new Point(2,0);
  Point SE=new Point(1,1);


  public Prob(Scanner scan)
  {
    int black_count =0;
    while(scan.hasNext())
    {
      String token = scan.next();

      Point p = walk(new Point(0,0), token);
      if (map.get(p)=='W')
      {
        map.set(p,'B');
        black_count++;

      }
      else
      {
        map.set(p,'W');
        black_count--;

      }

    }
    System.out.println("Black count: " + black_count);


    Map2D<Character> m = map;
      System.out.println("Start: " + countBlack(m));
    for(int i=0; i<100; i++)
    {

      m = conway(m);
      //int d = i+1;
      //System.out.println("Day: " + d + " " + countBlack(m));
    }

    System.out.println("Fin: " + countBlack(m));
    

  }

  public int countBlack(Map2D<Character> m)
  {
    int bc = 0;
    for(Point p : m.getAllPoints())
    {
      if (m.get(p)=='B') bc++;
    }
    return bc;
 
  }

  public Map2D<Character> conway(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>('W');

    for(long x = in.low_x - 3; x<= in.high_x +3; x++)
    for(long y = in.low_y - 3; y<= in.high_y +3; y++)
    {
      Point p = new Point(x,y);
      List<Point> n = getNeighbors(p);
      char z = in.get(p);
      int nc = 0;
      for(Point np : n)
      {
        if (in.get(np)=='B') nc++;
      }

      //System.out.println(" "+ z+ " " + p + " has " + nc);
      if (z=='B')
      {
        if ((nc==0) || (nc>2)) 
        {
          //out.set(p, 'W');
        }
        else out.set(p, 'B');
      }
      if (z=='W')
      {
        if (nc==2)
        {
          out.set(p, 'B');
        }
      }

    }
    return out;

  }

  public Point walk(Point start, String path)
  {
    if (path.length() ==0) return start;

    if (path.startsWith("nw"))
    {
      return walk(start.add(NW), path.substring(2));
    }
    if (path.startsWith("ne"))
    {
      return walk(start.add(NE), path.substring(2));
    }
    if (path.startsWith("w"))
    {
      return walk(start.add(W), path.substring(1));
    }
    if (path.startsWith("e"))
    {
      return walk(start.add(E), path.substring(1));
    }
    if (path.startsWith("sw"))
    {
      return walk(start.add(SW), path.substring(2));
    }
    if (path.startsWith("se"))
    {
      return walk(start.add(SE), path.substring(2));
    }

    throw new RuntimeException("Fail: " + path);

  }


  public List<Point> getNeighbors(Point p)
  {
    LinkedList<Point> dirs = new LinkedList<>();
    dirs.add(E);
    dirs.add(W);
    dirs.add(SE);
    dirs.add(SW);
    dirs.add(NE);
    dirs.add(NW);

    LinkedList<Point> n = new LinkedList<>();
    for(Point d : dirs)
    {
      n.add( p.add(d) );

    }
    return n;

  }

}
