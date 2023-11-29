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

  boolean part2 = false;

  public Prob(Scanner scan)
  {
    long p1 =0;
    long p2 = 0;
    for(String line : In.lines(scan))
    {
      LinkedList<String> input = new LinkedList<>();
      input.addAll(Tok.smart(line));

      part2=false;
      long v = readExp(input);
      p1+=v;

      part2=true;
      input.clear();
      input.addAll(Tok.smart(line));
      v = readExp(input);
      p2+=v;


    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);

  }

  public long readValue(LinkedList<String> input)
  {
    String v = input.poll();

    if (v.equals("("))
    {
      return readExp(input);
    }
    else
    {
      /*if (part2)
      if (input.size()>0)
      if (input.peek().equals("+"))
      {
        input.poll();
        return Long.parseLong(v) + readValue(input);
      }*/
      return Long.parseLong(v);
    }
  }
  public long readExp(LinkedList<String> input)
  {
    long v = 0L;

    if (!part2)
    {
      while(input.size() > 0)
      {
        String n = input.poll();
        if (n.equals(")")) return v;
        else if (n.equals("*"))
        {
          v = v * readValue(input);
        }
        else if (n.equals("+"))
        {
          v = v + readValue(input);
        }
        else if (n.equals("("))
        {
          v = readExp(input);
        }
        else
        { 
          v = Long.parseLong(n);
        }
      }
    }
    else
    {
      ArrayList<String> tokens = new ArrayList<>();
      while(input.size() > 0)
      {
        String n = input.poll();
        if (n.equals(")")) break;
        if (n.equals("("))
        {
          tokens.add("" + readExp(input));
        }
        else
        {
          tokens.add(n);
        }
      }

      while(tokens.size() > 1)
      {
        boolean found_add = false;
        for(int i=0; i<tokens.size(); i++)
        {
          if (tokens.get(i).equals("+"))
          {
            long a = Long.parseLong(tokens.get(i-1));
            long b = Long.parseLong(tokens.get(i+1));

            long n = a + b;

            tokens.remove(i-1);
            tokens.remove(i-1);
            tokens.remove(i-1);
            tokens.add(i-1, "" + n);
            found_add=true;
            break;
          }
        }
        if (!found_add)
        {
          for(int i=0; i<tokens.size(); i++)
          {
            if (tokens.get(i).equals("*"))
            {
              long a = Long.parseLong(tokens.get(i-1));
              long b = Long.parseLong(tokens.get(i+1));

              long n = a * b;

              tokens.remove(i-1);
              tokens.remove(i-1);
              tokens.remove(i-1);
              tokens.add(i-1, "" + n);
              break;
            }
          }
 
        }
      }
      return Long.parseLong(tokens.get(0));

    }

    return v;

  }

}
