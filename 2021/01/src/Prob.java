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
    int inc_count=0;
    int last=1000000;

    ArrayList<Integer> lst = new ArrayList<>();

    while(scan.hasNextInt())
    {
      int v = scan.nextInt();
      if (v > last) inc_count++;

      lst.add(v);
      last = v;
    }
    System.out.println(inc_count);

    inc_count=0;
    last=1000000;
    for(int i=2; i<lst.size(); i++)
    {
      int v = lst.get(i-2) + lst.get(i-1) + lst.get(i);
      if (v > last) inc_count++;
      last = v;
      
    }
    System.out.println(inc_count);

  }

}
