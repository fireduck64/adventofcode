import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  ArrayList<Point> spots=new ArrayList<>();
  Map2D<Integer> map = new Map2D<>(-1);

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line =scan.nextLine();
      line = line.replace(",","");

      Scanner s2 = new Scanner(line);
      int x = s2.nextInt();
      int y = s2.nextInt();

      spots.add(new Point(x,y));

      map.set(x,y,-1);

    }

    long low_x = map.low_x - 20;
    long low_y = map.low_y - 20;
    long high_x = map.high_x + 20;
    long high_y = map.high_y + 20;

    TreeSet<Integer> exclude_list = new TreeSet<>();

    TreeMap<Integer, Integer> count_map = new TreeMap<>();

    int close_count = 0;

    for(long x = low_x; x<= high_x; x++)
    for(long y = low_y; y<= high_y; y++)
    {
      int n = findClosest(x,y);
      map.set(x,y,n);

      if ((x == low_x) || (x == high_x) || (y == low_y) || (y == high_y))
      {
        exclude_list.add(n);
      }
      if (!count_map.containsKey(n)) count_map.put(n, 0);
      count_map.put(n, count_map.get(n) + 1);

      if (findRangeSum(x,y) < 10000L)
      {
        close_count++;
      }
    }

    for(int n : exclude_list)
    {
      count_map.remove(n);

    }
    count_map.remove(-2);

    System.out.println("Part 1");
    System.out.println(count_map.lastEntry().getValue());

    System.out.println("Part 2");
    System.out.println(close_count);

  }


  public int findClosest(long x, long y)
  {

    // Distance to count
    TreeMap<Long, TreeSet<Integer> > m = new TreeMap<>();

    for(int s=0; s<spots.size(); s++)
    {
      long d = Math.abs(spots.get(s).x - x) + Math.abs(spots.get(s).y - y);

      if (!m.containsKey(d)) m.put(d, new TreeSet<>());
      m.get(d).add(s);

    }

    TreeSet<Integer> set = m.firstEntry().getValue();
    if (set.size() > 1) return -2;
    return set.first();

  }
  public long findRangeSum(long x, long y)
  {

    long sum = 0;
    for(int s=0; s<spots.size(); s++)
    {
      long d = Math.abs(spots.get(s).x - x) + Math.abs(spots.get(s).y - y);

      sum+=d;

    }
    return sum;
  }

}
