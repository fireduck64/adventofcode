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

  Map2D<Integer> pit = new Map2D<Integer>(0);
  Map2D<Integer> pit_copy;

  public Prob(Scanner scan)
  {
    Point loc = new Point(0,0);

    pit.set(loc, 1);
    
    while(scan.hasNext())
    {
      String dir = scan.next();
      int dist = scan.nextInt();
      String hex = scan.next();

      p2hex(hex.replace("(","").replace("#","").replace(")",""));

      Point dir_p = Nav.getDir(dir.charAt(0));

      for(int i=0; i<dist; i++)
      {
        pit.set(loc.add(dir_p.mult(i+1)), 1);
      }
      loc = loc.add(dir_p.mult(dist));

    }

    pit_copy = pit.copy();

    pit.print();

    for(long i=pit_copy.low_x; i<=pit_copy.high_x; i++)
    for(long j=pit_copy.low_y; j<=pit_copy.high_y; j++)
    {
      if (pit.get(i,j) == 0) pit.set(i,j,0);

    }

    Search.search(new F( new Point(pit.low_x-1, pit.low_y -1)));

    pit.print();

    long p1 = pit.getCounts().get(0) + pit.getCounts().get(1);
    System.out.println(p1);

    p2calc();

  }

  public class F extends Flood
  {
    final Point loc;
    public F(Point loc)
    {
      this.loc = loc;

    }

    public String toString(){return loc.toString();}

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      if (loc.x < pit_copy.low_x - 2) return lst;
      if (loc.y < pit_copy.low_y - 2) return lst;
      if (loc.x > pit_copy.high_x + 2) return lst;
      if (loc.y > pit_copy.high_y + 2) return lst;

      if (pit.get(loc) == 0)
      {
        pit.set(loc, 8);
        for(Point p : pit.getAdj(loc, false))
        {
          lst.add(new F(p));
        }
      }

      return lst;
    }

  }


  public class F2 extends Flood
  {
    final Point loc;
    public F2(Point loc)
    {
      this.loc = loc;
    }

    public String toString(){return loc.toString();}

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      if (p2_states.get(loc) == 0)
      {
        p2_states.set(loc, 1);
        for(Point p : p2_states.getAdj(loc, false))
        {
          lst.add(new F2(p));
        }
      }

      return lst;
    }

  }

  TreeSet<Long> x_interest=new TreeSet<>();
  TreeSet<Long> y_interest=new TreeSet<>();
  LinkedList<PathEle> path=new LinkedList<>();

  public class PathEle
  {
    Point start;
    Point end;
    Point c1;
    Point c2;

    public PathEle(Point s, Point e)
    {
      start = s;
      end = e;

      c1 = new Point(Math.min(s.x, e.x), Math.min(s.y, e.y));
      c2 = new Point(Math.max(s.x, e.x), Math.max(s.y, e.y));

      addI(c1);
      addI(c2);

    }

    private void addI(Point p)
    {
      x_interest.add(p.x-1);
      x_interest.add(p.x);
      x_interest.add(p.x+1);

      y_interest.add(p.y-1);
      y_interest.add(p.y);
      y_interest.add(p.y+1);


    }
    public boolean overlap(Point o1, Point o2)
    {
      if (c1.x <= o1.x)
      if (c1.y <= o1.y)
      if (o2.x <= c2.x)
      if (o2.y <= c2.y)
      {
        return true;
      }
      return false;


    }

    public String toString()
    {
      return "" + start + "" + end;
    }

  }

  Point p2_loc = new Point(0,0);

  long path_dist = 0;

  public void p2hex(String hex)
  {
    long dist = Long.parseLong(hex, 16);
    long dir_n = dist % 16;
    Point dir = null;
    if (dir_n == 0) dir=Nav.E;
    if (dir_n == 1) dir=Nav.S;
    if (dir_n == 2) dir=Nav.W;
    if (dir_n == 3) dir=Nav.N;

    dist = dist/16L;
    p2distdir(dist, dir);
  }

  public void p2distdir(long dist, Point dir)
  {
    Point end = p2_loc.add( dir.mult(dist) );
    PathEle pe = new PathEle( p2_loc, end);

    path.add(pe);
    p2_loc = end;
    path_dist += dist - 1;
  }

  Map2D<Long> areas=new Map2D<Long>(0L);
  // 0 - undefined
  // 1 - outside
  // 2 - inside
  Map2D<Integer> p2_states = new Map2D<Integer>(-1);

  public void p2calc()
  {
    System.out.println("P2 end point: " + p2_loc);
    System.out.println("p2 total dist: " + path_dist);

    x_interest.add( x_interest.first() - 1000L);
    y_interest.add( y_interest.first() - 1000L);


    x_interest.add( x_interest.last() + 1000L);
    y_interest.add( y_interest.last() + 1000L);

    TreeSet<Long> area_set = new TreeSet<>();

    long p2_area = 0;
    //long p2_area = path_dist;
    System.out.println(x_interest.size());
    System.out.println(y_interest.size());

    ArrayList<Long> lst_x = new ArrayList<>(); 
    ArrayList<Long> lst_y = new ArrayList<>();

    lst_x.addAll(x_interest);
    lst_y.addAll(y_interest);



    for(int idx_x = 0; idx_x < lst_x.size()-1; idx_x++)
    for(int idx_y = 0; idx_y < lst_y.size()-1; idx_y++)
    {
      Point c1 = new Point( lst_x.get(idx_x), lst_y.get(idx_y));
      Point c2 = new Point( lst_x.get(idx_x+1) - 1, lst_y.get(idx_y+1) - 1);

      long area = (c2.x - c1.x + 1) * (c2.y - c1.y + 1);
      areas.set(idx_x, idx_y, area);
      //System.out.println("Area: " + area + " " + c1 + " " + c2);
      p2_states.set(idx_x, idx_y, 0);
      for(PathEle pe : path)
      {
        if (pe.overlap(c1,c2))
        {
          p2_states.set(idx_x, idx_y, 2);
        }
      }
    }

    Search.search(new F2(new Point(0,0)));

    p2_states.print();

    for(Point p : p2_states.getAllPoints())
    {
      if (p2_states.get(p)==2)
      {
        p2_area += areas.get(p);
      }
      if (p2_states.get(p)==0)
      {
        p2_area += areas.get(p);
      }

    }


    System.out.println("p2 area: " + p2_area);



  }

}
