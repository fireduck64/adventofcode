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

  private long high_x;
  private long high_y;
  public long high_z;

  private long low_x;
  private long low_y;
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

  public V get(long x, long y, long z)
  {
    Point p = new Point(x,y,z);

    if (map.get(p) == null) return (V) default_value;
    return map.get(p);

  }

  public V set(long x, long y, long z, V val)
  {
    V old = get(x,y,z);
    
    Point p = new Point(x,y,z);

    map.put(p,val);

    high_x = Math.max(high_x, x);
    high_y = Math.max(high_y, y);
    high_z = Math.max(high_z, z);

    low_x = Math.min(low_x, x);
    low_y = Math.min(low_y, y);
    low_z = Math.min(low_z, z);
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
