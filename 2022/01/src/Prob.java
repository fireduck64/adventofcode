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
    List<String> lines = In.lines(scan);  

    int highest=0;
    int sum = 0;

    ArrayList<Integer> elfs=new ArrayList<>();
    for(String l : lines)
    {
      if (l.length() == 0)
      {
        if (sum > 0) elfs.add(sum);
        sum=0;

      }
      else
      {
        int v = Integer.parseInt(l);
        sum+=v;
        highest = Math.max(sum, highest);

      }
    }
    System.out.println("Part 1: " + highest);
    Collections.sort(elfs);
    int p2 = 0;
    for(int i=0; i<3; i++)
    {
      p2+=elfs.get(elfs.size() - 1 - i);
    }
    System.out.println("Part 2: " + p2);
  }

}
