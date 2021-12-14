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

  TreeMap<Long, Point> locs=new TreeMap<>();

  public Prob(Scanner scan)
  {
    long target = scan.nextInt();
    part1(target);
    part2(target);
  }

  public void part1(long target)
  {
    Map2D<Long> map = new Map2D<Long>(0L);
    int px=0;
    int py=0;
    Point dir=new Point(1,0);
    locs.put(1L,new Point(0,0));
    map.set(0,0,1L);

    for(long i=2; i<=target; i++)
    {
      px += dir.x;
      py += dir.y;
      locs.put(i, new Point(px,py));
      map.set(new Point(px,py), i);

      {
        Point new_dir = nextDir(dir);
        Point check = new Point(new_dir.x + px, new_dir.y + py);
        if (map.get(check)==0)
        {
          dir=new_dir;
        }
      }
    }
    System.out.println( locs.get(target));
    long p1 = locs.get(target).x + locs.get(target).y;
    System.out.println("Part 1: " + p1);

  }
  public void part2(long target)
  {
    Map2D<Long> map = new Map2D<Long>(0L);
    int px=0;
    int py=0;
    Point dir=new Point(1,0);
    map.set(0,0,1L);

    while(true)
    {
      px += dir.x;
      py += dir.y;
      long v = 0;
      for(Point p : map.getAdj(new Point(px, py), true))
      {
        v+=map.get(p);
      }
      map.set(new Point(px,py), v);
      if (v > target)
      {
        System.out.println("Part 2: " + v);
        return;
      }

      {
        Point new_dir = nextDir(dir);
        Point check = new Point(new_dir.x + px, new_dir.y + py);
        if (map.get(check)==0)
        {
          dir=new_dir;
        }
      }
    }
    //System.out.println("Part 1: " + p1);

  }


  public Point nextDir(Point dir)
  {
    // right to up
    if (dir.x==1) { return new Point(0,-1); }
    // up to left
    if (dir.y==-1) { return new Point(-1,0); }

    // left to down
    if (dir.x==-1) { return new Point(0,1); }
    
    // down to right
    if (dir.y==1) { return new Point(1,0); }

    E.er();
    return null;
  }

}

