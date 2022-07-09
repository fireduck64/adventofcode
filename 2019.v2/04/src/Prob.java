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

  int low;
  int high;
  String low_s;
  String high_s;

  public Prob(Scanner scan)
  {

    {
      List<Integer> lst = Tok.ent(scan.nextLine(),"-");
      low = lst.get(0);
      high = lst.get(1);
    }
    {
      low_s = "" + low;
      high_s = "" + high;
    }

    int good=0;
    int good2=0;
    for(int c = low; c<=high; c++)
    {
      if (check(c)) good++;
      if (check2(c)) good2++;
    }
    System.out.println("Good: " + good);
    System.out.println("Good2: " + good2);

  }

  public boolean check(int v)
  {
    String s = "" + v;

    int doubles=0;
    for(int i=0; i<s.length()-1; i++)
    {
      char a = s.charAt(i);
      char b = s.charAt(i+1);
      if (a == b) doubles++;
      if (b < a) return false;


    }
    if (doubles == 0) return false;
    return true;
  }

  public boolean check2(int v)
  {
    String s = "" + v;
    TreeMap<Character, Integer> dubs =new TreeMap<>();

    int doubles=0;
    for(int i=0; i<s.length()-1; i++)
    {
      char a = s.charAt(i);
      char b = s.charAt(i+1);
      if (a == b)
      { 
        if (!dubs.containsKey(a))
        {
          dubs.put(a,0);
        }
        dubs.put(a, dubs.get(a) + 1);
      }
      if (b < a) return false;


    }
    doubles = 0;
    for(int vv : dubs.values())
    {
      if (vv==1) doubles++;
    }
    if (doubles == 0) return false;
    return true;
  }


}
