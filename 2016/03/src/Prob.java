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
    int p1=0;
    List<String> input = In.lines(scan);
    int [][]m = new int[input.size()][3];
    int n=0;
    for(String line : input)
    {
      List<Integer> lst = Tok.ent(line, " ");

      m[n][0]=lst.get(0);
      m[n][1]=lst.get(1);
      m[n][2]=lst.get(2);
      Collections.sort(lst);
      int a,b,c;
      a = lst.get(0);
      b = lst.get(1);
      c = lst.get(2);
      if (a+b > c)    
        p1++;
      n++;

    }
    System.out.println("Part 1: " + p1);

    int p2=0;
    for(int j=0; j<input.size(); j+=3)
    {
      for(int i=0; i<3; i++)
      {
        ArrayList<Integer> t = new ArrayList<>();
        t.add(m[j+0][i]);
        t.add(m[j+1][i]);
        t.add(m[j+2][i]);
        Collections.sort(t);
        int a = t.get(0);
        int b = t.get(1);
        int c = t.get(2);

        if (a+b > c) p2++;
      }
    }

    System.out.println("Part 2: " + p2);
    

  }

}
