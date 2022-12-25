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
    long sum = 0;
    for(String line : In.lines(scan))
    {
      sum += snaf2dec(line);
    }
    System.out.println(sum);
    System.out.println(dec2snaf(sum));
    System.out.println(snaf2dec(dec2snaf(sum)));


  }

  public long snaf2dec(String in)
  {
    LinkedList<Character> lst = new LinkedList();
    lst.addAll(Str.stolist(in));

    long val = 0;
    while(lst.size() > 0)
    {
      char z = lst.pop();
      long v = 0;
      if (z=='=') v=-2;
      if (z=='-') v=-1;
      if (z=='0') v=0;
      if (z=='1') v=1;
      if (z=='2') v=2;

      val = val*5 + v;

    }
    return val;
  }

  public String dec2snaf(long val)
  {
    LinkedList<Character> lst = new LinkedList<>();

    while(val > 0)
    {
      long m = val % 5;
      if (m == 0) lst.addFirst('0');
      if (m == 1) lst.addFirst('1');
      if (m == 2) lst.addFirst('2');
      if (m == 3) 
      {
        lst.addFirst('=');
        val+=5;
      }
      if (m == 4)
      {
        lst.addFirst('-');
        val+=5;
      }
      val = val /5;


    }



    return Str.listtos(lst);


  }

}
