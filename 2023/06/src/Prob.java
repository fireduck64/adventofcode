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

  public Prob(Scanner scan)
  {
    String time_l = scan.nextLine();
    String dist_l = scan.nextLine();
    List<Long> times = Tok.enl(time_l, " ");
    List<Long> dists = Tok.enl(dist_l, " ");

    int p1=1;

    for(int r=0; r< times.size(); r++)
    {
      int win_count = 0;
      long race_time = times.get(r);
      for(long h=1; h<race_time; h++)
      {
        long d = race(h, race_time);
        if (d > dists.get(r))
        {
          win_count++;
        }
      }
      p1*=win_count;
    }
    System.out.println("P1: " + p1);

    times = Tok.enl(time_l.replace(" ",""),":");
    dists = Tok.enl(dist_l.replace(" ",""),":");

    int p2=1;

    for(int r=0; r< times.size(); r++)
    {
      int win_count = 0;
      long race_time = times.get(r);
      for(long h=1; h<race_time; h++)
      {
        long d = race(h, race_time);
        if (d > dists.get(r))
        {
          win_count++;
        }
      }
      p2*=win_count;
    }
    System.out.println("P2: " + p2);

  }

  public long race(long hold_time, long total_time)
  {
    long speed = hold_time;
    long rem_time = total_time - hold_time;

    return rem_time * speed;


  }

}
