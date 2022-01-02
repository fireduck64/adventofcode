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

  TreeMap<String, Integer> card=new TreeMap<>();
  TreeMap<Integer, TreeMap<String, Integer>> sues = new TreeMap<>();

  public Prob(Scanner scan)
    throws Exception
  {
    Scanner s2 = new Scanner(new FileInputStream("crime"));
    for(String line : In.lines(s2))
    {
      line = line.replace(":","");
      String item = Tok.en(line, " ").get(0);
      int val = Tok.ent(line," ").get(0);
      card.put(item, val);
    }
    System.out.println(card);
    int match=0;

    for(String line : In.lines(scan))
    {
      line = line.replace(":","");
      line = line.replace(",","");
      List<String> parts = Tok.en(line, " ");
      int sue = Integer.parseInt(parts.get(1));
      TreeMap<String, Integer> data=new TreeMap<>();
      for(int i=2; i<parts.size(); i+=2)
      {
        String item = parts.get(i);
        int num = Integer.parseInt(parts.get(i+1));
        data.put(item, num);
      }
      if (checkMatch(data))
      {
        System.out.println("Part 1 - Match on sue: " + sue);
      }
      if (checkMatch2(data))
      {
        System.out.println("Part 2 - " + sue);
      }

      sues.put(sue, data);
    }


  }

  public boolean checkMatch2(TreeMap<String, Integer> sue)
  {
    for(String item : card.keySet())
    {
      int n = card.get(item);
      if (sue.containsKey(item))
      {
        if ((item.equals("cats")) || (item.equals("trees")))
        {
          if (n >= sue.get(item)) return false;

        }
        else if ((item.equals("pomeranians")) || (item.equals("goldfish")))
        {
          if (n <= sue.get(item)) return false;

        }
        else
        {

          if (n != sue.get(item)) return false;
        }
      }


    }
    return true;

  }


  public boolean checkMatch(TreeMap<String, Integer> sue)
  {
    for(String item : card.keySet())
    {
      int n = card.get(item);
      if (sue.containsKey(item))
      {
          if (n != sue.get(item)) return false;
      }


    }
    return true;

  }

}
