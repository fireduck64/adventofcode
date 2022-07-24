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

  Map2D<Character> map=new Map2D<Character>(' ');
  boolean mode_part2=false;

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();

    SS sol = (SS)Search.search(new SS(new Point(0,0), 0, new IntComp(line)));
    System.out.println("Part 1: " + sol.moves);

    // Search to fill in rest of map without looking for termination condition
    mode_part2=true;
    Search.search(new SS(new Point(0,0), 0, new IntComp(line)));
    map.print();
    System.out.println("Part 2: " + part2());

  }

  public int part2()
  {
    Map2D<Character> in=map.copy();
    int steps=0;
    while(in.getCounts().containsKey('.'))
    {
      Map2D<Character> out = in.copy();
      steps++;

      for(Point p : in.getAllPoints())
      {
        if (in.get(p)=='O')
        {
          for(Point q : in.getAdj(p, false))
          {
            if (in.get(q)=='.') out.set(q, 'O');
          }
        }
      }

      in=out;

    }
    return steps;

  }


  public class SS extends State
  {
    Point loc;
    int moves;
    IntComp comp;
    boolean found=false;

    public SS(Point loc, int moves, IntComp comp)
    {
      this.loc = loc;
      this.moves = moves;
      this.comp = comp;
    }


    public SS runMove(int move)
    {
      Point dir = null;
      if (move == 1) dir = new Point(0,-1);
      if (move == 2) dir = new Point(0,1);
      if (move == 3) dir = new Point(1,0);
      if (move == 4) dir = new Point(-1,0);


      SS n=new SS(loc, moves+1, comp.copy());


      n.comp.input.add((long)move);
      n.comp.exec();
      long res = n.comp.output.poll();
      if (res == 0)
      {
        Point wall = loc.add(dir);
        map.set(wall, '#');

      }
      else if (res==1)
      {
        n.loc = loc.add(dir);
        map.set(n.loc, '.');
      }
      else if (res==2)
      {
        n.loc = loc.add(dir);
        map.set(n.loc, 'O');
        n.found=true;
      }
      return n;
    }
    public boolean isTerm()
    {
      if (mode_part2) return false;

      return found;
    }
    @Override
    public String toString()
    {
      return loc.toString();
    }

    public double getCost()
    {
      return moves;
    }

    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      for(int m=1; m<=4; m++)
      {
        lst.add(runMove(m));
      }
      return lst;
    }

  }

}
