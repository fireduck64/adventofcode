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
    { // sample
      String o ="abcde";
      System.out.println(o);
      o = spin(o,1);
      System.out.println(o);
      o = exchange(o, 3,4);
      System.out.println(o);
      o = partner(o, 'e', 'b');
      System.out.println(o);

    }

    String o = "abcdefghijklmnop";
    String line = scan.nextLine();
    HashMap<String, Long> state_map=new HashMap<>(8192, 0.5f);
    for(long pass=0; pass<1000000000; pass++)
    {
      for(String cmd : Tok.en(line, ","))
      {
        //System.out.println(cmd);
        char i = cmd.charAt(0);
        if (i=='s')
        {
          int n = Integer.parseInt(cmd.substring(1));
          o = spin(o, n);
        }
        else if (i=='x')
        {
          cmd = cmd.replace("x","");
          List<Integer> nlst = Tok.ent(cmd, "/");
          o = exchange(o, nlst.get(0), nlst.get(1));
        }
        else if (i=='p')
        {
          //System.out.println(cmd);
          o = partner(o, cmd.charAt(1), cmd.charAt(3));
        }
        //System.out.println(o);

      }
      if (state_map.containsKey(o))
      {
        //System.out.println("Repeat on: " + pass + " " + o);
        long diff = pass - state_map.get(o);
        while (pass + diff < 1000000000L) pass += diff;
      }
      state_map.put( o, pass);
      if (pass == 0)
      {
        System.out.println("Part 1: " + o);

      }
    }
    System.out.println("Part 2: " + o);

  }

  public String spin(String in, int n)
  {
    int len = in.length();
    return in.substring(len - n) + in.substring(0,len-n);
  }

  public String exchange(String in, int pos_a, int pos_b)
  {
    char a = in.charAt(pos_a);
    char b = in.charAt(pos_b);

    return partner(in,a,b);

  }
  public String partner(String in, char a, char b)
  {
    //System.out.println("partner " + a + " " + b);
    return in.replace(a,'z').replace(b,a).replace('z',b);

  }




}
