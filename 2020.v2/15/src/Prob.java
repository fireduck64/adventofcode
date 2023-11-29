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
  ArrayList<Integer> start;

  HashMap<Integer, ArrayList<Integer>> last_spoke = new HashMap<>();

  public Prob(Scanner scan)
  {
    start = In.ints(new Scanner(scan.nextLine().replace(","," ")));


    int last=0;
    int pos=0;
    for(int i=0; i<start.size(); i++)
    {
      speak(i, start.get(i));
      last = start.get(i);
      pos++;
    }
    
    while(pos < 30000000)
    {
      if (last_spoke.get(last).size() == 1)
      {
        speak(pos, 0);
        last=0;
      }
      else
      {
        ArrayList<Integer> l = last_spoke.get(last);
        int diff = l.get(l.size()-1) - l.get(l.size()-2);
        speak(pos, diff);
        last=diff;
      }
      
      pos++;
      if (pos == 2020) System.out.println("Part 1: " + last);
      if (pos == 30000000) System.out.println("Part 2: " + last);
    }
  }

  public void speak(int pos, int number)
  {
    //System.out.println("speak: " + pos + " " + number);
    if (!last_spoke.containsKey(number))
    {
      last_spoke.put(number, new ArrayList<Integer>());
    }
    last_spoke.get(number).add(pos);


  }

}
