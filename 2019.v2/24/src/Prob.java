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
  Map2D<Character> start;

  public Prob(Scanner scan)
  {
    start = new Map2D<>('.');

    MapLoad.loadMap(start, scan);

    // Part 1
    {

      Map2D<Character> m = start;
      HashSet<Long> rate_map = new HashSet<>();

      while(true)
      {
        long v = getBioRating(m);
        if (rate_map.contains(v))
        {
          System.out.println("Part 1: " + v);
          break;
        }
        rate_map.add(v);
        m = step(m);
      }
    }

    // Part 2
    {
      Map3D<Character> m2 = new Map3D<>('.');
      for(Point p : start.getAllPoints())
      {
        m2.set(p, start.get(p));
      }

      for(int i=0; i<200; i++)
      {
        m2 = step2(m2);
        System.out.println(m2.getCounts());
      }
      for(int z=-1; z<=1; z++)
      {
        System.out.println("Depth: " + z);
        System.out.println(m2.getPrintOut(null, 0, 0, 4, 4, z));
      }
        System.out.println(m2.getCounts());

    }

  }

  public List<Point> getAdj(Point a)
  {
    LinkedList<Point> lst = new LinkedList<>();
    for(int i=-1; i<=1; i++)
    for(int j=-1; j<=1; j++)
    {
      Point v = new Point(a.x+i, a.y+j, a.z);
      if (v.x >= 0)
      if (v.x <= 4)
      if (v.y >= 0)
      if (v.y <= 4)
      if (Math.abs(i) + Math.abs(j) == 1)
      {
        lst.add(v);
      }
    }
    // Outer edge to inside
    if (a.x==0)
    {
      lst.add(new Point(1,2,a.z-1));
    }
    if (a.y==0)
    {
      lst.add(new Point(2,1,a.z-1));
    }
    if (a.x==4)
    {
      lst.add(new Point(3,2,a.z-1));
    }
    if (a.y==4)
    {
      lst.add(new Point(2,3,a.z-1));
    }
    // inner edge to outside
    if (a.equals(new Point(2,1, a.z)))
    { // north
      for(int i=0; i<5; i++) { lst.add(new Point(i,0,a.z+1)); }
    }
    if (a.equals(new Point(1,2, a.z)))
    { // west
      for(int i=0; i<5; i++) { lst.add(new Point(0,i,a.z+1)); }
    }
    if (a.equals(new Point(2,3, a.z)))
    { // south
      for(int i=0; i<5; i++) { lst.add(new Point(i,4,a.z+1)); }
    }
    if (a.equals(new Point(3,2, a.z)))
    { // east
      for(int i=0; i<5; i++) { lst.add(new Point(4,i,a.z+1)); }
    }
    //System.out.println("Adj: " + a + " - " + lst);

    return lst;
  }

  public long getBioRating(Map2D<Character> in)
  {
    long p=1;
    long v = 0;
    for(int j=0; j<5; j++)
    for(int i=0; i<5; i++)
    {
      if (in.get(i,j)=='#') v+=p;
      p=p*2;
    }
    return v;

  }


  public Map2D<Character> step(Map2D<Character> in)
  {
    Map2D<Character> out = new Map2D<Character>('.');
    for(int i=0; i<5; i++)
    for(int j=0; j<5; j++)
    {
      char z = in.get(i,j);
      int adj_count=0;
      for(Point p : in.getAdj(new Point(i,j), false))
      {
        if (in.get(p)=='#') adj_count++;
      }
      if (z=='#')
      {
        if (adj_count==1) out.set(i,j,'#');
        else out.set(i,j,'.');
      }
      if (z=='.')
      {
        if ((adj_count>=1) && (adj_count<=2))
        {
          out.set(i,j,'#');
        }
        else out.set(i,j,'.');

      }

    }

    return out;

  }

  public Map3D<Character> step2(Map3D<Character> in)
  {
    Map3D<Character> out = new Map3D<Character>('.');
    for(long k=in.low_z-2; k<=in.high_z+2; k++)
    for(int i=0; i<5; i++)
    for(int j=0; j<5; j++)
    {
      Point q = new Point(i,j,k);
      
      char z = in.get(q);
      int adj_count=0;
      if ((i!=2) || (j!=2))
      {
        for(Point p : getAdj(q))
        {
          if (in.get(p)=='#') adj_count++;
        }
        if (z=='#')
        {
          if (adj_count==1) out.set(q,'#');
        }
        if (z=='.')
        {
          if ((adj_count>=1) && (adj_count<=2))
          {
            out.set(q,'#');
          }
        }
      }
    }

    return out;

  }

}
