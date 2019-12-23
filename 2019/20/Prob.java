import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Character> map=new Map2D<>('?');

  HashMap<Point, Point> portals = new HashMap<>();

  HashMap<Character, HashSet<Point> > letter_map= new HashMap<>();

  Point start = null;
  HashSet<Point> dest = new HashSet<>();

  TreeMap<Double, State> queue = new TreeMap<>();
  HashSet<Point> visited= new HashSet<>();

  public class PS extends State
  {
    Point p;
    int cost=0;

    public PS(Point p, int cost)
    {
      this.p = p;
      this.cost = cost;
    }
    public String toString()
    {
      return String.format("%s - %d", p.toString(), cost);
    }
    public String getHash()
    {
      return p.toString();
    }
    public double getCost() {return cost;}

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

        for(int i=-1; i<=1; i++)
        for(int j=-1; j<=1; j++)
        if (Math.abs(i) + Math.abs(j) == 1)
        {
          long nx = p.x+i;
          long ny = p.y+j;
          if (canStep(nx,ny))
          {
            Point np = new Point(nx,ny);
            if (portals.containsKey(np))
            {
              np = portals.get(np);
            }
            PS ns = new PS(np, cost+1);
            lst.add(ns);

          }

        }
        return lst;
    }
    public boolean isTerm()
    {
      return (dest.contains(p));
    }

  }

  public Prob(Scanner scan)
  {
    int y = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      for(int x=0; x<line.length(); x++ )
      {
        char z = line.charAt(x);
        map.set(x,y,z);

        if (('A' <= z) && (z <= 'Z'))
        {
          if (!letter_map.containsKey(z))
          {
            letter_map.put(z, new HashSet<Point>());
          }
          letter_map.get(z).add(new Point(x,y));
        }

      }
      y++;
    }
    mapPortals();

    State res = Search.search( new PS(start,0));

    System.out.println(res);

  }

  public void mapPortals()
  {

    for(Point p : map.getAllPoints())
    {
      if (isLetter(p.x, p.y))
      {
        char a = map.get(p.x, p.y);
    
        String name = getPortalName(p);
        if (name != null)
        {
          boolean found = false;
          for(Point q : letter_map.get(a))
          {
            if (!p.equals(q))
            {
              String n2 = getPortalName(q);
              if ((n2 != null) && (name.equals(n2)))
              {
                //System.out.println("" + name + " from " + p + " to " + q);
                markPortal(p,q);
                found=true;
              }
            }
          }
          if (!found)
          {
            System.out.println("No portal for: " + name);
            if (name.equals("AA"))
            {
              start = getWalkway(p);
              
            }
            if (name.equals("ZZ"))
            {
              dest.add(getWalkway(p));

            }
          }
        }


      }
    }

  }

  public String getPortalName(Point p)
  {
    char a = map.get(p.x, p.y);
    char b = 0;
    if (isLetter(p.x+1, p.y))
    {
      b = map.get(p.x+1, p.y);
    }
    else if (isLetter(p.x, p.y+1))
    {
      b = map.get(p.x, p.y+1);
    }
    else
    {
      return null;
    }

    return "" + a + b;

  }

  public List<Point> getPortalPoints(Point p)
  {
    LinkedList<Point> lst = new LinkedList<>();

    for(int i=-1; i<=1; i++)
    for(int j=-1; j<=1; j++)
    if (Math.abs(i) + Math.abs(j) <= 1)
    {
      if (isLetter(p.x+i, p.y+j)) lst.add(new Point(p.x+i, p.y+j));
    }

    if (lst.size() != 2)
    {
      System.out.println("Weird portal list for : " + p + " " + lst);
    }
    return lst;
  }

  // Return walkable point near portal at 'p'
  public Point getWalkway(Point p)
  {
    for(Point w : getPortalPoints(p))
    {
      //System.out.println("Portal : " + p + " has " + w + " " + map.get(w.x, w.y));
      
      for(int i=-1; i<=1; i++)
      for(int j=-1; j<=1; j++)
      if (Math.abs(i) + Math.abs(j) == 1)
      {
        long x = w.x+i;
        long y = w.y+j;
        //System.out.println("  " + new Point(x,y) + " is " + map.get(x,y));
        
        if (map.get(x, y) == '.') return new Point(x, y);
      }

    }
    throw new RuntimeException("Unable to find walkway for: " + p);
    //return null;

  }

  // Mark both letters of portal 'src' as going to portal 'dst'
  public void markPortal(Point src, Point dst)
  {
    Point walk = getWalkway(dst);

    for(Point p : getPortalPoints(src))
    {
      portals.put(p, walk);
    }

  }

  public boolean isLetter(long x, long y)
  {
    char z = map.get(x,y);
    if (('A' <= z) && (z <= 'Z'))
    {
      return true;
    }
    return false;

  }

  public boolean canStep(long x, long y)
  { 
    if (isLetter(x,y)) return true;
    if (map.get(x,y) == '.') return true;

    return false;

  }

}
