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

  Map2D<Character> mapo_orig = new Map2D<Character>('.');
  Map2D<Character> mapo = new Map2D<Character>('.');

  Point v_loc;
  Point v_dir;
  int infect_count =0;

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(mapo_orig, scan);

    reset();
    for(int i=0; i<10000; i++)
    {
      lurk();
    }
    System.out.println("Part 1: " + infect_count + " infections");

    reset();
    for(int i=0; i<10000000; i++)
    {
      lurk2();
    }
    System.out.println("Part 2: " + infect_count + " infections");


  }

  public void reset()
  {
    mapo = mapo_orig.copy();
    v_dir=new Point(0,-1);
    v_loc = new Point( mapo.high_x /2 , mapo.high_y/2);
    infect_count = 0;

  }

  void lurk()
  {
    char cur = mapo.get(v_loc);

    if (cur=='#') v_dir = turnR(v_dir);
    else v_dir = turnL(v_dir);

    if (cur=='.')
    {
      mapo.set(v_loc, '#');
      infect_count++;
    }
    else
    {
      mapo.set(v_loc, '.');
    }
    v_loc = v_loc.add(v_dir);

  }

  void lurk2()
  {
    char cur = mapo.get(v_loc);

    if (cur=='.') v_dir = turnL(v_dir);
    if (cur=='#') v_dir = turnR(v_dir);
    if (cur=='F') v_dir = turnR(turnR(v_dir));

    if (cur=='.')
    {
      mapo.set(v_loc, 'W');
    }
    if (cur=='W')
    {
      mapo.set(v_loc, '#');
      infect_count++;
    }
    if (cur=='#')
    {
      mapo.set(v_loc, 'F');
    }
    if (cur=='F')
    {
      mapo.set(v_loc, '.');
    }

    v_loc = v_loc.add(v_dir);

  }


  Point turnR(Point in)
  {
    if (in.y==-1) return new Point(1,0); //up to right
    if (in.x==1) return new Point(0,1); //right to down
    if (in.y==1) return new Point(-1,0); // down to left
    if (in.x==-1) return new Point(0,-1); // left to up

    E.er();
    return null;

  }

  Point turnL(Point in)
  {
    return turnR(turnR(turnR(in)));
  }

}
