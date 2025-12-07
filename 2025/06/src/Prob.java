import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    Map2D<Long> map = new Map2D<Long>(0L);
    List<String> ops = null;

    int y = 0;
    while(true)
    {
      String line = scan.nextLine();
      List<String> tok = Tok.en(line, " ");
      List<Long> ll = Tok.enl(line, " ");

      if (ll.size() == 0)
      {
        ops = tok;
        break;

      }
      else
      {
        for(int i=0; i<ll.size(); i++)
        {
          map.set(i,y, ll.get(i));
        }
        y++;
      }
    }

    long p1=0;
    for(int i=0; i<ops.size(); i++)
    {
      String op = ops.get(i);
      if (op.equals("+"))
      {
        long n = 0;
        for(int j=0; j<y; j++) 
        {
          //System.out.print(" + " + map.get(i,j));
          n += map.get(i,j);
        }
        //System.out.println(" = " + n);
        p1 += n;
      }
      if (op.equals("*"))
      {
        long n = 1;
        for(int j=0; j<y; j++) n *= map.get(i,j);
        p1 += n;
      }


    }
		return ""+p1;
  }

  Map2D<Character> map = new Map2D<Character>(' ');
  public String Part2(Scanner scan)
    throws Exception
  {
    /*Map2D<Long> map = new Map2D<Long>(0L);
    List<String> ops = null;

    int y = 0;
    while(true)
    {
      String line = scan.nextLine();
      List<String> tok = Tok.en(line, " ");
      List<Long> ll = Tok.enl(line, " ");

      if (ll.size() == 0)
      {
        ops = tok;
        break;

      }
      else
      {
        for(int i=0; i<ll.size(); i++)
        {
          map.set(i,y, ll.get(i));
        }
        y++;
      }
    }

    long p2=0;
    for(int i=0; i<ops.size(); i++)
    {
      String op = ops.get(i);
      
      ArrayList<Long> lst = new ArrayList<>();
      for(int j=0; j<y; j++)
      {
        lst.add( map.get(i,j));
      }
      List<Long> use = getSquidVals(lst);
      if (op.equals("+"))
      {
        long n = 0;
        for(long v : use)
        {
          //System.out.print(" + " + map.get(i,j));
          n += v;
        }
        //System.out.println(" = " + n);
        p2 += n;
      }
      if (op.equals("*"))
      {
        long n = 1;
        for(long v : use)
        {
          n *= v;
        }
        p2 += n;
      }


    }
		return ""+p2;*/

    map = new Map2D<Character>(' ');

    MapLoad.loadMap(map, scan);
    // Lol
    map.rotateL().print();
    long p2=0;

    for(Point p : map.getAllPoints())
    {
      if (map.get(p) == '+')
      {
        List<Long> use = getNumbers(p);
        long n = 0;
        for(long v : use) n+=v;
        p2+=n;
      }
      if (map.get(p) == '*')
      {
        List<Long> use = getNumbers(p);
        long n = 1;
        for(long v : use) n*=v;
        p2+=n;
      }

    }

    return "" + p2;
  }

  public List<Long> getNumbers(Point operator)
  {
    long x=operator.x;

    List<Long> lst = new ArrayList<Long>();
    while(true)
    {
      Long v = getScan(new Point(x, operator.y));
      if (v == null) break;
      lst.add(v);
      x++;
    }

    //System.out.println("Point : " + operator + " " + lst);
    return lst;

  }

  public Long getScan(Point start)
  {
    String str = "";
    for(int y=0; y<start.y; y++)
    {
      if (map.get(start.x, y) != ' ') str += map.get(start.x,y);
    }
    if (str.length() == 0) return null;
    return Long.parseLong(str);

  }

  public List<Long> getSquidVals(ArrayList<Long> input)
  {
    ArrayList<Long> save =new ArrayList<>();
    save.addAll(input);
    LinkedList<Long> out = new LinkedList<>();

    while(true)
    {
      String n = "";

      boolean all_zero=true;

      for(int i=0; i< input.size(); i++)
      {
        long v = input.get(i);
        if (v > 0)
        {
          long s = v % 10;
          n = n + "" + s;
          input.set(i, v/10);
        }

      }

      if (n.equals("")) break;

      out.add(Long.parseLong(n));
    }

    System.out.println("Input: " + save + " " + out);
    
    return out;

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
