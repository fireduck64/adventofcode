import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.security.MessageDigest;

public class Map3D<V>
{
  public HashMap<Point,V > map;
  private V default_value;

  public long high_x;
  public long high_y;
  public long high_z;

  public long low_x;
  public long low_y;
  public long low_z;

  public Map3D(V def)
  {
    this.default_value = def;
    map = new HashMap<Point, V>();
    
  }

  public Map3D(Map3D<V> src)
  {
    map = new HashMap<>();

    map.putAll(src.map);

    default_value = src.default_value;
    high_x = src.high_x;
    high_y = src.high_y;
    high_z = src.high_z;

    low_x = src.low_x;
    low_y = src.low_y;
    low_z = src.low_z;
  }

  public V get(Point p)
  {
    if (map.get(p) == null) return (V) default_value;
    return map.get(p);
  }

  public V get(long x, long y, long z)
  {
    Point p = new Point(x,y,z);
    return get(p);
  }

  public V set(long x, long y, long z, V val)
  {
    return set(new Point(x,y,z), val);
  }
 
  public V set(Point p, V val)
  {
    V old = get(p);
    
    map.put(p,val);

    high_x = Math.max(high_x, p.x);
    high_y = Math.max(high_y, p.y);
    high_z = Math.max(high_z, p.z);

    low_x = Math.min(low_x, p.x);
    low_y = Math.min(low_y, p.y);
    low_z = Math.min(low_z, p.z);
    return old;
  }

  public List<Point > getAllPoints()
  {
    LinkedList<Point> lst = new LinkedList<>();

    lst.addAll(map.keySet());
    
    return lst;
  }

  public String getHashState()
  {
    return HUtil.getHash(map.toString());
  }

  public Map<V, Long> getCounts()
  {
    HashMap<V, Long> cmap = new HashMap<>();

    for(Point p : getAllPoints())
    {
      V v = get(p);
      if (!cmap.containsKey(v))
      {
        cmap.put(v, 0L);
      } 
      cmap.put(v, cmap.get(v) + 1L);
    }
    return cmap;
  }


  public List<Point> getAdj(Point p, boolean diags)
  {
    LinkedList<Point> lst = new LinkedList<>();

    for(int x=-1; x<=1; x++)
    for(int y=-1; y<=1; y++)
    for(int z=-1; z<=1; z++)
    {
      int d = Math.abs(x) + Math.abs(y) + Math.abs(z);
      if (d > 0)
      {
        if (diags || (d == 1))
        {
          lst.add(new Point(p.x + x, p.y + y, p.z + z));
        }
      }
    }

    return lst;
  }

  // Imagine the worst possible explanation
  // for this comment and that is the correct one.
  // I am terrible at rotations.
  // SSS - sok - no
  // FSS - sok - no
  // FFS - sok - 447 
  // FSF - sok
  // SFS - sok 
  // SFF - 
  // FFF -
  public Map3D<V> rotateZ()
  {
    Map3D<V> m = new Map3D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      Point n = new Point(p.y, -p.x, p.z);
      //Point n = new Point(-p.y, p.x, p.z);
      m.set(n, get(p));
    }
    return m;
  }

  public Map3D<V> rotateY()
  {
    Map3D<V> m = new Map3D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      Point n = new Point(p.z, p.y, -p.x);
      //Point n = new Point(-p.z, p.y, p.x);
      m.set(n, get(p));
    }
    return m;
  }

  public Map3D<V> rotateX()
  {
    Map3D<V> m = new Map3D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      //Point n = new Point(p.x, p.z, -p.y);
      Point n = new Point(p.x, -p.z, p.y);
      m.set(n, get(p));
    }
    return m;
  }




  public String getPrintOut(Map<V, Character> conv_map, long z)
  {
    return getPrintOut(conv_map, low_x, low_y, high_x, high_y, z);
  }
  public String getPrintOut(Map<V, Character> conv_map, Point p, int dist)
  {
    return getPrintOut(conv_map, p.x - dist, p.y- dist, p.x + dist, p.y+dist, p.z);
  }

  public String getPrintOut(Map<V, Character> conv_map, long min_x, long min_y, long max_x, long max_y, long z)
  {
    StringBuilder sb = new StringBuilder();
    for(long y=min_y; y<=max_y; y++)
    {
      for(long x=min_x; x<=max_x; x++)
      {
        char q=' ';
        V val = get(x,y,z);
        if (val != null)
        {
          if (conv_map == null)
          {
            q=(char) val;
          }
          else if (conv_map.containsKey(val))
          {
            q=conv_map.get(val);
          }
          else
          {
            q='?';
          }
        }
        sb.append(q);

      }
  
      sb.append("\n");
    }

    return sb.toString();

  }


}
