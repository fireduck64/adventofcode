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
  int len=0;

  public Prob(Scanner scan)
  {

    int [][]counts = null;

    TreeSet<String> lines=new TreeSet<>();

    while(scan.hasNext())
    {
      String v = scan.next();
      lines.add(v);
      len = v.length();
      if (counts == null) counts =new int[2][len];

      for(int i=0; i<v.length(); i++)
      {
        char z = v.charAt(i);
        if (z=='0') counts[0][i]++;
        if (z=='1') counts[1][i]++;
      }

    }
    String gamma="";
    String epsilon="";

    for(int i=0; i<len; i++)
    {
      if (counts[0][i] > counts[1][i])
      {
        gamma+="0";
        epsilon+="1";
      }
      if (counts[0][i] < counts[1][i])
      {
        gamma+="1";
        epsilon+="0";
      }
      if (counts[0][i] == counts[1][i])
      {
        throw new RuntimeException("undef");
      }
 
    }
    System.out.println("Gamma: " + gamma);
    System.out.println("Ep:" + epsilon);

    int g = Integer.parseInt(gamma, 2);
    int e = Integer.parseInt(epsilon, 2);
    System.out.println(g);
    System.out.println("Part1: " + g*e);

    String oxy=filter(true, lines, 0);
    String co2=filter(false, lines, 0);

    int oxy_v = Integer.parseInt(oxy, 2);
    int co2_v = Integer.parseInt(co2, 2);

    System.out.println("Part2: " + oxy_v * co2_v);


  }

  public String filter(boolean common, TreeSet<String> inputs, int idx)
  {
    if (inputs.size() ==1) return inputs.first();
   
    if (idx >= len) throw new RuntimeException("asplode " + inputs.size());

    TreeSet<String> one_list=new TreeSet<>();
    TreeSet<String> zero_list=new TreeSet<>();

    for(String s : inputs)
    {
      char z = s.charAt(idx);
      if (z=='0') zero_list.add(s);
      else one_list.add(s);
    }

    if (common)
    {
      if (one_list.size() >= zero_list.size())
      {
        return filter(common, one_list, idx+1);
      }
      if (one_list.size() < zero_list.size())
      {
        return filter(common, zero_list, idx+1);
      }
      throw new RuntimeException("no");
    }
    else
    {
      if (one_list.size() >= zero_list.size())
      {
        return filter(common, zero_list, idx+1);
      }
      if (one_list.size() < zero_list.size())
      {
        return filter(common, one_list, idx+1);
      }
     
      throw new RuntimeException("no");

    }

  }

}
