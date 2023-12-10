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
  Map2D<Integer> main_pipe = new Map2D<Integer>(-1);

  Map2D<Character> zoom = new Map2D<Character>(' ');
  public Prob(Scanner scan)
  {

    MapLoad.loadMap(input, scan);

    Point start = null;
    for(Point p : input.getAllPoints())
    {
      if (input.get(p) == 'S') start = p;
    }


    input.print();


    for(Point p : input.getAllPoints())
    {
      main_pipe.set(p, -2);
    }
    
    Search.search(new SS(start, 0));

    int p1=0;
    for(Point p : main_pipe.getAllPoints())
    {
      p1 = Math.max(p1, main_pipe.get(p));
    }
    System.out.println("Part 1: " + p1);


    
    // So I can WTF is going on
    Map2D<Character> picture = new Map2D<Character>('.');

    // Fill in zoom map with dots
    for(Point p : input.getAllPoints())
    {
      Point z = p.mult(2);
      for(Point q : zoom.getAdj(z, true))
      {
        zoom.set(q, '.');
        
      }
      zoom.set(z, '.');

    }

    // Ok, the basic idea here is that when we flood fill from outside, the pipes that don't connect
    // don't actually touch.
    // Example:
    // .||.
    // .||.
    // -JL.
    // ....
    // The flood fill can infiltrate between then J and L, since they don't connect.
    // So we zoom this in to draw it as:
    // --J.L--
    // in short, we expand the space between things and fill in pipes as is correct.
   

    for(Point p : main_pipe.getAllPoints())
    {
      if (main_pipe.get(p) >= 0)
      {
        picture.set(p, input.get(p));

        // Set the zoomed in map.  Connect the pipes on that bad boy.
        zoom.set(p.x*2, p.y*2, input.get(p));
        for(Point q : getConn(input.get(p), p))
        {
          zoom.set(p.x*2 + q.x, p.y*2+q.y, '*');
        }

      }
      if (main_pipe.get(p) == -2)
      {
        picture.set(p, 'I');
      } 
    }

    
    // Flood fill the zoom map
    fillOutside();

    picture.print();
    zoom.print();

    // Count dots remaining in zoom map if they are even (meaning real points)
    long p2=0;
    for(Point p : zoom.getAllPoints())
    {
      if (p.x % 2 ==0)
      if (p.y % 2 ==0)
      if (zoom.get(p) == '.')
      p2++;

    }
    System.out.println("PArt2 : " + p2);
    

  }
  public void fillOutside()
  {
    TreeSet<Point> next = new TreeSet<>();

    for(Point p : zoom.getAllPoints())
    {
      int edge = 0;
      if (p.x == zoom.high_x) edge++;
      if (p.x == zoom.low_x) edge++;
      if (p.y == zoom.low_y) edge++;
      if (p.y == zoom.high_y) edge++;


      if (edge > 0) if (zoom.get(p) == '.')
      next.add(p);

    }

    while(next.size() > 0)
    { 

      Point loc = next.pollFirst();

      if (zoom.get(loc) =='.')
      {
        zoom.set(loc, 'o');

        next.addAll( zoom.getAdj(loc, true));
      }
    }

  }

  public class SS extends State
  {
    int cost;
    Point loc;

    public SS(Point loc, int cost)
    {
      this.loc = loc;
      this.cost = cost;
    }
  	public double getCost()
    {
      return cost;

    }
  	public List<State> next()
    {
      main_pipe.set(loc, cost);
        char z = input.get(loc);

        LinkedList<State> lst = new LinkedList<>();

        for(Point p : getConn(z, loc))
        {
          lst.add(new SS(loc.add(p), cost+1));

        }
        return lst;

    }
  	public String toString()
    {
      return loc.toString();
    }
		

  	public boolean isTerm()
    {
      return false;
    }
 

  }

  public TreeMap<Point, Integer> flood(Point start)
  {
    TreeMap<Point, Integer> next = new TreeMap<>();
    next.put(start, 0);

    TreeMap<Point, Integer> out = new TreeMap<>();

    while(next.size() > 0)
    {
      Map.Entry<Point, Integer> me = next.pollFirstEntry();
      Point loc = me.getKey();
      int cost = me.getValue();

      if (!out.containsKey(loc))
      {
        out.put(loc, cost);

        char z = input.get(loc);

        for(Point p : getConn(z, loc))
        {
          next.put(loc.add(p), cost+1);

        }
      }
    }

    return out;


  }

  public List<Point> getConn(char z, Point n)
  {
    LinkedList<Point> lst = new LinkedList<>();
    if (z=='S')
    {
      for(Point p : input.getAdj(n, false))
      {
        for(Point q : getConn(input.get(p),null))
        {
          if (p.add(q).equals(n))
          {
            lst.add(Nav.rev(q));
          }
        }
      }

    }
    else if (z == '|')
    {
      lst.add(Nav.getDir('N'));
      lst.add(Nav.getDir('S'));
    }
    else if (z == '-')
    {
      lst.add(Nav.getDir('W'));
      lst.add(Nav.getDir('E'));
    }
    else if (z == 'L')
    {
      lst.add(Nav.getDir('N'));
      lst.add(Nav.getDir('E'));
    }
    else if (z == 'J')
    {
      lst.add(Nav.getDir('N'));
      lst.add(Nav.getDir('W'));
    }
    else if (z == '7')
    {
      lst.add(Nav.getDir('S'));
      lst.add(Nav.getDir('W'));
    }
    else if (z == 'F')
    {
      lst.add(Nav.getDir('S'));
      lst.add(Nav.getDir('E'));
    }
    return lst;


  }

  

}
