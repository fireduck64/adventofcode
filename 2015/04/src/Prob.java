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
    String input = scan.nextLine();
    int v = 0;
    while(true)
    {
      String hash = HUtil.getHash(input + v);
      if (hash.startsWith("00000"))
      {
        System.out.println("Part 1: " + v);
        break;
      }
      v++;
    }
    while(true)
    {
      String hash = HUtil.getHash(input + v);
      if (hash.startsWith("000000"))
      {
        System.out.println("Part 2: " + v);
        break;
      }
      v++;
    }


  }

}
