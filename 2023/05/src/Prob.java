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
  ArrayList<Long> seeds=new ArrayList<>();

  TreeMap<String, ArrayList<MapEntry> > mapmap = new TreeMap<>();
  ArrayList<String> maplist = new ArrayList<>();

  public Prob(Scanner scan)
    throws Exception
  {
    LinkedList<String> lines = new LinkedList<>();
    lines.addAll(In.lines(scan));

    {
      String line = lines.pop();
      seeds.addAll(Tok.enl(line, " "));
    }
    System.out.println("Seeds: " + seeds);

    String map_name = null;

    while(lines.size() > 0)
    {
      String line = lines.pop();
      if (line.contains("map:"))
      {
        map_name = new Scanner(line).next();
        mapmap.put(map_name, new ArrayList<MapEntry>());
        System.out.println(map_name);
        maplist.add(map_name);
      }
      else if (line.trim().length() == 0)
      {

      }
      else
      {
        MapEntry me = new MapEntry(line);
        mapmap.get(map_name).add(me);
      }

    }
    TreeSet<Long> sols = new TreeSet<>();
    for(long seed : seeds)
    {
      long v = mapThrough(seed, 0);
      sols.add(v);

    }
    System.out.println(sols);
    System.out.println("P1: " + sols.first());

    // Single thread solution
    /*long best_sol = -1;
    for(int i=0; i<seeds.size(); i+=2)
    {
      System.out.println("Seed: " + i + " " + seeds.get(i));

      int r = seeds.get(i+1);
      for(long j=0; j<r; j++)
      {

        long v = mapThrough(seeds.get(i) + j, 0);
        if ((best_sol < 0) || (v < best_sol))
        {
          System.out.println("Better: " + v);
          best_sol = v;
        }
      }
    }*/

    ArrayList<WorkThread> wts = new ArrayList();
    System.out.println("Starting some threads: " + seeds.size());
    long total=0;
    for(int i=0; i<seeds.size(); i+=2)
    {
      WorkThread wt = new WorkThread(seeds.get(i), seeds.get(i+1));
      wt.start();
      total+=seeds.get(i+1);
      wts.add(wt);
    }
    System.out.println("Total comp: " + total);

    long bbb = Long.MAX_VALUE;
    for(WorkThread wt : wts)
    {
      wt.join();

      bbb = Math.min(bbb, wt.best);
    }
    System.out.println("P2: " + bbb);


  }

  public class WorkThread extends Thread
  {
    long start;
    long range;
    volatile long best=Long.MAX_VALUE;
    public WorkThread(long start, long range)
    {
      this.start = start;
      this.range = range;

    }

    public void run()
    {
      for(long s = 0; s<range; s++)
      {
        long s2 = start + s;
        long v = mapThrough(s2, 0);
        if (v >= 0)
        {
          best = Math.min(v, best);
        }
      }
      System.out.println("Thread best: " + best);

    }

  }

  public class MapEntry
  {
    long dest;
    long src;
    long range;
    public MapEntry(String line)
    {
        List<Long> num = Tok.enl(line, " ");
        dest = num.get(0);
        src = num.get(1);
        range = num.get(2);

    }
    public String toString()
    {
      return String.format("Rule: dest: %d src: %d range: %d", dest, src,range);
    }


    public long match(long s)
    {
      if ((s >= src) && (s < src+range))
      {
        long diff = s - src;
        return dest + diff;

      }
      return -1L;
    }


  }

  public long mapThrough(long src, int conv_idx)
  {
    if (conv_idx == maplist.size()) return src;

    //System.out.println("P: " + src + " - " + conv_rem);

    String conv = maplist.get(conv_idx);
    ArrayList<MapEntry> maps = mapmap.get(conv);

    int matches = 0;
    for(MapEntry me : maps)
    {
      long m = me.match(src);
      if (m >= 0)
      {
        //System.out.println("Match on rule: " + me);
        matches++;

        long n = mapThrough(m, conv_idx+1);
        if (n >= 0) return n;
      }
    }
    if (matches == 0)
    {
      long n = mapThrough(src, conv_idx+1);
      if (n >= 0) return n;
    }

    return -1L;

  }

}
