import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.security.MessageDigest;

public class Map2D<V>
{
  public TreeMap<Long, TreeMap<Long, V> > map;
  private V default_value;

  protected long high_x = -1000000000L;
  protected long high_y= -1000000000L;

  protected long low_x = 1000000000L;
  protected long low_y = 1000000000L;

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

  public V get(Point p)
  {
    return get(p.x, p.y);
  }

  public V get(long x, long y)
  {
    if (map.get(x) == null) return (V) default_value;
    if (map.get(x).get(y) == null) return (V) default_value;
    return map.get(x).get(y);

  }

  public V set(Point p, V val)
  {
    return set(p.x, p.y, val);
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
  public List<Point > getAllPoints(V val)
  {
    List<Point> lst = new LinkedList<>();

    for(Point p : getAllPoints())
    {
      if (get(p).equals(val)) lst.add(p);

    }
    return lst;

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
    {
      int d = Math.abs(x) + Math.abs(y);
      if (d > 0)
      {
        if (diags || (d == 1))
        {
          lst.add(new Point(p.x + x, p.y + y));
        }

      }
    }

    return lst;
  }

  public String getHashState()
  {
    return HUtil.getHash(map.toString());
  }
  public void print()
  {
    System.out.println(String.format("Range %d %d %d %d", low_x, low_y, high_x, high_y));
    System.out.println(getPrintOut());
    System.out.println(getCounts());
  }
  public String getPrintOut()
  {
    return getPrintOut(null);
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
          if (conv_map == null)
          {
            z = val.toString();
          }
          else if (conv_map.containsKey(val))
          {
            z="" + conv_map.get(val);
          }
          else
          {
            z="?";
          }
        }
        sb.append(z);

      }
  
      sb.append("\n");
    }

    return sb.toString();

  }

  public Map2D<V> copy()
  {
    Map2D<V> m = new Map2D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      m.set(p, get(p));
    }
    return m;

  }

  public Map2D<V> rotateL()
  {
    Map2D<V> m = new Map2D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      Point n = new Point(p.y, -p.x+high_x);
      m.set(n, get(p));
    }
    return m;
  }

  public Map2D<V> rotateR()
  {
    return rotateL().rotateL().rotateL();

  }
  public Map2D<V> flipH()
  {
    long wid = high_x;
    Map2D<V> m = new Map2D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      Point n = new Point(wid-p.x, p.y);
      m.set(n, get(p));

    }
    return m;
  }
  public Map2D<V> flipV()
  {
    long wid = high_y;
    Map2D<V> m = new Map2D<V>(default_value);
    for(Point p : getAllPoints())
    { 
      Point n = new Point(p.x, wid-p.y);
      m.set(n, get(p));
    }
    return m;
  }




  public Map2D<V> copyRange(long x1, long y1, long x2, long y2)
  {
    Map2D<V> m = new Map2D<V>(default_value);
    for(long x=x1; x<=x2; x++)
    for(long y=y1; y<=y2; y++)
    {
      Point p = new Point(x,y);
      m.set(p, get(p));
    }
    return m;
  }

}
