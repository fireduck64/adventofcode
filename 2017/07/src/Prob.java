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

  // Maps X -> Y where X is held up by Y
  TreeMap<String, String> holding = new TreeMap<>();
  TreeMap<String, Long> weights = new TreeMap<>();
  TreeMap<String, TreeSet<String> > subs = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line = line.replace("(","");
      line = line.replace(")","");
      line = line.replace(",","");
      List<String> tok = Tok.en(line, " ");

      String name = tok.get(0);
      long w = Integer.parseInt(tok.get(1));
      weights.put(name, w);
      if (!subs.containsKey(name))
      {
        subs.put(name, new TreeSet<>());
      }

      for(int i = 3; i<tok.size(); i++)
      {
        String h = tok.get(i);
        holding.put(h, name);

        subs.get(name).add(h);


      }
    }

    System.out.println("Part 1:");
    System.out.println(findBottom(weights.firstKey()));

    
    String top_un=null;
    int top_depth=-1;
    ArrayList<Long> top_lst=null;

    // So we want to find the top most unbalanced node
    for(String n : weights.keySet())
    {
      ArrayList<Long> sub_w = new ArrayList<>();
      LinkedList<Long> sub_w_s = new LinkedList<>();
      for(String s : subs.get(n))
      {
        long w = getSubTreeWeight(s);
        sub_w.add(w);
        sub_w_s.add(w);
      }
      if (sub_w.size() > 1)
      {
        Collections.sort(sub_w_s);
        if (!sub_w_s.get(0).equals(sub_w_s.get(sub_w.size() - 1)))
        {
          
          int depth = findBottomDist(n);
          System.out.println(String.format("Unbalanced %d - %s - %s", depth, n, sub_w.toString()));

          if (depth > top_depth)
          {
            top_depth=depth;
            top_un = n;
            top_lst = sub_w;

          }

        }

      }

    }
    
    long mode = top_lst.get(top_lst.size() / 2);
    String n = top_un;
      for(String s : subs.get(n))
      {
        long w = getSubTreeWeight(s);
        if (w != mode)
        {
          long delta = mode - w;
          long new_w = weights.get(s) + delta;
          System.out.println("Part 2: " + new_w);

        }
      }
 


  }

  public int findBottomDist(String n)
  {
    if (holding.containsKey(n))
    {
      return findBottomDist(holding.get(n)) + 1;
    }
    return 0;
  }

  public long getSubTreeWeight(String n)
  {
    long w = weights.get(n);
    for(String s : subs.get(n))
    {
      w+=getSubTreeWeight(s);
    }
    return w;

  }



  public String findBottom(String n)
  {
    if (holding.containsKey(n))
    {
      return findBottom(holding.get(n));
    }
    return n;
  }

}
