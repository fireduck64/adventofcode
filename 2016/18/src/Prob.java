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
  Map2D<Character> mapo = new Map2D<Character>('.');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(mapo, scan);
    long high_x = mapo.high_x;

    Map2D<Character> p1 = mapo.copy();

    for(int y=1; y<40; y++)
    {
      for(long x=0; x<=high_x; x++)
      {
        setUnit(new Point(x,y), p1, p1);
      }
    }
    System.out.println("Part 1: " + p1.getCounts());

    long count = mapo.getCounts().get('.');
    Map2D<Character> p2 = mapo.copy();
    for(int y=1; y<400000; y++)
    {
      Map2D<Character> nm = new Map2D<Character>('.');
      for(long x=0; x<=high_x; x++)
      {
        setUnit(new Point(x,y), p2, nm);
      }
      count += nm.getCounts().get('.');
      p2 = nm;
    }
    System.out.println("Part 2: " + count);

  }

  public void setUnit(Point p, Map2D<Character> in, Map2D<Character> out)
  {
    String s="";
    s+=in.get(p.x-1, p.y-1);
    s+=in.get(p.x, p.y-1);
    s+=in.get(p.x+1, p.y-1);

    char z='.';

    if (s.equals("^^.")) z='^';
    if (s.equals(".^^")) z='^';
    if (s.equals("^..")) z='^';
    if (s.equals("..^")) z='^';

    out.set(p, z);

  }

}
