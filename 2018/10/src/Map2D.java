import java.util.TreeMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.security.MessageDigest;

public class Map2D<V>
{
  public TreeMap<Long, TreeMap<Long, V> > map;
  private V default_value;

  protected long high_x=-1000000;
  protected long high_y=-1000000;

  protected long low_x=1000000;
  protected long low_y=1000000;

  public Map2D(V def)
  {
    this.default_value = def;
    map = new TreeMap<>();
  }

  public Map2D(Map2D<V> src)
  {
    map = new TreeMap<>();

    for(long x : src.map.keySet())
    {
      map.put(x, new TreeMap<Long, V>());
      map.get(x).putAll(src.map.get(x));
    }
    default_value = src.default_value;
    high_x = src.high_x;
    high_y = src.high_y;

    low_x = src.low_x;
    low_y = src.low_y;
  }

  public V get(long x, long y)
  {
    if (map.get(x) == null) return (V) default_value;
    if (map.get(x).get(y) == null) return (V) default_value;
    return map.get(x).get(y);

  }

  public V set(long x, long y, V val)
  {
    V old = get(x,y);

    if (map.get(x) == null) map.put(x, new TreeMap<Long, V>());
    map.get(x).put(y,val);

    high_x = Math.max(high_x, x);
    high_y = Math.max(high_y, y);

    low_x = Math.min(low_x, x);
    low_y = Math.min(low_y, y);
    return old;
  }

  public List<Point > getAllPoints()
  {
    LinkedList<Point> lst = new LinkedList<>();

    for(Map.Entry<Long, TreeMap<Long, V> > i : map.entrySet())
    {
      for(Map.Entry<Long, V> j : i.getValue().entrySet())
      {
        lst.add(new Point(i.getKey(), j.getKey()));
      }
    }
    return lst;
  }

  public String getHashState()
  {
    return HUtil.getHash(map.toString());
  }
  public String getPrintOut(Map<V, Character> conv_map)
  {
    return getPrintOut(conv_map, low_x, low_y, high_x, high_y);
  }
  public String getPrintOut(Map<V, Character> conv_map, Point p, int dist)
  {
    return getPrintOut(conv_map, p.x - dist, p.y- dist, p.x + dist - 1, p.y+dist-1);
  }

  public String getPrintOut(Map<V, Character> conv_map, long min_x, long min_y, long max_x, long max_y)
  {
    StringBuilder sb = new StringBuilder();
    for(long y=min_y; y<=max_y; y++)
    {
      for(long x=min_x; x<=max_x; x++)
      {
        String z=" ";
        V val = get(x,y);
        if (val != null)
        {
          z = val.toString();
        }
        sb.append(z);
      }
      sb.append("\n");
    }

    return sb.toString();

  }

}
