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

  LinkedList<Integer> in_dp1=new LinkedList<>();
  LinkedList<Integer> in_dp2=new LinkedList<>();;

  public Prob(Scanner scan)
  {
    
    boolean reading_p2=false;
    for(String line : In.lines(scan))
    {
      if (line.startsWith("Player 2"))
      {
        reading_p2=true;
      }
      else if (line.length() == 0)
      {

      }
      else if (line.startsWith("Player 1"))
      {

      }
      else
      {
        int v = Integer.parseInt(line);
        if (!reading_p2) in_dp1.add(v);
        else in_dp2.add(v);

      }
    }

    { // part 1
      LinkedList<Integer> dp1=new LinkedList<>();
      LinkedList<Integer> dp2=new LinkedList<>();;
      dp1.addAll(in_dp1);
      dp2.addAll(in_dp2);

      System.out.println("Player1: " + dp1);
      System.out.println("Player2: " + dp2);

      while((dp1.size() != 0) && (dp2.size() != 0))
      {
        round(dp1, dp2);
      }

      LinkedList<Integer> win = new LinkedList<>();
      win.addAll(dp1);
      win.addAll(dp2);

      System.out.println("Part 1: " + score(win));
    }

    System.out.println("Part 2: " + score(recCombat(in_dp1, in_dp2).deck));



  }

  public void round(LinkedList<Integer> dp1, LinkedList<Integer> dp2)
  {
    int p1 = dp1.poll();
    int p2 = dp2.poll();

    if (p1 > p2)
    {
      dp1.add(p1);
      dp1.add(p2);
    }
    else
    {
      dp2.add(p2);
      dp2.add(p1);
    }



  }

  public int score(List<Integer> deck)
  {
    LinkedList<Integer> win = new LinkedList<>();
    win.addAll(deck);
    int part1=0;
    int v=1;
    while(win.size() > 0)
    {
      int card = win.pollLast();
      part1+=v*card;
      v++;
    }
    return part1;

  }


  public GameResult recCombat(LinkedList<Integer> dp1, LinkedList<Integer> dp2)
  {

    HashSet<String> memo = new HashSet<>();
    while(true)
    {

      String key = dp1.toString() + "/" + dp2.toString();
      if (memo.contains(key))
      { // p1 wins
        return new GameResult(1, dp1);
      }
      memo.add(key);
      //System.out.println(key);
 
      int p1 = dp1.poll();
      int p2 = dp2.poll();

      if ((dp1.size() >= p1) && (dp2.size() >= p2))
      {  //sub game
        LinkedList<Integer> sub1 = new LinkedList<>();
        sub1.addAll(dp1.subList(0, p1));
        
        LinkedList<Integer> sub2 = new LinkedList<>();
        sub2.addAll(dp2.subList(0, p2));

        GameResult r = recCombat(sub1, sub2);
        if (r.winner == 1)
        {
          dp1.add(p1);
          dp1.add(p2);
        }
        else
        {
          dp2.add(p2);
          dp2.add(p1);
        }
      }
      else
      { //regular round

        if (p1 > p2)
        {
          dp1.add(p1);
          dp1.add(p2);
        }
        else
        {
          dp2.add(p2);
          dp2.add(p1);
        }

      }

      if (dp1.size() == 0) return new GameResult(2, dp2);
      if (dp2.size() == 0) return new GameResult(1, dp1);

    }

    
  }

  public class GameResult
  {
    int winner;
    LinkedList<Integer> deck=new LinkedList<>();

    public GameResult(int winner, List<Integer> deck)
    {
      this.winner = winner;
      this.deck.addAll(deck);

    }


  }

}
