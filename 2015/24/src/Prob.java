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
  ArrayList<Integer> boxes=new ArrayList<>();

  long best_qe=0;
  int best_count=1000000;

  int tower_size;

  public Prob(Scanner scan)
  {
    while(scan.hasNextInt()) boxes.add(scan.nextInt());

    int sum = 0;
    for(int n : boxes) sum+=n;
    tower_size = sum / 3;

    rec(0, ImmutableList.of(0,0,0), 1L, 0); 
    System.out.println("Part 1: " + best_qe);
    
    tower_size = sum / 4;
    visit.clear();
    best_qe=0;
    best_count=1000000;
    rec(0, ImmutableList.of(0,0,0,0), 1L, 0); 
    System.out.println("Part 2: " + best_qe);
  }

  HashSet<String> visit = new HashSet<>(8192, 0.5f);

  public List<Integer> sort(List<Integer> grps)
  {
    int first = grps.get(0);
    ArrayList<Integer> o=new ArrayList<>();
    for(int i=1; i<grps.size(); i++)
    {
      o.add(grps.get(i));

    }
    Collections.sort(o);

    ArrayList<Integer> lst = new ArrayList<>();
    lst.add(first);
    lst.addAll(o);
    return lst;

  }


  public void rec(int pos, List<Integer> grps, long qe, int g1count)
  {
    if (best_count < 1000)
    if (g1count > best_count)
    {
      return;
    }
    for(int i=0; i<grps.size(); i++)
    {
      if (grps.get(i) > tower_size) return;
    }

    grps = sort(grps);

    String key = "" + pos + "/" + grps.toString() + "/" + qe + "/" + g1count;
    if (pos == boxes.size())
    {
      for(int i=0; i<grps.size(); i++)
      {
        if (grps.get(i) != tower_size) return;
      }
        //System.out.println("Sol: " + key);
        if (g1count < best_count)
        {
          best_qe = qe;
          best_count = g1count;
        }
        if (g1count == best_count)
        {
          if (qe < best_qe)
          {
            best_qe = qe;
            best_count = g1count;
          }
        }
      
      return;
    }
      
    if (pos < 2)
    {
      System.out.println("Working: " + key);
    }

    if (visit.contains(key)) return;
    visit.add(key);

    for(int i=0; i<grps.size(); i++)
    {
      int box = boxes.get(pos);

      long nqe = qe;
      int ng1 = g1count;
      if (i==0)
      {
        ng1++;
        nqe*=box;
      }

      ArrayList<Integer> nlst = new ArrayList<>();
      nlst.addAll(grps);

      nlst.set(i, nlst.get(i) + box);
      rec(pos+1, nlst, nqe, ng1);
    }

  }

}
