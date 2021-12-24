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

  TreeSet<String> tls_set = getTlsSet();
  TreeMap<String,String> ssl_set = getSslSet();

  public Prob(Scanner scan)
  {
    int p1=0;
    int p2=0;
    for(String line : In.lines(scan))
    {
      String cut = cutBracket(line);
      String only = onlyBracket(line);

      if (containsAbba(cut))
      if (!containsAbba(only))
      {
        p1++;
      }
      if (containsAba(cut, only))
      {
        p2++;
      }
    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);

  }

  public boolean containsAbba(String in)
  { 
    for(String s : tls_set)
    {
      if (in.contains(s)) return true;
    }
    return false;

  }

  public boolean containsAba(String cut, String only)
  { 
    for(String s : ssl_set.keySet())
    {
      if (cut.contains(s))
      {
        String bab = ssl_set.get(s);
        if (only.contains(bab)) return true;

      }

    }
    return false;

  }



  TreeMap<String,String> getSslSet()
  {
    TreeMap<String,String> seq=new TreeMap<>();

    for(char a='a'; a<='z'; a++)
    for(char b='a'; b<='z'; b++)
    {
      if (a!=b)
      {
        String s = "" + a + b + a;
        String f = "" + b + a + b;
        seq.put(s,f);
      }

    }
    return seq;
  }
 
  TreeSet<String> getTlsSet()
  {
    TreeSet<String> seq=new TreeSet<>();

    for(char a='a'; a<='z'; a++)
    for(char b='a'; b<='z'; b++)
    {
      if (a!=b)
      {
        String s = "" + a + b + b + a;
        seq.add(s);

      }

    }
    return seq;
  }
  public String cutBracket(String s)
  {
    boolean br=false;
    StringBuilder sb=new StringBuilder();
    for(int i=0; i<s.length(); i++)
    {
      char z = s.charAt(i);
      if (z==']') br=false;
      if (!br) sb.append(z);
      if (z=='[') br=true;

    }
    return sb.toString();

  }
  public String onlyBracket(String s)
  {
    boolean br=false;
    StringBuilder sb=new StringBuilder();
    for(int i=0; i<s.length(); i++)
    {
      char z = s.charAt(i);
      if (z=='[') br=true;
      if (br) sb.append(z);
      if (z==']') br=false;

    }
    return sb.toString();

  }

}
