import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();


  ArrayList<IntComp> comps = new ArrayList<>();
  final int C = 50;

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();
    for(int i=0; i<C; i++)
    {
      IntComp c = new IntComp(line);
      c.addInput(i);
      comps.add(c);
    }
    runNetwork();
  }

  public void runNetwork()
  {
    Point nat = null;
    Point nat_sent=new Point(-1,-1);
    int messages=0;

    while(true)
    {
      int nonidle = 0;
      for(int i=0; i<C; i++)
      {
        IntComp c = comps.get(i);
        if (c.input_queue.size() == 0)
        {
          c.addInput(-1L);
        }
        else
        {
          nonidle++;
        }
        c.execute();
        while(c.output_queue.size() > 0)
        {
          nonidle++;
          long dest = c.output_queue.poll();
          long x = c.output_queue.poll();
          long y = c.output_queue.poll();
          messages++;

          int dc = (int) dest;
          if (dc == 255)
          {
            if (nat == null)
            {
              System.out.println("Part 1 - Message to 255: " + x + " " + y);
            }
            nat = new Point(x,y);
          }
          else
          {
            comps.get(dc).addInput(x);
            comps.get(dc).addInput(y);
          }
        }

      }

      if (nonidle == 0)
      {
        System.out.println("Sending nat to zero: " + nat);
        comps.get(0).addInput(nat.x);
        comps.get(0).addInput(nat.y);
        if (nat.equals(nat_sent))
        {
          System.out.println("Part 2 - " + nat);
          System.out.println("Total messages: " + messages);
          return;

        }
        nat_sent=nat;
      }
    }

  }

}
