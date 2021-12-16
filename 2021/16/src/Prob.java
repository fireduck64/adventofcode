import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.math.BigInteger;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  String str_in;
  int pos = 0;
  int ver_sum;

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();

    BigInteger b_in = new BigInteger(line, 16);
    str_in = b_in.toString(2);
    while (str_in.length() < line.length() * 4)
    {
      str_in = "0" + str_in;
    }
    System.out.println(str_in);

    
    System.out.println("Part2: " + parse());

    System.out.println(ver_sum);


  }

  public long parse()
  {
    int v = read3();
    int r = read3();
    //System.out.println("v: " + v + " r: " + r); 
    ver_sum += v;

    if (r==4)
    {
      BigInteger val = new BigInteger(readPacket(), 2);
      //System.out.println("val: " + val);

      return val.longValue();
    }
    else
    {
      char len_type = str_in.charAt(pos); pos++;
      LinkedList<Long> subs = new LinkedList<>();
      if (len_type == '0')
      {
        int len = readn(15);
        int done_pos = pos + len;
        while(pos < done_pos)
        {
          subs.add(parse());
        }
      }
      else if (len_type=='1')
      {
        int len = readn(11);
        for(int i=0; i<len; i++)
        {
          subs.add(parse());
        }
      }
      if (r == 0)
      {
        long sum = 0;
        System.out.println("Add: " + subs);
        for(long l : subs) sum+=l;
        System.out.println("Add sum: " + sum);
        return sum;
      }
      if (r == 1)
      {
        long sum = 1;
        System.out.println("product subs: " + subs);
        for(long l : subs) sum*=l;
        System.out.println("product: " + sum);
        return sum;
      }
      if (r == 2)
      {
        long min = subs.get(0);
        for(long l : subs) min=Math.min(min, l);
        return min;
      }
      if (r == 3)
      {
        long max = subs.get(0);
        for(long l : subs) max=Math.max(max, l);
        return max;
      }
      if (r == 5)
      {
        if (subs.size() != 2) E.er();
        System.out.println("gtr: " + subs);
        if (subs.get(0) > subs.get(1)) return 1L;
        System.out.println("gtr no");
        return 0L;
      }
      if (r == 6)
      {
        if (subs.size() != 2) E.er();
        System.out.println("ltr: " + subs);
        if (subs.get(0) < subs.get(1)) return 1L;
        System.out.println("ltr no");
        return 0L;
      }
      if (r == 7)
      {
        if (subs.size() != 2) E.er();
        System.out.println("eq: " + subs);
        if (subs.get(0).equals(subs.get(1))) return 1L;
        System.out.println("eq no");
        return 0L;
      }
    }

    E.er();
    return -1;

  }

  int read3()
  {
    return readn(3);
  }
  int readn(int n)
  {
    String s = str_in.substring(pos,n+pos);
    pos+=n;
    return Integer.parseInt(s, 2);

  }

  String readPacket()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("0");

    while(true)
    {
      char bit = str_in.charAt(pos); pos++;
      String s = str_in.substring(pos, pos+4); pos+=4;

      sb.append(s);
      if (bit == '0') break;
    }

    return sb.toString();

  }

}
