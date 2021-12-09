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
    throws Exception
  {
    WristComp wc = new WristComp(scan);

    wc.r[0]=1;
    wc.execute();

    System.out.println(printNumbers(wc.r));

  }


  String printNumbers(long[] v)
  {
    StringBuilder sb=new StringBuilder();
    sb.append("[");
    for(int i=0; i<v.length; i++)
    {
      sb.append("" + v[i]);
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();

  }


}
