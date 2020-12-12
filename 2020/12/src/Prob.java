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
  ArrayList<Step> steps= new ArrayList<>();

  // This is the most important thing.
  // have a fucking plan.  Write it down.

  // North +y
  // South -y
  // West -x
  // East +x
  public Prob(Scanner scan)
  {

    while(scan.hasNext())
    {
      steps.add( new Step(scan.next() ));
    }

    { // part 1
      int x=0;
      int y=0;
      Point dir = new Point(1,0);

      for(Step s : steps)
      {
        if (s.act =='N') y+=s.n;
        if (s.act =='S') y-=s.n;
        if (s.act =='E') x+=s.n;
        if (s.act =='W') x-=s.n;
        if (s.act =='F')
        {
          x += dir.x * s.n;
          y += dir.y * s.n;
        }
        if (s.act=='R')
        {
          int n = s.n;
          while(n > 0)
          {
            n-=90;
            dir = turnRight(dir);
          }
        }
        if (s.act=='L')
        {
          int n = s.n;
          while(n > 0)
          {
            n-=90;
            dir = turnLeft(dir);
          }
        }

      }
      System.out.println(" " + x +"," + y);
      System.out.println(Math.abs(x) + Math.abs(y));
    }
    { // part 2

      long ship_x=0;
      long ship_y=0;
      long way_x=10;
      long way_y=1;

      for(Step s : steps)
      {
        if (s.act =='N') way_y+=s.n;
        if (s.act =='S') way_y-=s.n;
        if (s.act =='E') way_x+=s.n;
        if (s.act =='W') way_x-=s.n;
        if (s.act =='F')
        {
          ship_x += way_x * s.n;
          ship_y += way_y * s.n;
        }
        if (s.act=='R')
        {
          int n = s.n;
          Point w = new Point(way_x, way_y);
          while(n > 0)
          {
            n-=90;
            w = rotRight(w);
          }
          way_x = w.x;
          way_y = w.y;
        }
        if (s.act=='L')
        {
          int n = s.n;
          Point w = new Point(way_x, way_y);
          while(n > 0)
          {
            n-=90;
            w = rotLeft(w);
          }
          way_x = w.x;
          way_y = w.y;
        }

      }

      System.out.println(" " + ship_x +"," + ship_y);
      System.out.println(Math.abs(ship_x) + Math.abs(ship_y));

    }

  }

  public Point turnRight(Point in)
  {
    // east
    if (in.x==1) return new Point(0,-1);

    // south
    if (in.y==-1) return new Point(-1,0);

    //west
    if(in.x==-1) return new Point(0, 1);

    //north
    if (in.y==1) return new Point(1,0);

    return null;

  }
  public Point rotRight(Point in)
  {
    int out_x=0;
    int out_y=0;

    // I don't know.  I followed the rules of the turnRight above and rolled
    // with it.
    return new Point(in.y,-in.x);


  }



  public Point turnLeft(Point in)
  {
    return turnRight(turnRight(turnRight(in)));
  }

  public Point rotLeft(Point in)
  {
    return rotRight(rotRight(rotRight(in)));
  }

  public class Step
  {
    char act;
    int n;


    public Step(String line)
    {
      act = line.charAt(0);
      n = Integer.parseInt( line.substring(1));


    }

  }

}
