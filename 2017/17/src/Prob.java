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
    int skip = scan.nextInt();

    CircleList<Integer> cir  = new CircleList(ImmutableList.of(0), false);

    
    CircleList<Integer>.ListRecord loc = cir.getFirst();
    CircleList<Integer>.ListRecord loc0 = cir.getFirst();

    for(int i=1; i<=2017; i++)
    {
      for(int s=0; s<skip; s++) loc = loc.next;
      loc = loc.insertNext(i);
    }
    System.out.println("Part 1: " + loc.next.v);

    fancyPart2(skip);

    // Really we don't have to answer many questions about this list
    // we could do this with just a position number and a list size.
    // and when it is zero, we update the last inserted at zero number
    /*for(int i=2018; i<=50000000; i++)
    {
      if (i % 1000000 == 0) System.out.println("" + (i/1000000) + "M - " + loc0.next.v);
      for(int s=0; s<skip; s++) loc = loc.next;
      loc = loc.insertNext(i);
    }
    System.out.println("Part 2: " + loc0.next.v);*/

  }

  public void fancyPart2(int skip)
  {

    int pos=0;
    int list=1;

    int last_zero=-1;
    for(int i=1; i<=50000000; i++)
    {
      pos = (pos + skip) % list;
      list++;
      if (pos == 0)
      {
        last_zero=i;
        System.out.println("z: " + i);
      }
      pos = (pos + 1) % list;

    }
    System.out.println("Part 2: " + last_zero);


  }

}
