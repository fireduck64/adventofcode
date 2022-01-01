import java.io.FileInputStream;
import java.util.*;
import java.math.BigInteger;

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

    String v = scan.nextLine();
    while(true)
    {
      v = inc(v);
      if (isValid(v))
      {
        break;
      }
    }
    System.out.println("Part 1: " + v);
    while(true)
    {
      v = inc(v);
      if (isValid(v))
      {
        break;
      }
    }
    System.out.println("Part 2: " + v);
 
  }

  public boolean isValid(String in)
  {
    if (in.indexOf('i') >= 0) return false;
    if (in.indexOf('o') >= 0) return false;
    if (in.indexOf('l') >= 0) return false;

   

    int trips=0;
    for(int i=0; i<in.length()-2; i++)
    {
      char z=in.charAt(i);
      if (in.charAt(i+1)==z+1)
      if (in.charAt(i+2)==z+2)
      {
        trips++;
      }
    }
    if (trips==0) return false;

    int dubs=0;
    for(int i=0; i<in.length()-1; i++)
    {
      if (in.charAt(i) == in.charAt(i+1))
      {
        dubs++;
        i++;
      }

    }
    if (dubs<2) return false;


    return true;

  }


  public String inc(String in)
  {
    List<Character> lst = Str.stolist(in);

    int pos=lst.size()-1;
    while(true)
    {
      char z = lst.get(pos);
      if (z=='z')
      {
        lst.set(pos, 'a');
        pos--;
      }
      else
      {
        z++;
        lst.set(pos, z);
        break;
      }

    }
    return Str.listtos(lst);

  }

}
