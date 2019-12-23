import java.io.FileInputStream;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class ProbB
{
  public static void main(String args[]) throws Exception
  {
    new ProbB(new Scanner(new FileInputStream(args[0])));

  }

  Map2D<Character> map = new Map2D<Character>((char)0);

  BitSet all_keys = new BitSet();

  HashMap<Map.Entry<Long, Long>, HashMap<BitSet, Integer> > low_step_map = new HashMap<>();
  HashMap<String, HashMap<BitSet, Integer> > low_step_map_hash = new HashMap<>();

  Random rnd = new Random();

  TreeMap<Double, State> queue = new TreeMap<>();

  public ProbB(Scanner scan)
  {

    int y=0;

    State start = new State();
    int s_idx=0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();

      for(int x=0; x<line.length(); x++)
      {
        char c = line.charAt(x);

        map.set(x,y,c);
        if (isKey(x,y))
        {
          all_keys.set(c - 'a');
        }
        if (isStart(x,y))
        {
          start.x[s_idx] = x;
          start.y[s_idx] = y;
          s_idx++;
        }
      }

      y++;
    }
    System.out.println("Found starts: " + s_idx);

    queue.put(start.getCost(), start);

    int step = 0;

    while(queue.size() > 0)
    {
      Map.Entry<Double, State> me = queue.pollFirstEntry();
      State s = me.getValue();

      if (s.steps / 100 * 100 != step)
      {
        step=s.steps / 100 * 100;
        System.out.println(step);
      }

      if (s.expand())
      {
        System.out.println("All keys cost: " + s.steps);
        return;
      }
    }

  }


  public class State
  {
    long x[];
    long y[];
    int steps;
    BitSet keys;
    int last_r;

    public State()
    { 
      x = new long[4];
      y = new long[4];
      keys = new BitSet();
      last_r = -1;
    }
    public State(State src)
    {
      x = new long[4];
      y = new long[4];
      System. arraycopy(src.x, 0, x, 0, 4);
      System. arraycopy(src.y, 0, y, 0, 4);
      steps = src.steps;
      last_r = src.last_r;
      keys = new BitSet();
      keys.or(src.keys);
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      for(int r=0; r<4; r++)
      {
        sb.append(String.format("(%d, %d)", x[r], y[r]));
      }
      sb.append(String.format(" %d %d - %s", last_r, steps,keys));

      return sb.toString();

    }
    public String getHash()
    {
      StringBuilder sb = new StringBuilder();
      for(int r=0; r<4; r++)
      {
        sb.append(String.format("(%d, %d)", x[r], y[r]));
      }
      return HUtil.getHash(sb.toString());

    }

    public double getCost()
    {
      
      
      double s = steps * 1e6;
      s-=keys.cardinality();
      return s + rnd.nextDouble();
      

      /*double s = keys.cardinality() * -1e6;
      s+=steps;
      return s + rnd.nextDouble();*/

    }

    public boolean expand()
    {
      //System.out.println(toString());
      if (keys.cardinality() == all_keys.cardinality())
      {
        return true;
      }

      //if (doSuck(this)) return false;
      if (doSuckHash(this)) return false;

      if (last_r >= 0)
      {
        int i = last_r;
        expand(i, 1,0);
        expand(i, -1,0);
        expand(i, 0,1);
        expand(i, 0,-1);
      }
      else
      {
        for(int i=0; i<4; i++)
        {
          expand(i, 1,0);
          expand(i, -1,0);
          expand(i, 0,1);
          expand(i, 0,-1);
        }
      }

      return false;
      
    }

    private void expand(int r, int dx, int dy)
    {
      if (isPass(x[r]+dx, y[r]+dy, keys))
      {
        State n = new State(this);
        n.x[r] += dx;
        n.y[r] += dy;
        n.steps ++;
        n.last_r = r;

        if (isKey(n.x[r], n.y[r]))
        {
          int key = map.get(n.x[r], n.y[r]) -'a';
          if (!n.keys.get(key))
          {
            n.keys.set(key);
            n.last_r = -1;
          }
        }

        queue.put(n.getCost(), n);
      }
    }

  }


  public boolean doSuck(State s)
  {
    //for(int r=0; r<4; r++)
    int r = s.last_r;
    if (r < 0) return false;
    {
      Map.Entry<Long, Long> me = new SimpleEntry<Long,Long>(s.x[r],s.y[r]);

      HashMap<BitSet, Integer> m = low_step_map.get(me);
      if (m == null)
      {
        m = new HashMap<>();
        low_step_map.put(me, m);
      }

      if (m.containsKey(s.keys))
      {
        if (m.get(s.keys) <= s.steps) return true;
      }

      for(BitSet hs : m.keySet())
      {
        BitSet copy = new BitSet();
        copy.or(hs);
        copy.and(s.keys);
        if (copy.cardinality() == s.keys.cardinality())
        {
          int low = m.get(hs);
          if (low <= s.steps) return true;
        }

      }
      m.put(s.keys, s.steps);

    }
    return false;
  }

  public boolean doSuckHash(State s)
  {
    String hash = s.getHash();
      

    HashMap<BitSet, Integer> m = low_step_map_hash.get(hash);
    if (m == null)
    {
      m = new HashMap<>();
      low_step_map_hash.put(hash, m);
    }

    if (m.containsKey(s.keys))
    {
      if (m.get(s.keys) <= s.steps) return true;
    }

    for(BitSet hs : m.keySet())
    {
      BitSet copy = new BitSet();
      copy.or(hs);
      copy.and(s.keys);
      if (copy.cardinality() == s.keys.cardinality())
      {
        int low = m.get(hs);
        if (low <= s.steps) return true;
      }

    }
    m.put(s.keys, s.steps);

    return false;
  }


  public boolean isPass(long x, long y, BitSet keys)
  {
    Character c = map.get(x,y);
    if (c == null) return false;
    if (c =='#') return false;
    if (isDoor(x,y))
    {
      int door = c - 'A';
      if (keys.get(door)) return true;
      return false;
    }

    return true;
  }

  public boolean isStart(long x, long y)
  {
    Character c = map.get(x,y);
    if (c == null) return false;
    if (c =='@') return true;

    return false;
  }

  public boolean isKey(long x, long y)
  {
    Character c = map.get(x,y);
    if (c == null) return false;
    if ((c >= 'a') && (c <= 'z')) return true;

    return false;
    
  }

  public boolean isDoor(long x, long y)
  {
    Character c = map.get(x,y);
    if (c == null) return false;
    if ((c >= 'A') && (c <= 'Z')) return true;

    return false;
    
  }



}
