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

  Map2D<Character> lit = new Map2D<Character>(' ');



  public Prob(Scanner scan)
  {

    MapLoad.loadMap(input, scan);

    // Mangling my search to never terminate
    // but paint another map as it goes
    // basically, using my A* code to do a flood fill
    Search.search(new SS(new Point(0,0), Nav.E));

    long p1 = lit.getCounts().get('#');
    System.out.println("Part 1: " + p1);


    long p2 = 0;

    for(long x = input.low_x; x<=input.high_x; x++)
    {
      lit = new Map2D<Character>(' ');
      Search.search(new SS(new Point(x, 0), Nav.S));
      p2 = Math.max(p2, lit.getCounts().get('#'));

      lit = new Map2D<Character>(' ');
      Search.search(new SS(new Point(x, input.high_y), Nav.N));
      p2 = Math.max(p2, lit.getCounts().get('#'));
    }

    for(long y = input.low_y; y<=input.high_y; y++)
    {
      lit = new Map2D<Character>(' ');
      Search.search(new SS(new Point(0, y), Nav.E));
      p2 = Math.max(p2, lit.getCounts().get('#'));

      lit = new Map2D<Character>(' ');
      Search.search(new SS(new Point(input.high_x, y), Nav.W));
      p2 = Math.max(p2, lit.getCounts().get('#'));
    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);


  }


  public class SS extends Flood
  {
    Point loc;
    Point dir;
    //This means the beam is entering this loc traveling in dir
    public SS(Point loc, Point dir)
    {
      this.loc = loc;
      this.dir = dir;
    }

    public String toString()
    {
      return "" + loc.toString() + "/"+ dir.toString();
    }

    public List<State> next()
    {
      char z = input.get(loc);
      LinkedList<State> lst = new LinkedList<>();
      if (z == ' ') return lst;

      lit.set(loc, '#');

      if (z == '.')
      {
        Point q = loc.add(dir);
        lst.add(new SS(q, dir));
      }
      if (z == '-')
      {
        if (Math.abs(dir.x) > 0)
        { // point on
          lst.add(new SS(loc.add(dir), dir));
        }
        else
        {
          lst.add(new SS(loc.add(Nav.E), Nav.E));
          lst.add(new SS(loc.add(Nav.W), Nav.W));
        }
      }
      if (z == '|')
      {
        if (Math.abs(dir.y) > 0)
        { // point on
          lst.add(new SS(loc.add(dir), dir));
        }
        else
        {
          lst.add(new SS(loc.add(Nav.N), Nav.N));
          lst.add(new SS(loc.add(Nav.S), Nav.S));
        }
      }

      if (z == '/')
      {
        Point out = null;
        if (dir.equals(Nav.N)) out = Nav.E;
        if (dir.equals(Nav.S)) out = Nav.W;
        if (dir.equals(Nav.E)) out = Nav.N;
        if (dir.equals(Nav.W)) out = Nav.S;
        lst.add(new SS(loc.add(out), out));
      }
      if (z == '\\')
      {
        Point out = null;
        if (dir.equals(Nav.N)) out = Nav.W;
        if (dir.equals(Nav.S)) out = Nav.E;
        if (dir.equals(Nav.E)) out = Nav.S;
        if (dir.equals(Nav.W)) out = Nav.N;
        lst.add(new SS(loc.add(out), out));
      }
      return lst;

    }

  }

}
