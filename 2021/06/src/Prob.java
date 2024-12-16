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

  TreeMap<Integer, Long> f_map = new TreeMap<>();

  public Prob(Scanner scan)
  {
  for(int i=0; i<9; i++)
  {
    f_map.put(i,0L);
  }
    {
      String line = scan.nextLine();
      List<String> lst = Tok.en(line, ",");
      for(String s : lst)
      {
        int v = Integer.parseInt(s);
        f_map.put(v,f_map.get(v)+1);
      }

    }

    for(int i=0; i<256; i++)
    {
      f_map = simDay(f_map);
    }

    long cnt = 0;
    for(long v : f_map.values())
    {
      cnt+=v;
    }
    System.out.println(cnt);
    
  }

  public TreeMap<Integer, Long> simDay(TreeMap<Integer, Long> i_map)
  {
    TreeMap<Integer, Long> o_map=new TreeMap<>();
    for(int i=0; i<9; i++)
    {
      o_map.put(i,0L);
    }
    for(int days : i_map.keySet())
    {
      long count = i_map.get(days);
      if (days==0)
      {
        o_map.put(6, o_map.get(6)+count);
        o_map.put(8, o_map.get(8)+count);
      }
      else
      {
        o_map.put(days-1, o_map.get(days-1)+count);
      }
    }
    return o_map;

  }

}
