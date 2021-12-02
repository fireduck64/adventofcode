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
    MapLoad.loadMap(map, scan);

    System.out.println(map.getPrintOut(null));
    // 1 000 000 000 
    HashMap<String,Long> states=new HashMap<>();
    for(long i=0; i<1000000000 ; i++)
    {
      map = doTurn(map);
      String state = map.getHashState();
      if (states.containsKey(state))
      {
        long delta = i - states.get(state);

        // Someone who could do math could do this using
        // multiplication or something.  But why would
        // I use my brain when I can get the CPU to do
        // a few million things for me.
        while(i+delta < 1000000000) i+=delta;

        System.out.println("Repeat " + delta);
      }
      states.put(state, i);
      //System.out.println(map.getPrintOut(null));
    }
    System.out.println(map.getPrintOut(null));

    long count_tree = 0;
    long count_yard = 0;
    
    for(Point p : map.getAllPoints())
    {
      char v = map.get(p);
      if (v=='|') count_tree++;
      if (v=='#') count_yard++;
    }

    long value = count_tree * count_yard;
    System.out.println("Value: "  + value);

  }

  public Map2D<Character> doTurn(Map2D<Character> input)
  {
    Map2D<Character> output = new Map2D<Character>('.');



    for(Point p : input.getAllPoints())
    {
      char src = input.get(p);
      if (src == '.')
      {
        if (countAdj(p, '|', input) >= 3) output.set(p, '|');
        else output.set(p, '.');
      }
      if (src == '|')
      {
        if (countAdj(p, '#', input) >= 3) output.set(p, '#');
        else output.set(p, '|');
      }
      if (src == '#')
      {
        if ((countAdj(p, '#', input) >= 1) && (countAdj(p, '|', input) >= 1))
        {
          output.set(p, '#');
        }
        else
        {
          output.set(p, '.');
        }

      }
    }


    return output;

  }
  public int countAdj(Point p, char find, Map2D<Character> input)
  {
    int count = 0;

    for(int dx=-1; dx<=1; dx++)
    for(int dy=-1; dy<=1; dy++)
    {
      if (Math.abs(dx) + Math.abs(dy) > 0)
      {
        if (input.get(p.x+dx, p.y+dy) == find) count++;
      }     
    }
    return count;
  }

}
