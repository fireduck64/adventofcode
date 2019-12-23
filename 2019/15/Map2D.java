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

  private long high_x;
  private long high_y;

  private long low_x;
  private long low_y;

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

  public List<Map.Entry<Long,Long> > getAllPoints()
  {
    LinkedList<Map.Entry<Long, Long>> lst = new LinkedList<>();

    for(Map.Entry<Long, TreeMap<Long, V> > i : map.entrySet())
    {
      for(Map.Entry<Long, V> j : i.getValue().entrySet())
      {
        lst.add(new SimpleEntry<Long,Long>(i.getKey(), j.getKey()));

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
    StringBuilder sb = new StringBuilder();
    for(long y=low_y; y<=high_y; y++)
    {
      for(long x=low_x; x<=high_x; x++)
      {
        char z=' ';
        V val = get(x,y);
        if (val != null)
        {
          if (conv_map.containsKey(val))
          {
            z=conv_map.get(val);
          }
          else
          {
            z='?';
          }
        }
        sb.append(z);

      }
  
      sb.append("\n");
    }
    


    return sb.toString();

  }

}
