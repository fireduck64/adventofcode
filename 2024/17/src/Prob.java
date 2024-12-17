import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.math.BigInteger;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    Compy c = new Compy(scan);
    c.exec();

		return ("" + c.output).replace(" ","");
  }

  public String Part2(Scanner scan)
    throws Exception
  {
    Compy c = new Compy(scan);

    nxt=0L;

    //System.out.println(new BigInteger("6117156052247277", 8));

    c.code_match=false;
    /*List<State> lst = new LinkedList<>();
    for(long a = 0; a< 8L*8L; a++)
    {

      String oct = Long.toOctalString(a);
      lst.add(new SS(oct, c));
    }*/
    //return Search.searchM(lst).toString();
    //return Search.searchPara(new SS("7156052247277", c)).toString();
    //return Search.searchPara(new SS(   "6052247277", c)).toString();
    //return Search.searchPara(new SS("117440", c)).toString();

    SS fin = (SS) Search.searchPara(new SS("", c));

    //probe(c, fin.v);

    return "" + fin.v + " " + fin.oct;


    //2,4,1,3,7,5,1,5,0,3,4,1,5,5,3,0
    //long v = Long.parseLong("0355143051573142",8);
    //long v = Long.parseLong("35543557342",8);
    //long v = Long.parseLong("2413751503415530",8);

    //System.out.println(v);

    //nxt=v;

    //long a =2147483648L;
    //a=117440;

    /*while(true)
    {
      if (a % 100000 == 0) System.out.print(".");
      c.reset(a);
      c.exec();
      //System.out.println(c.output);
      //System.out.println(c.code);
      //return "";
      if (c.output.equals(c.code)) return "\n" + a;

      a++;
      if (a < 0) E.er();

    }*/

    //new T(c, 0L, 1000000000L).start();


    //long x=100000000L;
    //long x=4294967296L;
    //nxt=0;
    /*done=false;

    for(long i=0; i<64; i++)
    {
      new T(c).start();
    }

    while(true)
    {
      if (ans != null)
      {
        done=true;
        return "" + ans;
      }
      
          try{
          Thread.sleep(1000L);
          }catch(Exception e){}
    }*/
    //return "";
    //probe(c);
    //return "";
  }
  long step=100000000L;
  long nxt=0;
  volatile boolean done=false;
  public synchronized long getNext()
  {

    if (done) return -1;
    long v = nxt;
    nxt += step;

    return v;

  }

  volatile String ans = null;

  public void probe(Compy c, BigInteger v)
  { 
    c.code_match=false;
    c.reset(0);

    for(long a=0; a<8L*8L*8L*8L*8L*8L*8L; a++)
    {
      BigInteger t = v.subtract(BigInteger.valueOf(a));

      c.reset(t);
      c.exec();

      if (c.code.equals(c.output))
      {
        System.out.println("probe " + t +" " + c.output);
      }
    }
  }

  public class SS extends State
  {
    public String oct;
    public List<Long> out;
    Compy c;
    BigInteger v;
    int matches;
    public SS(String oct, Compy c)
    {
      this.oct = oct;
      v = BigInteger.ZERO;
      if (oct.length() > 0)
      {
        v = new BigInteger(oct, 8);
      }
      c.reset(v);
      c.code_match=true;
      c.exec();

      this.c = c;

      out = new ArrayList<Long>();
      out.addAll(c.output);
      matches = getMatches();

    }
    public String toString()
    {
      return oct;
    }

    @Override
    public double getLean()
    {
      return 0.0;

    }

    @Override
    public double getEstimate()
    {
      return 0.0;
    }
  
    public double getCost()
    {
      return v.doubleValue();
      //return -getMatches();
    }
    public boolean isTerm()
    {
      return (out.equals(c.code));
    }
    public int getMatches()
    {
      int sz = Math.min(out.size(), c.code.size());
      int m =0;
      for(int idx=0; idx<sz; idx++)
      {
        long a = out.get(idx);
        long b = c.code.get(idx);
        if (a == b) m++;
        else return m;
      }
      return m;

    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (oct.startsWith("000")) return lst;
      //if (oct.length() >= 20) return lst;
      //if (new Random().nextDouble() < 0.001)
      //  System.out.println(oct + " " + matches);

      for(long i=0; i<Math.round(Math.pow(8,3)); i++)
      {
        //if (i > 0)
        {
          String a = Long.toOctalString(i);
          SS ss2 = new SS(a + oct, c.copy());
          if (ss2.matches > 0)
          if (ss2.matches > matches)
          {
            lst.add(ss2);
          }
        }
        //lst.add(new SS(oct + a, c.copy()));
      }

      return lst;


    }


  }

  public class T extends Thread
  {
    Compy c;

    public T(Compy c)
    {
      this.c = c.copy();

    }

    public void run()
    {
      while(true)
      {
        long start = getNext();
        long a = start;
        if (start < 0) return;

        System.out.println("Start: " + start);

        while(a < start + step)
        {
          //System.out.println(a);
          c.reset(a);
          c.exec();
        
          if (c.output.equals(c.code))
          {
            ans = "" + a;
            System.out.println("" + a);
            System.out.println("" + a);
            System.out.println("" + a);
            System.out.println("" + a);
            System.out.println("" + a);
            return;
          }
          a++;

        }

      }


    }

  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
    }
    if (new File(input).exists())
    {
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
    }    
  }

}
