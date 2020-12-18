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


  // Top left, 0,0
  //
  // N = -y (0, -1)
  // E = +x (1, 0)
  // S = +y (0, 1)
  // W = -x (-1, 0)
  public static final Point N=new Point(0,-1);
  public static final Point E=new Point(1,0);
  public static final Point S=new Point(0,1);
  public static final Point W=new Point(-1,0);


  // Rot right
  //  -y, x
  // N rot = 1, 0
  // E rot = 0, 1
  // S rot = -1, 0
  // W rot = 0, -1


  Point rotRight(Point d)
  {
    return new Point(-d.y, d.x);
  }
  Point rotLeft(Point d)
  {
    return rotRight(rotRight(rotRight(d)));
  }

  ArrayList<Cart> carts = new ArrayList();
  Map2D<Character> map = new Map2D<>(' ');

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map, scan);

    //get carts
    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      Point dir = null;
      if (z=='^') dir = N;
      if (z=='<') dir = W;
      if (z=='>') dir = E;
      if (z=='v') dir = S;
      if (dir != null)
      {
        carts.add(new Cart(p , dir));
      }

    }
    System.out.println("Carts: " + carts.size());

    while(true)
    {
      
      // Get cart movement priority
      TreeMap<Double, Integer> move_order = new TreeMap<>();
      HashMap<Point, Integer> cart_locs=new HashMap<>();
      for(int i=0; i<carts.size(); i++)
      {
        Cart c = carts.get(i);
        move_order.put( c.getMovePri(), i);

        cart_locs.put(c.loc, i);
      }

      TreeSet<Integer> carts_to_remove=new TreeSet<>();



      for(int i : move_order.values())
      {
        Cart c = carts.get(i);
        Point old = c.loc;
        cart_locs.remove(old);

        c.updatePos();
        Point p = c.loc;
        if (cart_locs.containsKey(p))
        {
          System.out.println("Collision: " + p);
          carts_to_remove.add(i);
          carts_to_remove.add( cart_locs.get(p) );
          cart_locs.remove(p);
        }
        else
        {
          cart_locs.put(p, i);
        }

      }
      while(carts_to_remove.size() > 0)
      {
        int c = carts_to_remove.pollLast();
        carts.remove(c);
      }
      if (carts.size() == 0)
      {
        System.out.println("No carts left");
        return;
      }
      if (carts.size() == 1)
      {
        System.out.println("Last cart: " + carts.get(0).loc);
        return;
      }
 
    }
   

  }

  public class Cart
  {
    Point loc;
    Point dir;

    public Cart(Point loc, Point dir)
    {
      this.loc = loc;
      this.dir = dir;

    }

    // 0 - left
    // 1 - straight
    // 2 - right
    int turn=0;

    public void doInter()
    {
      if (turn == 0) dir = rotLeft(dir);
      if (turn == 1) {}
      if (turn == 2) dir = rotRight(dir);

      turn++;
      turn = turn % 3;
    }

    public void updatePos()
    {
      loc = new Point( loc.x + dir.x, loc.y + dir.y );
      char z = map.get(loc);
      //System.out.println("z:" + z + ";");
      if (z == '+') doInter();
      if (z == '/')
      {
        if (dir.equals(N)) dir=E;
        else if (dir.equals(W)) dir=S;
        else if (dir.equals(E)) dir=N;
        else if (dir.equals(S)) dir=W;

      }
      if (z == '\\') 
      {
        if (dir.equals(N)) dir=W;
        else if (dir.equals(W)) dir=N;
        else if (dir.equals(S)) dir=E;
        else if (dir.equals(E)) dir=S;
      }
      if (z==' ')
      {
        System.out.println("Off track: " + loc);
        System.out.println("Direction: " + dir);
        System.out.println( map.getPrintOut(null, loc.x-2, loc.y-2, loc.x+2, loc.y+2));
        throw new RuntimeException("Off track");
      }

    }

    public double getMovePri()
    {
      double y = loc.y;
      double x = loc.x;
      return y + x / 1e6;

    }


  }

}
