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

  Map2D<Character> map=new Map2D<>('#');
  Map2D<Integer> cost_map = new Map2D<>(0);

  int read_loc=1;
  String line;
  public Prob(Scanner scan)
  {
    line = scan.nextLine();
    ParseNode tree = readNode();

    if (line.charAt(read_loc) != '$')
    {
      E.er();
    }

    tree.expand(new Point(0,0));

    System.out.println(map.getPrintOut(null));

    System.out.println(getLongestPath());
    
  }

  public interface ParseNode
  {
    // Start from the given point and expand to possible other points (if any)
    public Set<Point> expand(Point p);
  }

  public class SimpleNode implements ParseNode
  {
    String word;
    public SimpleNode(String n)
    {
      word = n;
    }
    public Set<Point> expand(Point p)
    {
      long x = p.x;
      long y = p.y;
      for(int i = 0; i<word.length(); i++)
      {
        char z= word.charAt(i);
        Point delta = getDelta(z);
        map.set(x, y,'.');
        map.set(x + delta.x, y+delta.y, '-');
        map.set(x + delta.x*2, y+delta.y*2,'.');
        x = x+delta.x*2;
        y = y+delta.y*2;
      }
      return ImmutableSet.of(new Point(x,y));
    }

  }

  public Point getDelta(char z)
  {
    if (z=='N') return new Point(0,-1);
    if (z=='S') return new Point(0,1);
    if (z=='W') return new Point(-1,0);
    if (z=='E') return new Point(1,0);

    E.er(""+z);
    return new Point(0,0);
  }

  public class OptionNode implements ParseNode
  {
    List<ParseNode> opts=new LinkedList<>();

    public Set<Point> expand(Point p)
    {
      HashSet<Point> s = new HashSet<>();
      for(ParseNode pn : opts)
      {
        s.addAll(pn.expand(p));
      }
      return s;
    }
  }

  public class ListNode implements ParseNode
  {
    List<ParseNode> terms=new LinkedList<>();

    public Set<Point> expand(Point p)
    {
      HashSet<Point> in = new HashSet<>();
      in.add(p);
      for(ParseNode pn : terms)
      {
        HashSet<Point> out = new HashSet<>();
        for(Point p2 : in)
        {
          out.addAll(pn.expand(p2));
        }
        in = out;
      }
      return in;
    }
  }


  public ParseNode readNode()
  {
    ListNode ln = new ListNode();

    while(true)
    {
      char z = line.charAt(read_loc);
      if (isAlpha(z))
      {
        String term = readTerm();
        ln.terms.add(new SimpleNode(term));
      }
      else if (z == '(')
      {
        ln.terms.add(readOption());
      }
      else if (z=='$')
      {
        break;
      }
      else if (z==')')
      {
        break;
      }
      else if (z=='|')
      {
        break;
      }
      else
      {
        E.er("parse: "+z);
      }

    }
    return ln;
  }
  public ParseNode readOption()
  {
    char z = line.charAt(read_loc);
    if (z != '(') E.er();
    read_loc++;
    OptionNode on = new OptionNode();
    
    while(true)
    {
      z = line.charAt(read_loc);
      if (isAlpha(z))
      {
        on.opts.add(readNode());
      }
      else if (z=='|')
      {
        read_loc++;
        on.opts.add(readNode());
      }
      else if(z==')')
      {
        read_loc++;
        break;
      }
      else
      {
        E.er("parse: "+z);

      }

    }

    return on;

  }

  public String readTerm()
  { 
    StringBuilder sb=new StringBuilder();

    while(isAlpha(line.charAt(read_loc)))
    {
      sb.append(line.charAt(read_loc));
      read_loc++;
    }

    return sb.toString();

  }
  public boolean isAlpha(char z)
  {
    if (z>='A')
    if (z<='Z')
      return true;

    return false;
  }

  int getLongestPath()
  {
    Search.search(new SearchState(0, new Point(0,0)));  

    int high = 0;

    for(Point p : cost_map.getAllPoints())
    {
      // part 1
      //high = Math.max(high, cost_map.get(p));

      // part 2
      if (cost_map.get(p) >= 1000) high++;
    }
    return high;

  }

  public class SearchState extends State
  {
    int cost;
    Point p;
    public SearchState(int cost, Point p)
    {
      this.cost=cost;
      this.p=p;
    }
    public boolean isTerm(){return false;}

    public List<State> next()
    {
      cost_map.set(p, cost);
      LinkedList<State> ns = new LinkedList<>();
      for(char z : ImmutableList.of('N','S','E','W'))
      { 
        Point d = getDelta(z);
        if (map.get(p.x + d.x, p.y+d.y) =='-')
        {
          ns.add(new SearchState(cost+1, new Point(p.x + 2*d.x, p.y+2*d.y)));
        }

      }

      return ns;
    }

    public double getCost(){return cost;}

    public String toString()
    {
      return p.toString();
    }
  }

}
