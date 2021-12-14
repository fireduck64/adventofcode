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

  TreeMap<Integer, Integer> inst = new TreeMap<>();
  TreeMap<Integer, Integer> inst_orig = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextInt())
    {
      inst_orig.put(inst_orig.size(), scan.nextInt());
    }

    inst.clear();
    inst.putAll(inst_orig);

    System.out.println("Part 1: " + run());

    inst.clear();
    inst.putAll(inst_orig);

    System.out.println("Part 2: " + run2());
  }

  public long run()
  {
    int pos = 0;

    long steps = 0;
    while(true)
    {
      if ((pos < 0) || (pos >= inst.size()))
      {
        return steps;
      }
      int old_pos = pos;
      pos = pos + inst.get(pos);

      inst.put(old_pos, inst.get(old_pos) + 1);
      steps++;

    }

  }

  public long run2()
  {
    int pos = 0;

    long steps = 0;
    while(true)
    {
      if ((pos < 0) || (pos >= inst.size()))
      {
        return steps;
      }
      int old_pos = pos;
      pos = pos + inst.get(pos);

      int offset  =inst.get(old_pos);
      if (offset >= 3) offset--;
      else offset++;

      inst.put(old_pos, offset);
      steps++;

    }

  }


}
