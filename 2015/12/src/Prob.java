import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableSet;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;


public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
    throws Exception
  {
    String input = scan.nextLine();
    String in_n = input;
    in_n = in_n.replace('[', ' ');
    in_n = in_n.replace(']', ' ');
    in_n = in_n.replace(':', ' ');
    in_n = in_n.replace(',', ' ');
    in_n = in_n.replace(';', ' ');
    in_n = in_n.replace('}', ' ');
    in_n = in_n.replace('{', ' ');
    
    List<Integer> nums = Tok.ent(in_n, " ");
    long p1 = 0;
    for(int n : nums) p1 += n;
    System.out.println("Part 1: " + p1);
    
    JSONParser parser = new JSONParser( JSONParser.MODE_PERMISSIVE );
    JSONObject obj = (JSONObject) parser.parse(input);

    System.out.println("Part 2: " + countNotRed(obj));

  }

  public int countNotRed(Collection<Object> lst)
  {
    int sum =0;
    for(Object o : lst)
    {
      if (o instanceof Integer)
      {
        int v = (Integer)o;
        sum+=v;

      }
      else if (o instanceof JSONArray)
      {
        Collection c = (Collection) o;
        sum += countNotRed(c);

      }
      else if (o instanceof String)
      {
        String s = (String)o;
      }
      else if (o instanceof JSONObject)
      {
        sum += countNotRed( (JSONObject) o);
      }
      else
      {
        E.er();
      }


    }
    return sum;

  }

  public int countNotRed(JSONObject j)
  {
    int sum =0;

    for(Object o : j.values())
    {
      if (o instanceof Integer)
      {
        int v = (Integer)o;
        sum+=v;

      }
      else if (o instanceof JSONArray)
      {
        Collection c = (Collection) o;
        sum += countNotRed(c);

      }
      else if (o instanceof String)
      {
        String s = (String)o;
        if (s.equals("red")) return 0;
      }
      else if (o instanceof JSONObject)
      {
        sum += countNotRed( (JSONObject) o);
      }
      else
      {
        E.er();
      }

    }


    return sum;

  }

}
