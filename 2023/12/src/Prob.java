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

  public Prob(Scanner scan)
  {
    long p1 = 0;
    long p2 = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      String springs = line.split(" ")[0];

      ArrayList<Integer> dmg_lst = new ArrayList<>();
      dmg_lst.addAll( Tok.ent(line.split(" ")[1], ",") );

      //opt_map.clear();
      //System.out.println("Problem: " + springs);
      //p1 += countOptions(springs, dmg_lst);
      p1 += rec(springs, new LinkedList<Integer>(dmg_lst), 0);


      String p2_sp = "";
      ArrayList<Integer> dmg_lst2=new ArrayList<>();
      for(int i=0; i<5; i++)
      {
        if (i > 0) p2_sp += "?";
        p2_sp += springs;
        dmg_lst2.addAll(dmg_lst);
      }
      //p2 += countOptions(p2_sp, dmg_lst2);
      p2 += rec(p2_sp, new LinkedList<Integer>(dmg_lst2), 0);

    }
    System.out.println("P1: " + p1);
    System.out.println("P2: " + p2);
  }

  
  HashMap<String, Long> opt_map = new HashMap<>();

  // This is my supposed "cleaner" solution after thinking about it over night
  long rec(String rem_str, LinkedList<Integer> rem_dmg_lst, int dmg_run_len)
  {
    rem_dmg_lst = new LinkedList<Integer>(rem_dmg_lst);
    if (rem_dmg_lst.size() == 0)
    if (dmg_run_len > 0)
    {
      return 0L;
    }

    // End of stirng, do all the checks
    if (rem_str.length() == 0)
    {
      if (dmg_run_len > 0)
      {
        if (dmg_run_len == rem_dmg_lst.get(0))
        {
          dmg_run_len = 0;
          rem_dmg_lst.pollFirst();
        }
        else
        {
          return 0L;
        }
      }
      if (rem_dmg_lst.size() == 0)
      {
        if (dmg_run_len == 0)
        {
          return 1L;
        }
        return 0L;

      }

      return 0L;
    }

    String key = rem_str + "/" + rem_dmg_lst +"/"+dmg_run_len;
    if (opt_map.containsKey(key)) return opt_map.get(key);

    char z = rem_str.charAt(0);
    if (z == '#')
    {
      // Just continue the run
      return rec(rem_str.substring(1), rem_dmg_lst, dmg_run_len+1);
    }
    if (z == '.')
    {
      
      if (dmg_run_len > 0)
      {
        // Existing run must end correctly
        if (dmg_run_len == rem_dmg_lst.get(0))
        {
          dmg_run_len = 0;
          rem_dmg_lst.pollFirst();
        }
        else
        {
          return 0L;
        }
 
      }
      // Continue the non-run
      return rec(rem_str.substring(1), rem_dmg_lst, 0);
    }
    if (z=='?')
    {
      long v = 0;
      v+= rec("." + rem_str.substring(1), rem_dmg_lst, dmg_run_len);
      v+= rec("#" + rem_str.substring(1), rem_dmg_lst, dmg_run_len);
      opt_map.put(key, v);
    
      return v;

    }

    E.er();
    return 0L;

  }

  long countOptions(String springs, List<Integer> dmg_lst)
  {
    //System.out.println("" + springs + " " + dmg_lst);
    List<Integer> v = valid(springs, dmg_lst);
    if (v==null) return 0L;

    //System.out.println("Call " + springs + "d: " + dmg_lst + " v:" + v);

    int idx = springs.indexOf('?');
    if (idx < 0)
    {
      return 1L;
    }
    char pre = ':';
    if (idx > 0) pre = springs.charAt(idx-1);
    String key = "" + pre + "/" + idx +"/" + dmg_lst.toString() + "/" + v.toString() ;
    
    //System.out.println(key);

    // If we aren't at a good unit, we might be in the middle of a series of broken units
    // so we don't want to check the memo, because we aren't tracking how long of a series we are in.
    if (pre == '.')
    if (opt_map.containsKey(key)) return opt_map.get(key);

    long opt = countOptions(replace(springs, '#', idx), dmg_lst);
    opt+=countOptions(replace(springs, '.', idx), dmg_lst);

    //String spring2 = replace(springs, '.', idx);


    // I have no idea why this didn't work but it didn't
    /*List<Integer> v2 = valid(spring2, dmg_lst);
    if (v2 != null)
    {
      //System.out.println(v2);
      int valid_size = v2.size();
      opt += countOptions(spring2.substring(idx),
        dmg_lst.subList(valid_size, dmg_lst.size()));
    }*/

    opt_map.put(key, opt);

    return opt;

  }


  String replace(String in, char z, int idx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(in.substring(0, idx));
    sb.append(z);
    sb.append(in.substring(idx+1));
    return sb.toString();

  }

  // Checks the string against the damage list and returns null if it is impossible
  // other wise returns the elements of the dmg_lst that are covered already before the
  // first '?'
  ArrayList<Integer> valid(String springs, List<Integer> dmg_lst)
  {
    ArrayList<Integer> found_lst = new ArrayList<>();

    int cur_count = 0;
    boolean early=false;
    for(int i=0; i<springs.length(); i++)
    {
      char z = springs.charAt(i);
      if (z == '.')
      {
        if (cur_count > 0) found_lst.add(cur_count);
        cur_count =0;

      }
      else if (z == '#')
      {
        cur_count++;

      }
      else if (z == '?')
      {
        early=true;
        break;
      }
    }
    if (!early)
    {
      if (cur_count > 0) found_lst.add(cur_count);

      if (found_lst.equals(dmg_lst))
      {
        return found_lst;
      }
      return null;
      
    }
    else
    {
      if (found_lst.size() > dmg_lst.size()) return null;

      if (dmg_lst.subList(0, found_lst.size()).equals(found_lst))
      {
        return found_lst;
      }
      return null;

    }

  }

}
