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
  Map2D<Integer> mapo=new Map2D<Integer>(0);

  public Prob(Scanner scan)
  {
    List<String> input = In.lines(scan);

    { // part 1


      for(String line : input)
      {
        line=line.replace(",", " ");
        List<Integer> vals = Tok.ent(line, " ");
        int x1 = vals.get(0);
        int y1 = vals.get(1);
        int x2 = vals.get(2);
        int y2 = vals.get(3);

        int mode =-1;
        if (line.startsWith("toggle")) mode =2;
        if (line.startsWith("turn on")) mode =1;
        if (line.startsWith("turn off")) mode =0;

        doOp(x1,y1,x2,y2, mode);
      }
      System.out.println("Part 1: " + mapo.getCounts());
    }

    { // part 2
      mapo=new Map2D<Integer>(0);


      for(String line : input)
      {
        line=line.replace(",", " ");
        List<Integer> vals = Tok.ent(line, " ");
        int x1 = vals.get(0);
        int y1 = vals.get(1);
        int x2 = vals.get(2);
        int y2 = vals.get(3);

        int mode =-1;
        if (line.startsWith("toggle")) mode =2;
        if (line.startsWith("turn on")) mode =1;
        if (line.startsWith("turn off")) mode =-1;

        doOp2(x1,y1,x2,y2, mode);
      }
      long v = 0;
      for(Point p : mapo.getAllPoints())
      {
        v+= mapo.get(p);
      }
      System.out.println("Part 2: " + v);
    }


  }

  public void doOp(int x1, int y1, int x2, int y2, int mode)
  {
    for(int x = x1; x<=x2; x++)
    for(int y = y1; y<=y2; y++)
    {
      if (mode == 1) mapo.set(x,y,1);
      if (mode == 0) mapo.set(x,y,0);
      if (mode == 2)
      {
        int p = mapo.get(x,y);
        if (p == 1) mapo.set(x,y,0);
        else mapo.set(x,y,1);

      }

    }

  }
  public void doOp2(int x1, int y1, int x2, int y2, int mode)
  {
    for(int x = x1; x<=x2; x++)
    for(int y = y1; y<=y2; y++)
    {
      mapo.set(x,y, Math.max(0, mapo.get(x,y) + mode));
    }
  }

}
