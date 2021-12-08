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

  HashMap<Point, Long> map_cache=new HashMap<>(20480, 0.5f);
  final int depth;
  final Point target;

  public Prob(Scanner scan)
  {
    scan.next();
    depth=scan.nextInt();
    scan.next();
    List<Integer> t_lst = Tok.ent(scan.next(), ",");
    target = new Point(t_lst.get(0), t_lst.get(1));

    // To avoid a recusrive dynamic map generation
    // just extend the map from the start
    // A truly pathological input might still stray from this
    int map_extend=3;
    int max_x = Math.max((int)target.x * map_extend, 1000);
    int max_y = Math.max((int)target.y * map_extend, 1000);
    System.out.println("Extending map to " + max_x + " " + max_y);


    map_cache.put(new Point(0,0), 0L);
    map_cache.put(target, 0L);
    for(int x=0; x<=max_x; x++)
    {
      map_cache.put(new Point(x,0), x*16807L);
    }
    for(int y=0; y<=max_y; y++)
    {
      map_cache.put(new Point(0,y), y*48271L);
    }
    for(int x=1; x<=max_x; x++)
    for(int y=1; y<=max_y; y++)
    {
      if ((x!=target.x) || (y!=target.y))
      {
        long v1 = map_cache.get(new Point(x-1, y));
        long v2 = map_cache.get(new Point(x, y-1));
        long geo = getErr(v1) * getErr(v2);
        map_cache.put(new Point(x,y), geo);

      }
    }
    map_cache.put(new Point(0,0), 0L);

    System.out.println("Part 1: " + getRisk(0,0,target.x, target.y));

    State sol = Search.search(new SS(new Point(0,0), 1, 0));
    System.out.println("Part 2: " + sol.getCost());


  }
  public long getRisk(int x1, int y1, long x2, long y2)
  {
    long risk = 0;
    for(int x=x1; x<=x2; x++)
    for(int y=y1; y<=y2; y++)
    {
      risk += getType( map_cache.get(new Point(x,y)) );
    }
    return risk;

  }
  public long getErr(long geo)
  {
    return (geo + depth) % 20183;
  }

  public int getType(long geo)
  {
    int err = (int) (getErr(geo) % 3);
    return err;
    
  }


	public class SS extends State
	{
		Point p;

		// 0 - nothing
		// 1 - torch
		// 2 - ladder
		int equiped;

		int cost;

		public SS(Point p, int equiped, int cost)
		{
      this.p = p;
      this.equiped=equiped;
      this.cost = cost;

		}

    @Override
    public boolean isTerm()
    {
      return ((p.equals(target)) && (equiped==1));
    }

    @Override
    public double getEstimate()
    {
      //return 0.0;
      return Math.abs(p.x - target.x) + Math.abs(p.y - target.y);
    }

    @Override
    public String toString()
    {
      return p.toString() + "-" + equiped;
    }

    @Override
    public double getCost()
    {
      return cost;
    }

    @Override
    public List<State> next()
    {
      List<State> lst = new LinkedList<State>();
      for(int i=0; i<=2; i++)
      {
        // We can always twiddle equipment
        if (i != equiped)
        {
          if (mayEnter(p, i))
          {
            lst.add(new SS(p, i, cost+7));
          }
        }
      }
      for(int dx=-1; dx<=1; dx++)
      for(int dy=-1; dy<=1; dy++)
      {
        Point n = new Point(p.x + dx, p.y + dy);
        if (Math.abs(dx) + Math.abs(dy) == 1)
        if (p.x + dx >= 0)
        if (p.y + dy >= 0)
        if (mayEnter(n, equiped))
        {
          lst.add(new SS(n, equiped, cost+1));
        }
      }
      //System.out.println("From: " + this.toString() + " - " + lst);

      return lst;

    }
	}

  public boolean mayEnter(Point p, int equiped)
  {
    if (!map_cache.containsKey(p))
    {
      System.out.println("Out of bounds: " + p); 
    }
    int type = getType( map_cache.get(p) );
    if (type == 0)
    {
      if (equiped != 0) return true;
      return false;
    }
    if (type == 1)
    {
      if (equiped != 1) return true;
      return false;
    }
    if (type == 2)
    {
      if (equiped != 2) return true;
      return false;
    }

    E.er();
    return false;

  }

}
