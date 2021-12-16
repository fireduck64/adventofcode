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

  public TreeMap<Integer, TreeSet<Integer> > conns=new TreeMap<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      line = line.replace(",","");
      line = line.replace(" <->","");
      List<Integer> lst = Tok.ent(line, " ");
      int a = lst.get(0);
      for(int i=1; i<lst.size(); i++)
      {
        int b = lst.get(i);
        addConn(a,b);
        addConn(b,a);
      }

    }


    System.out.println("Part 1: " + getCount(0).size());

    TreeSet<Integer> all = new TreeSet<>();

    int groups =0;
    for(int n : conns.keySet())
    {
      if (!all.contains(n))
      {
        TreeSet<Integer> s = getCount(n);
        groups++;
        all.addAll(s);
      }
      
    }

    System.out.println("Part 2: " + groups);

  }

  public TreeSet<Integer> getCount(int n)
  {
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(n);
    TreeSet<Integer> inc = new TreeSet<>();

    while(queue.size() > 0)
    {
      int v = queue.pop();
      if (!inc.contains(v))
      {
        inc.add(v);
        queue.addAll( conns.get(v) );
      }


    }
    return inc;

  }

  public void addConn(int a, int b)
  {
    if (!conns.containsKey(a))
    {
      conns.put(a,new TreeSet<>());
    }
    conns.get(a).add(b);

  }

}
