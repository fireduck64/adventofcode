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
    long p1=0;
    long p2 = 0;
    for(String line : In.lines(scan))
    {
      LinkedList<Integer> ele = new LinkedList<>();

      ele.addAll(Tok.ent(line, " "));

      ArrayList<LinkedList<Integer> > map = new ArrayList<>();
      map.add(ele);

      List<Integer> last = ele;
      while(true)
      {
        LinkedList<Integer> next = reduce(last);
        map.add(next);
        if (allZero(next)) break;
        last = next;
      }
      p1 += fill(map);
      p2 += fillLeft(map);

    }
    System.out.println("Part1: " + p1);
    System.out.println("Part2: " + p2);

  }

  public LinkedList<Integer> reduce(List<Integer> in)
  {
    LinkedList<Integer> out = new LinkedList<>();

    for(int i=0; i<in.size() -1; i++)
    {
      int diff = in.get(i+1) - in.get(i);
      out.add(diff);
    }

    return out;

  }
  public boolean allZero(List<Integer> in)
  {
    for(int i : in)
    {
      if (i != 0) return false;
    }

    return true;

  }

  public int fill(ArrayList<LinkedList<Integer> > map)
  {
    int z = 0;
    for(int i=map.size() -1; i>=0; i--)
    {
      if (i == map.size() -1)
      {
        map.get(i).add(0);
      }
      else
      {
        z =  map.get(i).  peekLast()  + map.get(i+1).peekLast();
        map.get(i).add(z);
      }

    }
    return z;
    

  }
  public int fillLeft(ArrayList<LinkedList<Integer> > map)
  {
    int z = 0;
    for(int i=map.size() -1; i>=0; i--)
    {
      if (i == map.size() -1)
      {
        map.get(i).addFirst(0);
      }
      else
      {
        z =  map.get(i).peekFirst()  - map.get(i+1).peekFirst();
        map.get(i).addFirst(z);
      }

    }
    return z;
    

  }

}


