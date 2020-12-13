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

  ArrayList<Integer> bus_lst = new ArrayList<>();
  ArrayList<Integer> bus2_lst = new ArrayList<>();
  int start_time;

  public Prob(Scanner scan)
  {
    start_time = scan.nextInt();
    String bus_line = scan.next();
    String part_2_line = bus_line;
    bus_line = bus_line.replace("x"," ");
    bus_line = bus_line.replace(",", " ");
    Scanner scan2 = new Scanner(bus_line);
    while(scan2.hasNextInt())
    {
      bus_lst.add( scan2.nextInt() );
    }
    System.out.println(bus_lst);
    int lowest = -1;
    long wait = 1000000;
    for(int bus : bus_lst)
    {
      long w = getWaitTime(start_time, bus);
      if (w < wait)
      {
        lowest = bus;
        wait = w;
      }
    }
    System.out.println("" + lowest + " " + wait + " " + (lowest*wait) );

    part_2_line = part_2_line.replace(","," ");
    System.out.println(part_2_line);

    TreeSet<Long> all_lines=new TreeSet<>();

    scan2 = new Scanner(part_2_line);
    long product = 1;
    while(scan2.hasNext())
    {
      String b = scan2.next();
      if (b.equals("x"))
      {
        bus2_lst.add(-1);
      }
      else
      {
        int bus = Integer.parseInt(b);
        bus2_lst.add( bus );
        all_lines.add((long)bus);
        product = product * (long)bus;
      }

    }
    System.out.println(bus2_lst);


    TreeMap<String, Long> link=new TreeMap<>();
    TreeSet<String> link_done=new TreeSet<>();


    long step = 1L;
    long start = 0L;

    System.out.println("Product: " + product);

    // So run it once with these commented out
    // grab some high start and step numbers from the output and put them
    // in there.  Ha.
    //step=105370369L;
    //start=109493804L;

    long best_step = 1L;
    long best_start = 0L;


    for(long tm = start; tm<20000000L; tm+=step)
    {
      boolean ok=true;
      ArrayList<Integer> ok_list = new ArrayList<>();

      for(int idx = 0; idx < bus2_lst.size(); idx++)
      {
        int b = bus2_lst.get(idx);
        if (b < 0) continue;
        if (checkBus(tm+idx, b))
        {
          ok_list.add(b);
        }
        else
        {
          ok =false;
          //break;
        }

      }
      if (ok_list.size() > 1)
      {

        String key = ok_list.toString();
        if (!link.containsKey(key))
        {
          link.put(key, tm);
          System.out.println(ok_list + " link " + tm);
        }
        else if (!link_done.contains(key))
        {
          long fstep = tm - link.get(key);
          System.out.println(key + " step " + fstep + " start " + tm);
          link_done.add(key);
          if (fstep > best_step)
          {
            best_step = fstep;
            best_start = tm;
          }
        }
      }
      if (ok)
      {
        System.out.println("SOLUTION:");
        System.out.println(tm);
        break;
      }

    }

    start = best_start;
    step = best_step;
    System.out.println("Restarting with start: " + start + " step: " + step);

    for(long tm = start; tm<product; tm+=step)
    {
      boolean ok=true;

      for(int idx = 0; idx < bus2_lst.size(); idx++)
      {
        int b = bus2_lst.get(idx);
        if (b < 0) continue;
        if (checkBus(tm+idx, b))
        {
        }
        else
        {
          ok =false;
          break;
        }

      }
      if (ok)
      {
        System.out.println("SOLUTION:");
        System.out.println(tm);
        break;
      }

    }




  }


  public boolean checkBus(long tm, long bus)
  {
    return (tm % bus == 0);
  }
  public long getWaitTime(long tm, long bus)
  {
    if (tm % bus == 0L) return 0L;

    return bus - (tm % bus);


  }

}
