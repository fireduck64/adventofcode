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
    LinkedList<Integer> p1 = new LinkedList<>();
    LinkedList<Integer> p2 = new LinkedList<>();
    scan.nextLine();
    boolean in_p2=false;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.trim().length()==0)
      {
        if (!in_p2)
        {
          in_p2=true;
          scan.nextLine();
        }
        else
        {
          break;
        }

      }
      else
      {
        int v = Integer.parseInt(line);
        if (in_p2) p2.add(v);
        else p1.add(v);

      }
    }

    System.out.println(" " + p1.size() + " " + p2.size());

    { // part 1

      ArrayList<Integer> winner = new ArrayList<>();
      LinkedList<Integer> p1_part1 = new LinkedList<>();
      LinkedList<Integer> p2_part1 = new LinkedList<>();
      p1_part1.addAll(p1);
      p2_part1.addAll(p2);
      if(play1(p1_part1,p2_part1))
      {
        winner.addAll(p1_part1);
      }
      else
      {
        winner.addAll(p2_part1);
      }
      
      System.out.println("Part 1 Winner: " + winner);
      System.out.println(getScore(winner));

    }

    { // part 2
      ArrayList<Integer> winner = new ArrayList<>();
    
      if (play(p1, p2))
      {
        winner.addAll(p1);
      }
      else
      {
        winner.addAll(p2);
      }

      System.out.println("Winner: " + winner);
      System.out.println(getScore(winner));
    }

  }

  public int getScore(List<Integer> winner)
  {
    int s = 0;
    for(int i=0; i<winner.size(); i++)
    {
      s+=winner.get(i) * (winner.size() - i);
    }
    return s;
  }


  /**
   * return true is p1 win
   */
  public boolean play(LinkedList<Integer> p1, LinkedList<Integer> p2)
  {

    TreeSet<String> memo = new TreeSet<>();

    while ((p1.size() >0) && (p2.size() > 0))
    {
      String k = p1.toString()+"/" + p2.toString();
      if (memo.contains(k))
      {
        return true;
      }
      memo.add(k);

      int c1 = p1.pollFirst();
      int c2 = p2.pollFirst();

      if ((c1 <= p1.size()) && (c2 <= p2.size()))
      {
        LinkedList<Integer> p1_sub = new LinkedList<>();
        LinkedList<Integer> p2_sub = new LinkedList<>();


        p1_sub.addAll(p1.subList(0, c1));
        p2_sub.addAll(p2.subList(0, c2));

        if (play(p1_sub, p2_sub))
        { // p1 wins

          p1.add(c1);
          p1.add(c2);
        }
        else
        { // p2 wins

          p2.add(c2);
          p2.add(c1);
        }

      }
      else
      {
        if (c1 > c2)
        {
          p1.add(c1);
          p1.add(c2);
        }
        else
        {
          p2.add(c2);
          p2.add(c1);

        }
      }

    }

    return (p1.size() > 0);


  }

  public boolean play1(LinkedList<Integer> p1, LinkedList<Integer> p2)
  {

    while ((p1.size() >0) && (p2.size() > 0))
    {
      int c1 = p1.pollFirst();
      int c2 = p2.pollFirst();

      {
        if (c1 > c2)
        {
          p1.add(c1);
          p1.add(c2);
        }
        else
        {
          p2.add(c2);
          p2.add(c1);

        }
      }

    }

    return (p1.size() > 0);


  }


}
