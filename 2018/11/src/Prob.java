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
  Map2D<Long> grid = new Map2D<>(0L);

  HashMap<String, Long> memo = new HashMap<>();

  //Map3D<Long> grid_sum = new Map3D<>(0L);

  long grid_serial=6548;

  public Prob(Scanner scan)
  {
    for(int i=1; i<=300; i++)
    for(int j=1; j<=300; j++)
    {
      //System.out.print(new Point(i,j));
      setCell(i,j);
    }
    System.out.println("Cells set");

    long best = 0;
    Point best_p = null;
    long split=1;

    for(int s=1; s<=300; s++)
    {
      while (s > split) split*=2;
      for(int i=1; i<=300-s+1; i++)
      for(int j=1; j<=300-s+1; j++)
      {
        long v = getSum( new Point(i,j), new Point(i+s, j+s),split/2);

        if (v > best)
        {
          best =v;
          best_p = new Point(i,j,s);
        }
      }
      System.out.println(String.format("get:%d hit:%d miss:%d", get_count, memo_hit, memo_miss));
      get_count = 0; memo_hit = 0; memo_miss = 0;
    }
    System.out.println(best);
    System.out.println(best_p);


  }

  long get_count=0;
  long memo_hit=0;
  long memo_miss=0;

  public long getSum(Point top, Point bottom, long split)
  {
    split = Math.max(split,4);
    get_count++;
    if (top.x == bottom.x) return 0L;
    if (top.y == bottom.y) return 0L;
    if ((top.x+1==bottom.x) && (top.y+1==bottom.y)) return grid.get(top.x, top.y);

    String key = top.toString() + bottom.toString();

    if (memo.containsKey(key))
    {
      memo_hit++;
      return memo.get(key);
    }
    memo_miss++;
    
    {
      long dx = bottom.x - top.x;
      long dy = bottom.y - top.y;
      //System.out.println(new Point(dx,dy,split));

    }

    //So we do a double binary

    ArrayList<Long> set_x = new ArrayList<Long>();
    ArrayList<Long> set_y = new ArrayList<Long>();
    long gapo=split;

    set_x.add(top.x);

    for(long i=top.x+1; i<bottom.x; i++)
    {
      if (i % gapo == 0) set_x.add(i);
    }
    
    //set_x.add((top.x + bottom.x) / 2);
    set_x.add(bottom.x);

    set_y.add(top.y);

    for(long i=top.y+1; i<bottom.y; i++)
    {
      if (i % gapo == 0) set_y.add(i);
    }
 
    //set_y.add((top.y + bottom.y) / 2);
    set_y.add(bottom.y);


    long sum = 0;
    if (set_x.size() + set_y.size() == 4)
    {
      for(long i=top.x; i<bottom.x; i++)
      for(long j=top.y; j<bottom.y; j++)
      {
        sum+=getSum(new Point(i,j),new Point(i+1, j+1),1);
      }

    }
    else
    {


      for(int i=0; i<set_x.size()-1; i++)
      for(int j=0; j<set_y.size()-1; j++)
      {
        sum+=getSum(
          new Point(set_x.get(i),set_y.get(j)),
          new Point(set_x.get(i+1), set_y.get(j+1)),
          split/2);
      }
    }

    memo.put(key, sum);
    return sum;


  }

  public void setCell(long x, long y)
  {
    long rack_id = x+10;
    long power = rack_id * y;
    power += grid_serial;
    power *=rack_id;
    
    power = power / 100;
    power = power % 10;
    power -= 5;

    grid.set(x,y,power);

    /*for(int s=1; s<=3; s++)
    {
      for(int i=-s+1; i<=0; i++)
      for(int j=-s+1; j<=0; j++)
      {
        grid_sum.set(x+i,y+j,s,
          grid_sum.get(x+i,y+j,s) + power);
      }
    }*/
  }

}
