
public class Point
{
  final long x;
  final long y;

  public Point(long x, long y)
  {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString()
  {
    return String.format("(%d %d)", x,y);
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
