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

  Map2D<String> map=new Map2D<String>("");

  public Prob(Scanner scan)
  {
    int y=0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> t = Tok.en(line, " ");
      int x=0;
      for(String s: t)
      {
        map.set(x,y, s);
        x++;
      }
      y++;
    }
    map.print();
    ArrayList<Integer> nx = new ArrayList<Integer>();
    for(int i=0; i<4; i++) nx.add(0);
    rec("", nx, "??");
  }

  public boolean match(String a, String b)
  {
    if (a.equals("??")) return true;
    if (b.equals("??")) return true;
    if (a.charAt(0) == b.charAt(0)) return true;
    if (a.charAt(1) == b.charAt(1)) return true;
    return false;

  }

  public boolean rec(String cmd, ArrayList<Integer> next, String last)
  {
    int done =0;
    for(int i=0; i<4; i++) 
    {
      if (next.get(i) == 4) done++;

    }
    if (done==4)
    {
      System.out.println(cmd);
      return true;
    }

    for(int i=0; i<4; i++)
    {
      int nx = next.get(i);
      if (nx < 4)
      {
        String b = map.get(nx, i);
        if (match(last,b))
        {
          ArrayList<Integer> n = new ArrayList<>();
          n.addAll(next);
          n.set(i, nx+1);

          if (rec(cmd +" " + b,n,b)) return true;

        }

      }

    }


    return false;

  }

}
