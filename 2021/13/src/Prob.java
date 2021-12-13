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

  Map2D<Boolean> map = new Map2D<Boolean>(false);

  public Prob(Scanner scan)
  {
    TreeMap<Boolean, Character> conv= new TreeMap<>();
    conv.put(true,'#');
    conv.put(false,'.');
    int fold_count=0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();

      if (line.startsWith("fold"))
      {
        String inst = Tok.en(line, " ").get(2);
        String axis = Tok.en(inst, "=").get(0);
        int axis_val = Integer.parseInt(Tok.en(inst, "=").get(1));
        if (axis.equals("y")) map = foldUp(map, axis_val);
        else map = foldLeft(map, axis_val);

        fold_count++;

        if (fold_count==1)
        {
          System.out.println("Part 1");
          System.out.println(map.getCounts());
        }


      }
      else
      {
        if (line.trim().length() > 0)
        {
          List<Integer> lst = Tok.ent(line, ",");
          Point p = new Point(lst.get(0), lst.get(1));
          map.set(p, true);
        }

      }

    }

    System.out.println("Part 2");
    System.out.println( map.getPrintOut(conv) );
    System.out.println(map.getCounts());

  }
  public Map2D<Boolean> foldLeft(Map2D<Boolean> in, int x_axis)
  {
    Map2D<Boolean> out = new Map2D<Boolean>(false);

    for(Point p : in.getAllPoints())
    {
      if (p.x < x_axis)
      {
        out.set(p, in.get(p));

      }
      else
      {
        long dist = Math.abs( p.x - x_axis);
        long n = x_axis - dist;

        out.set(n, p.y, in.get(p));
      }

    }


    return out;


  }


  public Map2D<Boolean> foldUp(Map2D<Boolean> in, int y_axis)
  {
    Map2D<Boolean> out = new Map2D<Boolean>(false);

    for(Point p : in.getAllPoints())
    {
      if (p.y < y_axis)
      {
        out.set(p, in.get(p));

      }
      else
      {
        long dist = Math.abs( p.y - y_axis);
        long n = y_axis - dist;

        out.set(p.x, n, in.get(p));
      }

    }


    return out;


  }

}
