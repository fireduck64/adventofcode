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
  String line;
  ArrayList<IntComp> comps = new ArrayList<>();
  TreeSet<Long> nat_y_set=new TreeSet<>();


  public Prob(Scanner scan)
  {
    line = scan.nextLine();

    comps.clear();
    for(int i=0; i<50; i++)
    {
      IntComp c = new IntComp(line);
      c.input.add((long)i);
      comps.add(c);
    }

    boolean p1=false;
    

    while(!p1)
    {
      for(int i=0; i<50; i++)
      {
        IntComp c = comps.get(i);
        if (c.input.size() ==0) c.input.add(-1L);
        c.exec();
        while(c.output.size()>0)
        {
          long dest = c.output.poll();
          long x = c.output.poll();
          long y = c.output.poll();
          if (dest == 255L)
          {
            p1=true;
            System.out.println("Part 1: " + y);

          }
          else
          {
            comps.get((int)dest).input.add(x);
            comps.get((int)dest).input.add(y);
          }
        }

      }
    }
    comps.clear();
    for(int i=0; i<50; i++)
    {
      IntComp c = new IntComp(line);
      c.input.add((long)i);
      comps.add(c);
    }


    long nat_x=0;
    long nat_y=0;

    while(true)
    {
      int active=0;

      for(int i=0; i<50; i++)
      {
        IntComp c = comps.get(i);
        if (c.input.size() >0) active++;
        if (c.input.size() ==0) c.input.add(-1L);
        c.exec();
        while(c.output.size()>0)
        {
          active++;
          long dest = c.output.poll();
          long x = c.output.poll();
          long y = c.output.poll();
          if (dest == 255L)
          {
            nat_x = x;
            nat_y = y;

          }
          else
          {
            comps.get((int)dest).input.add(x);
            comps.get((int)dest).input.add(y);
          }
        }

      }
      if (active==0)
      {
        //System.out.println("Restarting with " + nat_x + "/" + nat_y);
        comps.get(0).input.add(nat_x);
        comps.get(0).input.add(nat_y);
        if (nat_y_set.contains(nat_y))
        {
          System.out.println("Part 2: " + nat_y);
          return;
        }

        nat_y_set.add(nat_y);

      }
 
    }




  }

}
