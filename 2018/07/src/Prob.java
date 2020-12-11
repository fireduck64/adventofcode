import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    TreeSet<String> all_steps = new TreeSet<>();

    TreeSet<String> done = new TreeSet<>();
    TreeMap<String, TreeSet<String> > needs_map = new TreeMap<>();


    while(scan.hasNext())
    {
      String a = scan.next();
      String b = scan.next();

      all_steps.add(a);
      all_steps.add(b);

      if (!needs_map.containsKey(b)) needs_map.put(b, new TreeSet<>());
      if (!needs_map.containsKey(a)) needs_map.put(a, new TreeSet<>());

      needs_map.get(b).add(a);
    }

    String path = "";
    while(done.size() < all_steps.size())
    {
      for(String n : all_steps)
      {
        if (!done.contains(n))
        {
          boolean ready = true;
          for(String k : needs_map.get(n))
          {
            if (!done.contains(k)) ready=false;

          }
          if (ready)
          {
            path += n;
            done.add(n);
            break;

          }


        }

      }
    }
    System.out.println("Part 1");
    System.out.println(path);
    done.clear();

    TreeSet<String> started=new TreeSet<>();

    int time=0;
    int free_workers=5;
    TreeMap<Integer, TreeSet<String> > pending_work=new TreeMap<>();

    for(int i=0; i<100000; i++) pending_work.put(i, new TreeSet<String>());

    while(done.size() < all_steps.size())
    {
      for(String d : pending_work.get(time))
      {
        free_workers++;
        done.add(d);
      }

      for(String n : all_steps)
      {
        if (!started.contains(n))
        {
          boolean ready = true;
          for(String k : needs_map.get(n))
          {
            if (!done.contains(k)) ready=false;
          }
          if ((ready) && (free_workers>0))
          {
            free_workers--;
            int cost = 60 + n.charAt(0) - 'A' + 1;
            pending_work.get(cost+time).add(n);
            started.add(n);
          }
        }
      }

      time++;
    }
    time--;
    System.out.println("Time: " + time);


  }


}
