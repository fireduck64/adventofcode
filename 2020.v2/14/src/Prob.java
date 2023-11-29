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

  TreeMap<Long, Long> mem = new TreeMap<>();
  TreeMap<Long, Long> mem2 = new TreeMap<>();

  long mask_set;
  long mask_clear;
  long mask_float;
  String mask_str;

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      line = line.replace(" ","");
      if (line.startsWith("mask"))
      {
        setMask( line.split("=")[1]);
      }
      else
      {
        line = line.replace("mem","").replace("[","").replace("]","");
        long addr = Long.parseLong(line.split("=")[0]);
        long val = Long.parseLong(line.split("=")[1]);

        long v = applyMask(val);

        mem.put(addr,v);

        String a_str = Long.toBinaryString(addr);
        while(a_str.length() < 36) a_str = "0" + a_str;

        applyP2("", a_str, val);
      }
    }
    long p1 = 0;
    for(long v : mem.values())
    {
      p1+=v;
    }
    System.out.println("Part 1: " + p1);

    long p2 = 0;
    for(long v : mem2.values())
    {
      p2+=v;
    }
    System.out.println("Part 2: " + p2);
    System.out.println("  Count: " + mem2.size());


  }

  public long applyMask(long in)
  {
    return (in | mask_set) & (~mask_clear);
  }

  public void setMask(String mask)
  {
    mask_str = mask;
    
    String mask_set_str = mask.replace("X", "0");

    mask_set = Long.parseLong(mask_set_str, 2);

    String mask_clear_str = mask.replace("0", "Z").replace("1","0").replace("X","0").replace("Z","1");

    mask_clear = Long.parseLong(mask_clear_str, 2);

    String mask_float_str = mask.replace("1", "0").replace("X","1");

  }

  public void applyP2(String addr_start, String addr, long val)
  {
    if (addr_start.length() == addr.length())
    {
      long a = Long.parseLong(addr_start, 2);
      mem2.put(a, val);
      return;
    }

    int idx = addr_start.length();
    char z = mask_str.charAt(idx);
    if (z =='0')
    {
      applyP2(addr_start + addr.charAt(idx), addr, val);
      return;
    }
    if (z=='1')
    {
      applyP2(addr_start + '1', addr, val);
      return;
    }
    if (z=='X')
    {
      applyP2(addr_start + '0', addr, val);
      applyP2(addr_start + '1', addr, val);
      return;
    }

  }

}
