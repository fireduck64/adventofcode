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

    String key = scan.next();
    long v = 0;
    StringBuilder sb = new StringBuilder();
    TreeMap<Integer, Character> out=new TreeMap<>();
    while((sb.length() < 8) || (out.size() < 8))
    {
      String h = HUtil.getHash(key + v);
      if (h.startsWith("00000"))
      {
        System.out.println("Found: " + h);
        char pos = h.charAt(5);
        int pos_v = Integer.parseInt("" + pos, 16);
        if (pos_v<8)
        if (!out.containsKey(pos_v))
        {
          out.put(pos_v, h.charAt(6));
        }
        if (sb.length() < 8)
        {
          sb.append(h.charAt(5));
        }
      }

      v++;
    }
    System.out.println("Part 1: " + sb.toString());
    System.out.println("Part 2: " + Str.listtos(out.values()));

  }

}
