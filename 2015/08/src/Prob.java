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
    int str_len=0;
    int char_len=0;
    int recode_len=0;
    for(String line : In.lines(scan))
    {
      str_len += line.length();
      char_len += countChars(line);
      recode_len+=countEncoded(line);
    }
    int p1 = str_len - char_len;
    System.out.println("Part 1: " + p1);
    int p2 = recode_len - str_len;
    System.out.println("Part 2: " + p2);


  }
  public int countEncoded(String line)
  {
    int count =2;
    int pos = 0;
    while(pos < line.length())
    {
      char z = line.charAt(pos);
      if (z == '"') count++;
      if (z == '\\') count++;
      pos++;
      count++;
    }
    //System.out.println("" + line + " - " + count);
    return count;
  }

  public int countChars(String line)
  {
    line = line.substring(1, line.length()-1);

    int count =0;
    int pos = 0;
    while(pos < line.length())
    {
      char z = line.charAt(pos);
      if (z == '\\')
      {
        char n = line.charAt(pos+1);
        if (n=='x')
        {
          count++;
          pos+=3;
        }
        else if (n=='\\')
        {
          count++;
          pos++;
        }
        else if (n=='"')
        {
          count++;
          pos++;
        }

      }
      else
      {
        count++;
      }

      pos++;
    }

    System.out.println(count);

    return count;

  }

}
