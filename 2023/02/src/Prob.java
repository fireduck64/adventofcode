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
  ArrayList<Game> games = new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      games.add(new Game(line));
    }

    int p1=0;
    TreeMap<String, Integer> p1_allow = new TreeMap<>();
    p1_allow.put("red",12);
    p1_allow.put("green",13);
    p1_allow.put("blue",14);
    
    long p2=0;
    for(Game g : games)
    {
      boolean good = true;
      for(TreeMap<String, Integer> s : g.seq)
      {
        for(String color : s.keySet())
        {
          if (!p1_allow.containsKey(color)) good=false;
          else
          {
            if (s.get(color) > p1_allow.get(color)) good=false;
          }


        }

      }
      if (good)
      {
        p1+=g.id;

      }

      p2+=g.getMinPower();


    }
    System.out.println("Part1: " + p1);
    System.out.println("Part2: " + p2);
     

  }

  public class Game
  {
    int id;
    ArrayList<TreeMap<String, Integer> > seq=new ArrayList<>();

    public Game(String line)
    {
      id = Tok.ent(line.replace(":", " "), " ").get(0);
      String all_seq = Tok.en(line, ":").get(1);

      for(String sstr : Tok.en(all_seq, ";"))
      {
        sstr = sstr.replace(",","").trim();
        System.out.println(sstr);
        TreeMap<String, Integer> s = new TreeMap<>();

        Scanner scan = new Scanner(sstr);
        while (scan.hasNext())
        {
          int n=scan.nextInt();
          String c = scan.next();
          s.put(c,n);

        }
        seq.add(s);

      }

    }

    public long getMinPower()
    {
      TreeMap<String, Integer> mins = new TreeMap<>();
      
      for(TreeMap<String, Integer> s : seq)
      {
        for(String color : s.keySet())
        {
          if (!mins.containsKey(color)) mins.put(color, 0);

          mins.put(color, Math.max(s.get(color), mins.get(color)));

        }

      }

      long r = 1;
      for(int n : mins.values()) r = r*n;

      return r;


    }

  }

}
