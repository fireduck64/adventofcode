import java.io.FileInputStream;
import java.util.*;

public class ProbB
{

  public static void main(String args[]) throws Exception
  {
    new ProbB(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map3D<Character> map = new Map3D<>('.');

  public final int SZ=5;

  public ProbB(Scanner scan)
  {
    

    for(int y=0; y<SZ; y++)
    {
      String line = scan.nextLine();
      for(int x=0; x<SZ; x++)
      {
        map.set(x,y,0, line.charAt(x));
      }
    }
    System.out.println("Count: " + getCount());
    print();

    for(int i=0; i<200; i++)
    {
      step();
      System.out.println("Count: " + getCount());
      //print();
    }

    //System.out.println("Count: " + getCount());
    //print();

      //step();

    //System.out.println("Count: " + getCount());
    //print();
  }

  public void step()
  {
    Map3D<Character> n = new Map3D<>('.');

    for(long x=0; x<SZ; x++)
    for(long y=0; y<SZ; y++)
    for(long z=map.low_z-1; z<=map.high_z+1; z++)
    {
      Point p = new Point(x,y,z);

      if ((p.x != 2) || (p.y != 2))
      {


        int friends = countFriends(p.x,p.y,p.z);
        boolean hasbug = (map.get(p.x,p.y,p.z) == '#');
        if (hasbug)
        {
          if (friends == 1) n.set(p.x,p.y,p.z,'#');
        }
        else
        {
          if ((friends >= 1) && (friends <= 2))
          {
            n.set(p.x,p.y,p.z,'#');
          }
        }

      }
    }
    map = n;
  }

  public int countFriends(long x, long y,long z)
  {
    int friends=0;
    for(int dx=-1; dx<=1; dx++)
    for(int dy=-1; dy<=1; dy++)
    if (Math.abs(dx) + Math.abs(dy) == 1)
    {
      if ((x+dx == 2) && (y+dy==2))
      { // go down a layer
        
        if (Math.abs(dx) == 1)
        {
          int in_x=4;
          if (dx==1) in_x=0;
          for(int in_y=0; in_y<SZ; in_y++)
          {
            if (map.get(in_x, in_y, z-1)=='#') friends++;
          }

        }
        if (Math.abs(dy) == 1)
        {
          int in_y=4;
          if (dy==1) in_y=0;
          for(int in_x=0; in_x<SZ; in_x++)
          {
            if (map.get(in_x, in_y, z-1)=='#') friends++;
          }
        }
      }
      else if ((x+dx <0) || (y+dy <0) || (x+dx >= SZ) || (y+dy >= SZ))
      {
        int out_x = 2;
        int out_y = 2;
        if (map.get(out_x+dx, out_y+dy, z+1) =='#') friends++;
        // up a layer
      }
      else if (map.get(x+dx, y+dy, z) =='#') friends++;

    }
    return friends;  
  
  }


  long getCount()
  {
    long count =0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p.x, p.y, p.z) == '#') count++;

    }
    return count;

  }

  public void print()
  {
    for(long z = map.low_z; z<=map.high_z; z++)
    {
      System.out.println("Depth : " + z);
      System.out.println(map.getPrintOut(null,0,0,SZ-1,SZ-1, z));
      System.out.println();

    }

  }

}
