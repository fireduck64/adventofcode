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


  LinkedList<Long> cups = new LinkedList<>();

  CircleList<Long> circle;
  CircleList<Long>.ListRecord current_cup;

  long max_cup = 0;

  public Prob(Scanner scan)
  {
    String in = scan.next();

    for(int i=0; i<in.length(); i++)
    {
      long v=  Long.parseLong("" + in.charAt(i));
      max_cup = Math.max(v, max_cup);
      cups.add( Long.parseLong("" + in.charAt(i)));
    }



    
    // Part 2
    /*while(cups.size() < 1000000)
    {
      long v = max_cup+1;
      max_cup = Math.max(v, max_cup);
      cups.add(v);
    }*/

    System.out.println("Max Cup: " + max_cup);

    circle = new CircleList<Long>(cups, true);
    current_cup = circle.getFirst();
    


    // part 1
    if (cups.size() < 100)
    {
      for(int r=0; r<100; r++)
      {
       round();
      }
      CircleList<Long>.ListRecord p = circle.find(1L).next;
      for(int i=0; i<8; i++)
       {
        System.out.print(p.v);
        p = p.next;
      }
      System.out.println();
    }
    else
    {

      for(int i=0; i<10000000; i++)
      {
        if (i % 100000 == 0) System.out.println(i);
        round();
      }

      long v1 = circle.find(1L).next.v;
      long v2 = circle.find(1L).next.next.v;
      System.out.println("eggs: " + v1 + " " + v2);
      long p2 = v1 * v2;
      System.out.println(p2);
    }

  }

  public void round()
  {
    TreeSet<Long> s_set = new TreeSet<>();
    LinkedList<Long> s_lst = new LinkedList<>();

    for(int i=0; i<3; i++)
    {
      long v = current_cup.removeNext();
      s_set.add(v);
      s_lst.add(v);
    }

    long cur_val = current_cup.v;
    long next = cur_val - 1;
    if (next < 1) next = max_cup;
    while (s_set.contains(next))
    {
      next--;
      if (next < 1) next = max_cup;
    }

    CircleList<Long>.ListRecord dest = circle.find(next);

    for(int i=2; i>=0; i--)
    {
      dest.insertNext( s_lst.get(i) );
    }

    current_cup = current_cup.next;
  }



}
