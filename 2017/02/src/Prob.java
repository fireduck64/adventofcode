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
    long diff_sum=0;
    long div_sum = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line=line.replace("\t"," ");
      List<Integer> numbers = Tok.ent(line, " ");
      Collections.sort(numbers);
      long diff = numbers.get( numbers.size() - 1) - numbers.get(0);
      diff_sum += diff;

      for(int i=0; i<numbers.size(); i++)
      for(int j=0; j<numbers.size(); j++)
      {
        if (i!=j)
        {
          int a = numbers.get(i);
          int b = numbers.get(j);
          if ((a > b) && (a % b == 0))
          {
            div_sum += a/b;

          }

        }

      }
      

    }
    System.out.println("Part 1: " + diff_sum);
    System.out.println("Part 2: " + div_sum);

  }

}
