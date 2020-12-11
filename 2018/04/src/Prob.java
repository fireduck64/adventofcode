import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();


  Map<Integer, Guard> gmap=new TreeMap<>();

  public Prob(Scanner scan)
  {
    int time=0;
    Guard g=null;
    boolean awake=true;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.contains("Guard"))
      {
        { 
          if (g != null)
          {
            for(int t=time; t<60; t++)
            {
              if (!awake)
              {
                g.sleep_min++;
                g.asleep[t]++;
              }

            }

          }

        }
        int cut=line.indexOf('#');
        Scanner s2 = new Scanner(line.substring(cut+1));
        int id = s2.nextInt();
        if (!gmap.containsKey(id))
        {
          gmap.put(id, new Guard());

        }
        awake=true;

        g = gmap.get(id);
        time = 0;
      }
      else
      {
        int cut=line.indexOf(':');
        String cut_line = line.substring(cut+1);
        cut_line = cut_line.replace("]", " ");
        Scanner s2 = new Scanner(cut_line);
        int event_time = s2.nextInt();
        if (line.contains("wakes up"))
        {
          for(int t = time; t<event_time; t++)
          {
            g.sleep_min++;
            g.asleep[t]++;

          }
          time = event_time;
          awake=true;
        }
        if (line.contains("falls asleep"))
        {
          time = event_time;
          awake=false;
        }

      }
    }

    int max_min = 0;
    int max_id = 0;
    for(int id : gmap.keySet())
    {
      if (gmap.get(id).sleep_min > max_min)
      {
        max_id = id;
        max_min = gmap.get(id).sleep_min;
      }


    }
    System.out.println("Guard: " + max_id);

    {
      g = gmap.get(max_id);
      int best_min = 0;
      int sleeps = 0;
      for(int t=0;t<60; t++)
      {
        if (g.asleep[t] > sleeps)
        {
          best_min = t;
          sleeps = g.asleep[t];

        }

      }

      System.out.println("Best min: " + best_min);
      System.out.println(max_id * best_min);
    }

    { // strat 2
      int best_min = 0;
      int best_guard = 0;
      int sleeps = 0;
      for(int id : gmap.keySet())
      {
        g = gmap.get(id);
        for(int t=0;t<60; t++)
        {
          if (g.asleep[t] > sleeps)
          {
            best_min = t;
            best_guard = id;
            sleeps = g.asleep[t];
          }
        }


      }
      System.out.println("G " + best_guard + " t" + best_min);
      System.out.println( best_guard * best_min);
   

    }
    

  }

  public class Guard
  {
    int sleep_min=0;
    int asleep[]=new int[60];

  }

}
