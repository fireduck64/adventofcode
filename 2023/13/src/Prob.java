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
  
  public Prob(Scanner scan)
  {
    long p1 = 0;
    long p2 = 0;
    while(scan.hasNextLine())
    {
      StringBuilder sb = new StringBuilder();
      while(scan.hasNextLine())
      {
        String line = scan.nextLine();
        if (line.trim().length()==0) break;

        sb.append(line + "\n");
      }
      Map2D<Character> input = new Map2D<Character>(' ');
      MapLoad.loadMap(input, new Scanner(sb.toString()));

      input.print();

      for(int y = 0; y<input.high_y; y++)
      {
        if (checkVertReflection(input, y, 0))
        {
          System.out.println("match y " + y);
          p1 += (y+1) * 100;
        }
        if (checkVertReflection(input, y, 1))
        {
          System.out.println("match y " + y);
          p2 += (y+1) * 100;
        }

      }
      for(int x = 0; x<input.high_x; x++)
      {
        if (checkHorzReflection(input, x, 0))
        {
          System.out.println("match x " + x);
          p1 += (x+1);
        }
        if (checkHorzReflection(input, x, 1))
        {
          System.out.println("match x " + x);
          p2 += (x+1);
        }

      }

    }

    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);
    
  }

  public boolean checkVertReflection(Map2D<Character> map, long offset, int req_error)
  {
    int error_count = 0;
    for(Point p : map.getAllPoints())
    {
      long y = p.y;
      if (y <= offset)
      {
        long diff = offset - y;
        long oy = offset+1+diff;

        Point o = new Point(p.x, oy);

        if (map.get(o) != ' ')
        if (map.get(o) != map.get(p))
        {
          error_count ++;
        }
      }
    }

    return (error_count == req_error);



  }

  public boolean checkHorzReflection(Map2D<Character> map, long offset, int req_error)
  {
    int error_count = 0;
    for(Point p : map.getAllPoints())
    {
      long x = p.x;
      if (x <= offset)
      {
        long diff = offset - x;
        long ox = offset+1+diff;

        Point o = new Point(ox, p.y);

        if (map.get(o) != ' ')
        if (map.get(o) != map.get(p))
        {
          error_count++;
        }
      }
    }
    return (error_count == req_error);



  }


}
