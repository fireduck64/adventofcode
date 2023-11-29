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

  // 0 - empty
  // 1 - lava
  // 2 - water
  Map3D<Integer> map = new Map3D<Integer>(0);

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      List<Integer> num = Tok.ent(line,",");
      map.set(new Point(num.get(0), num.get(1), num.get(2)), 1);
    }


    // It would be easy to over think this. 
    // Really every adjacent cube that isn't lava is a face
    int faces=0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p)== 1)
      {
        for(Point q : map.getAdj(p,false))
        {
          if (map.get(q) == 0) faces++;

        }

      }

    }
    System.out.println("Part 1: " + faces);


    // Flood fill in from the outside
    // A data structure that a good flood fill might use
    TreeSet<Point> queue = new TreeSet<Point>();

    map.set(new Point(-2,-2,-2), 2);

    // A proper flood fill I wrote after the fact.
    // runs a lot faster
    queue.add(new Point(-2,-2,-2));
    while(queue.size() > 0)
    {
      Point p = queue.pollFirst();
      if (map.get(p) == 2)
      for(Point q : map.getAdj(p, false))
      {
          // Did some quick cat | tr | sort to see the ranges
          if (q.x >= -2)
          if (q.y >= -2)
          if (q.z >= -2)
          if (q.x <= 22)
          if (q.y <= 22)
          if (q.z <= 22)
          if (map.get(q)==0)
          {
            map.set(q,2);
            queue.add(q);
          }
      }
    }

    // But instead, what follows is the stupidest flood fill
    /*int added=1;
    while(added>0)
    {
      added=0;
    
      for(Point p : map.getAllPoints())
      {
        if (map.get(p) == 2)
        for(Point q : map.getAdj(p, false))
        {
          // Did some quick cat | tr | sort to see the ranges
          if (q.x >= -2)
          if (q.y >= -2)
          if (q.z >= -2)
          if (q.x <= 22)
          if (q.y <= 22)
          if (q.z <= 22)
          if (map.get(q)==0)
          {
            added++;
            map.set(q,2);
          }
        }
      }
    }*/

    faces=0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p)== 1)
      {
        for(Point q : map.getAdj(p,false))
        {
          if (map.get(q) == 2) faces++;
        }
      }
    }
    System.out.println("Part 2: " + faces);


  }

}
