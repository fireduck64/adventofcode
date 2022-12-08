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
    Map2D<Integer> map = new Map2D<Integer>(-1);

  public Prob(Scanner scan)
  {
    MapLoad.loadMapInt(map, scan);

    int p1 = 0;
    int p2 = 0;
    for(Point p  : map.getAllPoints())
    {
      if (isVisible(p)) p1++;

      p2 = Math.max(getScore(p), p2);
    }
    System.out.println(p1);
    System.out.println(p2);


  }

  public boolean isVisible(Point p)
  {
    int h = map.get(p);

    if (findTallest(p, new Point(0,1)) < h) return true;
    if (findTallest(p, new Point(0,-1)) < h) return true;
    if (findTallest(p, new Point(1,0)) < h) return true;
    if (findTallest(p, new Point(-1,0)) < h) return true;

    return false;
  }
  public int getScore(Point p)
  {
    int h = map.get(p);

    return
      countTrees(p, new Point(0, 1), h) *
      countTrees(p, new Point(0, -1), h) *
      countTrees(p, new Point(1, 0), h) *
      countTrees(p, new Point(-1, 0), h);
  }

  public int countTrees(Point p, Point delta, int h)
  {
    int count =0;
    p = p.add(delta);

    while(map.get(p) >= 0)
    {
      count++;
      if (map.get(p) >= h) break;
      p = p.add(delta);

    }
    return count;
    

  }
  public int findTallest(Point p, Point delta)
  {
    int tallest = -1;
    p = p.add(delta);
    while(map.get(p) >= 0)
    {
      tallest = Math.max(map.get(p), tallest);
      p = p.add(delta);
    }
    return tallest;

  }

}
