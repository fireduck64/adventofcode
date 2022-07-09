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
  int width=25;
  int height=6;
  ArrayList<Map2D<Character> > layers = new ArrayList<>();

  public Prob(Scanner scan)
  {
    
    String line = scan.nextLine();
    int pos = 0;
    Map2D<Character> view=new Map2D<>('.');

    while(pos < line.length())
    {
      Map2D<Character> m = new Map2D<>('.');
      for(int j=0; j<height; j++)
      for(int i=0; i<width; i++)
      {
        char z = line.charAt(pos);
        if (z!='2')
        {
          if (view.get(i,j) == '.')
          {
            if (z=='0') view.set(i,j,' ');
            else view.set(i,j,'#');
          }
        }
        m.set(new Point(i,j), z);
        pos++;
      }
      layers.add(m);
    }
    long lowest_z=1000;
    long p1=0;
    for(Map2D<Character> m : layers)
    {
      long z=m.getCounts().get('0');
      if (z < lowest_z)
      {
        lowest_z=z;
        p1 = m.getCounts().get('1') * m.getCounts().get('2');

      }

    }
    System.out.println("Part 1: " + p1);
    view.print();

  }

}
