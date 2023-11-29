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
  Map2D<Character> map=new Map2D<>('.');

  public Prob(Scanner scan)
  {

    MapLoad.loadMap(map, scan);

    System.out.println("Part 1: " + treeCount(new Point(0,0), new Point(3, 1)));

    long n = 1;
    n = n * treeCount(new Point(0,0), new Point(1,1));
    n = n * treeCount(new Point(0,0), new Point(3,1));
    n = n * treeCount(new Point(0,0), new Point(5,1));
    n = n * treeCount(new Point(0,0), new Point(7,1));
    n = n * treeCount(new Point(0,0), new Point(1,2));
    System.out.println("Part 2: " + n);


  }

  public long treeCount(Point start, Point slope)
  {
    if (start.y > map.high_y) return 0;
    Point p = new Point( start.x % (map.high_x+1), start.y);

    long tree = 0;
    if (map.get(p)=='#') tree++;


    return tree + treeCount(p.add(slope), slope);


  }

}
