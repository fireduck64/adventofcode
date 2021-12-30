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
  ArrayList<Node> nodes = new ArrayList<>();

  Map2D<Node> mapo = new Map2D<Node>((Node)null);
  int action_dist = 1000;

  public Prob(Scanner scan)
    throws Exception
  {
    for(String line : In.lines(scan))
    {
      if (line.startsWith("/dev/grid"))
      {
        line = line.replace("T","");
        line = line.replace("%","");
        List<Integer> lst = Tok.ent(line, " ");
        String name = Tok.en(line, " ").get(0);

        String f = name.replace("-"," ").replace("x","").replace("y","");
        List<Integer> nums = Tok.ent(f, " ");
        Point loc = new Point(nums.get(0), nums.get(1));
        Node n = new Node(loc, lst.get(0), lst.get(1));
        if (lst.get(1) + lst.get(2) != lst.get(0)) E.er();

        nodes.add(n);
        mapo.set(n.loc, n);
      }
    }
    System.out.println("Nodes: " + nodes.size());
    int pairs =0;
    for(int i=0; i<nodes.size(); i++)
    for(int j=0; j<nodes.size(); j++)
    {
      if (i != j)
      {
        Node a = nodes.get(i);
        Node b = nodes.get(j);
        if (a.used > 0)
        if (a.used <= b.free)
        {
          pairs++;
        }
      }
    }
    System.out.println("Part 1: " + pairs);

    //System.out.println(mapo.getPrintOut());

    Node target = mapo.get(mapo.high_x, 0);
    System.out.println("Target: " + target);
    System.out.println("Searching with action dist: " + action_dist);

    SS fin=(SS)Search.searchPara(new SS(mapo.copy(), target.loc, 0));
    if (fin == null)
    {
      System.out.println("Part 2 - no results");
    }
    else
    {
      System.out.println("Part 2: " + fin.getCost());
      System.out.println(fin);
    }
    

  }

  public class SS extends State
  {
    Map2D<Node> map;
    final Point magic_loc;
    final int cost;

    public SS(Map2D<Node> map, Point magic_loc, int cost)
    {
      this.map = map;
      this.magic_loc = magic_loc;
      this.cost = cost;

    }

    public String toString()
    {
      return map.getHashState() + "/" + magic_loc; 
    }
    public double getCost()
    {
      return cost;
    }

    @Override
    public double getLean()
    {
      double total = 0.0;
      for(Point p : map.getAllPoints())
      {
        double y=p.y;
        double used = map.get(p).used;
        total += y*used;
      }
      // The more we have things away from y=0 the higher the value
      return -total;

    }
    @Override
    public double getEstimate()
    {
      return magic_loc.getDistM(new Point(0,0)) * 10.0;
    }
    public boolean isTerm()
    {
      return (magic_loc.equals(new Point(0,0)));
    }
    public List<State> next()
    {
      //System.out.println(this);
      LinkedList<State> lst = new LinkedList<>();

      for(Point pa : map.getAllPoints())
      {
      if (pa.getDistM(magic_loc) <= action_dist)
      {
        for(Point pb : map.getAdj(pa,false))
        {
          if (map.get(pb) != null)
          {
            if (map.get(pa).used > 0)
            if (map.get(pa).used <= map.get(pb).free)
            {
              int move = map.get(pa).used;
              Point next_magic = magic_loc;
              if (pa.equals(magic_loc)) 
              {
                System.out.println("Moving magic "+move +" " + pa + " to " + pb);
                next_magic=pb;
              }

              Map2D<Node> nmap = map.copy();
              nmap.set(pa, map.get(pa).addData(-move));
              nmap.set(pb, map.get(pb).addData( move));

              lst.add(new SS(nmap, next_magic, cost+1));

            }
          }
        }
      }
      }

      return lst;
    }

  }

  public class Node
  {
    final int size;
    final int used;
    final int free;
    final Point loc;

    public Node(Point loc, int size, int used)
    {
      this.loc = loc;
      this.size = size;
      this.used = used;
      this.free = size - used;
    }
    public String toString()
    {
      return "" + used;
      //return String.format("{s:%d u:%d f:%d}", size,used, free);
      //return String.format("Node{%s s:%d u:%d f:%d}", loc, size,used,free);

    }
    public Node addData(int s)
    { 
      return new Node(loc, size, used+s);
    }
  }

  public TreeSet<Point> floodOut(Point start)
  {
    TreeSet<Point> accessible=new TreeSet<>();

    LinkedList<Point> queue = new LinkedList<Point>();
    TreeSet<Point> visit = new TreeSet<>();

    while(queue.size() > 0)
    {
      Point p = queue.pollFirst();
      for(Point q : mapo.getAdj(p, false))
      {
        if (mapo.get(q) != null)
        {

        }

      }

    }

    return accessible;



  }

}
