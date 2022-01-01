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
  ArrayList<Thing> things = new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      things.add(new Thing(line));
    }

    long p1 = rec(0, new TreeMap<Integer, Integer>(), 100);
    System.out.println("Part 1: " + p1);
    check_cal=true;
    long p2 = rec(0, new TreeMap<Integer, Integer>(), 100);
    System.out.println("Part 2: " + p2);

  }

  boolean check_cal=false;

  public long rec(int t, Map<Integer, Integer> counts, int rem)
  {
    long best = 0;

    if (t == things.size())
    { // score

      long score = 1;
      int cal=0;
      for(int i=0; i<things.size(); i++)
      {
        cal += counts.get(i) * things.get(i).cal;
      }
      if (check_cal)
      if (cal != 500) 
        return 0;
      for(int s=0; s<4; s++)
      {
        long sub = 0;
        for(int i=0; i<things.size(); i++)
        {
          sub += counts.get(i) * things.get(i).stuff[s];
        }
        if (sub < 0) return 0;
        score *= sub;
      }
      return score;
     

    }
    else
    {
      for(int i=0; i<=rem; i++)
      {
        TreeMap<Integer, Integer> c2 = new TreeMap<>();
        c2.putAll(counts);
        c2.put(t, i);
        best = Math.max(best, rec(t+1, c2, rem-i));
      }
    }


    return best;

  }

  public class Thing
  {
    String name;
    int[] stuff=new int[4];
    int cal;

    public Thing(String line)
    {
      line = line.replace(":", "").replace(",","");
      name = Tok.en(line, " ").get(0);
      List<Integer> vlst = Tok.ent(line, " ");
      for(int i=0; i<4; i++) stuff[i] = vlst.get(i);
      cal = vlst.get(4);
    }

  }
}
