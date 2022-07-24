import java.util.TreeMap;

public class TreeeMap<K> extends TreeMap<K,Long>
{
  public TreeeMap()
  {
    super();
  }

  public void add(K k, long val)
  {
    if (containsKey(k))
    {
      put(k, get(k) + val);
    }
    else
    {
      put(k, val);
    }
  }
  public long getL(K k)
  {
    if (containsKey(k)) return get(k);

    return 0L;
  }

}
