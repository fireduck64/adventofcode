
public class PointD implements Comparable<PointD>
{
  final double x;
  final double y;
  final double z;
  final double w;

  public PointD(double x, double y)
  {
    this(x,y,0.0,0.0);
  }
  public PointD(double x, double y, double z)
  {
    this(x,y,z,0.0);
  }
  public PointD(double x, double y, double z, double w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  @Override
  public String toString()
  {
    return String.format("(%f %f %f %f)", x,y, z, w);
  }

  @Override
  public int hashCode()
  {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof PointD)
    {
      PointD op = (PointD) o;
      if (x != op.x) return false;
      if (y != op.y) return false;
      if (z != op.z) return false;
      if (w != op.w) return false;
      return true;


    }
    return false;
  }

  @Override
  public int compareTo(PointD p)
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

  public PointD add(PointD p)
  {
    return new PointD(x + p.x, y +  p.y, z+p.z, w+p.w);
  }
  public PointD mult(double m)
  {
    return new PointD(x * m, y * m, z * m, w * m);
  }

  public double getDist(PointD p)
  {
    return Math.sqrt(getDist2(p));
  }

  public double getDist2(PointD p)
  {
    double dx=Math.abs(p.x - x); 
    double dy=Math.abs(p.y - y); 
    double dz=Math.abs(p.z - z); 
    double dw=Math.abs(p.w - w); 

    return dx*dx+dy*dy+dz*dz+dw*dw;

  }
public double getDistM(PointD p)
  {
    double dx=Math.abs(p.x - x); 
    double dy=Math.abs(p.y - y); 
    double dz=Math.abs(p.z - z); 
    double dw=Math.abs(p.w - w); 

    return dx+dy+dz+dw;

  }


}
