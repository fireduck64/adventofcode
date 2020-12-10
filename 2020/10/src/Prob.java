import java.io.FileInputStream;
import java.util.*;


/**
 * Part 1 is stupid.
 * Part 2 is basicially counting options.  
 * Keys - the order of using things is always the same, strictly assending.
 *        So that allows you to to drop things you aren't using from the remaining set
 *        and the memoization works.
 */
public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  TreeSet<Integer> adapt=new TreeSet<>();
  int target;

  TreeMap<String, Long> memo=new TreeMap<>();
  
  public Prob(Scanner scan)
  {
    while(scan.hasNext())
    {
      adapt.add(scan.nextInt());
    }

    target = adapt.last() + 3;

    System.out.println("Part 1");
    System.out.println(
      findPath(0, adapt)
    );


    System.out.println("Part 2:");
    System.out.println( "\n" + 
      findPath2(0, adapt)
    );

  }

  /**
   * This crap doesn't help at all.  Doesn't remove a single case. Ha.
   */
  public boolean possible(int input, TreeSet<Integer> remaining)
  {
    int v=input;
    for(int x : remaining)
    {
      if (x > v)
      {
        int d = x - v;
        if (d > 3) return false;
        v = x;
      }
    }

    if (v + 3 < target) return false;
    return true;
  }

  public long findPath2(int input, TreeSet<Integer> remaining)
  {
    if (!possible(input, remaining))
    {
      System.out.print(',');
      return 0L;
    }
    String key = "" + input + ":" + remaining;
    if (memo.containsKey(key))
    {
      System.out.print(".");
      return memo.get(key);
    }

    //System.out.println("in: " + input + " " + remaining);
    long count = 0;
    if ((input +1 <= target) && (target <= input+3))
    {
      count++;
    }

    TreeSet<Integer> copy = new TreeSet<>();
    copy.addAll(remaining);
    for(int a : remaining)
    {
      // We remove as we go for some reason
      // I don't even know
      copy.remove(a);
      if ((input + 1 <= a) && (a <= input + 3))
      {
        count += findPath2(a, copy);
      }
    }
    memo.put(key, count);
    return count;
  }


  public Point findPath(int input, TreeSet<Integer> remaining)
  {
    
    if (remaining.size() == 0)
    {
      if (input + 3 == target) return new Point(0,1);
    }

    for(int a : remaining)
    {
      if ((input + 1 <= a) && (a <= input + 3))
      {
        TreeSet<Integer> copy = new TreeSet<>();
        copy.addAll(remaining);
        copy.remove(a);

        Point z = findPath(a, copy);
        if (z != null)
        {
          int delta = a - input;
          int d1 =0;
          int d3 =0;
          if (delta == 3) d3++;
          if (delta == 1) d1++;

          return new Point(z.x + d1, z.y + d3);

        }


      }


    }

    return null;


  }

}
