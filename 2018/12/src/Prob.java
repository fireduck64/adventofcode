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
  Map2D<Character> hall = new Map2D<>('.');

  TreeMap<String, Character> rules = new TreeMap<>();


  public Prob(Scanner scan)
  {
    String state = scan.nextLine();
    state = state.substring( state.indexOf(':')+1);
    state = state.trim();
    System.out.println(state);

    for(int i=0; i<state.length();i++)
    {
      hall.set(i, 0, state.charAt(i));
    }

    while(scan.hasNextLine())
    {
      String line = scan.nextLine().trim();
      if (line.length() > 0)
      {
        String rule = line.substring(0,5);

        rules.put(rule, line.charAt(9));

      }

    }
    System.out.println(rules);

    Map2D<Character> h = hall;

    HashMap<String, Long> repeat=new HashMap<>();

    long last = 0;
    long diff = 0;

    for(long i=1; i<=5000; i++)
    {
      h = makeGen(h);
      String hash = h.getPrintOut(null);

      if (repeat.containsKey(hash))
      {
        long cycle_len = i - repeat.get(hash);
        //System.out.println("Cycle len: " + cycle_len);

      }
      repeat.put(hash, i);

      System.out.println(hash);
      System.out.println(count(h));
      System.out.println(count(h) - last);
      diff = count(h) - last;
      last = count(h);
      
    }

    System.out.println("Fin: ");

    long fin = last + (50000000000L - 5000L) * diff;
    System.out.println(fin);

  }
  public long count(Map2D<Character> hall)
  {
    long sum = 0;
    for(long y = hall.low_x; y <= hall.high_x; y++)
    {
      if (hall.get(y, 0)=='#') sum+=y;
    }
    return sum;

  }

  public Map2D<Character> makeGen(Map2D<Character> hall)
  {
    Map2D<Character> out = new Map2D<Character>('.');

    for(long y = hall.low_x-5; y <= hall.high_x+5; y++)
    {
      String s = "";
      for(long j=-2; j<=2; j++)
      {
        s+=hall.get(j+y, 0);
      }
      char z = rules.get(s);
      if (z=='#')
      {
        out.set(y,0,z);
      }

    }
    return out;

  }



}
