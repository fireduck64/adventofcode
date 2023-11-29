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
  TreeSet<Long> bus_ids=new TreeSet<>();
  TreeMap<Integer, Long> bus_idx=new TreeMap<>();

  public Prob(Scanner scan)
  {
    long start_time = scan.nextLong();
    String line=scan.next();
    Scanner s2 = new Scanner(line.replace(",", " "));


    int idx=0;
    while(s2.hasNext())
    {
      String s = s2.next();
      if (s.equals("x"))
      {

      }
      else
      {
        long x = Long.parseLong(s);
        bus_ids.add(x);
        bus_idx.put(idx, x);
      }
      idx++;
    }
    System.out.println(bus_ids);
    System.out.println(bus_idx);

    long best_id = 0;
    long best_time=0L;

    for(long b : bus_ids)
    {
      long miss_by = start_time % b;
      long next = (start_time - miss_by) + b;
      System.out.println("b: " + b + " next: " + next);
      if ((best_id == 0) || (next < best_time))
      {
        best_id = b;
        best_time = next;

      }

    }
    long wait_p1 = best_time - start_time;
    long p1 = wait_p1 * best_id;
    System.out.println("Part 1: " + p1);

    bl.addAll(bus_idx.entrySet());
    part2();

  }

  ArrayList<Map.Entry<Integer, Long> > bl = new ArrayList<>();

  public void part2()
  {

    long t = 0;
    long step = 1;
    for(int bl_idx = 0; bl_idx < bl.size(); bl_idx++)
    {
      List<Map.Entry<Integer, Long>> sub = bl.subList(0, bl_idx+1);

      long next = findNext(sub, t, step);

      long n2 = findNext(sub, next, step);

      step = n2 - next;
      t = next;
      
    }
    System.out.println("Part 2: " + t);
    //System.out.println("Part 2: " + findNext(bl, 0, 1789));


  }

  public long findNext(List<Map.Entry<Integer, Long>> lst, long start, long step)
  {
    long t = start+step;
    while(true)
    {
      int correct = 0;

      for(Map.Entry<Integer, Long> me : lst)
      {
        long idx = me.getKey();
        long bus_id = me.getValue();
        if ( (t+idx) % bus_id == 0) correct++;

      }
      if (correct == lst.size()) return t;
      t+=step;


    }

  }

  

}
