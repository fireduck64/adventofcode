import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> map = null;
  Map2D<Character> map_clean = null;
  public String Part1(Scanner scan)
    throws Exception
  {

    map = new Map2D<Character>('#');
    for(int i=0; i<=70; i++)
    for(int j=0; j<=70; j++)
    {
      map.set(i,j,'.');
    }
    loadMap(map, scan,1024);
    //map.print();

    SS fin = (SS) Search.search(new SS(new Point(0,0), 0));
    
		return "" + fin.cost;
  }

  public void loadMap(Map2D<Character> map, Scanner scan, int max_load)
  {
    int loaded=0;
    for(String line : In.lines(scan))
    {
      List<Integer> lst = Tok.ent(line, ",");

      Point a = new Point(lst.get(0), lst.get(1));
      map.set(a, '#');

      loaded++;
      if (max_load == loaded) return;

    }

  }

  public ArrayList<Point> loadMap( Scanner scan)
  {
    ArrayList<Point> out = new ArrayList<>();
    for(String line : In.lines(scan))
    {
      List<Integer> lst = Tok.ent(line, ",");

      Point a = new Point(lst.get(0), lst.get(1));
      out.add(a);
    }
    return out;

  }

  public String Part2(Scanner scan)
    throws Exception
  {

    map = new Map2D<Character>('#');
    for(int i=0; i<=70; i++)
    for(int j=0; j<=70; j++)
    {
      map.set(i,j,'.');
    }
    map_clean = map.copy();

    ArrayList<Point> drops = loadMap(scan);

    /*for(int i=0; i<drops.size(); i++)
    {
      Point a= drops.get(i);
      map.set(a, '#');
      SS fin = (SS) Search.search(new SS(new Point(0,0), 0));
      if (fin == null)
      {
        System.out.println("Iterative: " + a.x +"," + a.y);
        break;
      }
      
    }*/

    Point a = rec(0, drops.size()-1, drops);

		return "" + a.x + "," + a.y;
  }
  public Point rec(int low, int high, ArrayList<Point> drops)
  {
    
    int mid = (low+high)/2;
    System.out.println("" + low + " " + mid + " " + high);
    if (low == high)
    {
      return drops.get(low);
    }
    
    map = map_clean.copy();
    for(int i=0; i<=mid; i++)
    {
      map.set(drops.get(i), '#');
    }

    if (Search.search(new SS(new Point(0,0), 0))==null)
    {
      return rec(low, mid, drops);
    }
    else
    {
      return rec(mid+1, high, drops);
    }



  }

  public class SS extends State
  {
    Point loc;
    int cost;
    public SS(Point loc, int cost)
    {
      this.loc = loc;
      this.cost = cost;

    }
    public String toString(){return loc.toString(); }
    public double getCost(){return cost; }

    @Override
    public double getEstimate()
    {
      Point t = new Point(70,70);
      return t.getDistM(loc);
    }

    public boolean isTerm()
    {
      if (loc.x == 70)
      if (loc.y == 70)
      {
        return true;
      }
      return false;

    }
    public List<State> next()
    {
      List<State> lst = new LinkedList<>();

      for(Point n : map.getAdj(loc, false))
      {
        if (map.get(n) == '.')
        {
          lst.add(new SS(n, cost+1));
        }

      }
      return lst;
    }

  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
