import java.io.FileInputStream;
import java.util.*;
import java.math.BigInteger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  ArrayList<Monkey> monkies = new ArrayList<>();

  long magic_mod = 1;

  public Prob(Scanner scan)
  {

    List<String> lines = In.lines(scan);
    {//p1
      scan = In.newScan(lines);
      while(scan.hasNextLine())
      {
        monkies.add(new Monkey(scan));
      }
      magic_mod=3;

      for(int r =0; r<20; r++)
      {
        for(Monkey m : monkies)
        {
          m.run();
        }
      }
      TreeMap<Double, Long> active = new TreeMap<>();
      for(Monkey m : monkies)
      {
        active.put(rnd.nextDouble() - m.item_count, m.item_count);
      }

      ArrayList<Long> ma = new ArrayList<>();
      ma.addAll(active.values());
      long p1 = ma.get(0) * ma.get(1);
      System.out.println();

      System.out.println("Part 1: " + p1);
    }


    {//p2
      monkies.clear();
      magic_mod=1;
      scan = In.newScan(lines);
      while(scan.hasNextLine())
      {
        monkies.add(new Monkey(scan));
      }

      for(int r =0; r<10000; r++)
      {
        if (r % 100 == 0) System.out.print('.');
        for(Monkey m : monkies)
        {
          m.run();
        }
      }
      TreeMap<Double, Long> active = new TreeMap<>();
      for(Monkey m : monkies)
      {
        active.put(rnd.nextDouble() - m.item_count, m.item_count);
      }

      ArrayList<Long> ma = new ArrayList<>();
      ma.addAll(active.values());
      long p2 = ma.get(0) * ma.get(1);
      System.out.println();
      System.out.println("Part 2: " + p2);
    }

  }

  public class Monkey
  {
    int id= 0;
    LinkedList<BigInteger> items = new LinkedList<>();
    int op_mult=1;
    int op_add=0;
    int op_pow=1;
    int div;
    int target_true;
    int target_false;
    long item_count;
    public Monkey(Scanner scan)
    {
      id = Tok.ent(scan.nextLine(), " :").get(0);

      for (long n : Tok.enl(scan.nextLine(), ", "))
      {
        items.add(BigInteger.valueOf(n));
      }
      String op = scan.nextLine();
      try
      {
        int num = Tok.ent(op, " ").get(0);
        if (op.contains("*")) op_mult=num;
        else op_add=num;
      }
      catch(Exception e)
      {
        op_pow=2;
      }

      div = Tok.ent(scan.nextLine(), " ").get(0);
      magic_mod = magic_mod * div;

      target_true = Tok.ent(scan.nextLine(), " ").get(0);
      target_false = Tok.ent(scan.nextLine(), " ").get(0);

      if (scan.hasNextLine()) scan.nextLine();

    }

    public void run()
    {
      for(BigInteger item : items)
      {
        //System.out.println(item);
        item_count++;
        if (op_pow == 2) item=item.multiply(item);
        item = item.multiply(BigInteger.valueOf(op_mult));
        item = item.add(BigInteger.valueOf(op_add));

        if (magic_mod == 3)
        { // part 1
          item = item.divide(BigInteger.valueOf(magic_mod));
        }
        else
        { // part 2
          item = item.mod(BigInteger.valueOf(magic_mod));
        }
        int target =-1;
        if (item.mod(BigInteger.valueOf(div)).equals(BigInteger.ZERO))
        {
          target = target_true;
        }
        else
        {
          target = target_false;
        }
        monkies.get(target).items.add(item);
      }
      items.clear();


    }

  }

}
