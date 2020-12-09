import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  ArrayList<Long> list = new ArrayList<Long>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLong())
    { 
      list.add(scan.nextLong());
    }

    long bad = 0;
    for(int i=25; i<list.size(); i++)
    {
      if (!scanPreamble(i))
      {
        bad =  list.get(i);
        System.out.println("Part 1: " + list.get(i));
        break;
      }

    }

    for(int i=0; i<list.size(); i++)
    {
      long sum =0;
      TreeSet<Long> group = new TreeSet<>();
      for(int j=i; j<list.size(); j++)
      {
        sum+=list.get(j);
        group.add(list.get(j));
        if (sum==bad)
        {
          long s = group.first() + group.last();
          System.out.println(group);
          System.out.println("Part 2: " + s);
          return;

        }
        if (sum > bad) break;



      }
    }

  }

  public boolean scanPreamble(int idx)
  {
    long v = list.get(idx);
    for(int a=idx-25; a<idx; a++)
    for(int b=idx-25; b<idx; b++)
    {
      if (a!=b)
      if (list.get(a)+list.get(b) == v) return true;

    }
    return false;
  }

}


