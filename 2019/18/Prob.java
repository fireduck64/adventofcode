import java.io.FileInputStream;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  Map2D<Character> map = new Map2D<Character>((char)0);

  BitSet all_keys = new BitSet();

  HashMap<Map.Entry<Long, Long>, HashMap<BitSet, Integer> > low_step_map = new HashMap<>();

  Random rnd = new Random();

  TreeMap<Double, State> queue = new TreeMap<>();


  public Prob(Scanner scan)
  {

    int y=0;

    State start = new State();
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
          start.x = x;
          start.y = y;
        }
      }

      y++;
    }

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
    long x;
    long y;
    int steps;
    BitSet keys;

    public State()
    {
      keys = new BitSet();
    }
    public State(State src)
    {
      x = src.x;
      y = src.y;
      steps = src.steps;
      keys = new BitSet();
      keys.or(src.keys);
    }

    public String toString()
    {
      return String.format("(%d, %d) %d - %s", x,y,steps,keys);
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
      if (isKey(x,y))
      {
        keys.set(map.get(x,y) - 'a');
      }
      if (keys.cardinality() == all_keys.cardinality())
      {
        return true;
      }

      if (doSuck(this)) return false;
      //System.out.println(toString());
      expand(1,0);
      expand(-1,0);
      expand(0,1);
      expand(0,-1);

      return false;
      
    }

    private void expand(int dx, int dy)
    {
      if (isPass(x+dx, y+dy, keys))
      {
        State n = new State(this);
        n.x += dx;
        n.y += dy;
        n.steps ++;

        queue.put(n.getCost(), n);
      }
    }

  }


  public boolean doSuck(State s)
  {
    Map.Entry<Long, Long> me = new SimpleEntry<Long,Long>(s.x,s.y);

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
