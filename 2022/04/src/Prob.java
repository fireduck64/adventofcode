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
    int p1=0;
    int p2=0;
    for(String line : In.lines(scan))
    {
      List<Integer> l = Tok.ent(line, ",-");

      int a = l.get(0);
      int b = l.get(1);
      int c = l.get(2);
      int d = l.get(3);

      int over=0;
      if ((a <= c) && (b >= d))
      {
        over=1;
      }
      if ((c <= a) && (d >= b))
      {
        over=1;
      }

      p1+=over;

      // Write once, read never
      if ((a<= c) && (c <=b)) over=1;
      if ((a<= d) && (d <=b)) over=1;

      if ((c<= a) && (a <=d)) over=1;
      if ((c<= b) && (b <=d)) over=1;

      p2+=over;



    }

    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);
  }

}
