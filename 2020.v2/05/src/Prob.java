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
    int p1 = 0;
    TreeSet<Integer> seats = new TreeSet<>();

    for(String line : In.lines(scan))
    {
      line = line.replace("F","0").replace("B","1");
      line = line.replace("L","0").replace("R","1");
      int n = Integer.parseInt(line, 2);

      p1 = Math.max(n, p1);


      seats.add(n);

    }
    
    System.out.println("Part 1: " + p1);

    for(int a : seats)
    {
      if (!seats.contains(a+1))
      if (seats.contains(a+2))
      {
        System.out.println("Part 2: " + (a+1));

      }

    }

  }

}
