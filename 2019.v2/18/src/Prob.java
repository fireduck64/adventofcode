import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Character> map=new Map2D<Character>(' ');
  TreeSet<Character> known_keys=new TreeSet<>();

  public Prob(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(map, scan);

    map.print();
    Point start = null;
    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      if (isKey(z))
        known_keys.add(z);
      if (z=='@') start =p;

    }
    System.out.println("There are " + known_keys.size() + " keys");
    System.out.println("Starting at: " + start);

    SS ss = (SS) Search.search(new SS(ImmutableSet.of(start), 0, ImmutableSet.of(), -1));
    System.out.println("Part 1: " + ss.getCost());

    // Part 2
    for(Point p : map.getAdj(start, true))
    {
      map.set(p, '@');
    }
    for(Point p : map.getAdj(start, false))
    {
      map.set(p, '#');
    }

    LinkedList<Point> start_list=new LinkedList<>();
    for(Point p : map.getAdj(start, true))
    {
      if (map.get(p)=='@') start_list.add(p);
    }
    SS ss2 = (SS) Search.searchPara(new SS(start_list, 0, ImmutableSet.of(), -1));
    System.out.println("Part 2: " + ss2.getCost());



  }


  public class SS extends State
  {
    List<Point> pos;
    int moves;
    TreeSet<Character> keys;
    int action_idx;

    public SS(Collection<Point> pos_in, int moves, Set<Character> keys, int action_idx)
    {
      this.pos = new ArrayList<>(); pos.addAll(pos_in);
      this.moves = moves;
      this.keys = new TreeSet<>(); this.keys.addAll(keys);
      this.action_idx=action_idx;
    }

    public boolean isTerm()
    {
      return keys.size() == known_keys.size();
    }
    public double getCost()
    {
      return moves;

    }
    public String toString()
    {
      return "" + pos.toString() + " " + keys.toString();
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();

      for(int i=0; i<pos.size(); i++)
      {
        if ((action_idx < 0) || (action_idx==i))
        for(Point p : map.getAdj(pos.get(i), false))
        {
          if (isWalkable(p, keys))
          {
            Set<Character> next_keys = keys;
            int next_action=i;
            if (isKey(map.get(p)))
            {
              next_keys = new TreeSet<>();
              next_keys.addAll(keys);
              next_keys.add(map.get(p));
              next_action=-1;
            }
            ArrayList<Point> new_pos = new ArrayList<>();
            new_pos.addAll(pos);
            new_pos.set(i, p);
            lst.add(new SS(new_pos, moves+1, next_keys, next_action));
          }
        }
      }
      return lst;
    }


  }
  public boolean isWalkable(Point p, Set<Character> keys)
  {
    char z = map.get(p);
    if (z=='.') return true;
    if (isKey(z)) return true;
    if (isDoor(z))
    {
      char k = Character.toLowerCase(z);
      if (keys.contains(k)) return true;
    }
    return false;

  }
  public boolean isKey(char z)
  {
      if ('a' <= z)
      if (z <= 'z')
        return true;
      return false;
  }
  public boolean isDoor(char z)
  {
      if ('A' <= z)
      if (z <= 'Z')
        return true;
      return false;
  }


}
