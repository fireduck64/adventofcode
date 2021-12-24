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
    System.out.println("Part 1: " + getLength(input,false));
    System.out.println("Part 2: " + getLength(input,true));
  }

  public long getLength(String in, boolean part2)
  {
    long len = 0;
    int pos =0;
    while(pos < in.length())
    {
      char z = in.charAt(pos);
      if (z=='(')
      {
        int end = in.indexOf(')', pos);

        String exp = in.substring(pos+1, end);
        List<Integer> lst = Tok.ent(exp, "x");
        int chars=lst.get(0);
        long times=lst.get(1);
        
        String chunk = in.substring(end+1, end+1+chars);


        pos = end+1;
        pos+=chars;
        if (part2)
        {
          len+=times * getLength(chunk, part2);
        }
        else
        {
          len+=times * chars;

        }

      }
      else
      {
        pos++;
        len++;
      }


    }
    return len;

  }

}
