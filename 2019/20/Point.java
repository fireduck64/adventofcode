
public class Point
{
  final long x;
  final long y;
  final long z;

  public Point(long x, long y)
  {
    this.x = x;
    this.y = y;
    this.z = 0;
  }

  public Point(long x, long y, long z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }


  @Override
  public String toString()
  {
    return String.format("(%d %d %d)", x,y,z);
  }

  @Override
  public int hashCode()
  {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return toString().equals(o.toString());
  }

}
