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
  String line;
  int pos=0;
  int trash_count;

  public Prob(Scanner scan)
  {
    line = scan.nextLine();

    System.out.println("Part 1");
    System.out.println(readGroup(1));
    System.out.println("Part 2");
    System.out.println(trash_count);
  }

  public long readGroup(int level)
  {
    {
      if (line.charAt(pos) != '{') E.er();
      pos++;
    }
    long score = level;

    while(true)
    {
      char z = line.charAt(pos);
      if (z == '{')
      {
        score+=readGroup(level+1);
      }
      else if (z == ',')
      {
        pos++;
      }
      else if (z == '}')
      {
        pos++;
        break;
      }
      else if (z == '<')
      {
        readTrash();
      }
      else
      {
        E.er(""+z);
      }
    }
    return score;
  }

  public void readTrash()
  {
    if (line.charAt(pos) != '<') E.er();
    pos++;

    while(true)
    {
      char z = line.charAt(pos);
      if (z=='!')
      {
        pos+=2;
      }
      else if (z=='>')
      {
        pos++;
        return;
      }
      else
      {
        trash_count++;
        pos++;
      }

    }

  }

  

}
