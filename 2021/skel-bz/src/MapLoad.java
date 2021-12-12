
import java.util.Scanner;

public class MapLoad
{

  /**
   * Reads lines from scanner as characters into 2d map.
   * top left corner is 0,0.
   * Each row is +1y.  Each Column is +1x.
   * returns a point of highest x and highest y.
   */
  public static Point loadMap(Map2D<Character> m, Scanner scan)
  {
    int max_x=0;
    int max_y=0;

    int y =0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      max_y = y;

      for(int x=0; x<line.length(); x++)
      {
        m.set(x,y, line.charAt(x));
        max_x = Math.max(max_x, x);
      }

      y++;
    }

    return new Point(max_x, max_y);

  }
  public static Point loadMapInt(Map2D<Integer> m, Scanner scan)
  {
    int max_x=0;
    int max_y=0;

    int y =0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      max_y = y;

      for(int x=0; x<line.length(); x++)
      {
        int v = Integer.parseInt("" + line.charAt(x));
        m.set(x,y, v);
        max_x = Math.max(max_x, x);
      }

      y++;
    }

    return new Point(max_x, max_y);

  }


}
