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

  ArrayList<Point> pos=new ArrayList<>();
  ArrayList<Point> vel=new ArrayList<>();

  ArrayList<Point> pos_orig = new ArrayList<>();
  ArrayList<Point> vel_orig = new ArrayList<>();

  Point repeat=new Point(0,0,0);

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine().replace("<",",").replace(">",",").replace("=",",");
      List<Long> lst = Tok.enl(line, ",");
      Point p = new Point(lst.get(0), lst.get(1), lst.get(2));
      pos.add(p);
      vel.add(new Point(0L, 0L, 0L));
    }
    pos_orig.addAll(pos);
    vel_orig.addAll(vel);

    long steps = 0;
    while(true)
    {
      applyGravity();
      applyVelocity();
      steps++;
      if (steps == 1000)
        System.out.println("Part 1: " + getEnergy());
      checkRepeatX(steps);
      checkRepeatY(steps);
      checkRepeatZ(steps);

      if (repeat.x > 0)
      if (repeat.y > 0)
      if (repeat.z > 0)
      {
        break;
      }

    }
    System.out.println("Repeats: " + repeat);
    System.out.println("Part 2: " + getF(ImmutableList.of(repeat.x, repeat.y, repeat.z)));

  }

  public boolean checkRepeatX(long steps)
  {
    if (repeat.x != 0) return false;
    for(int i=0; i<pos.size(); i++)
    {
      if (pos.get(i).x != pos_orig.get(i).x) return false;
      if (vel.get(i).x != vel_orig.get(i).x) return false;
    }
    repeat = repeat.add(new Point(steps, 0,0));
    return true;
  }

  public boolean checkRepeatY(long steps)
  {
    if (repeat.y != 0) return false;
    for(int i=0; i<pos.size(); i++)
    {
      if (pos.get(i).y != pos_orig.get(i).y) return false;
      if (vel.get(i).y != vel_orig.get(i).y) return false;
    }
    repeat = repeat.add(new Point(0, steps,0));
    return true;
  }

  public boolean checkRepeatZ(long steps)
  {
    if (repeat.z != 0) return true;
    for(int i=0; i<pos.size(); i++)
    {
      if (pos.get(i).z != pos_orig.get(i).z) return false;
      if (vel.get(i).z != vel_orig.get(i).z) return false;
    }
    repeat = repeat.add(new Point(0, 0, steps));
    return true;
  }


  public long getF(Collection<Long> lst)
  {
    TreeMap<Long, Long> f_map = new TreeMap<>();
    for(long x : lst)
    {
      TreeMap<Long, Long> fact = getFactors(x);
      for(long f : fact.keySet())
      {
        if (f_map.containsKey(f))
        {
          f_map.put( f, Math.max(f_map.get(f), fact.get(f)));
        }
        else
        {
          f_map.put(f, fact.get(f));
        }

      }
    }
    long x = 1L;

    for(long f :f_map.keySet())
    {
      for(long i = 0; i<f_map.get(f); i++) x*=f;
    }


    return x;

  }

  public TreeMap<Long, Long> getFactors(long x)
  {
    long f=2;
    TreeMap<Long, Long> fact = new TreeMap<>();

    while(x >= f)
    {
      while(x % f == 0)
      {
        if (!fact.containsKey(f)) fact.put(f, 0L);
        fact.put(f, fact.get(f) + 1L);
        x = x / f;
      }
      f++;
    }
    if (x > 1) fact.put(x,1L);
    return fact;

  }



  public long getEnergy()
  {
    long total = 0;
    
    for(int i=0; i<pos.size(); i++)
    {
      long pot = getAbs(pos.get(i));
      long kin = getAbs(vel.get(i));

      total += pot * kin;
    }
    return total;

  }
  public long getAbs(Point a)
  {
    return Math.abs(a.x) + Math.abs(a.y) + Math.abs(a.z);
  }

  public void applyVelocity()
  {
    for(int i=0; i<pos.size(); i++)
    {
      pos.set(i, pos.get(i).add(vel.get(i)));
    }

  }

  public void applyGravity()
  {
    for(int i=0; i<pos.size(); i++)
    {
      Point g = new Point(0,0,0);
      for(int j=0; j<pos.size(); j++)
      {
        if (i != j)
        {
          g = g.add(getGravity(pos.get(i), pos.get(j)));
        }
      }

      vel.set(i, vel.get(i).add(g));
    }


  }

  /** get gravity on point a caused by point b */
  public Point getGravity(Point a, Point b)
  {
    long x = getGravityS(a.x,b.x);
    long y = getGravityS(a.y,b.y);
    long z = getGravityS(a.z,b.z);

    return new Point(x,y,z);

  }
  public int getGravityS(long a, long b)
  {
    if (a<b) return 1;
    if (a>b) return -1;
    return 0;

  }

}
