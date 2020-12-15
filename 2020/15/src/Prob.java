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

  TreeMap<Long, Long> last_spoken = new TreeMap<>();

  ArrayList<Long> initial_lst = new ArrayList<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLong())
    {
      initial_lst.add(scan.nextLong());
    }

    boolean last_new = false;
    long last_age = 0L;

    long turn=0;
    for(int i=0;i<initial_lst.size(); i++)
    {
      turn++;
      last_spoken.put( initial_lst.get(i), (long)turn);
    }

    last_new = true;
    last_age = turn-1;

    long last = initial_lst.get(initial_lst.size()-1);

    while(turn < 30000000)
    {
      turn++;
      long speak = 0;
      if (last_new)
      {
        speak = 0;
      }
      else
      {
        long age = last_age ;
        speak = age;
      }

      //System.out.println("Turn: " + turn + " Speak: " + speak);

      if (last_spoken.containsKey(speak))
      {
        last_new = false;
        last_age = turn - last_spoken.get(speak);
        //System.out.println("Age: turn: " + turn + " last: " + last_spoken.get(speak));
        
      }
      else
      {
        last_new = true;
        last_age = 0;

      }
      last_spoken.put( speak, turn);
      last = speak;
      
    }
    System.out.println(last);

  }

}
