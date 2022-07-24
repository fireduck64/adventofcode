import java.util.*;

public class CircleList<V>
{
  private HashMap<V, ListRecord> memo;

  private ListRecord first;

  public CircleList(List<V> vals, boolean index_vals)
  {
    if (index_vals)
    {
      memo = new HashMap<V, ListRecord >(16,0.5f);
    }

    ListRecord last = null;

    for(V val : vals)
    {
      if (last == null)
      {
        ListRecord lr = new ListRecord(val);
        lr.next = lr;
        lr.prev = lr;
        last = lr;

        first = lr;
      }
      else
      {
        last = last.insertNext(val);
      }
    }
  }


  public ListRecord getFirst(){return first;}

  public ListRecord find(V val)
  {
    return memo.get(val);
  }

	public class ListRecord
	{
		V v;
		ListRecord next;
    ListRecord prev;

		public ListRecord(V v)
		{
			this.v =v;
      if (memo != null) memo.put(this.v, this);
		}

		public V removeNext()
		{
			V next_val = next.v;

      next.next.prev = this;
			next = next.next;

      if(memo!=null) memo.remove(next_val);

			return next_val;
		}

		public ListRecord insertNext(V nv)
		{
			ListRecord nr = new ListRecord(nv);

      this.next.prev = nr;
			nr.next = next;
      nr.prev = this;
			this.next = nr;

      return nr;

		}

   

	}


}
