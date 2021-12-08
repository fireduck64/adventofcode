
import java.util.*;


public class Tok
{
  public static List<String> en(String input, String delim)
  {
    ArrayList<String> lst = new ArrayList<>();
    StringTokenizer stok = new StringTokenizer(input, delim);

    while(stok. hasMoreTokens())
    {
      lst.add(stok. nextToken());
    }

    return lst;
  }
  public static List<Integer> ent(String input, String delim)
  {
    List<String> lst = en(input, delim);
    List<Integer> out = new ArrayList<>();
    for(String s : lst)
    {
      out.add(Integer.parseInt(s));
    }
    return out;

  }


  public static void demo()
  {
    List<String> lst = Tok.en("a,b,c,d",",");

    System.out.println(lst);


  }

}
