import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  HashSet<String> words = new HashSet<>();

  public Prob(Scanner scan)
  {
    int h2=0;
    int h3 =0;


    while(scan.hasNext())
    {
      String s= scan.next();
      words.add(s);
      Map<Character, Integer> m = getCounts(s);
      int has2 = 0;
      int has3 = 0;
      for(char z='a'; z<='z'; z++)
      {
        if (m.get(z) == 2) has2++; 
        if (m.get(z) == 3) has3++; 
      }
      if (has2>0) h2++;
      if (has3>0) h3++;

      crossCheck(s);
    }

    System.out.println( h2 * h3);

  }

  private void crossCheck(String s)
  {
    for(int i=0; i<s.length(); i++)
    {
      String start = s.substring(0,i);
      String end = s.substring(i+1);
      char not = s.charAt(i);
      for(char z='a'; z<='z'; z++) 
      {
        String ch = start + z + end;
        if (z != not)
        if (words.contains(ch))
        {
          System.out.println("s:" + start + " z:" + z  + " e:" + end);
          System.out.println(start + end);
        }

      }


    }

  }

  Map<Character, Integer> getCounts(String s)
  {
    Map<Character, Integer> m = new TreeMap<>();
      for(char z='a'; z<='z'; z++) m.put(z, 0);

    for(int i=0; i<s.length(); i++)
    {
      char z = s.charAt(i);
      m.put(z, m.get(z) + 1);
    }
    return m;
  }


}
