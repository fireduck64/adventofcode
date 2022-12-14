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
  Point source = new Point(500,0);

  Map2D<Character> map = new Map2D<>('.');
  int high_y=0;
  

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      List<Integer> n = Tok.ent(line, " ,");
      List<Point> lst = new ArrayList<>();
      for(int i=0; i<n.size(); i+=2)
      {
        Point p = new Point(n.get(i), n.get(i+1));
        lst.add(p);
        high_y = Math.max(high_y, ((int)p.y)+2);
      }



      for(int i=0; i<lst.size() -1; i++)
      {
        Point start = lst.get(i);
        Point end = lst.get(i+1);

        int dy = (int)(end.y - start.y);
        if (dy != 0)
        {
          dy = dy/Math.abs(dy);
        }
        int dx = (int)(end.x - start.x);
        if (dx != 0)
        {
          dx = dx/Math.abs(dx);
        }
        Point delta = new Point(dx,dy);

        Point cur=start;
        while(!cur.equals(end))
        {
          map.set(cur, '#');
          cur = cur.add(delta);
          map.set(cur, '#');
        }


      }

    }

      Map2D<Character> m_clone = map.copy();
      int high_y_copy = high_y;
      high_y=10000;

      // Part 1
      int p1=0;
      while(true)
      {
        if (drop(source)) p1++;
        else break;
      }
      System.out.println(p1);

      // Part 1
      int p2=0;
      map = m_clone;
      high_y = high_y_copy;
      while(true)
      {
        if (drop(source)) p2++;
        else break;
      }
      System.out.println(p2);




  }

  /** return true if settles */
  public boolean drop(Point cur)
  {
    if (map.get(cur)!='.') return false;
    Point down = new Point(0,1);
    Point left = new Point(-1,1);
    Point right = new Point(1,1);
    while(true)
    {
      if (cur.y > 500) return false;

      if (cur.y + 1 < high_y)
      {
        if (map.get(cur.add(down))=='.')
        {
          cur = cur.add(down);
          continue;
        }
        if (map.get(cur.add(left))=='.')
        {
          cur = cur.add(left);
          continue;
        }
        if (map.get(cur.add(right))=='.')
        {
          cur = cur.add(right);
          continue;
        }
      }
      map.set(cur, 'O');
      return true;
    }

  }

}
