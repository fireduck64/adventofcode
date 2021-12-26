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

  ArrayList<Disc> discs=new ArrayList<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      discs.add(new Disc(line));
    }

    int tm=0;
    while(true)
    {
      if (drop(tm))
      {
        break;
      }
      tm++;
    }
    System.out.println("Part 1: " + tm);

    discs.add(new Disc("11 0"));

    tm=0;
    while(true)
    {
      if (drop(tm))
      {
        break;
      }
      tm++;
    }
    System.out.println("Part 2: " + tm);


  }

  public boolean drop(int tm)
  {
    
    for(Disc d : discs)
    {
      tm++;
      if (d.getPos(tm) != 0) return false;
    }
    return true;

  }

  public class Disc
  {
    int size;
    int offset;

    public Disc(String line)
    {
      List<Integer> lst = Tok.ent(line.replace(".","")," ");
      size=lst.get(0);
      offset=lst.get(1);
      //System.out.println("sz: " + size + " offset:" + offset);
    }

    public int getPos(int tm)
    {
      int x = (tm + offset) % size;
      return x;
    }

  }
}
