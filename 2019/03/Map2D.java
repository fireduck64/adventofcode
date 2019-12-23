import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map;
import java.util.TreeMap;
import java.util.AbstractMap.SimpleEntry;

public class Map2D<V>
{
  private TreeMap<Integer, TreeMap<Integer, V> > map;
  private V default_value;

  public Map2D(V def)
  {
    map = new TreeMap<>();
    this.default_value = def;

  }

  public V get(int x, int y)
  {
    if (map.get(x) == null) return default_value;
    if (map.get(x).get(y) == null) return default_value;
    return map.get(x).get(y);
  }
  public void set(int x, int y, V val)
  {
    if (map.get(x) == null) map.put(x, new TreeMap<Integer, V>());
    map.get(x).put(y, val);

  }

  public List<Map.Entry> getAllPoints()
  {
    LinkedList<Map.Entry> lst = new LinkedList<>();

    for(int i : map.keySet())
    for(int j : map.get(i).keySet())
    {
      lst.add(new SimpleEntry<Integer, Integer>(i,j));
    }


    return lst;
  }

}
