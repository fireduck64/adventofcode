import java.io.FileInputStream;
import java.util.*;
import java.math.BigInteger;

public class ProbB2
{

  // Copied much without full understanding from:
  // https://github.com/SimonBaars/adventOfCode-2019

  public static void main(String args[]) throws Exception
  {
    new ProbB2(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public static final long CARDS=119315717514047L;
  public static final long SHUFFLES=101741582076661L;
  //public static final long SHUFFLES=1L;

  public static final BigInteger CARDS_B=BigInteger.valueOf(CARDS);
  public static final BigInteger SHUFFLES_B=BigInteger.valueOf(SHUFFLES);

  public static final BigInteger one = BigInteger.valueOf(1);
  public static final BigInteger two = BigInteger.valueOf(2);
  public static final BigInteger n_one = BigInteger.valueOf(-1);

  public ProbB2(Scanner scan)
  {
    LinkedList<String> commands = new LinkedList<>();

    while(scan.hasNextLine())
    {
      commands.addFirst(scan.nextLine());
    }

    long x = 2020;
    BigInteger x_b = BigInteger.valueOf(x);

    Accum acc = new Accum();

    //while(true)
    {
      long y = x;
      for(String line : commands)
      {
        x = doCommand(line, x);

        doCommandAcc(line, acc);
      }

      acc.add = acc.add.mod(CARDS_B);
      acc.mult = acc.mult.mod(CARDS_B);

      System.out.println("Add: " + acc.add);
      System.out.println("Mult: " + acc.mult);


      var pow = acc.mult.modPow(SHUFFLES_B, CARDS_B);
      System.out.println("Pow: " + pow);

      var r = pow.multiply(x_b).add(acc.add.multiply(pow.add(CARDS_B).subtract(one)).multiply(acc.mult.subtract(one).modPow(CARDS_B.subtract(two), CARDS_B))).mod(CARDS_B);
      System.out.println("R: " + r);


      System.out.println("Value at 2020, one shuffle: " + x);

    }


  }

  public class Accum
  {
    public Accum()
    {
    }
    // x = add + input * mult
    BigInteger add = BigInteger.valueOf(0L);
    BigInteger mult = BigInteger.valueOf(1L);
  }

  void doCommandAcc(String line, Accum acc)
  {
    String[] split=line.split(" ");


    if (line.equals("deal into new stack"))
    {
      acc.mult = acc.mult.multiply(BigInteger.valueOf(-1L)).mod(CARDS_B);
      acc.add = acc.add.add(one).multiply(n_one).mod(CARDS_B);
    }
    else if (line.startsWith("cut"))
    {
      BigInteger cut_number = new BigInteger(split[1]);
      acc.add = acc.add.add( cut_number ).mod(CARDS_B);
    }
    else if (line.startsWith("deal with increment"))
    {
        
      BigInteger inc = new BigInteger(split[3]);
      BigInteger two = BigInteger.valueOf(2L);

      var p = inc.modPow(CARDS_B.subtract(two), CARDS_B);
      acc.add = acc.add.multiply(p);
      acc.mult = acc.mult.multiply(p);

    }
    else
    {
      throw new RuntimeException("Z");
    }

  }
  
  // The card at location "input", where did it come from?
  long doCommand(String line, long input)
  {
    String[] split=line.split(" ");

    if (line.equals("deal into new stack"))
    {
      return CARDS - input - 1;
    }
    else if (line.startsWith("cut"))
    {

      long cut_number = Long.parseLong(split[1]);
      long v = (input + cut_number) % CARDS;
      if (v < 0) v+=CARDS;
      return v;

    }
    else if (line.startsWith("deal with increment"))
    {
        
      long inc = Long.parseLong(split[3]);

      BigInteger in = BigInteger.valueOf(input);
      BigInteger v = in.multiply(BigInteger.valueOf(inc).modPow(BigInteger.valueOf(-1), CARDS_B));
      
      v = v.mod(CARDS_B);

      return v.longValue();

    }
    else
    {
      throw new RuntimeException("Z");
    }
  }



}
