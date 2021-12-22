
public class Point implements Comparable<Point>
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

  @Override
  public int compareTo(Point p)
  {
    if (x < p.x) return -1;
    if (x > p.x) return 1;

    if (y < p.y) return -1;
    if (y > p.y) return 1;

    if (z < p.z) return -1;
    if (z > p.z) return 1;

    if (w < p.w) return -1;
    if (w > p.w) return 1;

    return 0;
  }

  public Point add(Point p)
  {
    return new Point(x + p.x, y +  p.y, z+p.z, w+p.w);
  }
  public Point mult(long m)
  {
    return new Point(x * m, y * m, z * m, w * m);
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
