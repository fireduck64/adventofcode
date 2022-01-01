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
  ArrayList<SkyCat> cats= new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      cats.add(new SkyCat(line));
    }

    for(int i=0; i<2503; i++)
    {
      sim();
    }
    int p1=0;
    int p2=0;
    for(SkyCat cat : cats)
    {
      p1 = Math.max(p1, cat.pos);
      p2 = Math.max(p2, cat.points);
    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);

  }
  public void sim()
  {
    for(SkyCat cat : cats)
    {
      cat.sim();
      
    }
    int b = 0;
    for(SkyCat cat : cats)
    {
      b = Math.max(b, cat.pos);
    }
    for(SkyCat cat : cats)
    {
      if (cat.pos == b) cat.points++;
    }

  }

  public class SkyCat
  {
    final String name;
    final int speed; // km/s
    final int endurance; // sec
    final int rest; // sec

    int pos=0;
    int rest_rem=0;
    int end_rem=0;
    int points=0;
    
    public SkyCat(String line)
    {
      name = Tok.en(line, " ").get(0);
      List<Integer> lst = Tok.ent(line, " ");
      speed = lst.get(0);
      endurance = lst.get(1);
      rest = lst.get(2);

      end_rem = endurance;

    }
    public void sim()
    {
      if (rest_rem > 0)
      {
        rest_rem--;
        if (rest_rem == 0) end_rem = endurance;
        return;
      }
      if (end_rem > 0)
      {
        pos+=speed;
        end_rem--;
        if (end_rem == 0) rest_rem = rest;
        return;
      }
      
      E.er();
    }


  }

}
