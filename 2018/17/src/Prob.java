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
  Map2D<Character> map=new Map2D('.');

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> tok = Tok.en(line, ",");

      int x1=-1;
      int x2=-1;
      int y1=-1;
      int y2=-1;

      String first = tok.get(0);
      List<String> f_tok = Tok.en(first,"=");
      String f_dim = f_tok.get(0);
      int f_val = Integer.parseInt(f_tok.get(1));
      if (f_dim.equals("x"))
      {
        x1=f_val;
        x2=f_val;
      }
      if (f_dim.equals("y"))
      {
        y1=f_val;
        y2=f_val;
      }

      String second = tok.get(1).trim();
      List<String> s_tok = Tok.en(second,"=");
      String s_dim = s_tok.get(0);
      
      List<String> ss_tok = Tok.en(s_tok.get(1).replace("..",","), ",");
      int v1=Integer.parseInt(ss_tok.get(0));
      int v2=Integer.parseInt(ss_tok.get(1));
      if (s_dim.equals("x"))
      {
        x1=v1; x2=v2;
      }

      if (s_dim.equals("y"))
      {
        y1=v1; y2=v2;
      }


      fillMap('#',x1,x2,y1,y2);
    }
    long min_y = map.low_y;
    System.out.println("High y: " + map.high_y);

    map.set(500,0,'+');

    while(true)
    {
      int c=changes;
      for(Point p : map.getAllPoints())
      {
        char v = map.get(p);
        if ((v=='+') || (v=='|'))
        {
          waterFlow((int)p.x, (int)p.y);
        }
      }
      int diff = changes - c;
      System.out.println("Changes: " + diff);
      if (changes == c) break;
    }

    while(true)
    {
      int c=changes;
      for(Point p : map.getAllPoints())
      {
        char v = map.get(p);
        if ((v=='+') || (v=='|'))
        {
          waterFlow((int)p.x, (int)p.y);
        }
      }
      int diff = changes - c;
      System.out.println("Changes: " + diff);
      if (changes == c) break;
    }


    map.set(500,0,'+');
    System.out.println(map.getPrintOut(null));
    System.out.println("High y: " + map.high_y);
    int wet=0;
    int slow=0;
    for(Point p : map.getAllPoints())
    {
      if (p.y >= min_y)
      {
      if (map.get(p)=='~') wet++;
      if (map.get(p)=='~') slow++;
      if (map.get(p)=='|') wet++;
      }
    }
    System.out.println("Wet: " + wet);
    System.out.println("Slow: " + slow);

  }

  /**
   * Fill map area inclusive
   */
  public void fillMap(char fill, int x1, int x2, int y1, int y2)
  {
    if (x1 < 0) throw new RuntimeException("x1");
    if (x2 < 0) throw new RuntimeException("x2");
    if (y1 < 0) throw new RuntimeException("y1");
    if (y2 < 0) throw new RuntimeException("y2");
    if (x2 < x1) throw new RuntimeException("Wrong");
    if (y2 < y1) throw new RuntimeException("Wrong");

    //System.out.println("" + x1 + " " + x2 + " " + y1 + " " + y2);
    for(int x=x1; x<=x2; x++)
    for(int y=y1; y<=y2; y++)
    {
      setMap(x,y,fill);
    }

  }
  int changes=0;
  public void setMap(int x, int y, char fill)
  {
    char old = map.set(x,y,fill);
    if (old=='#') 
    {
      if (fill!=old) throw new RuntimeException("Overwrite wall");
    }
    if (old != fill) changes++;
  }

  public void waterFlow(int x, int y)
  {
    if (y > map.high_y) return;

    // Already blocked
    if (isFull(x,y)) return;

    setMap(x,y,'|');
    // It is empty below
    if (isEmpty(x,y+1))
    {
      if (map.get(x,y+1) =='.')
      {
        waterFlow(x,y+1);
      }
      return;
    }


    if (isFull(x,y+1))
    { // flow to the sides, below is blocked
      int left_x=x;
      int right_x=x;
      boolean left_blocked=false;
      boolean right_blocked=false;

      while(true)
      {
        if (isFull(left_x - 1, y))
        {
          left_blocked=true;
          break;
        }

        // If there is still something below us, move
        if (isFull(left_x, y + 1))
        {
          left_x--;
        }
        else
        {
          break;
        }
      }

      while(true)
      {
        if (isFull(right_x + 1, y))
        {
          right_blocked=true;
          break;
        }

        // If there is still something below us, move
        if (isFull(right_x, y + 1))
        {
          right_x++;
        }
        else
        {
          break;
        }
      }


      // If both are blocked, then we fill from left to right
      if ((right_blocked) && (left_blocked))
      {
        fillMap('~',left_x,right_x,y,y);
        return;
      }

      fillMap('|',left_x,right_x,y,y);
      // If one or both are open, then we flow down them
      
      if (!right_blocked)
      {
        waterFlow(right_x, y);
      }
      if (!left_blocked)
      {
        waterFlow(left_x, y);
      }
    
    }

    return;

  }
  public boolean isFull(int x, int y)
  {
    if (map.get(x,y) == '#') return true;
    if (map.get(x,y) == '~') return true;
    return false;
  }
  public boolean isEmpty(int x, int y)
  {
    return !isFull(x,y);
  }


}
