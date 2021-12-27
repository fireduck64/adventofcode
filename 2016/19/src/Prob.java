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
  int total_elf;

  public Prob(Scanner scan)
  {
    total_elf = scan.nextInt();

    ArrayList<Integer> lst = new ArrayList<>();
    {
      for(int i=1; i<=total_elf; i++) lst.add(i);
      CircleList<Integer> cir = new CircleList<Integer>(lst, false);

      CircleList<Integer>.ListRecord cur = cir.getFirst();
      while(true)
      {
        cur.removeNext();
        cur = cur.next;
        if (cur == cur.next) break;
      }
      System.out.println("Part 1: " + cur.v);
    }



  }

}
