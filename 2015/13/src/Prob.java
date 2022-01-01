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
  TreeSet<String> all = new TreeSet<>();
  TreeMap<String, TreeMap<String, Integer> > dmap = new TreeMap<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      line = line.replace("."," ");
      int n = Tok.ent(line, " ").get(0);
      List<String> parts = Tok.en(line, " ");
      String a = parts.get(0);
      String b = parts.get(parts.size()-1);
      String dir = parts.get(2);
      if (dir.equals("lose")) n=-n;
      addFace(a,b,n);
    }
    System.out.println(rec( ImmutableSet.of(), ImmutableList.of() ));
    
    all.add("me");
    System.out.println(rec( ImmutableSet.of(), ImmutableList.of() ));
  }
  public void addFace(String a, String b, int dist)
  {
    all.add(a);
    //System.out.println(a + " " + b + " " + dist);
    if (!dmap.containsKey(a)) dmap.put(a, new TreeMap<String, Integer>());

    dmap.get(a).put(b,dist);

  }
  public int getS(String a, String b)
  {
    if (a.equals("me")) return 0;
    if (b.equals("me")) return 0;
    return dmap.get(a).get(b);
  }

  public int rec(Set<String> seated, List<String> list)
  {
    if (list.size() == all.size())
    {
      // Score
      int score = 0;
      for(int i=0; i<list.size(); i++)
      {
        TreeSet<String> neigh = new TreeSet<>();
        if (i == 0)
        {
          neigh.add(list.get(1));
          neigh.add(list.get(list.size()-1));
        }
        else if (i==list.size()-1)
        {
          neigh.add(list.get(0));
          neigh.add(list.get(i-1));
        }
        else
        {
          neigh.add(list.get(i-1));
          neigh.add(list.get(i+1));
        }
        for(String n : neigh)
        {
          score += getS(list.get(i),n);

        }

      }
      return score;

    }
    else
    {
      int best = 0;
      for(String n : all)
      {
        if (!seated.contains(n))
        {
          TreeSet<String> s2 = new TreeSet<>();
          s2.addAll(seated);
          s2.add(n);
          ArrayList<String> l2 = new ArrayList<>();
          l2.addAll(list);
          l2.add(n);
          best = Math.max(best, rec(s2, l2));
        }
      }
      return best;
    }

  }

}
