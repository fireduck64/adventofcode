import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  TreeSet<Point> junctions;

  TreeMap<Double, List<Point> > distances;

  TreeMap<Point, TreeSet<Point> > conns;

  public String Part1(Scanner scan)
    throws Exception
  {
    junctions = new TreeSet<Point>();
    distances = new TreeMap<>();
    conns = new TreeMap<>();
    while(scan.hasNextLine())
    {
      List<Long> lst = Tok.enl(scan.nextLine(), ",");
      junctions.add(new Point(lst.get(0), lst.get(1), lst.get(2)));

    }

    ArrayList<Point> plst = new ArrayList<>();
    plst.addAll(junctions);

    for(int i=0; i<plst.size(); i++)
    for(int j=i+1; j<plst.size(); j++)
    {
      Point a = plst.get(i);
      Point b = plst.get(j);
      double dist = a.getDist2(b) + rnd.nextDouble();

      distances.put(dist, ImmutableList.of(a,b));
    }

    int count = 1000;
    if (junctions.size() == 20) count = 10;
    for(int i=0; i<count; i++)
    {
      List<Point> lst = distances.pollFirstEntry().getValue();
      Point a = lst.get(0);
      Point b = lst.get(1);
      connect(a,b);
      connect(b,a);
    }
    marked = new TreeSet<>();


    LinkedList<Long> sizes = new LinkedList<>();
    for(Point p : plst)
    {
      if (!marked.contains(p))
      {
        long c1 = marked.size();
        Search.search(new FF(p));

        long c2 = marked.size();
        long  n = c2 - c1;

        sizes.add(n);

      }

    }
    Collections.sort(sizes);
    long p1 = 1;
    for(int i =0 ;i<3; i++)
    {
      p1 = p1 * sizes.pollLast();
    }


		return "" + p1;
  }

  TreeSet<Point> marked;

  public void connect(Point a, Point b)
  {
    if (!conns.containsKey(a)) conns.put(a, new TreeSet<>());
    conns.get(a).add(b);

  }

  public class FF extends Flood
  {
    Point loc;
    public FF(Point a)
    {
      this.loc = a;
    }

    public String toString()
    { 
      return loc.toString();
    }

    public List<State> next()
    {
      marked.add(loc);
      LinkedList<State> lst = new LinkedList<>();

      if (conns.containsKey(loc))
      {
        for(Point p : conns.get(loc))
        {
          lst.add(new FF(p));
        }
      }

      return lst;

    }


  }

  public String Part2(Scanner scan)
    throws Exception
  {
    junctions = new TreeSet<Point>();
    distances = new TreeMap<>();
    conns = new TreeMap<>();
    while(scan.hasNextLine())
    {
      List<Long> lst = Tok.enl(scan.nextLine(), ",");
      junctions.add(new Point(lst.get(0), lst.get(1), lst.get(2)));

    }

    ArrayList<Point> plst = new ArrayList<>();
    plst.addAll(junctions);

    for(int i=0; i<plst.size(); i++)
    for(int j=i+1; j<plst.size(); j++)
    {
      Point a = plst.get(i);
      Point b = plst.get(j);
      double dist = a.getDist2(b) + rnd.nextDouble();

      distances.put(dist, ImmutableList.of(a,b));
    }

    marked = new TreeSet<>();

    {
      List<Point> lst = distances.pollFirstEntry().getValue();
      Point a = lst.get(0);
      Point b = lst.get(1);
      connect(a,b);
      connect(b,a);
      

      Search.search(new FF(a));
    }
    distances.clear();

    {

    }

    long p2 = 0;
    while(marked.size() < plst.size())
    {
      System.out.println(marked.size());
      distances.clear();

      for(Point a : marked)
      for(Point b : plst)
      {
        if (!marked.contains(b))
        {
          double dist = a.getDist2(b) + rnd.nextDouble();
          distances.put(dist, ImmutableList.of(a,b));
        }
      }
      {
        List<Point> lst = distances.pollFirstEntry().getValue();
        Point a = lst.get(0);
        Point b = lst.get(1);
        connect(a,b);
        connect(b,a);
        //Search.search(new FF(a));
        marked.add(b);
        marked.add(a);
        p2 = a.x * b.x;
      }

    }


		return ""+p2;

  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
