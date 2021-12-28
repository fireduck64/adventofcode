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

  TreeMap<Long, Integer > opens=new TreeMap<>();
  TreeMap<Long, Integer > closes=new TreeMap<>();

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      line = line.replace("-", " ");
      List<Long> lst = Tok.enl(line, " ");
      long start = lst.get(0);
      long end = lst.get(1);

      addMap(opens, start,1);
      addMap(opens, end+1, -1);
    }

    System.out.println("Part 1: " + findLowest());
    System.out.println("Part 2: " + findAllowed());

  }

  public void addMap(TreeMap<Long, Integer > m, long v, int val)
  {
    if (!m.containsKey(v))
    {
      m.put(v, 0);
    }
    m.put(v, m.get(v) + val);

  }

  public long findAllowed()
  {
    long x =0;
    int o = opens.get(x);
    long allowed=0;

    while(true)
    {
      Map.Entry<Long, Integer> me = opens.higherEntry(x);
      if (me == null) break;
      if (o == 0)
      {
        allowed += Math.abs(x - me.getKey());
      }

      x = me.getKey();
      o += me.getValue();
    }

    return allowed;

  }

  public long findLowest()
  {
    long x =0;
    int o = opens.get(x);

    while(true)
    {
      Map.Entry<Long, Integer> me = opens.higherEntry(x);
      x = me.getKey();
      o += me.getValue();
      if (o == 0) return x;
    }



  }


}
