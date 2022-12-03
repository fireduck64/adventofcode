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
    int p1_score = 0;
    int p2_score = 0;
    ArrayList<String> badge_group = new ArrayList<>();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();

      badge_group.add(line);

      String p1 = line.substring(0,line.length()/2);
      String p2 = line.substring(line.length()/2);

      if (p1.length() != p2.length()) E.er();

      Map<Character, Integer> m1 = getCounts(p1);
      Map<Character, Integer> m2 = getCounts(p2);

      for(char z : m1.keySet())
      {
        if (m2.containsKey(z))
        {
          p1_score += getVal(z);

        }
      }
      if (badge_group.size() == 3)
      {
        ArrayList<Set<Character> > az = new ArrayList<>();
        for(String l : badge_group)
        {
          az.add(getCounts(l).keySet());
        }
        for(char z : az.get(0))
        {
          if (az.get(1).contains(z))
          if (az.get(2).contains(z))
          {
            p2_score += getVal(z);
          }

        }
        
        badge_group.clear();
      } 
    }
    System.out.println("Part 1: " + p1_score);
    System.out.println("Part 2: " + p2_score);

  }
  
  int getVal(char z)
  {
    if (('a' <= z)&& (z <= 'z'))
    {
      return 1+z - 'a';
    }
    return 27+z-'A';
  }

  public TreeMap<Character, Integer> getCounts(String in)
  {
    TreeMap<Character, Integer> map = new TreeMap<>();

    for(char z : Str.stolist(in))
    {
      if (!map.containsKey(z)) map.put(z, 0);
      map.put(z, map.get(z) + 1);
    }
    return map;
  }

}
