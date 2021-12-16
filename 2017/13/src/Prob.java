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
  TreeMap<Integer, Integer> layers = new TreeMap<>();

  int max_layer = 0;

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      List<Integer> in = Tok.ent(line.replace(": "," "), " ");

      int layer = in.get(0);
      int depth = in.get(1);
      layers.put(layer, depth);
      max_layer = Math.max(layer, max_layer);
    }
    System.out.println("Part 1: " + sim(0,false));

    int t =0;
    while(true)
    {
      if (sim(t,true) == 0)
      {
        System.out.println("Part 2: " + t);
        break;
      }
      t++;


    }

  }

  public int sim(int start_time, boolean count_hits)
  {
    int t=start_time;
    int loc=0;
    int sev = 0;
    int hits = 0;

    // Starting check !?
    while(loc <= max_layer)
    {
      if (scanned(loc, t))
      { 
        sev += loc * layers.get(loc);
        hits++;
      }
      loc++;
      t++;
    }

    if (count_hits) return hits;
    return sev;



  }

  public boolean scanned(int layer, int time)
  {
    if (layers.containsKey(layer))
    {
      int d = layers.get(layer);
      d = d +(d-2); // Goes out d, back d-2 until it is at the zero again

      if (time % d == 0)
      {
        return true;
      }

    }
    return false;

  }

}
