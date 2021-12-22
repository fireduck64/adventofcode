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
    int v_sum=0;
    for(String line : In.lines(scan))
    {
      int sector_id = Tok.ent(line.replace("[","-"),"-").get(0);
      String name = getName(line);
      String check = getCheck(line);
      

      TreeMap<Character, Integer> counts = getCounts(name);
      if (checkSum(name, check, counts))
      {
        System.out.println("-----------------------------");
        System.out.println(name + " " + check + " " + sector_id);
        System.out.println("  real");
        System.out.println(decrypt(name, sector_id));
        v_sum+=sector_id;
      }


    }
    System.out.println("Part 1: " + v_sum);

  }

  public String decrypt(String name, int sector)
  {
    StringBuilder sb = new StringBuilder();
    sector = sector % 26;
    for(int i=0; i<name.length(); i++)
    {
      if (name.charAt(i)=='-') sb.append('-');
      else
      {
        int z = name.charAt(i) - 'a';
        z = (z + sector) % 26;

        char w = (char)('a' + z);
        sb.append(w);
      }
      
      


    }

    return sb.toString();

  }

  public boolean checkSum(String name, String check, TreeMap<Character, Integer> counts)
  {
    int last_count=10000;
    char last_char='.';
    for(int ci = 0; ci<check.length(); ci++)
    {
      char z = check.charAt(ci);
      if (!counts.containsKey(z)) return false;
      int count = counts.get(z);
      if (count < last_count)
      {
        last_count=count;
        last_char=z;
      }
      else if ((count == last_count) && (last_char < z))
      {

        last_count=count;
        last_char=z;
      }
      else
      {
        return false;
      }

    }
    return true;

  }
  public TreeMap<Character, Integer> getCounts(String name)
  {
    TreeMap<Character, Integer> m = new TreeMap<>();
    for(int i=0; i<name.length(); i++)
    {
      char z = name.charAt(i);
      if (!m.containsKey(z)) m.put(z, 0);

      m.put(z, m.get(z)+1);
      
    }
    return m;

  }
  public String getName(String in)
  {
    StringBuilder sb=new StringBuilder();

    for(int i=0; i<in.length(); i++)
    {
      char z = in.charAt(i);
      if (z == '-')
      {
        sb.append(z);

      }
      else if ((z >= '0') && (z <= '9'))
      {
        return sb.toString();
      }
      else
      {
        sb.append(z);
      }

    }
    E.er();
    return sb.toString();

  }
  public String getCheck(String in)
  {
    String c = in.substring( in.indexOf('[')+1);
    c = c.replace("]","");
    return c;
  }

}
