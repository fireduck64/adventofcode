
import java.util.*;

public class Str
{

  public static List<Character> stolist(String str)
  {
    List<Character> lst = new ArrayList<>();
    for(int i=0; i<str.length(); i++)
    {
      lst.add(str.charAt(i));
    }
    return lst;

  }
  public static String listtos(Collection<Character> lst)
  {
    StringBuilder sb = new StringBuilder();
    for(char z : lst) sb.append(z);
    return sb.toString();
  }
}
