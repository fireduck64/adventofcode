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
    String line = scan.nextLine();

    for(int i=0; i<line.length(); i++)
    {
        String s = line.substring(i,i+4);
        if (!hasDup(s))
        {
          int len = i+4;
          System.out.println("Part 1: " + len);
          break;
        }


    }
    for(int i=0; i<line.length(); i++)
    {
        String s = line.substring(i,i+14);
        if (!hasDup(s))
        {
          int len = i+14;
          System.out.println("Part 2: " + len);
          return;
        }

    }


  }

  public boolean hasDup(String str)
  {
    for(int i=0; i<str.length(); i++)
    for(int j=i+1; j<str.length(); j++)
    {
      if (str.charAt(i)==str.charAt(j)) return true;
    }

    return false;

  }

}
