import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])),false);
    new Prob(new Scanner(new FileInputStream(args[0])),true);
  }

  Random rnd=new Random();

  ArrayList<Character> card_order = new ArrayList();
  TreeMap<Character, Character> order_map = new TreeMap<>();
  

  boolean part2=true;


  public Prob(Scanner scan, boolean part2)
  {
    this.part2=part2;

    card_order.add('A');
    card_order.add('K');
    card_order.add('Q');
    if (!part2) card_order.add('J');
    card_order.add('T');
    card_order.add('9');
    card_order.add('8');
    card_order.add('7');
    card_order.add('6');
    card_order.add('5');
    card_order.add('4');
    card_order.add('3');
    card_order.add('2');
    if (part2) card_order.add('J');

    for(int i=0; i<card_order.size(); i++)
    {
      char z = (char)('a' + i);
      order_map.put(card_order.get(i), z);
    }
    System.out.println(order_map);

    ArrayList<Hand> hands = new ArrayList<>();

    while(scan.hasNext())
    {
      String cards = scan.next();
      int bid = scan.nextInt();

      Hand h = new Hand(cards, bid);

      hands.add(h);
    }
    Collections.sort(hands);


    long p1 = 0;
    for(int i=0; i<hands.size(); i++)
    {
      int rank = hands.size() - i;
      long score = rank * hands.get(i).bid;
      p1 += score;

    }
    //System.out.println(hands);
    if (part2)
    {
      System.out.println("Part 2: " + p1);
    }
    if (!part2)
    {
      System.out.println("Part 1: " + p1);
    }




  }

  public class Hand implements Comparable<Hand>
  {
    String cards;
    TreeMap<Character, Integer> counts=new TreeMap<>();
    String rank;
    int bid;
    public Hand(String cards, int bid)
    {
      this.cards = cards;
      this.bid = bid;

      counts.put('J',0);
      for(int i=0; i<cards.length(); i++)
      {
        char z = cards.charAt(i);
        if (!counts.containsKey(z)) counts.put(z,0);

        counts.put( z, counts.get(z) + 1);
      }

      rank = toRankString();
      //System.out.println("" + cards + " " + rank);
    }
    public String toString()
    {
      return "" + cards + " " + rank + " " + bid;
    }

    public int compareTo(Hand o)
    {
      return rank.compareTo(o.rank);
    }

    public String toRankString()
    {
      String sb = "";
      sb += getKind(5);
      sb += getKind(4);
      sb += getFullHouse();
      sb += getKind(3);
      sb += getTwoPair();
      sb += getKind(2);

      sb += inOrderRankString();

      return sb;


    }

    public String getKind(int n)
    {
      if (part2)
      {
        int jokers = counts.get('J');
        for(char z : counts.keySet())
        {
          if (z != 'J')
          {
            if (counts.get(z) + jokers >= n) return inOrderRankString();
          }
          else
          {
            if (jokers >= n) return inOrderRankString();
          }
        }
      }
      else
      {
        for(char z : counts.keySet())
        {
          if (counts.get(z) >= n) return inOrderRankString();
        }
      }
      return "zzzzz";
    }

    public String getFullHouse()
    {
      int h3=0;
      int h2=0;
      for(char z : counts.keySet())
      {
        
        if (counts.get(z) == 2) h2++;
        if (counts.get(z) == 3) h3++;
      }
      int jokers = counts.get('J');
      if (!part2) jokers=0;

      // if we have h3 and a joker, then 4 of a kind
      // if we have h2=1, then we can't make a full house
      // unless we have two jokers, and then four of a kind
      if (h2==2)
      if (jokers > 0)
      {
        return inOrderRankString();

      }

      if (h3>0)
      if (h2>0)
        return inOrderRankString();
      return "zzzzz";

    }
    public String getTwoPair()
    {
      TreeSet<Character> pair = new TreeSet<>();

      for(char z : counts.keySet())
      {
        if (counts.get(z) == 2)
        {
          pair.add(order_map.get(z));
        }
      }

      if (pair.size() == 2)
      {
        return inOrderRankString();
        //return "" + pair.last() + pair.first();
      }
      return "zzzzz";

    }

    public String inOrderRankString()
    {
      String sb = "";

      for(int i=0; i<cards.length(); i++)
      {
        char z= cards.charAt(i);
        sb += order_map.get(z);
      }

      return sb;


    }

    public String getFullCardRank()
    {
      LinkedList<Character> all = new LinkedList<>();

      for(int i=0; i<cards.length(); i++)
      {
        char z= cards.charAt(i);
        all.add( order_map.get(z) );
      }

      Collections.sort(all);

      String sb = "";
      while(all.size() > 0)
      {
        sb += all.pollLast();
      }
      return sb;

    }




  }

}
