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
  long magic;
  Point target = new Point(31, 39);

  public Prob(Scanner scan)
    throws Exception
  {
    magic = scan.nextLong();

    SS fin = (SS)Search.search(new SS(0, new Point(1,1)));
    System.out.println("Part 1:");
    System.out.println(fin.getCost());
    System.out.println(fin);

    target=new Point(50,50);

    Collection<Double> v_cost = 
    Search.searchPara(new SS(0, new Point(1,1))).visited.values();
    int p2=0;
    for(double v : v_cost)
    {
      if (v<=50.00001) p2++;

    }
    System.out.println("P2: " + p2);
  }

  public class SS extends State
  {
    int cost;
    Point loc;

    public SS(int cost, Point loc)
    {
      this.cost = cost;
      this.loc = loc;
    }
    public String toString() { return loc.toString(); }
    public boolean isTerm()
    {
      if (loc.equals(target)) return true;
      return false;
    }
    public double getCost(){return cost;}

    @Override
    public double getEstimate()
    {
      return loc.getDistM(target);
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      for(Point q : Map2D.getAdj(loc, false))
      {
        if (isValid(q))
        {
          lst.add(new SS(cost+1, q));
        }
      }
      return lst;
    }
   

  }
  public boolean isValid(Point p)
  {
    if (p.x < 0) return false;
    if (p.y < 0) return false;
    return isSpace(p);

  }

  public boolean isSpace(Point p)
  {
    return !isWall(p);
  }
  public boolean isWall(Point p)
  {
    long x= p.x;
    long y= p.y;
    long v = x*x + 3*x + 2*x*y + y + y*y;
    v+=magic;

    String s=Long.toString(2);
    long []ll = new long[1];
    ll[0] = v;
    BitSet bs = BitSet.valueOf(ll);
    if (bs.cardinality() % 2 == 1) return true;

    return false;

  }

}
