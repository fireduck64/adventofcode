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
  int total_elf;

  public Prob(Scanner scan)
  {
    total_elf = scan.nextInt();

    ArrayList<Integer> lst = new ArrayList<>();
    for(int i=1; i<=total_elf; i++) lst.add(i);

    { // part 1
      CircleList<Integer> cir = new CircleList<Integer>(lst, false);

      CircleList<Integer>.ListRecord cur = cir.getFirst();
      while(true)
      {
        cur.removeNext();
        cur = cur.next;
        if (cur == cur.next) break;
      }
      System.out.println("Part 1: " + cur.v);
    }

    { // Part 2 - weird and fast
      CircleList<Integer> cir = new CircleList<Integer>(lst, false);

      int mid = lst.get(lst.size() / 2);


      CircleList<Integer>.ListRecord cur = cir.getFirst();
      int wobble=0;

      CircleList<Integer>.ListRecord opp = cur;
      while(opp.v != mid)
      {
        opp=opp.next;
      }
      while(true)
      {
        //System.out.print("cur: " + cur.v);
        //System.out.println(" opp: " + opp.v);
        cur = cur.next;

        CircleList<Integer>.ListRecord wop = opp.next;
        opp.prev.removeNext();
        // As we move delete a record and move cur, one side
        // from cur to opp get smaller (as cur advances)
        // and other side gets longer, but only every other time
        // This was found by trial and error, to get this solution
        // to match up with p2 slow below for smallish numbers.
        // then it worked for large numbers.
        if (wobble % 2 == 1) wop = wop.next;
        opp=wop;
        wobble++;
        //opp = opp.prev;

        if (cur == cur.next) break;
      }
      System.out.println("Part 2: " + cur.v);

    }
    { // Part 2 - right but slow
      TreeSet<Integer> set = new TreeSet<>();
      set.addAll(lst);
      int cur = set.first();
      while(set.size() > 1)
      {
        int opp = findOpp(set, cur);

        set.remove(opp);
        cur = findNext(set, cur);
        if (set.size() % 1000 ==0) System.out.println("  size: " + set.size());
         
      }
      System.out.println("Part 2b: " + set);
    }



  }

  public int findOpp(TreeSet<Integer> set, int cur)
  {
    ArrayList<Integer> lst = new ArrayList<Integer>();
    lst.addAll(set);
    int idx_cur =0;
    for(int i=0; i<lst.size(); i++)
    {
      if (lst.get(i) == cur) idx_cur=i;
    }

    int idx_opp = (idx_cur + lst.size() / 2) % lst.size();
    return lst.get(idx_opp);

  }
  public int findNext(TreeSet<Integer> set, int cur)
  {
    Integer ii = set.higher(cur);
    if (ii == null) return set.first();

    return ii;

  }

}
