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

  TreeMap<Long, Long> mem=new TreeMap<>();
  String cur_mask=null;

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.startsWith("mask = "))
      {
        cur_mask = line.substring( line.indexOf('=')+1).trim();
        System.out.println("Mask: " + cur_mask);
      }
      else if (line.startsWith("mem"))
      {
        line = line.replace("["," ").replace("]"," ");
        System.out.println(line);
        Scanner scan2 = new Scanner(line);
        scan2.next(); //mem
        long loc = scan2.nextLong();
        scan2.next(); //=
        long val = scan2.nextLong();

        setMem(loc, val);

      }
    }
    long sum = 0;
    for(long v : mem.values()) sum+=v;
    System.out.println(sum);
    

  }

  public void setMem(long loc, long val)
  {
    String val_str = getBit(val);
  
    String fin_val = maskString(cur_mask, val_str);

    long fin = Long.parseLong(fin_val, 2);

    // part 1
    //mem.put(loc, fin);

    // part 2
    String loc_str = getBit(loc);
    loc_str = memMaskString(cur_mask, loc_str);
    writeLoc(val, loc_str, "");
  }

  public void writeLoc(long val, String loc_str, String build)
  {
    //System.out.println(loc_str + " " + build);
    int idx = build.length();
    if (idx == loc_str.length())
    {
      long loc = Long.parseLong(build, 2);
      mem.put(loc,val);
      return;

    }
    char z = loc_str.charAt(idx);
    if (z=='X')
    {
      writeLoc(val, loc_str, build +"0");
      writeLoc(val, loc_str, build +"1");
    }
    else
    {
      writeLoc(val, loc_str, build + z);
    }

  }

  public String getBit(long val)
  {
    String s = Long.toBinaryString(val);
    while(s.length() < 36)
    {
      s= "0" + s;
    }
    return s;
  }
  public String maskString(String mask, String val)
  {
    String out = "";
    for(int i=0; i<mask.length(); i++)
    {
      char m = mask.charAt(i);
      char v = val.charAt(i);
      if (m=='X') out = out + v;
      else out = out + m;
    }
    return out;

  }

  public String memMaskString(String mask, String val)
  {
    String out = "";
    for(int i=0; i<mask.length(); i++)
    {
      char m = mask.charAt(i);
      char v = val.charAt(i);
      if (m=='0') out = out + v;
      if (m=='1') out = out + m;
      if (m=='X') out = out + m;
    }
    return out;

  }


}
