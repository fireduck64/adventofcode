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

  TreeMap<String, String> rights = new TreeMap<>();
  TreeMap<String, String> lefts = new TreeMap<>();

  public Prob(Scanner scan)
  {
    String steps = scan.nextLine();

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.trim().length() > 0)
      {
        line = line.replace("=","").replace("(","").replace(")","").replace(","," ");
        line = line.replace("  "," ");

        Scanner s2 = new Scanner(line);
        String loc = s2.next();
        String l = s2.next();
        String r = s2.next();

        rights.put(loc, r);
        lefts.put(loc, l);
      }
    }
  
    {
      int p1 = 0;
      String loc = "AAA";
      int step_idx = 0;
      while(true)
      {
        step_idx = step_idx % steps.length();

        char m = steps.charAt(step_idx);
        String next = null;
        if (m == 'R') next = rights.get(loc);
        if (m == 'L') next = lefts.get(loc);

        loc = next;
        p1++;
        if (loc.equals("ZZZ")) break;

        step_idx++;
      }
      System.out.println("P1: " + p1);
    }

    
    {
      TreeMap<Integer, Long> cycle_times = new TreeMap<>();
      TreeMap<String, Long> repeat_map = new TreeMap<>();

      long p2 = 0;
      ArrayList<String> loc = new ArrayList<String>();
      for(String s : rights.keySet())
      {
        if (s.endsWith("A")) loc.add(s);
      }
      System.out.println("Start: " + loc);
      int step_idx = 0;
      while(true)
      {
        step_idx = step_idx % steps.length();
        char m = steps.charAt(step_idx);

        ArrayList<String> next = new ArrayList<>();
        int z_count = 0;
        p2++;
        for(String s : loc)
        {
          String n = null;
          if (m=='R') n = rights.get(s);
          if (m=='L') n = lefts.get(s);
          
          if (n.endsWith("Z"))
          {
            int idx = next.size();
            if (!cycle_times.containsKey(idx))
            {
              cycle_times.put(idx, p2);
              System.out.println(cycle_times);
            }
            z_count++;
          }

          
          /*String key = next.size() + "/" + n +"/" + step_idx;
          if (repeat_map.containsKey(key))
          {
            long prev = repeat_map.get(key);
            System.out.println(key + " repeat at " + p2 + " from " + prev);
            System.exit(0);

          }
          repeat_map.put(key, p2);*/
          
          next.add(n);

        }
        if (z_count == next.size())
        {
          break;
        }
        if (cycle_times.size() == loc.size()) break;
        loc = next;
        //System.out.println(loc);
        step_idx++;

      }
      System.out.println("P2: " + cycle_times);
      System.out.println("P2: " + ClownMath.lcm_l(cycle_times.values()));

    }
  }

}
