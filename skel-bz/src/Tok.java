
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
      try
      {
      out.add(Integer.parseInt(s));
      }
      catch(Exception e)
      {}
    }
    return out;

  }
  public static List<Long> enl(String input, String delim)
  {
    List<String> lst = en(input, delim);
    List<Long> out = new ArrayList<>();
    for(String s : lst)
    {
      try
      {
      out.add(Long.parseLong(s));
      }
      catch(Exception e)
      {}
    }
    return out;

  }

  /**
   * Whitespace is considered a separator
   * numeric strings are combined
   * all other things are separate tokens
   */
  public static LinkedList<String> smart(String input)
  {
    LinkedList<String> out = new LinkedList<>();
    StringBuilder sb = null;
    int type = 0;
    // 0 - nothering
    // 1 - number
    // 2 - alpha

    for(char z : Str.stolist(input))
    {
      if (Character.isWhitespace(z))
      {
        if (sb != null) out.add(sb.toString());
        sb=null;
        type=0;
      }
      else if (Character.isDigit(z))
      {
        if (type!=1)
        {
          if (sb != null) out.add(sb.toString());
          sb=new StringBuilder();
          type=1;
        }
        sb.append(z);
      }
      else if (Character.isAlphabetic(z))
      {
        if (type!=2)
        {
          if (sb != null) out.add(sb.toString());
          sb=new StringBuilder();
          type=2;
        }
        sb.append(z);
      }
      else
      {
        if (sb != null) out.add(sb.toString());
        sb=null;
        out.add("" + z);
        type=0;
      }
    }
    if (sb != null) out.add(sb.toString());

    return out;

  }


  public static void demo()
  {
    List<String> lst = Tok.en("a,b,c,d",",");

    System.out.println(lst);

    System.out.println(smart("cat:9182901dog_   =[  ]=km"));


  }

}
