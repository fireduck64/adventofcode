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
    List<String> lines = In.lines(scan);

    int part1=0;
    int part2=0;
    
    for(String line : lines)
    {
      line = line.replace("-"," ").replace(":","");
      System.out.println(line);
      String[] split = line.split(" ");
      int low = Integer.parseInt(split[0]);
      int high = Integer.parseInt(split[1]);
      char letter = split[2].charAt(0);
      String pass = split[3];

      int count=0;
      for(int i=0; i<pass.length(); i++)
      {
        if (pass.charAt(i) == letter) count++;
      }
      if (low <= count)
      if (count <= high)
      {
        part1++;
      }

      int match=0;
      try
      {
        if (pass.charAt(low-1) == letter) match++;
      }
      catch(Exception e){match=100;}

      try
      {
        if (pass.charAt(high-1) == letter) match++;
      }
      catch(Exception e){match=100; e.printStackTrace();}

      if (match == 1) part2++;


    }

    System.out.println("Part 1: " + part1);
    System.out.println("Part 2: " + part2);


  }

}
