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

  TreeMap<String, Point> dirs = new TreeMap<>();

  // Fucking hex grid

  public Prob(Scanner scan)
  {
    dirs.put("n", new Point(0,-2));
    dirs.put("ne", new Point(1,-1));
    dirs.put("nw", new Point(-1,-1));
    dirs.put("s", new Point(0,2));
    dirs.put("se", new Point(1,1));
    dirs.put("sw", new Point(-1,1));

    List<String> steps = Tok.en(scan.nextLine(), ",");
    Point p = new Point(0,0);


    long max = 0;
    for(String s : steps)
    {
      p = p.add( dirs.get(s));
      max = Math.max(max, stepDist(p));
    }
    System.out.println("Final: " + p);
    System.out.println("Part 1: " + stepDist(p));
    System.out.println("Part 2: " + max);




  }

  public long stepDist(Point p)
  {
    long d = Math.abs(p.x) + Math.abs(p.y);
    if (d % 2 == 1) d++;
    return d/2;
  }



}
