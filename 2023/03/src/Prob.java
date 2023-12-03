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
    MapLoad.loadMap(input,scan);

    long p1 = 0;
    long p2 = 0;
    for(Point p : input.getAllPoints())
    {
      int v = isPart(p);
      if (v > 0)
      {
        p1 += v;
      }
      p2+=isGear(p);


    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);
  }

  public int isGear(Point p)
  {
    if (input.get(p) != '*') return 0;
    TreeSet<Point> starts = new TreeSet<>();

    for(Point w : input.getAdj(p, true))
    {
      char z = input.get(w);
      if (isNumber(z))
      {
        int step =0;
        Point s = w;
        while(true)
        {
          Point y = w.add(new Point(-step, 0));
          if (!isNumber(input.get(y))) break;
          step++;
          s = y;
        }

        starts.add(s);

      }

    }
    if (starts.size() == 2)
    {
      return isPart(starts.first()) * isPart(starts.last());

    }
    return 0;


  }

  public int isPart(Point p)
  {
    if (isNumber(input.get(p.add(new Point(-1, 0))))) return 0;

    if (!isNumber(input.get(p))) return 0;

    String str = "";

    ArrayList<Point> points = new ArrayList<>();

    int step=0;
    while(true)
    {
      Point q = p.add(new Point(step, 0));
      if (!isNumber(input.get(q))) break;
      str += input.get(q);

      points.add(q);
      step++;
    }

    boolean is_part = false;
    for(Point w : points)
    {
      for(Point q : input.getAdj(w, true))
      {
        char z = input.get(q);
        if (isSymbol(z)) is_part=true;


      }

    }
    if (!is_part) return 0;

    return Integer.parseInt(str);

   

  }

  public boolean isSymbol(char z)
  {
    if (z=='.') return false;
    if (isNumber(z)) return false;
    return true;
  }

  public boolean isNumber(char z)
  {
    if (z < '0') return false;
    if (z > '9') return false;

    return true;
  }

  
  

}
