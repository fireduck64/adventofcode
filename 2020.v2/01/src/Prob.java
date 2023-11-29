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
    ArrayList<Long> lst = In.numbers(scan);

    for(int i=0; i<lst.size(); i++)
    for(int j=0; j<lst.size(); j++)
    {
      long a = lst.get(i);
      long b = lst.get(j);
      if (a+b == 2020)
      {
        System.out.println("Part 1: " + a*b);
      }

    }
    for(int i=0; i<lst.size(); i++)
    for(int j=0; j<lst.size(); j++)
    for(int k=0; k<lst.size(); k++)
    {
      long a = lst.get(i);
      long b = lst.get(j);
      long c = lst.get(k);
      if (a+b+c == 2020)
      {
        System.out.println("Part 2: " + a*b*c);
      }

    }


  }

}
