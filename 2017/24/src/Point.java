
public class Point
{
  final long x;
  final long y;
  final long z;
  final long w;

  public Point(long x, long y)
  {
    this(x,y,0L,0L);
  }
  public Point(long x, long y, long z)
  {
    this(x,y,z,0L);
  }
  public Point(long x, long y, long z, long w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  @Override
  public String toString()
  {
    return String.format("(%d %d %d %d)", x,y, z, w);
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

  public Point add(Point p)
  {
    return new Point(x + p.x, y +  p.y, z+p.z, w+p.w);
  }

  public double getDist(Point p)
  {
    return Math.sqrt(getDist2(p));
  }

  public long getDist2(Point p)
  {
    long dx=Math.abs(p.x - x); 
    long dy=Math.abs(p.y - y); 
    long dz=Math.abs(p.z - z); 
    long dw=Math.abs(p.w - w); 

    return dx*dx+dy*dy+dz*dz+dw*dw;

  }
public long getDistM(Point p)
  {
    long dx=Math.abs(p.x - x); 
    long dy=Math.abs(p.y - y); 
    long dz=Math.abs(p.z - z); 
    long dw=Math.abs(p.w - w); 

    return dx+dy+dz+dw;

  }


}
