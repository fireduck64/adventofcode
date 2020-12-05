import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    long m_id = 0;
    TreeSet<Long> taken = new TreeSet<>();
    while(scan.hasNext())
    {
      String s =scan.next();
      //Point p = decodeSeat(s);
      long id = decodeSeat(s);

      m_id = Math.max(id, m_id);

      taken.add(id);


    }

    System.out.println(m_id);

    for(long n=0; n<1000000; n++)
    {
      if (!taken.contains(n))
      if (taken.contains(n-1))
      if (taken.contains(n+1))
      {
        System.out.println(n);
      } 
    }


  }

  long decodeSeat(String s)
  {
    String row_s = s.substring(0,7);
    String col_s = s.substring(7);

    row_s = row_s.replaceAll("F","0").replaceAll("B","1");
    col_s = col_s.replaceAll("R","1").replaceAll("L","0");

    s = s.replaceAll("F","0").replaceAll("B","1");
    s = s.replaceAll("R","1").replaceAll("L","0");
    int row = Integer.parseInt(row_s, 2);
    int col = Integer.parseInt(col_s, 2);



    //return new Point(row, col); 

    return Integer.parseInt(s, 2);

  }

}
