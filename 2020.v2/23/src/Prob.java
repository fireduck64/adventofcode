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
  int ring_max;

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();
    LinkedList<Integer> start_list=new LinkedList<>();

    for(int i=0; i<line.length(); i++)
    {
      char z = line.charAt(i);
      int v = Integer.parseInt("" + z);
      ring_max = Math.max(ring_max, v);
      start_list.add(v);

    }
    System.out.println(start_list);

    { // part1

      CircleList<Integer> cl = new CircleList(start_list, true);
      CircleList<Integer>.ListRecord current = cl.getFirst();


      for(int i=0; i<100; i++)
      {
        current = move(cl, current);
      }

      System.out.println("Part 1: " + getAfter(1, cl));
    }

    { // part 2

      int c = 10;
      while(start_list.size() < 1000000)
      {
        start_list.add(c);
        ring_max = Math.max(ring_max, c);
        c++;
      }
      
      CircleList<Integer> cl = new CircleList(start_list, true);
      CircleList<Integer>.ListRecord current = cl.getFirst();
      for(int i=0; i<10000000; i++)
      {
        if (i % 1000000 == 0) System.out.println(".." + i); 
        current = move(cl, current);
      }
      long p2a = cl.find(1).next.v;
      long p2b = cl.find(1).next.next.v;
      System.out.println(p2a);
      System.out.println(p2b);
      long p2 = p2a * p2b;
      System.out.println("Part 2: " + p2);



    }

  }

  public String getAfter(int n, CircleList<Integer> cl)
  {
    CircleList<Integer>.ListRecord c = cl.find(n);
    c = c.next;
    String s = "";
    while(c.v != n)
    {
      s+= "" + c.v;
      c = c.next;
    }
    return s;

  }
  public CircleList<Integer>.ListRecord move(CircleList<Integer> cl, CircleList<Integer>.ListRecord current)
  {
    LinkedList<Integer> grab = new LinkedList<>();

    for(int i=0; i<3; i++)
    {
      int c = current.removeNext();
      grab.add(c);
    }
    int target = current.v - 1;
    while(cl.find(target) == null)
    {
      if (target == 0)
      {
        target=ring_max;
      }
      else
      {
        target--;
      }
    }

    CircleList<Integer>.ListRecord tr = cl.find(target);
    while(grab.size() > 0)
    {
      tr.insertNext(grab.pollLast());
    }

    return current.next;

  }

}
