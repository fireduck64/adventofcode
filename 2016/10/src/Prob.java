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
  TreeMap<Integer, Bot> bots=new TreeMap<>();
  TreeMap<Integer, TreeSet<Integer> > out_bin=new TreeMap<>();

  // Question - who has both 61 and 17 at once?


  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      List<Integer> nums = Tok.ent(line, " ");
      if (line.startsWith("value"))
      {
        int val = nums.get(0);
        int b = nums.get(1);
        getBot(b).holding.add(val);
      }
      else
      {
        int b = nums.get(0);
        int low = nums.get(1);
        int high = nums.get(2);
        //Store rule, handle out bin case
        List<String> tok = Tok.en(line, " ");
        String low_out=tok.get(5);
        String high_out=tok.get(10);

        if (low_out.equals("output")) low+=10000;
        if (high_out.equals("output")) high+=10000;

        Bot bot = getBot(b);
        bot.give_high=high;
        bot.give_low=low;

        getBot(high);
        getBot(low);

      }

    }
    while(true)
    {
      int actions=0;
      for(int b : bots.keySet())
      {
        if (b<10000)
        {
          if (getBot(b).act(b)) actions++;

        }

      }
      if (actions==0) break;
    }
    long p2=1;
    for(int i=0; i<3; i++)
    {
      for(int v : getBot(i+10000).holding)
      {
        p2*=v;
      }

    }
    System.out.println("Part 2: " + p2);

  }

  public Bot getBot(int n)
  {
    if (!bots.containsKey(n)) bots.put(n, new Bot());

    return bots.get(n);

  }

  public class Bot
  {
    // rule
    // positive means a bot, 10000+ means out box
    int give_high;
    int give_low;

    TreeSet<Integer> holding=new TreeSet<>();

    public boolean act(int me)
    {
      if (holding.size() > 2) E.er();
      if (holding.size() == 2)
      {
        if (holding.contains(61) && holding.contains(17))
        {
          System.out.println("Part 1: " + me + " " + holding);

        }

        getBot(give_low).holding.add(holding. first());
        getBot(give_high).holding.add(holding. last());

        holding.clear();

        return true;
        

      }

      return false;
    }

  }

}
