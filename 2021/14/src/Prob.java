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


  TreeMap<String, Character> rules=new TreeMap<>();

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();
    String chain_str = line;

    while(scan.hasNextLine())
    {
      line = scan.nextLine();
      line = line.trim();
      if (line.length() > 0)
      {
        List<String> tok = Tok.en(line," ");
        String from = tok.get(0);
        char to = tok.get(2).charAt(0);
        rules.put(from, to);

      }

    }
    {//part 1

      TreeMap<Character, Long> rec_count = new TreeMap<>();
      for(int i=0; i<chain_str.length(); i++)
      {
        mapAdd(rec_count, chain_str.charAt(i), 1);
      }
      for(int i=0; i<chain_str.length()-1; i++)
      {
        mapAdd(rec_count, rec( chain_str.charAt(i), chain_str.charAt(i+1), 10));

      }
      System.out.println(rec_count);
      ArrayList<Long> counts = new ArrayList<>();
      for(long v : rec_count.values())
      {
        counts.add(v);
      }
      Collections.sort(counts);
      System.out.println("Part 1");
      System.out.println(counts.get( counts.size() - 1) - counts.get(0));
    }


    {//part 2

      TreeMap<Character, Long> rec_count = new TreeMap<>();
      for(int i=0; i<chain_str.length(); i++)
      {
        mapAdd(rec_count, chain_str.charAt(i), 1);
      }
      for(int i=0; i<chain_str.length()-1; i++)
      {
        mapAdd(rec_count, rec( chain_str.charAt(i), chain_str.charAt(i+1), 40));

      }
      System.out.println(rec_count);
      ArrayList<Long> counts = new ArrayList<>();
      for(long v : rec_count.values())
      {
        counts.add(v);
      }
      Collections.sort(counts);
      System.out.println("Part 2");
      System.out.println(counts.get( counts.size() - 1) - counts.get(0));
    }



  }


  TreeMap<String, TreeMap<Character, Long>> memo = new TreeMap<>();

  /** recursive get counts of things between a and b */
  public TreeMap<Character, Long> rec(char a, char b, int layers)
  {
    // Only add things we actually add to the map.  Assume a and b are
    // accounted for elsewhere
    TreeMap<Character, Long> out = new TreeMap<>();

    String state = "" + a + b + " " + layers;
    if (memo.containsKey(state))
    {
      return memo.get(state);
    }

    if (layers == 0) return out;

    String r = "" + a + b;
    if (!rules.containsKey(r))
    {
      return out;
    }


    char n = rules.get(r);

    // Add the one we add
    mapAdd(out, n, 1L);

    // Now we have two other groups
    mapAdd(out, rec(a,n,layers-1));
    mapAdd(out, rec(n,b,layers-1));

    memo.put(state, out);

    return out;


  }

  public void mapAdd(Map<Character, Long> m, char n, long cnt)
  {
    if (!m.containsKey(n)) m.put(n, cnt);
    else m.put(n, m.get(n) + cnt);
  }

  public void mapAdd(Map<Character, Long> m, Map<Character, Long> o)
  {
    for(Map.Entry<Character, Long> me : o.entrySet())
    {
      mapAdd(m, me.getKey(), me.getValue());
    }

  }

}
