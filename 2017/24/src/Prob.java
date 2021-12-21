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

  ArrayList<Point> parts=new ArrayList<>();

  public Prob(Scanner scan)
  {
    TreeSet<Integer> all_parts = new TreeSet<>();
    for(String line : In.lines(scan))
    {
      List<Integer> l = Tok.ent(line, "/");
      parts.add(new Point(l.get(0), l.get(1)));
      all_parts.add(all_parts.size());
    }

    System.out.println("Part 1: " + rec(0, 0, all_parts, ImmutableList.of()));
    double v = rec2(0, 0, all_parts, ImmutableList.of());
    v = v - Math.floor(v);
    long p2=Math.round(v*1e6);
    System.out.println("Part 2: " + p2);

  }

  
  HashMap<String, Long> memo = new HashMap<>();  

  long rec(long end, long score, TreeSet<Integer> rem, List<Point> path)
  {
    String state = String.format("%d %d %s",end,score,rem);
    if (memo.containsKey(state)) return memo.get(state);
    //System.out.println(path);
    //System.out.println("  " + state);
    long best = score;

    for(int p_id : rem)
    {
      Point p = parts.get(p_id);
      //System.out.println("    - p:" + p + " e:" + end);
      if (p.x == end)
      {
        TreeSet<Integer> l2=new TreeSet<>();
        l2.addAll(rem);
        l2.remove(p_id);
        LinkedList<Point> p2 = new LinkedList<>();
        p2.addAll(path);
        p2.add(p);
        best = Math.max(best, rec(p.y, score+p.x+p.y, l2, p2));
      }
      if (p.y == end)
      {
        TreeSet<Integer> l2=new TreeSet<>();
        l2.addAll(rem);
        l2.remove(p_id);
        LinkedList<Point> p2 = new LinkedList<>();
        p2.addAll(path);
        p2.add(p);
        best = Math.max(best, rec(p.x, score+p.x+p.y, l2, p2));
      }

    }
    memo.put(state, best);
    return best;
  }

  HashMap<String, Double> memo2 = new HashMap<>();  
  // returns LEN.00STR
  double rec2(long end, long score, TreeSet<Integer> rem, List<Point> path)
  {
    String state = String.format("%d %d %s",end,score,rem);
    if (memo2.containsKey(state)) return memo2.get(state);
    //System.out.println(path);
    //System.out.println("  " + state);
    double best = (path.size()*1.0) + score/1e6;

    for(int p_id : rem)
    {
      Point p = parts.get(p_id);
      //System.out.println("    - p:" + p + " e:" + end);
      if (p.x == end)
      {
        TreeSet<Integer> l2=new TreeSet<>();
        l2.addAll(rem);
        l2.remove(p_id);
        LinkedList<Point> p2 = new LinkedList<>();
        p2.addAll(path);
        p2.add(p);
        best = Math.max(best, rec2(p.y, score+p.x+p.y, l2, p2));
      }
      if (p.y == end)
      {
        TreeSet<Integer> l2=new TreeSet<>();
        l2.addAll(rem);
        l2.remove(p_id);
        LinkedList<Point> p2 = new LinkedList<>();
        p2.addAll(path);
        p2.add(p);
        best = Math.max(best, rec2(p.x, score+p.x+p.y, l2, p2));
      }

    }
    memo2.put(state, best);
    return best;
  }


}
