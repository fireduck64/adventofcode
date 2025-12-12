import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();


  ArrayList<Map2D<Character> > shapes;

  ArrayList<Collection<Map2D<Character> > > all_shapes;
  ArrayList<String> challenges;
  public void loadInput(Scanner scan)
  {
    memo.clear();
    shapes=new ArrayList<>();
    challenges=new ArrayList<>();
    all_shapes = new ArrayList<>();

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.indexOf('x') > 0)
      {
        challenges.add(line);
      }
      else
      {
        int val = Integer.parseInt(line.replace(":",""));
        if (val != shapes.size()) E.er("Line: " + line + " " + shapes.size());

        Map2D<Character> m = new Map2D<Character>('.');
        MapLoad.loadMap(m, scan);
        shapes.add(m);

        all_shapes.add(getAllVersions(m));
      }
    }
  }

  public String Part1(Scanner scan)
    throws Exception
  {
    loadInput(scan);

    int p1 = 0;
    for(String line : challenges)
    {
      List<Integer> lst = Tok.ent(line.replace("x"," ").replace(":",""), " ");
      int x = lst.get(0);
      int y = lst.get(1);
      Map2D<Character> frame = new Map2D<Character>(' ');
      for(int i=0; i<x; i++)
      for(int j=0; j<y; j++)
      {
        frame.set(i,j,'.');
      }

      List<Integer> box = lst.subList(2, lst.size());


      System.out.println(line);
      if (checkPossible(x*y, box))
      {
        memo.clear();
        LinkedList<Integer> box_lst = new LinkedList<>();
        for(int b = 0; b<box.size(); b++)
        {
          int n = box.get(b);
          for(int i=0; i<n; i++) box_lst.add(b);
        }
        Collections.shuffle(box_lst);
        if (rec(frame, box_lst))
        {
          System.out.println("  solution found");
          p1++;
        }
        else
        {
          System.out.println("  long check impossible");
        }
      }
      else
      {
        System.out.println("  fast discard");
      }
    }

		return "" +p1;
  }

  HashMap<String, Boolean> memo=new HashMap<>();

  public boolean rec(Map2D<Character> frame, LinkedList<Integer> box_list)
  {
    if (box_list.size() == 0) return true;

    String key = frame.getHashState() + "/" + box_list;
    if (memo.containsKey(key)) return memo.get(key);
    //frame.print();
    //System.out.println(box_list);

    for(Point p : frame.getAllPoints())
    {
      int box = box_list.poll();
      for(Map2D<Character> b : all_shapes.get(box))
      {
        Map2D<Character> nf = place(frame, p, b);
        if (nf != null)
        {
          if (rec(nf, box_list)) 
          { 
            return true;
          }
        }
      }
      box_list.addFirst(box);

    }
    //if (rnd.nextDouble() < 0.2)
    memo.put(key,false);

    return false;


  }

  public boolean checkPossible(long area, List<Integer> box)
  {
    long used = 0;
    for(int i=0; i<box.size(); i++)
    {
      Map2D<Character> b = shapes.get(i);
      long s = b.getCounts().get('#');
      used += box.get(i) * s;

    }
    //System.out.println("Plan needs: " + used + " has " + area);
    return (used <= area);
  }

  public Collection<Map2D<Character> > getAllVersions(Map2D<Character> obj)
  {
    LinkedList<Map2D<Character> > lst = new LinkedList<>();

    HashMap<String, Map2D<Character> > map = new HashMap<>();

    lst.add(obj);
    lst.add(obj.rotateL());
    lst.add(obj.rotateL().rotateL());
    lst.add(obj.rotateL().rotateL().rotateL());
    Map2D<Character> flip = obj.flipH();

    lst.add(flip);
    lst.add(flip.rotateL());
    lst.add(flip.rotateL().rotateL());
    lst.add(flip.rotateL().rotateL().rotateL());

    for(Map2D<Character> m : lst)
    {
      map.put(m.getHashState(), m);
    }
    return map.values();
  }

  public Map2D<Character> place(Map2D<Character> frame, Point loc, Map2D<Character> obj)
  {
    for(Point p : obj.getAllPoints())
    {
      if (obj.get(p) == '#')
      {
        Point n = loc.add(p);
        if (frame.get(n) != '.') return null;
      }
    }
    Map2D<Character> out = frame.copy(); 
    for(Point p : obj.getAllPoints())
    {
      if (obj.get(p) == '#')
      {
        Point n = loc.add(p);
        out.set(n, '#');
      }
    }
    return out;
  }

  public String Part2(Scanner scan)
    throws Exception
  {
		return "";
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
