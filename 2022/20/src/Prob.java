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
  ArrayList<Long> input = new ArrayList<>();
  ArrayList<String> inputs = new ArrayList<>();
  ArrayList<String> inputs2 = new ArrayList<>();



  public Prob(Scanner scan)
  {

    int idx =0;
    String zero=null;

    while(scan.hasNextInt())
    {
      long v = scan.nextInt();
      input.add(v);
      String label = "" + idx +"/" +v;
      if (v==0) zero = label;
      inputs.add(label);

      v *= 811589153L; 
      String label2 = "" + idx +"/" +v;
      inputs2.add(label2);
      idx++;
    }

    {

      CircleList<String> cl = new CircleList<String>(inputs, true);

      if (input.size() < 20) cl.print(cl.find(zero));
      for(String s : inputs)
      {
        long n = Tok.enl(s,"/").get(1);
        if (!cl.find(s).v.equals(s)) E.er("" + cl.find(s).v);
        cl.moveNIns(cl.find(s), n, input.size()-1);

        if (input.size() < 20) cl.print(cl.find(zero));
      }


      {
        int p1 =0;

        if (input.size() < 20) cl.print(cl.find(zero));

        System.out.println(cl.seek(cl.find(zero), 1000).v);
        System.out.println(cl.seek(cl.find(zero), 2000).v);
        System.out.println(cl.seek(cl.find(zero), 3000).v);

        p1 += Tok.ent(cl.seek(cl.find(zero), 1000).v, "/").get(1);
        p1 += Tok.ent(cl.seek(cl.find(zero), 2000).v, "/").get(1);
        p1 += Tok.ent(cl.seek(cl.find(zero), 3000).v, "/").get(1);

        System.out.println("Part 1: " + p1);


      }
    }
    {
      CircleList<String> cl = new CircleList<String>(inputs2, true);

      if (input.size() < 20) cl.print(cl.find(zero));

      for(int pass =0; pass<10; pass++)
      {
      for(String s : inputs2)
      {
        long n = Tok.enl(s,"/").get(1);
        if (!cl.find(s).v.equals(s)) E.er("" + cl.find(s).v);
        cl.moveNIns(cl.find(s), n, input.size()-1);

        if (input.size() < 20) cl.print(cl.find(zero));
      }
      }


      {
        long p2 =0;

        if (input.size() < 20) cl.print(cl.find(zero));

        System.out.println(cl.seek(cl.find(zero), 1000).v);
        System.out.println(cl.seek(cl.find(zero), 2000).v);
        System.out.println(cl.seek(cl.find(zero), 3000).v);

        p2 += Tok.enl(cl.seek(cl.find(zero), 1000).v, "/").get(1);
        p2 += Tok.enl(cl.seek(cl.find(zero), 2000).v, "/").get(1);
        p2 += Tok.enl(cl.seek(cl.find(zero), 3000).v, "/").get(1);

        System.out.println("Part 2: " + p2);


      }

    }


    

  }


}
