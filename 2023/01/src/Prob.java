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

  TreeMap<String, Integer> tokens=new TreeMap<>();

  public Prob(Scanner scan)
  {
    int p1=0;
    int p2=0;

    for(String line : In.lines(scan))
    {
      tokens.clear();
      for(int i =1; i<10; i++)
      {
        tokens.put("" + i, i); 
      }

      LinkedList<Integer> num = readTokens(line);

      p1+= num.peekFirst() * 10 + num.peekLast();

      tokens.put("one", 1);
      tokens.put("two", 2);
      tokens.put("three", 3);
      tokens.put("four", 4);
      tokens.put("five", 5);
      tokens.put("six", 6);
      tokens.put("seven", 7);
      tokens.put("eight", 8);
      tokens.put("nine", 9);

      num = readTokens(line);

      p2+= num.peekFirst() * 10 + num.peekLast();

    }
    System.out.println("Part1: " + p1);
    System.out.println("Part2: " + p2);

  }

  public LinkedList<Integer> readTokens(String in)
  {
    LinkedList<Integer> num=new LinkedList<>();
    for(int i=0; i<in.length(); i++)
    {
      String s = in.substring(i);
      for(String k : tokens.keySet())
      {
        if (s.startsWith(k))
        {
          num.add(tokens.get(k));
        }
      }
    }
    return num;
  }


}
