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
    int floor = 0;
    int pos=0;
    boolean p2=false;
    for(char z : Str.stolist(line))
    {
      pos++;
      if (z=='(') floor++;
      if (z==')')
      {
        floor--;
        if (!p2)
        if (floor == -1)
        {
          System.out.println("Part 2 - " + pos);
          p2=true;
        }
      }
    }
    System.out.println("Part 1 - " + floor);

  }

}
