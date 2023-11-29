

public class Nav
{
  
  // North 0,-1
  // East 1,0
  // South 0,1
  // West -1,0

  public static Point turnRight(Point in)
  {
    return new Point(-in.y, in.x);

  }

  public static Point turnLeft(Point in)
  {
    return new Point(in.y, -in.x);
  }

  public static Point getDir(char z)
  {
    if (z=='N') return new Point(0,-1);
    if (z=='S') return new Point(0,1);
    if (z=='E') return new Point(1,0);
    if (z=='W') return new Point(-1,0);

    throw new RuntimeException("Bad dir:" + z);

  }
  

}
