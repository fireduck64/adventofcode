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
  ArrayList<Induct> induct_lst=new ArrayList<>();

  public Prob(Scanner scan)
  {
    {
      induct_lst.add( new Induct(0, "abcefg") );
      induct_lst.add( new Induct(1, "cf") );
      induct_lst.add( new Induct(2, "acdeg") );
      induct_lst.add( new Induct(3, "acdfg") );
      induct_lst.add( new Induct(4, "bcdf") );
      induct_lst.add( new Induct(5, "abdfg") );
      induct_lst.add( new Induct(6, "abdefg") );
      induct_lst.add( new Induct(7, "acf") );
      induct_lst.add( new Induct(8, "abcdefg") );
      induct_lst.add( new Induct(9, "abcdfg") );
    }



    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> tok = Tok.en(line, "|");

      process(
        sortList(Tok.en( tok.get(0).trim(), " ")),
        sortList(Tok.en( tok.get(1).trim(), " ")));

    }

    System.out.println("Part 1: " + count_1);
    System.out.println("Part 2: " + out_sum);
  }
  int count_1=0;
  long out_sum=0;

  public void process(List<String> unique, List<String> signal)
  {
    System.out.println("u: " + unique + " s:" + signal);

    for(String s : signal)
    {
      int len = s.length();
      if (len == 2) count_1++;
      if (len == 4) count_1++;
      if (len == 3) count_1++;
      if (len == 7) count_1++;
    }

    TreeSet<String> missing_signal = new TreeSet<>();
    missing_signal.addAll(signal);
    TreeMap<String, Integer> fin_map = new TreeMap<>();
    TreeSet<String> unknown_u = new TreeSet<>();
    unknown_u.addAll(unique);
    TreeSet<Integer> assigned_n = new TreeSet<>();

    while(missing_signal.size() > 0)
    {
      System.out.println(fin_map);
      for(String u : unique)
      {
        int len = u.length();
        if (len == 2)
        {
          fin_map.put(u, 1);
          missing_signal.remove(u);
          unknown_u.remove(u);
          assigned_n.add(1);
        }
        if (len == 4)
        {
          fin_map.put(u, 4);
          missing_signal.remove(u);
          unknown_u.remove(u);
          assigned_n.add(4);
        }
        if (len == 3)
        {
          fin_map.put(u, 7);
          missing_signal.remove(u);
          unknown_u.remove(u);
          assigned_n.add(7);
        }
        if (len == 7)
        {
          fin_map.put(u, 8);
          missing_signal.remove(u);
          unknown_u.remove(u);
          assigned_n.add(8);
        }

        // We are working an unknown unique
        if (unknown_u.contains(u))
        {
          TreeSet<Integer> reals = new TreeSet<>();
          // Basically try every number and see if it jives
          for(int i=0; i<=9; i++)
          {
            if (!assigned_n.contains(i))
            if (len == induct_lst.get(i).segset.size())
            {
              boolean all_ok=true;
              for(Map.Entry<String, Integer> me : fin_map.entrySet())
              {
                if (!matchPossiblity(i, u, me.getValue(), me.getKey()))
                {
                  all_ok=false;
                }
              }
              if (all_ok) reals.add(i);
            }

          }
          if (reals.size() == 1)
          {
            int v = reals.first();
            fin_map.put(u, v);
            missing_signal.remove(u);
            unknown_u.remove(u);
            assigned_n.add(v);

          }


        }
      }

    }

    int decode=0;
    for(String s : signal)
    {
      decode*=10;
      decode+= fin_map.get(s);

    }
    out_sum+=decode;


  }

  public class Induct
  {
    int v;
    TreeSet<Character> segset;

    public Induct(int v, String segs)
    {
      this.v =v ;
      segset = new TreeSet<>();
      for(int i=0; i<segs.length(); i++)
      {
        segset.add( segs.charAt(i));
      }
      

    }

  }

  public boolean matchPossiblity(int a, String a_seg, int b, String b_seg)
  {
    // Return true if a_seg and b_seg have exactly the number of common elements as Induct a and Induct b

    Induct ia = induct_lst.get(a);
    Induct ib = induct_lst.get(b);
    TreeSet<Character> all = new TreeSet<>();
    all.addAll(ia.segset);
    all.addAll(ib.segset);
    int common = 0;

    for(char z : all)
    {
      if (ia.segset.contains(z))
      if (ib.segset.contains(z))
      {
        common++;
      }

    }
    return countCommon(a_seg, b_seg) == common;

  }

  public int countCommon(String a, String b)
  {
    int common=0;
    for(int i = 0; i<a.length(); i++)
    {
      char z = a.charAt(i);
      if (b.indexOf(z) >= 0) common++;

    }
    return common;

  }

  public String sortString(String s)
  {
    TreeSet<Character> out = new TreeSet<>();
    for(int i=0; i<s.length(); i++)
    {
      out.add(s.charAt(i));
    }
    StringBuilder sb = new StringBuilder();
    for(char z : out) sb.append(z);

    return sb.toString();

  }
  public List<String> sortList(List<String> in)
  {
    List<String> out = new LinkedList<String>();
    for(String s : in)
    {
      out.add(sortString(s));
    }
    return out;

  }

}
