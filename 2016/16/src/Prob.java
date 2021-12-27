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
  String src;

  public Prob(Scanner scan)
  {
    src = scan.nextLine();
    String junk = gen(272);
    String check = checksum(junk);

    System.out.println("Part 1: " + check);

    String p2 = gen(35651584);
    String check2 = checksum(p2);
    System.out.println("Part 2: " + check2);


  }

  public String dragon(String in)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(in);

    sb.append('0');

    for(int i=in.length()-1; i>=0; i--)
    {
      char z = in.charAt(i);
      if (z == '0') sb.append('1');
      if (z == '1') sb.append('0');
    }
    return sb.toString();
   
  }
  public String gen(int size)
  {
    String s = src;
    while(s.length() < size)
    {
      s = dragon(s);
    }

    s = s.substring(0, size);
    return s;

  }
  public String checksum(String in)
  {
    String sum = in;
    while(true)
    {
      StringBuilder sb = new StringBuilder();

      for(int i=0; i<sum.length(); i+=2)
      {
        String seg = sum.substring(i, i+2);
        if (seg.charAt(0) == seg.charAt(1))
        {
          sb.append("1");
        }
        else
        {
          sb.append("0");
        }

      }
      sum = sb.toString();
      if (sum.length() % 2 == 1) break;
      

    }
    return sum;

  }

}
