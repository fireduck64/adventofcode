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

  int rolls;

  int next_roll=1;

  public Prob(Scanner scan)
  {
    int p1_loc_s = Integer.parseInt(Tok.en(scan.nextLine(), " ").get(4));
    int p2_loc_s = Integer.parseInt(Tok.en(scan.nextLine(), " ").get(4));

    {
      int p1_loc=p1_loc_s;
      int p2_loc=p2_loc_s;
      int p1_score=0;
      int p2_score=0;
      while(true)
      {
        p1_loc += getRolls();
        while (p1_loc > 10) p1_loc -= 10;
        p1_score += p1_loc;
        if (p1_score>=1000) break;

        p2_loc += getRolls();
        while (p2_loc > 10) p2_loc -= 10;
        p2_score += p2_loc;
        if (p2_score>=1000) break;
      }

      int lose_score = Math.min(p1_score, p2_score);
      System.out.println("Part 1: " + lose_score * rolls);
    }

    TreeMap<Integer, Long> r = rec(0,0,p1_loc_s,p2_loc_s,1, 0, 3);
    System.out.println("Q: " + r);
    System.out.println("Part 2: " + Math.max(r.get(1), r.get(2)));
    System.out.println("Memo states: " + memo.size());
  }


  public int getRolls()
  {
    return getNextRoll() + getNextRoll() + getNextRoll();
  }

  public int getNextRoll()
  {
    int r = next_roll;

    rolls++;
    next_roll++;
    if (next_roll > 100) next_roll=1;
    return r;

  }

  TreeMap<String, TreeMap<Integer, Long>> memo = new TreeMap<>();

  TreeMap<Integer, Long> rec(int p1_score, int p2_score, int p1_loc, int p2_loc, int turn, int roll_sum, int roll_rem)
  {
    String state = String.format("%d,%d,%d,%d,%d,%d,%d", p1_score, p2_score, p1_loc, p2_loc, turn, roll_sum, roll_rem);
    if (memo.containsKey(state)) return memo.get(state);

    TreeMap<Integer, Long> out = new TreeMap<>();
    out.put(1, 0L);
    out.put(2, 0L);

    if (roll_rem > 0)
    {
      for(int d=1; d<=3; d++)
      {
        add(out, rec(p1_score, p2_score, p1_loc, p2_loc, turn, roll_sum+d, roll_rem-1));
      }

    }
    else
    {
      if (turn==1)
      {
        p1_loc += roll_sum;
        while (p1_loc > 10) p1_loc -= 10;
        p1_score += p1_loc;
        if (p1_score >= 21) add(out, 1, 1L);
        else add(out, rec(p1_score, p2_score, p1_loc, p2_loc, 2, 0, 3));
      }
      else
      {
        p2_loc += roll_sum;
        while (p2_loc > 10) p2_loc -= 10;
        p2_score += p2_loc;
        if (p2_score >= 21) add(out, 2, 1L);
        else add(out, rec(p1_score, p2_score, p1_loc, p2_loc, 1, 0, 3));
      }
    }

    memo.put(state, out);

    return out;

  }

  public void add(TreeMap<Integer, Long> col, int player, long wins)
  {
    col.put(player, col.get(player)+wins);
  }
  public void add(TreeMap<Integer, Long> col, TreeMap<Integer, Long> add)
  {
    for(int k : add.keySet())
    {
      col.put(k, col.get(k) + add.get(k));

    }

  }
  

}
