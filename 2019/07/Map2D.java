import java.util.TreeMap;
import java.util.Map;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class Map2D<V>
{
  private TreeMap<Integer, TreeMap<Integer, V> > map;
  private V default_value;


  public Map2D(V def)
  {
    this.default_value = def;
    map = new TreeMap<>();
  }

  public V get(int x, int y)
  {
    if (map.get(x) == null) return (V) default_value;
    if (map.get(x).get(y) == null) return (V) default_value;
    return map.get(x).get(y);

  }

  public V set(int x, int y, V val)
  {
    V old = get(x,y);

    if (map.get(x) == null) map.put(x, new TreeMap<Integer, V>());
    map.get(x).put(y,val);

    return old;
  }

  public List<Map.Entry<Integer,Integer> > getAllPoints()
  {
    LinkedList<Map.Entry<Integer, Integer>> lst = new LinkedList<>();

    for(Map.Entry<Integer, TreeMap<Integer, V> > i : map.entrySet())
    {
      for(Map.Entry<Integer, V> j : i.getValue().entrySet())
      {
        lst.add(new SimpleEntry<Integer,Integer>(i.getKey(), j.getKey()));

      }
    }
    return lst;
    

  }


}
