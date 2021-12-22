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

  Map2D<Integer> pad = new Map2D<>(0);
  Map2D<Character> pad2 = new Map2D<Character>('.');

  public Prob(Scanner scan)
  {

    {
      int n=1;
      for(int j=0; j<3; j++)
      for(int i=0; i<3; i++)
      { 
        pad.set(i,j,n);
        n++;
      }
      pad.print();
    }
    List<String> input = In.lines(scan);


    {
      StringBuilder sb = new StringBuilder();
      for(String line : input)
      {
        Point p = new Point(1,1);
        for(int i=0; i<line.length(); i++)
        {
          Point d = getDir(line.charAt(i));

          Point n = p.add(d);
          if (pad.get(n)>0) p=n;
        }
        sb.append("" + pad.get(p));
        

      }
      System.out.println("Part 1: " + sb.toString());
    }

    {
      pad2.set(2, 0, '1');
      pad2.set(1, 1, '2');
      pad2.set(2, 1, '3');
      pad2.set(3, 1, '4');
      pad2.set(0, 2, '5');
      pad2.set(1, 2, '6');
      pad2.set(2, 2, '7');
      pad2.set(3, 2, '8');
      pad2.set(4, 2, '9');
      pad2.set(1, 3, 'A');
      pad2.set(2, 3, 'B');
      pad2.set(3, 3, 'C');
      pad2.set(2, 4, 'D');
      pad2.print();

    }

    {
      StringBuilder sb = new StringBuilder();
      for(String line : input)
      {
        Point p = new Point(0,2);
        for(int i=0; i<line.length(); i++)
        {
          Point d = getDir(line.charAt(i));

          Point n = p.add(d);
          if (pad2.get(n)!='.') p=n;
        }
        sb.append("" + pad2.get(p));
        

      }
      System.out.println("Part 2: " + sb.toString());
    }

 

  }

  public Point getDir(char z)
  {
    if (z =='U') return new Point(0,-1);
    if (z =='D') return new Point(0,1);
    if (z =='R') return new Point(1,0);
    if (z =='L') return new Point(-1,0);
    E.er("" + z);
    return null;
  }

}
