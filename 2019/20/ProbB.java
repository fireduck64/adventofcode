import java.io.FileInputStream;
import java.util.*;

public class ProbB
{

  public static void main(String args[]) throws Exception
  {
    new ProbB(new Scanner(new FileInputStream(args[0])));
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
    public double getCost(){return cost;}
    public boolean isTerm()
    {
      return (dest.contains(p));
    }

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
            Point np = new Point(nx,ny, p.z);
            if (portals.containsKey(np))
            {
              np = portals.get(np);
            }
            State ns = new PS(np, cost+1);
						lst.add(ns);

          }

        }
 
			return lst;
		}
  }

  public ProbB(Scanner scan)
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

		State ans = Search.search(new PS(start,0));

		System.out.println(ans);

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

    boolean src_edge = isEdge(src);

    List<Point> lst = getPortalPoints(src);

    if (src_edge)
    { 
      // We can step down from high z
      for(int i=1; i<1000; i++)
      {
        for(Point p : lst)
        portals.put( new Point(p.x, p.y, i), new Point(walk.x, walk.y, i-1));

      }
    }
    else
    {
      // We can step up to higher z
      for(int i=0; i<1000; i++)
      {
        for(Point p : lst)
        portals.put( new Point(p.x, p.y, i), new Point(walk.x, walk.y, i+1));

      }

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

    char v = map.get(x,y);
    if (v == '.') return true;
    if (isLetter(x,y)) return true;

    return false;

  }

  public boolean isEdge(Point p)
  {
    if (Math.abs(p.x - map.low_x) <= 2) return true;
    if (Math.abs(p.y - map.low_y) <= 2) return true;
    if (Math.abs(p.y - map.high_y) <= 2) return true;
    if (Math.abs(p.x - map.high_x) <= 2) return true;
    return false;
  }

}
