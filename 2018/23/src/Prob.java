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
  ArrayList<NanoBot> bot_lst=new ArrayList<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      bot_lst.add(new NanoBot(line));
    }

    {
      NanoBot large=null;
      for(NanoBot nb : bot_lst)
      {
        if ((large==null) || (nb.range > large.range)) large=nb;
      }

      int in_range=0;
      for(NanoBot nb : bot_lst)
      {
        if (inRange(large, nb)) in_range++;
      }
      System.out.println("Part 1: " + in_range);
    }
    
    long min_x=0;
    long max_x=0;
    long min_y=0;
    long max_y=0;
    long min_z=0;
    long max_z=0;
    for(NanoBot nb : bot_lst)
    {
      min_x = Math.min(nb.p.x, min_x);
      min_y = Math.min(nb.p.y, min_y);
      min_z = Math.min(nb.p.z, min_z);
      
      max_x = Math.max(nb.p.x, max_x);
      max_y = Math.max(nb.p.y, max_y);
      max_z = Math.max(nb.p.z, max_z);
    }
    System.out.println(String.format("Range: x %d %d y %d %d z %d %d", min_x, max_x, min_y, max_y, min_z, max_z));

    long scale = 10000000L;
    SS start = new SS(new Box(new Point(min_x, min_y, min_z),new Point(max_x, max_y, max_z)), scale, 0);
    SS fin = (SS)Search.search(start);
    System.out.println( getZeroDist(fin.box.p1));

    Point origin = fin.box.p1;
    Point best = origin;
    int best_cnt = 0;
    for(long x=origin.x-20; x<=origin.x+20; x++)
    for(long y=origin.y-20; y<=origin.y+20; y++)
    for(long z=origin.z-20; z<=origin.z+20; z++)
    {
      Point p = new Point(x,y,z); 
      int cnt = countInRange(p);
      if (cnt > best_cnt)
      {
        best = p;
        best_cnt = cnt;
      }
      if (cnt == best_cnt)
      {
        if (getZeroDist(best) > getZeroDist(p))
        {
            
        best = p;
        best_cnt = cnt;
        }
      }

    }
    System.out.println(best);
    System.out.println(best_cnt);
    System.out.println(getZeroDist(best));


  }


  public class NanoBot
  {
    Point p;
    long range;
    long r2;

    public NanoBot(String line)
    {
      line=line.replace("pos=<","");
      line=line.replace(">, r=",",");
      line=line.replace(",", " ");

      List<Integer> lst = Tok.ent(line, " ");
      p = new Point(lst.get(0), lst.get(1), lst.get(2));
      range= lst.get(3);
      r2=range*range;
    }

  }


  // Within the given space, mark boxes of size scale with how many of the bots
  // touch the boxes anywhere
  public void markBoxes(Map3D<Integer> map, long scale, 
    long min_x, long min_y, long min_z, long max_x, long max_y, long max_z)
  {
    for(long x = min_x; x<=max_x; x+=scale)
    for(long y = min_y; y<=max_y; y+=scale)
    for(long z = min_z; z<=max_z; z+=scale)
    {
      Box box = new Box(new Point(x,y,z), scale);
      int count = 0;
      for(NanoBot nb : bot_lst)
      {
        if (boxTouches(nb, box)) count++;

      }
      map.set(x,y,z, count);
      //System.out.println("Box: " + box + " " + count);

    }
   
  }

  public boolean boxTouches(NanoBot nb, Box box)
  {
    long dx = 0;
    long dy = 0;
    long dz = 0;

    if ((box.p1.x <= nb.p.x) && (nb.p.x <= box.p2.x))
    {
      dx =0;
    }
    else
    {
      dx = Math.min( Math.abs(box.p1.x - nb.p.x), Math.abs(box.p2.x - nb.p.x));
    }

    if ((box.p1.y <= nb.p.y) && (nb.p.y <= box.p2.y))
    {
      dy = 0;
    }
    else
    {
      dy = Math.min( Math.abs(box.p1.y - nb.p.y), Math.abs(box.p2.y - nb.p.y));
    }

    if ((box.p1.z <= nb.p.z) && (nb.p.z <= box.p2.z))
    {
      dz = 0;
    }
    else
    {
      dz = Math.min( Math.abs(box.p1.z - nb.p.z), Math.abs(box.p2.z - nb.p.z));
    }

    if (dx + dy + dz <= nb.range) return true;

    return false;


  }
  public int countInRange(Point p)
  {
    int cnt = 0;
    for(NanoBot nb : bot_lst)
    {
      if (inRange(nb, p)) cnt++;
    }
    return cnt;
  }

  public long getZeroDist(Point p)
  {
    return Math.abs(p.x) + Math.abs(p.y) + Math.abs(p.z);
  }

  public boolean inRange(NanoBot src, NanoBot dst)
  {
    return inRange(src, dst.p);
  }
  public boolean inRange(NanoBot src, Point p)
  {
    long dx = Math.abs(src.p.x - p.x);
    long dy = Math.abs(src.p.y - p.y);
    long dz = Math.abs(src.p.z - p.z);

    long dist = dx + dy + dz;
    long dist_2 = dx*dx + dy*dy + dz*dz;

    if (dist <= src.range) return true;
    return false;
  }

  public class SS extends State
  {
    Box box;
    long scale;
    int count;

    public SS(Box box, long scale, int count)
    {
      this.box = box;
      this.scale = scale;
      this.count = count;

    }

    public double getCost()
    {
      return bot_lst.size() - count + getZeroDist(box.p1) / 1e12;
    }
    public boolean isTerm()
    {
      return (scale <= 0);
    }

    public String toString()
    {
      return box + " " + scale + " " + count;
    }

    public List<State> next()
    {
      LinkedList<State> lst=new LinkedList<>();

      Map3D<Integer> map = new Map3D<Integer>(0);
      markBoxes(map, scale, box.p1.x, box.p1.y, box.p1.z, box.p2.x, box.p2.y, box.p2.z);
      for(Point p : map.getAllPoints())
      {
        int nc = map.get(p);
        lst.add(new SS(new Box(p, scale), scale/2, nc));
      }
      return lst;
    }


  }
}
