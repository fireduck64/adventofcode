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
    String list = scan.next();
    int match=0;
    for(int i=0; i<list.length(); i++)
    {
      int r=i-1;
      if (r <0) r=list.length()-1;
      if (list.charAt(i) == list.charAt(r))
      {
        match += Integer.parseInt("" + list.charAt(i));
      }
    }
    System.out.println("Part 1: " + match);

    match=0;
    for(int i=0; i<list.length(); i++)
    {
      int r=i+list.length()/2;
      if (r >= list.length()) r-=list.length();

      if (r <0) r=list.length()-1;
      if (list.charAt(i) == list.charAt(r))
      {
        match += Integer.parseInt("" + list.charAt(i));
      }
    }
    System.out.println("Part 2: " + match);


  }

}
