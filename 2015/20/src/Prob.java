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
  long target;
  
  int top = 3000000;

  long gifts[] = new long[top];


  public Prob(Scanner scan)
  {
    target = scan.nextLong();

    for(int i=1; i<top; i++)
    {
      long v = i*10L;
      for(int j=i; j<top; j+=i)
      {
        gifts[j] += v;
      }
    }

    for(int i=0; i<top; i++)
    {
      if (gifts[i] >= target)
      {
        System.out.println("Part 1: " + i);
        break;
      }
    }

    gifts=new long[top];
     for(int i=1; i<top; i++)
    {
      long v = i*11L;
      int houses=0;
      for(int j=i; j<top; j+=i)
      {
        gifts[j] += v;
        houses++;
        if (houses==50) break;
      }
    }

    for(int i=0; i<top; i++)
    {
      if (gifts[i] >= target)
      {
        System.out.println("Part 2: " + i);
        break;
      }
    }



  }


}
