public class Box
{
  Point p1;
  Point p2;

  public Box(Point p1, long scale)
  {
    this.p1=p1;
    p2=new Point(p1.x+scale, p1.y+scale, p1.z+scale);
  }

  public Box(Point p1, Point p2)
  {
    this.p1 = p1;
    this.p2 = p2;
  }


  public String toString()
  {
    return String.format("Box{ %s - %s }", p1.toString(), p2.toString());

  }

}
