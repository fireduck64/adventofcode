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
    String s = scan.nextLine();
    for(int i=0; i<40; i++)
    {
      s = cycle(s);
    }
    System.out.println(s.length());
    for(int i=0; i<10; i++)
    {
      s = cycle(s);
    }
    System.out.println(s.length());


  }

  public String cycle(String in)
  {
    int pos = 0;
    char last = 0;
    int count = 0;
    StringBuilder sb = new StringBuilder();
    while(pos < in.length())
    {
      char z = in.charAt(pos);
      if (z == last)
      {
        count++;
      }
      else
      {
        if (count > 0)
        {
          sb.append("" + count);
          sb.append(last);
        }
        last=z;
        count=1;
      
      }
      pos++;
    }
    if (count > 0)
    {
      sb.append("" + count);
      sb.append(last);
    }
    return sb.toString();
  }

}
