import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  Map2D<Character> readT(Scanner scan)
  {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<7; i++)
    {
      sb.append(scan.nextLine());
      sb.append('\n');

    }
    Map2D<Character> m = new Map2D<Character>(' ');
    MapLoad.loadMap(m, new Scanner(sb.toString()));
    if (scan.hasNextLine()) scan.nextLine();

    return m;

  }

  public void readInput(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      Map2D<Character> m = readT(scan);

      ArrayList<Integer> code = getCode(m);
      if (isLock(m))
      {
        locks.add(code);

      }
      else
      {
        keys.add(code);
      }

    }

  }

  ArrayList<ArrayList<Integer> > locks=new ArrayList<>();
  ArrayList<ArrayList<Integer> > keys = new ArrayList<>();

  public boolean isLock(Map2D<Character> m)
  {
    for(int i=0; i<5; i++)
    {
      if (m.get(i, 0) == '.') return false;
    }
    return true;

  }
  public ArrayList<Integer> getCode(Map2D<Character> m)
  {
    ArrayList<Integer> lst = new ArrayList<>();
    for(int i=0; i<5; i++)
    {
      int cnt = 0;
      for(int j=0; j<7; j++)
      {
        if (m.get(i,j) == '#') cnt++;
      }
      lst.add(cnt-1);
    }
    return lst;

  }


  public String Part1(Scanner scan)
    throws Exception
  {
    readInput(scan);
    System.out.println("Locks: " + locks.size());
    System.out.println("Keys: " + keys.size());
    int cnt=0;

    for(ArrayList<Integer> lock : locks)
    for(ArrayList<Integer> key : keys)
    {
      boolean fit=true;
      for(int i=0; i<5; i++)
      {
        int t = lock.get(i) + key.get(i);
        if (t > 5) fit=false;

      }
      if (fit) cnt++;

    }
		return "" + cnt;
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
