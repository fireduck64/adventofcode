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
    long sum = 0;
    while(scan.hasNextLine())
    {
      sum+=evalString(scan.nextLine());
    }
    System.out.println(sum);
    

  }

  public long evalString(String input)
  {
    System.out.println("Eval: " + input);
    input = input.replace(" ","");

    int loc = 0;
    long val = 0;
    char op=' ';

    while(loc < input.length())
    {
      char z = input.charAt(loc);
      if (('0' <= z) && (z <= '9'))
      {
        long next = Integer.parseInt("" + z);
        loc++;
        val = getVal(val, op, next);
        op =' ';
      }
      if (z =='+')
      {
        op='+';
        loc++;
      }
      if (z=='*')
      {
        // part 1
        //op='*';

        loc++;

        //part 2
        return val * evalString(input.substring(loc));
      }
      if (z=='(')
      {
        int end = findMatching(input, loc);
        long next = evalString(input.substring(loc+1, end));
        val = getVal(val, op, next);
        op =' ';
        loc=end+1;
        
      }
    }
    return val;
  }

  long getVal(long val, char op, long next)
  {
    if (op=='*') return val*next;
    if (op=='+') return val+next;
    return next;
  }

  // Return index of matching paren
  int findMatching(String input, int loc)
  {
    int inside=1;
    loc++;
    while(true)
    {
      char z = input.charAt(loc);
      if (z=='(') inside++;
      if (z==')') 
      { 
        inside--;
        if (inside==0) return loc;
      }
      loc++;

    }


  }



  

}
