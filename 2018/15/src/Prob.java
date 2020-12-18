import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(args[0]);
  }

  Random rnd=new Random();
  Map2D<Spot> map = new Map2D<Spot>(new Spot('#'));

  public Prob(String filename)
    throws Exception
  {
   
    while(true)
    {
      try
      {
        map = new Map2D<Spot>(new Spot('#'));
        MapLoad.loadMap(map, new Scanner(new FileInputStream(filename)));
        System.out.println(map.getPrintOut(null));

        int rnd=0;
        while(true)
        {
          rnd++;
          if (!round(rnd))
          {
            break;
          }

        }

        System.out.println("Full Rounds: " + (rnd-1));
        int hp = 0;
        for(Point p : map.getAllPoints())
        {
          hp += map.get(p).hp;
        }
        System.out.println("Final HP: " + hp);
        int val =hp * (rnd-1);
        System.out.println("Score: " + val);
        return;

      }
      catch(Throwable t)
      {
        System.out.println(t);
        Spot.elf_power++;
      }
    }


  }

  public boolean combatOver()
  {
    Map<Character, Integer> alive=new TreeMap<>();
    Map<Character, Integer> hp=new TreeMap<>();
    alive.put('E',0);
    alive.put('G',0);
    hp.put('E',0);
    hp.put('G',0);

    for(long y=map.low_y; y<=map.high_y; y++)
    for(long x=map.low_x; x<=map.high_x; x++)
    {
      Spot s = map.get(x,y);
      if (s.isBug())
      {
        alive.put(s.z, alive.get(s.z)+1);
        hp.put(s.z, hp.get(s.z)+s.hp);
      }
    }

    if (alive.get('E') == 0) return true;
    if (alive.get('G') == 0) return true;

    return false;
 
  }

  // Return true if it is a full round
  public boolean round(int rnd)
  {
    boolean combat_over=combatOver();
    boolean full_round=true;
    for(long y=map.low_y; y<=map.high_y; y++)
    for(long x=map.low_x; x<=map.high_x; x++)
    {
      Spot s = map.get(x,y);
      if (s.isBug())
      if (s.last_move < rnd)
      {
        s.last_move = rnd;
        if (combat_over) full_round=false;
        if (actionBug(new Point(x,y)))
        {
          combat_over=true;
        }
      }
    }

    Map<Character, Integer> alive=new TreeMap<>();
    Map<Character, Integer> hp=new TreeMap<>();
    alive.put('E',0);
    alive.put('G',0);
    hp.put('E',0);
    hp.put('G',0);


    for(long y=map.low_y; y<=map.high_y; y++)
    for(long x=map.low_x; x<=map.high_x; x++)
    {
      Spot s = map.get(x,y);
      if (s.isBug())
      {
        alive.put(s.z, alive.get(s.z)+1);
        hp.put(s.z, hp.get(s.z)+s.hp);
      }
    }
 
    //System.out.println(map.getPrintOut(null));
    System.out.println("rnd:" + rnd + " " + alive + " " + hp);
    System.out.println("Full: " + full_round);
    System.out.println("Val: " + rnd * (Math.max(hp.get('E'), hp.get('G'))));
    System.out.println("---------------------------------------------------------------");
    //if (alive.get('E') == 0) return false;
    //if (alive.get('G') == 0) return false;
    return full_round;

    //return !combat_over;


  }

  //Return true if combat is over
  public boolean actionBug(Point p)
  {
    Spot spot = map.get(p);
    char target='-';
    if (spot.z=='E') target='G';
    if (spot.z=='G') target='E';

    // Move
    
    MoveState term = (MoveState) Search.search(new MoveState(target, 0.0, p, new LinkedList<Point>()));
    if (term != null)
    if (term.path.size() > 0)
    {
      //System.out.println("Path: " + term.path);
      Point nx = term.path.get(0);
      map.set(nx, spot);
      map.set(p, new Spot('.'));
      p = nx;

    }

    // Attack
   
    Point t = getAttackTarget(p, target);
    if (t != null)
    {
      Spot ts = map.get(t);
      ts.hp -= spot.att;
      
      if (ts.hp <= 0)
      {
        // part 2
        if (target=='E') throw new RuntimeException("death elf");
        map.set(t, new Spot('.'));

      }
    }
    return combatOver();

  }

  public Point getAttackTarget(Point p, char target)
  {
    TreeMap<Double, Point> possible_targets = new TreeMap<>();

    for(int j=-1; j<=1; j++)
    for(int i=-1; i<=1; i++)
    if (Math.abs(i) + Math.abs(j) == 1)
    {
      Point n = new Point(p.x +i, p.y+j);
      Spot s = map.get(n);
      if (s.z == target)
      {
        double v = s.hp + n.y / 1e3 + n.x / 1e6;
        possible_targets.put(v, n);
      }

    }
    for(Point n : possible_targets.values())
    {
      return n;
    }
    return null;

  }


	public class MoveState extends State
	{
		final char target;
		final double cost;
		final Point loc;

    final List<Point> path;


		public MoveState(char target, double cost, Point loc, List<Point> path)
		{
			this.target = target;
			this.cost = cost;
			this.loc = loc;
      this.path = path;
		}

    @Override
		public double getCost() 
    {
      double cost_plus = 0.0;
      if (isTerm())
      {
        double y=loc.y;
        double x=loc.x;
        cost_plus = y / 1e2 + x / 1e4; 
      }

      return cost + cost_plus; 
    }

    @Override
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      Spot s = map.get(loc);
      if ((s.isWalkable()) || (path.size() == 0))
      {
        for(int i=-1; i<=1; i++)
        for(int j=-1; j<=1; j++)
        {
          if (Math.abs(i) + Math.abs(j) == 1)
          {
            Point n = new Point(loc.x+i, loc.y+j);
            if (map.get(n).isWalkable())
            {
              double cost_plus = 0.0;
              if (path.size() == 0)
              {
                double x = loc.x+i;
                double y = loc.y+j;
                cost_plus = y / 1e6 + x / 1e8;
              }
              LinkedList<Point> l2 = new LinkedList<>();
              l2.addAll(path);
              l2.add(n);
              lst.add( new MoveState(target, cost+1.0+cost_plus, n, l2));
            }
          }
        }
      }

      return lst;
    }

    @Override
    public boolean isTerm()
    {
      for(int i=-1; i<=1; i++)
      for(int j=-1; j<=1; j++)
      if (Math.abs(i) + Math.abs(j) == 1)
      {
        if (map.get(loc.x +i, loc.y +j).z ==target) return true;
      }
      return false;
    }

    @Override
    public String toString()
    {
      return loc.toString();
    }

	}

}
