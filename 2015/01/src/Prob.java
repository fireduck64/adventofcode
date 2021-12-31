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
    for(char z : Str.stolist(line))
    {
      if (z=='(') floor++;
      if (z==')') floor--;
    }
    System.out.println("Part 1 - " + floor);

  }

}
