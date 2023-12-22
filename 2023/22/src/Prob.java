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
  ArrayList<Brick> bricks=new ArrayList<>();

  Brick nbrick = new Brick(0, "0,0,0,0,0,0");
  Map3D<Brick> map = new Map3D<Brick>(nbrick);
  TreeMap<Integer, Brick> brick_map = new TreeMap<>();

  public Prob(Scanner scan)
  {
    {
      int id=1;
      for(String line : In.lines(scan))
      {
        Brick b = new Brick(id, line);

        bricks.add(b);
        brick_map.put(id, b);
        id++;

        b.repaint();

      }
    }
    System.out.println(bricks.size());

    grav();

    int p1 = 0;
    for(Brick b : bricks)
    {
      System.out.println("Brick " + b);
      TreeSet<Integer> above = b.anythingAbove();
      System.out.println("  supports: " + above);
      boolean removable=true;
      for(int id : above)
      {
        if (brick_map.get(id).getSupported().size() <= 1) removable=false;
      }
      if (removable) p1++;
    }
    System.out.println("Part 1: " + p1);

    long p2 = 0;

    for(Brick b : bricks)
    {
      p2 += countGone(b.id, new TreeSet<Integer>()).size() - 1;

    }
    System.out.println("Part 2: " + p2);


  }

  public void grav()
  {
    long total = 0;
    while(true)
    {
      int moves =0;
      for(Brick b : bricks)
      {
        if (b.gravity()) moves++;
      }
      if (moves==0) break;

      total+=moves;

    }
    System.out.println("Total grav moves: " + total);
  }

  

  public class Brick
  {
    Point e1;
    Point e2;
    final int id;

    public Brick(int id, String line)
    {
      this.id = id;
      List<Integer> lst = Tok.ent( line.replace("~",","), ",");

      e1 = new Point(lst.get(0), lst.get(1), lst.get(2));
      e2 = new Point(lst.get(3), lst.get(4), lst.get(5));

    }
    public String toString()
    {
      return "Brick " + id + " " + e1.toString() + " " + e2.toString();


    }

    public TreeSet<Integer> anythingAbove()
    {
      long min_x = Math.min(e1.x, e2.x);
      long max_x = Math.max(e1.x, e2.x);

      long min_y = Math.min(e1.y, e2.y);
      long max_y = Math.max(e1.y, e2.y);

      long min_z = Math.min(e1.z, e2.z);
      long max_z = Math.max(e1.z, e2.z);

      long next_z = max_z+1;

      TreeSet<Integer> set = new TreeSet<>();
      
      for(long x = min_x; x<=max_x; x++)
      for(long y = min_y; y<=max_y; y++)
      {
        if (map.get(x,y,next_z) != nbrick) set.add(map.get(x,y,next_z).id);
      }
      return set;
    }

    public TreeSet<Integer> getSupported()
    {
      long min_x = Math.min(e1.x, e2.x);
      long max_x = Math.max(e1.x, e2.x);

      long min_y = Math.min(e1.y, e2.y);
      long max_y = Math.max(e1.y, e2.y);

      long min_z = Math.min(e1.z, e2.z);
      long max_z = Math.max(e1.z, e2.z);

      long next_z = min_z-1;

      TreeSet<Integer> set = new TreeSet<>();
      for(long x = min_x; x<=max_x; x++)
      for(long y = min_y; y<=max_y; y++)
      {
        if (map.get(x,y,next_z) != nbrick)
        {
          set.add(map.get(x,y,next_z).id);
        }
      }
      return set;
    }

    public boolean gravity()
    {
      long min_x = Math.min(e1.x, e2.x);
      long max_x = Math.max(e1.x, e2.x);

      long min_y = Math.min(e1.y, e2.y);
      long max_y = Math.max(e1.y, e2.y);

      long min_z = Math.min(e1.z, e2.z);
      long max_z = Math.max(e1.z, e2.z);

      if (min_z <= 1) return false;

      long next_z = min_z-1;

      
      for(long x = min_x; x<=max_x; x++)
      for(long y = min_y; y<=max_y; y++)
      {
        Point check = new Point(x,y,next_z);
        //System.out.println("Checking: " + check);
        if (map.get(x,y,next_z) != nbrick) return false;
      }

      List<Point> old_points = getAllPoints();

      for(Point p : old_points)
      {
        map.set(p, nbrick);
      }


      //System.out.println(toString() +  " From " + e1 + " " + e2);
      e1 = e1.add(new Point(0,0,-1));
      e2 = e2.add(new Point(0,0,-1));
      //System.out.println("  To   " + e1 + " " + e2);

      repaint();
      return true;
    }

    public void repaint()
    {

      /*for(Point p : map.getAllPoints())
      {
        if (map.get(p) == this)
        {
          map.set(p, nbrick);
        }
      }*/

      for(Point p : getAllPoints())
      {
        map.set(p, this);

      }

    }
    public List<Point> getAllPoints()
    {
      long min_x = Math.min(e1.x, e2.x);
      long max_x = Math.max(e1.x, e2.x);

      long min_y = Math.min(e1.y, e2.y);
      long max_y = Math.max(e1.y, e2.y);

      long min_z = Math.min(e1.z, e2.z);
      long max_z = Math.max(e1.z, e2.z);

      LinkedList<Point> lst = new LinkedList<>();

      for(long x = min_x; x<=max_x; x++)
      for(long y = min_y; y<=max_y; y++)
      for(long z = min_z; z<=max_z; z++)
      {
        lst.add(new Point(x,y,z));
      }


      return lst;
    }

    @Override
    public int hashCode()
    {
      return e1.hashCode() + e2.hashCode();
    }

  }

  public TreeSet<Integer> countGone(int rm, TreeSet<Integer> already_gone)
  {
    TreeSet<Integer> fall = new TreeSet<>();

    fall.addAll(already_gone);
    fall.add(rm);
    TreeSet<Integer> above = brick_map.get(rm).anythingAbove();

    for(int a : above)
    {
      TreeSet<Integer> supports = brick_map.get(a).getSupported();
      supports.removeAll(fall);
      if (supports.size() == 0)
      {
        fall.add(a);
        fall.addAll(countGone(a, fall));
      }
    }

    return fall;


  }

}
