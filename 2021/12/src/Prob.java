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

  TreeMap<String, TreeSet<String>> map = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> lst = Tok.en(line, "-");

      String f = lst.get(0);
      String m = lst.get(1);
      if (!map.containsKey(f)) map.put(f, new TreeSet<String>());
      if (!map.containsKey(m)) map.put(m, new TreeSet<String>());

      map.get(f).add(m);
      map.get(m).add(f);
    }
    System.out.println(map);
    
    System.out.println( "Part1: " + recFindAllPaths("start", new TreeSet<String>(), "part1"));
    System.out.println( "Part2: " + recFindAllPaths("start", new TreeSet<String>(), null));
  }

  TreeMap<String, Long> memo=new TreeMap<>();

  public long recFindAllPaths(String loc, TreeSet<String> visit, String doubler)
  {
    if (loc.equals("end")) return 1;

    if (isSmall(loc))
    if (visit.contains(loc))
    {
      if (loc.equals("start")) return 0;
      if (doubler==null)
      {
        doubler=loc;
      }
      else
      {
        return 0;
      }
    }

    String state = loc + " " + visit + " " + doubler;
    //System.out.println(state);
    if (memo.containsKey(state))
    {
      return memo.get(state);
    }

    TreeSet<String> v2 = new TreeSet<>();
    v2.addAll(visit);
    v2.add(loc);


    long paths = 0;
    if (map.containsKey(loc))
    {
      for(String n : map.get(loc))
      {
        paths += recFindAllPaths(n, v2, doubler);
      }
    }
    memo.put(state, paths);
    return paths;


  }

  public boolean isSmall(String n)
  {
    return (n.toLowerCase().equals(n));
  }


  

}
