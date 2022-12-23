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

  Map2D<Character> input = new Map2D<Character>('.');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(input, scan);
    move_dirs.add(new Point(0,-1));
    move_dirs.add(new Point(0,1));
    move_dirs.add(new Point(-1,0));
    move_dirs.add(new Point(1,0));

    input.print();

    Map2D<Character> p1 = input.copy();
    for(int i=0; i<10; i++)
    {
      p1 = step(p1, i);
    }
    p1.print();

    long area= (p1.high_y - p1.low_y + 1) * (p1.high_x - p1.low_x + 1);
    long empty = area - p1.getCounts().get('#');
    System.out.println("P1: " + empty);

    Map2D<Character> p2 = input.copy();
    int step = 0;
    while(true)
    {
      global_move_count=0;
      p2 = step(p2, step);
      if (global_move_count==0) break;
      step++;
    }
    step++;
    System.out.println("P2: " + step);
 

  }

  ArrayList<Point> move_dirs = new ArrayList<>();

  int global_move_count=0;

  public Map2D<Character> step(Map2D<Character> in, int step_count)
  {
    TreeMap<Point, Integer> target_counts = new TreeMap<>();
    TreeMap<Point, Point> moves = new TreeMap<>();

    Map2D<Character> out = new Map2D<Character>('.');

    for(Point p : in.getAllPoints())
    {
      if (in.get(p)=='#')
      {
        boolean move=false;
        if (countAround(in, p) > 0)
        {
          for(int i=0; i<4; i++)
          {
            Point dir = move_dirs.get((step_count +i) % 4);
            if (countDir(in, p, dir) == 0)
            {
              Point t = p.add(dir);
              moves.put(p, t);
              if (!target_counts.containsKey(t)) target_counts.put(t, 0);
              target_counts.put(t, target_counts.get(t) + 1);
              move=true;
              break;
            }
          }

        }
        if (!move)
        {
          out.set(p, '#');
        }
      }

    }

    for(Map.Entry<Point, Point> me : moves.entrySet())
    {
      Point src = me.getKey();
      Point dst = me.getValue();

      if (target_counts.get(dst) == 1)
      {
        out.set(dst, '#');
        global_move_count++;
      }
      else
      {
        out.set(src, '#');
      }

    }
    return out;

  }


  public int countDir(Map2D<Character> in, Point p, Point dir)
  {
    
    int cnt=0;
    Point t = p.add(dir);
    for(Point q : in.getAdj(p,true))
    {
      if (in.get(q)=='#') 
      {
        if ((dir.x != 0) && (t.x == q.x))
        {
          cnt++;
        }      
        if ((dir.y != 0) && (t.y == q.y))
        {
          cnt++;
        }
      }
    }
    return cnt;

  }

  public int countAround(Map2D<Character> in, Point p)
  {
    int cnt=0;
    for(Point q : in.getAdj(p,true))
    {
      if (in.get(q)=='#') cnt++;
    }
    return cnt;
  }

}
