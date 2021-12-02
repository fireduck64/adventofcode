
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


  public static void demo()
  {
    List<String> lst = Tok.en("a,b,c,d",",");

    System.out.println(lst);


  }

}
