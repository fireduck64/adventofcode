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

  Map2D<Character> input = new Map2D<Character>(' ');
  public Prob(Scanner scan)
  {
    MapLoad.loadMap(input,scan);

    Map2D<Character> p1m = new Map2D<Character>(input);

    slide(p1m, Nav.getDir('N'));
    p1m.print();
    System.out.println("Part 1: " + getLoadN(p1m));

    Map2D<Character> p2m = new Map2D<Character>(input);

    HashMap<String, Long> states = new HashMap<>();

    long cycle_count=0;

    while(cycle_count < 1000000000L)
    {
      cycle(p2m);
      cycle_count++;

      String hash = p2m.getHashState();

      if (states.containsKey(hash))
      {
        long cycle_len = cycle_count - states.get(hash);
        while(cycle_count + cycle_len <= 1000000000L) cycle_count+=cycle_len;
      }
      else
      {
        states.put(hash, cycle_count);
      }

    }
    System.out.println("Part 2: " + getLoadN(p2m));

  }

  public void cycle(Map2D<Character> out)
  {
    slide(out, Nav.getDir('N'));
    slide(out, Nav.getDir('W'));
    slide(out, Nav.getDir('S'));
    slide(out, Nav.getDir('E'));
  }

  Map2D<Character> slide(Map2D<Character> out, Point dir)
  {
    int moves=1;
    while(moves > 0)
    {
      moves =0;
      for(Point p : out.getAllPoints())
      {
        Point n = p.add(dir);
        if (out.get(p)=='O')
        if (out.get(n)=='.')
        {
          moves++;
          out.set(n,'O');
          out.set(p,'.');
        }
      }
    }
    return out;
  }

  public long getLoadN(Map2D<Character> in)
  {
    long load=0;
    
    for(Point p : in.getAllPoints())
    {
      if (in.get(p).equals('O'))
      {
        long dist = in.high_y+1-p.y;
        load+=dist;
      }

    }
    return load;
    


  }

}
