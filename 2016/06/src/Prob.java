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

  Map2D<Character> map = new Map2D<Character>('.');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map,scan);

    String msg="";
    String msg2="";
    for(int x=0; x<=map.high_x; x++)
    {
      char m = getHighest(getColCount(x));
      msg+=m;

      msg2 += getLowest(getColCount(x));

    }
    System.out.println("Part 1: " + msg);
    System.out.println("Part 2: " + msg2);


  }

  public TreeMap<Character, Integer> getColCount(int x)
  {
    TreeMap<Character, Integer> c=new TreeMap<>();
    for(int y=0; y<=map.high_y; y++)
    {
      char v = map.get(x,y);
      if (!c.containsKey(v)) c.put(v,0);
      c.put(v, c.get(v) + 1);


    }
    return c;
  }

  public char getHighest(TreeMap<Character, Integer> in)
  {
    int high_c=0;
    char high_z='.';

    for(char z : in.keySet())
    {
      int c=in.get(z);
      if (c > high_c)
      {
        high_c =c;
        high_z = z;
      }

    }
    return high_z;
  }
  public char getLowest(TreeMap<Character, Integer> in)
  {
    int high_c=100000;
    char high_z='.';

    for(char z : in.keySet())
    {
      int c=in.get(z);
      if (c < high_c)
      {
        high_c =c;
        high_z = z;
      }

    }
    return high_z;
  }


}
