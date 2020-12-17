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

  Map4D<Character> initial = new Map4D<Character>('.');

  boolean part1 = true;

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(initial, scan);

    Map4D<Character> q = initial;



    for(int i=0; i<6; i++)
    {
      q = cycle(q);
    }
    System.out.println("Part 1: " + count(q));

    part1=false;
    q = initial;

    for(int i=0; i<6; i++)
    {
      q = cycle(q);
    }
    System.out.println("Part 2: " + count(q));



  }

  public int count(Map4D<Character> q)
  {
    int cnt=0;
    for(Point p : q.getAllPoints())
    {
      if (q.get(p) == '#') cnt++;
    }
    return cnt;
  }


  public List<Point> getNeighbors(Point a)
  {
    ArrayList<Point> lst = new ArrayList<>();

    for(int i=-1; i<=1; i++)
    for(int j=-1; j<=1; j++)
    for(int k=-1; k<=1; k++)
    for(int l=-1; l<=1; l++)
    {
      if ((!part1) || (l==0))
      if ((i!=0) || (j!=0) || (k!=0) || (l!=0))
      {
        lst.add(new Point(a.x + i, a.y+j, a.z+k, a.w +l));
      }
    }

    return lst;

  }

  public  Map4D<Character> cycle( Map4D<Character> input)
  {
    Map4D<Character> out = new Map4D<Character>('.');

    for(long x= input.low_x -2; x<= input.high_x+2; x++)
    for(long y= input.low_y -2; y<= input.high_y+2; y++)
    for(long z= input.low_z -2; z<= input.high_z+2; z++)
    for(long w= input.low_w -2; w<= input.high_w+2; w++)
    {
      Point p = new Point(x,y,z,w);
      int active = 0;
      for(Point n : getNeighbors(p))
      {
        if ( input.get(n)=='#') active++;
      }
      if (input.get(p) =='#')
      {
        if ((active >=2 ) && (active<=3)) out.set(p, '#');

      }
      else
      {
        if (active==3) out.set(p, '#');
      }
      
    }



    return out;
  }

}
