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

  /** To find a path from a point to move the magic data */
  public class SSub extends State
  {
    SS ctx;
    Point loc;
    TreeMap<Point,Integer> deltas; // change of used.
    List<Point> path;

    public SSub(SS ctx, Point loc, TreeMap<Point, Integer> deltas, List<Point> path)
    {
      this.ctx = ctx;
      this.loc = loc;
      this.deltas = deltas;
      this.path = path;
    }

    public boolean isTerm()
    {
      return (loc.equals(ctx.magic_loc));
    }
    public String toString()
    {
      //return "ssub: " + loc +"/" + deltas.toString();
      //return "ssub: " + loc +"/" + deltas.keySet().toString();
      int d = 0;
      if (deltas.containsKey(loc)) d = deltas.get(loc);
      return "ssub: " + loc + "/" + d;
    }
    public double getCost()
    {
      return path.size()-1;
    }
    public double getEstimate()
    {
      return loc.getDistM(ctx.magic_loc);
    }
    public List<State> next()
    {
      List<State> lst = new LinkedList<State>();

      int cap = ctx.map.get(loc).free;
      if (deltas.containsKey(loc)) cap -= deltas.get(loc);

      for(Point n : ctx.map.getAdj(loc, false))
      {
        if (ctx.map.get(n) != null)
        if (!deltas.containsKey(n))
        if (ctx.map.get(n).used <= cap)
        {
          // moving n to loc
          TreeMap<Point, Integer> nd = new TreeMap<Point, Integer>();
          nd.putAll(deltas);
          int shift = ctx.map.get(n).used;
          nd.put(n, -shift);

          int loc_delta = 0;
          if (nd.containsKey(loc)) loc_delta = nd.get(loc);
          nd.put(loc, loc_delta + shift);
          ArrayList<Point> np = new ArrayList<Point>();
          np.addAll(path);
          np.add(n);
          lst.add(new SSub(ctx, n, nd, np));
        }
      }

      return lst;
    }
    private void apply(Map2D<Node> map)
    {
      for(Point p : deltas.keySet())
      {
        int d = deltas.get(p);
        map.set(p, map.get(p).addData(d));

      }

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
      //`return map.getHashState() + "/" + magic_loc; 
      // What! How can this work?
      return  magic_loc.toString(); 
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
      return magic_loc.getDistM(new Point(0,0)) * 1.0;
    }
    public boolean isTerm()
    {
      return (magic_loc.equals(new Point(0,0)));
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();


      /*for(Point pa : map.getAllPoints())
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
      }*/

      /*for(Point n : map.getAdj(magic_loc, false))
      {
        Map2D<Integer> deltas = new Map2D<Integer>(-10000);
        deltas.set(magic_loc, -map.get(magic_loc).used);
        deltas.set(n, map.get(magic_loc).used);
        lst.addAll( pushPop( deltas, n, n) );
      }*/

      List<State> sub_start=new LinkedList<>();
      for(Point n : map.getAllPoints())
      {
        if (!n.equals(magic_loc))
        {
          sub_start.add(new SSub(this, n, new TreeMap<Point, Integer>(),ImmutableList.of(n)));
        }
      }

      List<State> res_lst = Search.searchMulti( sub_start, 8);
      for(State s : res_lst)
      {
        SSub res = (SSub)s;
        if (res != null)
        {
          Map2D<Node> nm = map.copy();
          int nc = cost + (int)Math.round(res.getCost());
          res.apply(nm);

          //System.out.println("Magic: " + magic_loc);
          //System.out.println("Path: " + res.path);

          Point next_magic = res.path.get(res.path.size() - 2);
          if (magic_loc.getDistM(res.path.get(res.path.size() -1))!=0) E.er("Not magic");
          if (next_magic.getDistM(magic_loc) != 1) E.er("not adj");
          lst.add(new SS(nm, next_magic, nc));
        }
      }
    

      //System.out.println("Magic moves: " + lst.size());

      return lst;
    }

    HashSet<String> rec_visit=new HashSet<>();

    private List<State> pushPop(Map2D<Integer> deltas, Point loc, Point next_magic)
    {
      //System.out.println(deltas.getAllPoints().size());
      LinkedList<State> lst = new LinkedList<>();
      if (map.get(loc) == null) return lst;
      if (deltas.getAllPoints().size() > 50) return lst;
      if (map.get(loc).free >= deltas.get(loc))
      {  // We can fit what we need there already
        // Generate new state
        int cost_add = 0;
        Map2D<Node> nmap = map.copy();
        for(Point p : deltas.getAllPoints())
        {
          cost_add++;
          nmap.set(p, map.get(p).addData( deltas.get(p) ) );
        }
        lst.add(new SS(nmap, next_magic, cost+cost_add-1));
        //System.out.println("Found one path: " + deltas.getAllPoints().size());

        return lst;
      }
      String key = deltas.getAllPoints() + "/" + loc.toString() + "/" + next_magic.toString();
      if (rec_visit.contains(key))
      {
        return lst;
      }
      rec_visit.add(key);
      int shift = map.get(loc).used;

      for(Point n : map.getAdj(loc, false))
      {
        if (deltas.get(n) == -10000) // not on the list yet
        if (map.get(n) != null)
        if (map.get(n).size >= shift)
        {
          Map2D<Integer> nd = deltas.copy();
          // Crap from current node need to be pushed into next
          nd.set(loc, nd.get(loc) - shift);
          nd.set(n, shift);
          lst.addAll( pushPop(nd, n, next_magic) );
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
