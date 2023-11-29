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
    Map3D<Character> input = new Map3D<Character>('.');

    MapLoad.loadMap(input, scan);

    { // part 1

      Map3D<Character> m = input;
      for(int i=0; i<6; i++) m = cycle(m);
      System.out.println(m.getCounts());

    }

    { // part 2
      Map4D<Character> m = new Map4D<Character>('.');
      for(Point p : input.getAllPoints())
      {
        m.set(p, input.get(p));
      }
      for(int i=0; i<6; i++) m = cycle2(m);
      System.out.println(m.getCounts());

    }


  }

  public Map3D<Character> cycle(Map3D<Character> input)
  {
    Map3D<Character> out = new Map3D<Character>('.');


    for(long x=input.low_x -1; x<=input.high_x+1; x++)
    for(long y=input.low_y -1; y<=input.high_y+1; y++)
    for(long z=input.low_z -1; z<=input.high_z+1; z++)
    {
      Point p = new Point(x,y,z);
      int filled=0;
      for(Point q : input.getAdj(p, true))
      {
        if (input.get(q)=='#') filled++;
      }
      char start = input.get(p);
      if (start == '#')
      {
        if ((filled >=2) && (filled <= 3)) out.set(p, '#');

      }
      else
      {
        if (filled == 3) out.set(p, '#');

      }

    }


    return out;

  }

  public Map4D<Character> cycle2(Map4D<Character> input)
  {
    Map4D<Character> out = new Map4D<Character>('.');

    for(long x=input.low_x -1; x<=input.high_x+1; x++)
    for(long y=input.low_y -1; y<=input.high_y+1; y++)
    for(long z=input.low_z -1; z<=input.high_z+1; z++)
    for(long w=input.low_w -1; w<=input.high_w+1; w++)
    {
      Point p = new Point(x,y,z,w);
      int filled=0;
      for(Point q : input.getAdj(p, true))
      {
        if (input.get(q)=='#') filled++;
      }
      char start = input.get(p);
      if (start == '#')
      {
        if ((filled >=2) && (filled <= 3)) out.set(p, '#');

      }
      else
      {
        if (filled == 3) out.set(p, '#');

      }

    }


    return out;

  }

}
