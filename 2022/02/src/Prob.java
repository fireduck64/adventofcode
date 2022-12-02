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
    int score_total = 0;
    int score_total_p2 = 0;

    // Rock A X
    // Paper B Y
    // Scissors C Z
    while(scan.hasNext())
    {
      String op = scan.next();
      String me = scan.next();

      score_total+=getScore(op, me);
      score_total_p2+=getScoreP2(op, me);

    }
    System.out.println("Part 1: " + score_total);
    System.out.println("Part 2: " + score_total_p2);

  }

  public int getScore(String op, String me)
  {
    if (me.equals("X")) me="A";
    if (me.equals("Y")) me="B";
    if (me.equals("Z")) me="C";

    int score = 0;
    if (me.equals("A")) score+=1;
    if (me.equals("B")) score+=2;
    if (me.equals("C")) score+=3;

    if (op.equals(me))
    {
      score+=3;
      return score;
    }
    if (me.equals("A"))
    {
      if (op.equals("B")) return score;
      if (op.equals("C")) return score+6;

    }
    if (me.equals("B"))
    {
      if (op.equals("A")) return score+6;
      if (op.equals("C")) return score;

    }
    if (me.equals("C"))
    {
      if (op.equals("A")) return score;
      if (op.equals("B")) return score+6;

    }

    throw new RuntimeException("NO");



  }
  public int getScoreP2(String op, String me)
  {
    if (me.equals("Y"))
    {
      return getScore(op,op);
    }
    if (me.equals("X"))
    {
      int low = 100;
      low = Math.min(low, getScore(op, "X"));
      low = Math.min(low, getScore(op, "Y"));
      low = Math.min(low, getScore(op, "Z"));

      return low;

    }
    if (me.equals("Z"))
    {
      int h = 0;
      h = Math.max(h, getScore(op, "X"));
      h = Math.max(h, getScore(op, "Y"));
      h = Math.max(h, getScore(op, "Z"));
      return h;
    }

    throw new RuntimeException("NO");

  }
}


