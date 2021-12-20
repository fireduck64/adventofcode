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
  ArrayList<Part> parts= new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      Part p = new Part(line);
      parts.add(p);
    }

    for(int j=0; j<100; j++)
    {

      HashMap<Point, Integer> locs = new HashMap<>(2048, 0.5f);
      for(int i=0; i<100; i++)
      {
        locs.clear();
        for(Part p : parts)
        {
          p.tick();
          p.addLoc(locs);
        }
        for(Part p : parts)
        {
          p.smash(locs);
        }

      }
      
      Point zero = new Point(0,0,0);
      int best_idx=-1;
      long best_dist=1000000000L * 1000L;
      for(int i=0; i<parts.size(); i++)
      {
        Part p = parts.get(i);
        long d = p.p.getDistM(zero);

        
        if ((best_idx < 0) || (d < best_dist))
        {
          best_idx=i;
          best_dist=d;
        }

      }
      System.out.println("Part 1: " + best_idx + " " + best_dist);
      int left = 0;
      for(Part p : parts)
      {
        if (!p.broken) left++;

      }
      System.out.println("Remaining: " + left);
    }


  }

  public class Part
  {
    Point p;
    Point v;
    Point a;

    boolean broken=false;

    public Part(String line)
    {
      line = line.replace("<","");
      line = line.replace(">","");
      line = line.replace(" ","");
      line = line.replace("=","");
      line = line.replace("a","");
      line = line.replace("v","");
      line = line.replace("p","");
      List<Integer> lst = Tok.ent(line, ",");
      p = new Point(lst.get(0), lst.get(1), lst.get(2));
      v = new Point(lst.get(3), lst.get(4), lst.get(5));
      a = new Point(lst.get(6), lst.get(7), lst.get(8));
    }
    public String toString()
    {
      return String.format("P{p=%s v=%s a=%s}", p,v,a);
    }
    public void tick()
    {
      v = v.add(a);
      p = p.add(v);
    }
    public void addLoc(Map<Point, Integer> locs)
    {
      if (!broken)
      {
        if (!locs.containsKey(p))
        {
          locs.put(p, 1);
        }
        else
        {
          locs.put(p, 8);
        }


      }

    }
    public void smash(Map<Point, Integer> locs)
    {
      if (!broken)
      {
        if (locs.get(p) > 1) broken=true;
      }

    }

  }

}
