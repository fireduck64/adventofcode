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
    int valid=0;
    int valid2=0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> lst = Tok.en(line, " ");
      TreeSet<String> set = new TreeSet<>();
      set.addAll(lst);
      if (lst.size() == set.size()) valid++;
      TreeSet<String> sset = new TreeSet<>();
      for(String s : set)
      {
        sset.add(sortString(s));

      }
      if (lst.size() == sset.size()) valid2++;

    }
    System.out.println("Part 1: " + valid);
    System.out.println("Part 2: " + valid2);
  }


  public String sortString(String in)
  {
    List<Character> lst = new LinkedList<>();
    for(int i=0; i<in.length(); i++) lst.add( in.charAt(i));
    Collections.sort(lst);
    StringBuilder sb = new StringBuilder();
    for(char x : lst)
    {
      sb.append(x);
    }
    return sb.toString();
    
  }

}
