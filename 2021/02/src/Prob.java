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
    int pos_x=0;
    int pos_y=0;
    int aim=0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> tok = Tok.en(line, " ");
      int val = Integer.parseInt(tok.get(1));
      String dir = tok.get(0);
      
      if (dir.equals("forward"))
      { 
        pos_x+=val;
        pos_y += aim * val;
      }
      if (dir.equals("up")) aim-=val;
      if (dir.equals("down")) aim+=val;

    }
    int v = pos_x * pos_y;
    System.out.println(v);
  }

}
