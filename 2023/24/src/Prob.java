import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.StringSymbol;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Expr;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  PointD test_c1 = new PointD(200000000000000.0,200000000000000.0,-1000000000L);
  PointD test_c2 = new PointD(400000000000000.0,400000000000000.0,1000000000L);
  //PointD test_c1 = new PointD(7,7,-1000000000L);
  //PointD test_c2 = new PointD(27,27,1000000000L);

  ArrayList<Stone> stones = new ArrayList<>();

  public Prob(Scanner scan)
  {
    System.out.println("Version 7");

    for(String line : In.lines(scan))
    {
      Stone s = new Stone(line);
      stones.add(s);
    }
    int p1 = 0;
    for(int i=0; i<stones.size(); i++)
    for(int j=i+1; j<stones.size(); j++)
    {
      Stone a= stones.get(i);
      Stone b = stones.get(j);
      //System.out.println(a);
      //System.out.println(b);

      PointD inter = getIntersectXY( a, b);
      if (inter != null)
      //System.out.println("Cross at: " + inter);

      if (inter!=null)
      if (testInX(inter))
      if (testInY(inter))
      {
        //System.out.println("  inside");
        p1++;

      }
      //System.out.println();
      //System.out.println(getClosest(b, a, 0, 100000000000L));
    }
    System.out.println("Part 1: " + p1);

    {

      Stone toss = new Stone(
        new PointD(24,13,10), new PointD(-3,1,2));
      int hit = checkCollisions(toss);
      System.out.println("" + toss + " " + hit);
    }
    { // use solver

      Context ctx = new Context();


      Solver s = ctx.mkSolver();

      Expr px = ctx.mkRealConst(ctx.mkSymbol("px"));
      Expr py = ctx.mkRealConst(ctx.mkSymbol("py"));
      Expr pz = ctx.mkRealConst(ctx.mkSymbol("pz"));
      Expr vx = ctx.mkRealConst(ctx.mkSymbol("vx"));
      Expr vy = ctx.mkRealConst(ctx.mkSymbol("vy"));
      Expr vz = ctx.mkRealConst(ctx.mkSymbol("vz"));

      int stone_count = 5;

      LinkedList<Integer> index_lst = new LinkedList<>();
      for(int i=0; i<stones.size(); i++) index_lst.add(i);

      Collections.shuffle(index_lst);
      while(index_lst.size() > stone_count) index_lst.pollLast();

      System.out.println(index_lst);
      for(int stone_idx : index_lst)
      {
        Stone a = stones.get(stone_idx);
        Expr t = ctx.mkRealConst(ctx.mkSymbol("t_" + stone_idx));

        s.add( ctx.mkEq( 
          ctx.mkAdd(px, ctx.mkMul(t, vx)),
          ctx.mkAdd(ctx.mkInt((long)a.pos.x), ctx.mkMul(t, ctx.mkInt((long)a.v.x)))
          ));

        s.add( ctx.mkEq( 
          ctx.mkAdd(py, ctx.mkMul(t, vy)),
          ctx.mkAdd(ctx.mkInt((long)a.pos.y), ctx.mkMul(t, ctx.mkInt((long)a.v.y)))
          ));

        s.add( ctx.mkEq( 
          ctx.mkAdd(pz, ctx.mkMul(t, vz)),
          ctx.mkAdd(ctx.mkInt((long)a.pos.z), ctx.mkMul(t, ctx.mkInt((long)a.v.z)))
          ));
      }

      System.out.println("Invoking Z3 solver...");
      System.out.println("Check: " + s.check());
      //System.out.println("Model: " + s.getModel());

      long p2_solver = 
        ClownMath.z3get(s, px) +
        ClownMath.z3get(s, py) +
        ClownMath.z3get(s, pz);

      System.out.println("P2 solver: " + p2_solver);
    }
   
    for(int x=-range; x<=range; x++)
		{
      new RangeCheck(x).start();

			/*System.out.println("Range: " + x);
			for(int y=-range; y<=range; y++)
			for(int z=-range; z<=range; z++)
			{
				PointD v = new PointD(x,y,z);
				PointD pos = pickStart(v, stones.get(0), stones.get(1));
				if (pos != null)
				{
					Stone toss = new Stone(pos, v);
					int hit = checkCollisions(toss);
					if (hit > 0)
					{
						System.out.println("Hit: " + hit + " " + toss);
					}
					if (hit == stones.size())
					{
						System.out.println("Hit all: " + toss);
						long p2 = (long)Math.round(toss.pos.x + toss.pos.y + toss.pos.z);
						System.out.println("Part 2: " + p2);
						return;

					}
				}

			}*/
		}

  }


 int range=250;

	public class RangeCheck extends Thread
  {
    long x;
    public RangeCheck(int x)
    {
      this.x = x;

    }
    @Override
    public void run()
    {
      Random rnd = new Random();
		  for(int y=-range; y<=range; y++)
			for(int z=-range; z<=range; z++)
			{
				PointD v = new PointD(x,y,z);
				PointD pos = pickStart(v, 
          stones.get(rnd.nextInt(stones.size())), 
          stones.get(rnd.nextInt(stones.size())));
				if (pos != null)
				{
					Stone toss = new Stone(pos, v);
					int hit = checkCollisions(toss);
					if (hit > 0)
					{
						System.out.println("Hit: " + hit + " " + toss);
					}
					if (hit == stones.size())
					{
						System.out.println("Hit all: " + toss);
						long p2 = (long)Math.round(toss.pos.x + toss.pos.y + toss.pos.z);
						System.out.println("Part 2: " + p2);
						return;

					}
				}

			}
      //System.out.println("Range " + x + " complete");

    }

  }
  

  public PointD pickStart(PointD v, Stone h1, Stone h2)
  {  // With help from /u/Smooth-Aide-1751 
     // https://www.reddit.com/r/adventofcode/comments/18pnycy/comment/ker8l05/?utm_source=share&utm_medium=web2x&context=3
    if (v.x == 0) return null;
    if (v.y == 0) return null;
    if (v.z == 0) return null;

    double A = h1.pos.x, a = h1.v.x - v.x;
   	double B = h1.pos.y, b = h1.v.y - v.y;
   	double C = h2.pos.x, c = h2.v.x - v.x;
    double D = h2.pos.y, d = h2.v.y - v.y;

    if (c == 0.0 || (a * d) - (b * c) == 0.0)
    {
      return null;
    }
    double t = (d * (C - A) - c * (D - B)) / ((a * d) - (b * c));

    double x = h1.pos.x + h1.v.x * t - v.x * t;
    double y = h1.pos.y + h1.v.y * t - v.y * t;
    double z = h1.pos.z + h1.v.z * t - v.z * t;

		return new PointD(Math.round(x),Math.round(y),Math.round(z));

  }



  public class Stone
  {
    final PointD pos;
    final PointD v;

    public Stone(PointD pos, PointD v)
    {
      this.pos = pos;
      this.v = v;

    }
    public Stone(String line)
    {
      List<Long> lst = Tok.enl(line.replace(",",""), " ");
      if (lst.size() != 6) E.er();

      pos = new PointD(lst.get(0), lst.get(1), lst.get(2));
      v = new PointD(lst.get(3), lst.get(4), lst.get(5));

    }

    public String toString()
    {
      return ("Stone{" + pos + " @ " + v + "}");
    }

    public PointD getIntersectX(double x_plane, boolean allow_neg_time)
    {
      double dist = x_plane - pos.x;
      double time = dist / v.x;
      if (!allow_neg_time) if (time < 0.0) return null;
      PointD a = pos.add(v.mult(time));
      //System.out.println("x " + x_plane + " " + a);
      return a;
    }
    public PointD getIntersectY(double y_plane, boolean allow_neg_time)
    {
      double dist = y_plane - pos.y;
      double time = dist / v.y;
      if (!allow_neg_time) if (time < 0.0) return null;
      //if (time < 0.0) return null;
      PointD a = pos.add(v.mult(time));
      //System.out.println("y " + y_plane + " " + a);
      return a;
    }
    public PointD getIntersectZ(double z_plane, boolean allow_neg_time)
    {
      double dist = z_plane - pos.z;
      double time = dist / v.z;
      if (!allow_neg_time) if (time < 0.0) return null;
      //if (time < 0.0) return null;
      PointD a = pos.add(v.mult(time));
      //System.out.println("z " + z_plane + " " + a);
      return a;
    }

    public PointD getLocAtTime(long t)
    {
      return pos.add(v.mult(t));
    }

  }

  public boolean testInX(PointD p)
  {
    if (test_c1.x <= p.x)
    if (p.x <= test_c2.x)
    {
      return true;
    }
    return false;

  }
  public boolean testInY(PointD p)
  {
    if (test_c1.y <= p.y)
    if (p.y <= test_c2.y)
    {
      return true;
    }
    return false;

  }

  public PointD getIntersectXY(Stone a, Stone b)
  {
    double slope_a = getSlope(a);
    double slope_b = getSlope(b);
    double c_a = getC(a);
    double c_b = getC(b);

    //double inter_x = (slope_a - slope_b) / (c_b - c_a);
    double inter_x = (c_b - c_a) / (slope_a - slope_b);

    PointD pa = a.getIntersectX(inter_x, false);
    PointD pb = b.getIntersectX(inter_x, false);

    if (pa == null) return null;
    if (pb == null) return null;

    double ya = slope_a * inter_x + c_a;
    double yb = slope_b * inter_x + c_b;

    //System.out.println("Interesect at " + pa + " " + pb);
    //System.out.println("ya: " + ya);
    //System.out.println("yb: " + yb);
    return pa;
  }

  public double getSlope(Stone a)
  {
    double slope = a.v.y / a.v.x;
    return slope;
  }
  public double getC(Stone a)
  {
    // constant at x = 0;

    return a.getIntersectX(0.0, true).y;
  }


  public double getClosest(Stone a, Stone b, long t1, long t2)
  {
    // Parallel tracks
    if (a.v.equals(b.v))
    {
      return a.pos.getDist(b.pos);
    }

    // Start at same pos
    if (a.pos.equals(b.pos))
    {
      return 0.0;
    }

    // rounds down
    long t_mid = (t2 + t1) / 2;
    double d_mid = getDistAtT(a,b,t_mid);

    if ((t2 - t1) <= 1L) return d_mid;

    double d_low = getDistAtT(a,b, t_mid-1L);
    double d_high = getDistAtT(a,b,t_mid+1L);

    // if we are closer low, go low
    if (d_low < d_high)
    {
      return getClosest(a,b,t1, t_mid);

    }
    // if we are closer high, go high
    if (d_low > d_high)
    { 
      return getClosest(a,b, t_mid, t2);
    }

    return d_mid;

  }
  public double getDistAtT(Stone a, Stone b, long t)
  {
    return a.getLocAtTime(t).getDist(b.getLocAtTime(t));
  }

  public int checkCollisions(Stone toss)
  {
    int matches = 0;
    for(Stone a : stones)
    {
      double d = getClosest(toss,a,0L, 100000000000000L );
      if (d <= 0.25) matches++;
      else return -1;
    }
    return matches;


  }


}
