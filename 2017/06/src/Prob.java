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

  int banks[]=new int[16];

  public Prob(Scanner scan)
  {
    for(int i=0; i<16; i++)
    {
      banks[i] = scan.nextInt();

    }
    System.out.println(printNumbers(banks));

    HashSet<String> states = new HashSet<>();
    int cycle_count=0;
    String part2_state = null;
    while(true)
    {
      cycle();
      cycle_count++;

      String state = printNumbers(banks);
      if (states.contains(state))
      {
        part2_state = state;
        break;
      }
      states.add(state);
    }
    System.out.println("Part 1: " + cycle_count);

    int repeat_len = 0;
    while(true)
    {
      cycle();
      repeat_len++;

      if (printNumbers(banks).equals(part2_state))
      {
        break;
      }
    }
    System.out.println("Part 2: " + repeat_len);


  }

  public void cycle()
  {
    int high_b = -1;
    int high_n = -1;

    for(int i=0; i<banks.length; i++)
    {
      if (banks[i] > high_n)
      {
        high_n=banks[i];
        high_b=i;
      }

    }

    int idx = high_b;
    banks[idx] = 0;
    int dist = high_n;
    while(dist > 0)
    {
      idx++;
      idx = idx % banks.length;

      banks[idx]++;
      dist--;

    }


  }


  public String printNumbers(int b[])
  {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<b.length; i++)
    {
      if (i > 0) sb.append(",");
      sb.append(b[i]);

    }
    return sb.toString();

  }

}
