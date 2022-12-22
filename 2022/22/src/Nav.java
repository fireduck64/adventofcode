

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

  public static Point rev(Point in)
  {
    return new Point(-in.x, -in.y);
  }
  

}
