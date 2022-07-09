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
  String comp_line;

  public Prob(Scanner scan)
  {
    comp_line = scan.nextLine();

    {
      TreeSet<Long> rem = new TreeSet<>();
      for(long i=0; i<5; i++) rem.add(i);
      long p1 = rec(new LinkedList<Long>(), rem);

      System.out.println("Part 1: " + p1);
    }
    {
      TreeSet<Long> rem = new TreeSet<>();
      for(long i=5; i<10; i++) rem.add(i);
      long p2 = rec2(new LinkedList<Long>(), rem);

      System.out.println("Part 2: " + p2);
    }


  }

  public long rec(LinkedList<Long> phases, TreeSet<Long> rem)
  {
    if (rem.size() ==0) return tryPerm(phases);

    long max = 0;
    for(long p : rem)
    {
      phases.add(p);
      TreeSet<Long> r2 = new TreeSet<>();
      r2.addAll(rem);
      r2.remove(p);
      max = Math.max(rec(phases, r2), max);
      phases.removeLast();
    }
    return max;
  }

  public long tryPerm(List<Long> phases)
  {
    long output = 0;
    for(int i=0; i<5; i++)
    {
      IntComp comp = new IntComp(comp_line);
      comp.input.add(phases.get(i));
      comp.input.add(output);
      comp.exec();
      output = comp.output.peekLast();

    }
    return output;
  }

  public long rec2(LinkedList<Long> phases, TreeSet<Long> rem)
  {
    if (rem.size() ==0) return tryPerm2(phases);

    long max = 0;
    for(long p : rem)
    {
      phases.add(p);
      TreeSet<Long> r2 = new TreeSet<>();
      r2.addAll(rem);
      r2.remove(p);
      max = Math.max(rec2(phases, r2), max);
      phases.removeLast();
    }
    return max;
  }

  public long tryPerm2(List<Long> phases)
  {
    ArrayList<IntComp> comps = new ArrayList<>();

    for(int i=0; i<5; i++)
    {
      IntComp comp = new IntComp(comp_line);
      comp.input.add(phases.get(i));
      comps.add(comp);
    }
    comps.get(0).input.add(0L);
    while(true)
    {
      int run_count=0;
      for(int i=0; i<5; i++)
      {
        comps.get(i).exec();

        if (!comps.get(i).term) run_count++;
        int n = (i+1)%5;
        comps.get(n).input.addAll(comps.get(i).output);
        comps.get(i).output.clear();

      }
      if (run_count==0) break;

    }

    return comps.get(0).input.peekLast();
  }


}
