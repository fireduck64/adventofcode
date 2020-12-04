import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  int len;
  int height;
  Map2D<Character> map;

  public Prob(Scanner scan)
  {
    map = new Map2D<Character>((char)0);
    int y=0;
    Point p = MapLoad.loadMap(map, scan);

    height=(int)p.y+1;
    len = (int)p.x+1;

    long prod = 1;
    prod *= check(1,1);
    prod *= check(3,1);
    prod *= check(5,1);
    prod *= check(7,1);
    prod *= check(1,2);

    System.out.println(prod);

  }

  long check(int dx, int dy)
  {
    int loc_x =0;
    int loc_y =0;
    long hit = 0;
    while(loc_y < height)
    {
      loc_x += dx;
      loc_y += dy;
      int eff_x = loc_x % len;
      if (map.get(eff_x, loc_y)=='#') hit++;

    }

    return hit;

  }

}
