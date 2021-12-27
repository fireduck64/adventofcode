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
  Point target=new Point(4,4);
  List<String> order = getOrder();
  Map<String, Point> dirs = getDirs(); 

  String seed;

  public Prob(Scanner scan)
  {
    seed = scan.nextLine();

    SS fin = (SS) Search.search(new SS(0, "", new Point(1,1)));
    System.out.println(fin);
    System.out.println("Part 1: " + fin.path);

    SS fin2 = (SS) SearchWorst.search(new SS(0, "", new Point(1,1)));
    System.out.println(fin2);
    System.out.println("Part 2: " + fin2.cost);

  }

  public class SS extends State
  {
    int cost;
    String path;
    Point loc;

    public SS(int cost, String path, Point loc)
    {
      this.cost = cost;
      this.path = path;
      this.loc = loc;
    }
    public String toString()
    {
      return path + "_" + loc;
    }
    public double getCost(){return cost; }
    public boolean isTerm()
    {
      return (loc.equals(target));
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      String hash = HUtil.getHash(seed + path);
      
      for(int i=0; i<order.size(); i++)
      {
        if (doorOpen(hash, i))
        {
          String z = order.get(i);
          Point dir = dirs.get(z);
          Point n = loc.add(dir);
          if (isValid(n))
          {
            lst.add(new SS(cost+1, path+z, n));
          }
        }

      }

      return lst;

    }

  }

  public boolean doorOpen(String hash, int idx)
  {
    char z = hash.charAt(idx);
    if ('b' <= z)
    if (z <= 'f')
      return true;
    return false;

  }

  public boolean isValid(Point p)
  {
    if (p.x < 1) return false;
    if (p.y < 1) return false;
    if (p.x > 4) return false;
    if (p.y > 4) return false;
    return true;

  }
  public List<String> getOrder()
  {
    ArrayList<String> lst = new ArrayList<>();
    lst.add("U");
    lst.add("D");
    lst.add("L");
    lst.add("R");
    return lst;
  }
  public TreeMap<String, Point> getDirs()
  {
    TreeMap<String, Point> m = new TreeMap<>();
    m.put("U", new Point(0,-1));
    m.put("D", new Point(0,1));
    m.put("L", new Point(-1,0));
    m.put("R", new Point(1,0));
    return m;
  }
}
