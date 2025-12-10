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

  public void swap(ListRecord a, ListRecord b)
  {
    V v_a = a.v;
    V v_b = b.v;

    a.v = v_b;
    b.v = v_a;
    if (memo != null) memo.put(a.v, a);
    if (memo != null) memo.put(b.v, b);

  }
  public void moveNSwap(ListRecord lr, long n, long sz)
  {
    n = n % sz;
    //while (n >= sz) n-=sz;
    //while (-n >= sz) n+=sz;
    ListRecord cur = lr;

    //while (n >= sz) n-=sz;
    //while (-n >= sz) n+=sz;
    //if (n > sz) n--;
    //if (-n < sz) n++;


    if (n > 0)
    {
      for(long i=0; i<n; i++)
      {
        swap(cur, cur.next);
        cur = cur.next;
      }

    }
    if (n < 0)
    {
      for(long i=0; i<Math.abs(n); i++)
      {
        swap(cur, cur.prev);
        cur = cur.prev;
      }
    }
    
  }

  public void moveNIns(ListRecord lr, long n, long sz)
  {
    n = n % sz;
    V v = lr.v;
    ListRecord cur;
    if (n > 0) cur = lr.next;
    else cur = lr.prev;

    ListRecord rm = lr;
    rm.remove();

    if (n > 0)
    { 
      for(int i=0; i<n-1; i++) cur = cur.next;
    }
    else if (n == 0)
    {

    }
    else if (n < 0)
    {
      for(int i=0; i<Math.abs(n); i++) cur = cur.prev;
    }
    
    cur.insertNext(v);

  }


  public ListRecord getFirst(){return first;}

  public ListRecord find(V val)
  {
    return memo.get(val);
  }

  public ListRecord seek(ListRecord lr, int n)
  {
    ListRecord cur = lr;

    if (n > 0)
    { 
      for(int i=0; i<n; i++) cur = cur.next;
    }
    return cur;

  }

  public void print(ListRecord lr)
  {
    ListRecord cur = lr;
    System.out.print(" " + cur.v);
    while(cur.next != lr)
    {
      cur = cur.next;
      System.out.print(" " + cur.v);
    }
    System.out.println();

  }
  public LinkedList<V> list(ListRecord lr)
  {
    LinkedList<V> out = new LinkedList<>();
    ListRecord cur = lr;
    out.add(cur.v);
    while(cur.next != lr)
    {
      cur = cur.next;
      out.add(cur.v);
    }
    return out;

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
    public void remove()
    {
      prev.next = next;
      next.prev = prev;
      
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
