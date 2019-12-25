import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  Map2D<Character> map = new Map2D<>('.');

  public final int SZ=5;

  public Prob(Scanner scan)
  {
    for(int y=0; y<SZ; y++)
    {
      String line = scan.nextLine();
      for(int x=0; x<SZ; x++)
      {
        map.set(x,y, line.charAt(x));
      }

    }
    HashSet<Long> states = new HashSet<>();

    while(true)
    {
      long v = getScore();
      System.out.println(map.getPrintOut(null));
      if (states.contains(v))
      {
        System.out.println("First repeat: " + v);
        System.out.println(map.getPrintOut(null));
        break;
      }
      states.add(v);
      step();

    }


  }

  public void step()
  {
    Map2D<Character> n = new Map2D<>('.');

    for(int y=0; y<SZ; y++)
    for(int x=0; x<SZ; x++)
    {
      int friends = countFriends(x,y);
      System.out.println("Friends: " + friends);
      System.out.println(map.getPrintOut(null, new Point(x,y), 1));
      boolean hasbug = (map.get(x,y) == '#');
      if (hasbug)
      {
        if (friends == 1) n.set(x,y,'#');
      }
      else
      {
        if ((friends >= 1) && (friends <= 2))
        {
          n.set(x,y,'#');
        }
      }
    }
    map = n;
  }

  public int countFriends(long x, long y)
  {
    int friends=0;
    for(int dx=-1; dx<=1; dx++)
    for(int dy=-1; dy<=1; dy++)
    if (Math.abs(dx) + Math.abs(dy) == 1)
    {
      if (map.get(x+dx, y+dy) =='#') friends++;

    }
    return friends;  
  
  }

  public long getScore()
  {
    long sum =0;
    long val = 1L;
    for(int y=0; y<SZ; y++)
    for(int x=0; x<SZ; x++)
    {
      if (map.get(x,y) == '#')
      {
        sum+=val;
      }

      val *= 2;
    }
    return sum;

  }

  

}
