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

  TreeMap<Integer, Card> cards = new TreeMap<>();

  public Prob(Scanner scan)
  {
    int p1 = 0;
    for(String line : In.lines(scan))
    {
      Card c = new Card(line);
      p1 += c.score;

      cards.put(c.card_id, c);
    }
    System.out.println(p1);

    long p2=0;

    for(int c : cards.keySet())
    {
      p2+= getCount(c);
    }
    System.out.println(p2);
  }

  public long getCount(int card)
  {
    int m = cards.get(card).matches;

    if (m == 0) return 1L;

    long total = 1L;
    for(int i=1; i<=m; i++)
    {
      total += getCount(card + i);
    }
    return total;

  }

  public class Card
  {
    int card_id ;
    ArrayList<Integer> win=new ArrayList<>();
    ArrayList<Integer> ours = new ArrayList<>();
    int score=0;
    int matches=0;
    public Card(String line)
    {
      List<Integer> num = Tok.ent(line.replace(":",""), " ");
      card_id = num.get(0);

      for(int i=1; i<=10; i++)
      {
        win.add(num.get(i));

      }
      for(int i=11; i<num.size(); i++)
      {
        ours.add(num.get(i));
      }

      System.out.println("" + win.size() + " " + ours.size());
      System.out.println("" + win + " " + ours);

      for(int i=0; i<win.size(); i++)
      for(int j=0; j<ours.size(); j++)
      {
        
        if (win.get(i) == ours.get(j))
        {
          matches++;
          if (score == 0) score=1;
          else score = score * 2;

        }


      }
    }

  }

}
