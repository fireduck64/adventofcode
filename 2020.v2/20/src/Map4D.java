import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.security.MessageDigest;

public class Map4D<V>
{
  public HashMap<Point,V > map;
  private V default_value;

  public long high_x;
  public long high_y;
  public long high_z;
  public long high_w;


  public long low_x;
  public long low_y;
  public long low_z;
  public long low_w;

  public Map4D(V def)
  {
    this.default_value = def;
    map = new HashMap<Point, V>();
    
  }

  public V get(Point p)
  {
    return get(p.x, p.y, p.z, p.w);
  }

  public V get(long x, long y, long z, long w)
  {
    Point p = new Point(x,y,z,w);

    if (map.get(p) == null) return (V) default_value;
    return map.get(p);

  }

  public V set(Point p, V val)
  {
    return set(p.x, p.y, p.z, p.w, val);
  }

  public V set(long x, long y, long z, long w, V val)
  {
    V old = get(x,y,z,w);
    
    Point p = new Point(x,y,z,w);

    map.put(p,val);

    high_x = Math.max(high_x, x);
    high_y = Math.max(high_y, y);
    high_z = Math.max(high_z, z);
    high_w = Math.max(high_w, w);

    low_x = Math.min(low_x, x);
    low_y = Math.min(low_y, y);
    low_z = Math.min(low_z, z);
    low_w = Math.min(low_w, w);
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


  public List<Point> getAdj(Point p, boolean diags)
  {
    LinkedList<Point> lst = new LinkedList<>();

    for(int x=-1; x<=1; x++)
    for(int y=-1; y<=1; y++)
    for(int z=-1; z<=1; z++)
    for(int w=-1; w<=1; w++)
    {
      int d = Math.abs(x) + Math.abs(y) + Math.abs(z) + Math.abs(w);
      if (d > 0)
      {
        if (diags || (d == 1))
        {
          lst.add(new Point(p.x + x, p.y + y, p.z + z, p.w + w));
        }
      }
    }

    return lst;
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


}
