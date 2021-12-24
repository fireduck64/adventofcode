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

  Map2D<Integer> mapo = new Map2D<Integer>(0);

  final int max_x=50;
  final int max_y=6;

  public Prob(Scanner scan)
  {
    for(int i=0; i<max_x; i++)
    for(int j=0; j<max_y; j++)
    {
      mapo.set(i,j,0);
    }
    for(String line : In.lines(scan))
    {
      mapo = doInst(mapo, line);
    }
    mapo.print();
    TreeMap<Integer, Character> conv=new TreeMap<>();
    conv.put(0,' ');
    conv.put(1,'#');

    System.out.println(mapo.getPrintOut(conv));

  }

  public Map2D<Integer> doInst(Map2D<Integer> in, String line)
  {
    line = line.replace("="," ");
    Map2D<Integer> out = in.copy();
    if (line.startsWith("rect"))
    {
      line = line.replace("x", " ");
      List<Integer> nums = Tok.ent(line, " ");
      int x = nums.get(0);
      int y = nums.get(1);
      for(int i=0; i<x; i++)
      for(int j=0; j<y; j++)
      {
        out.set(i,j,1);
      }
    }
    else if (line.startsWith("rotate"))
    {
      List<Integer> nums = Tok.ent(line, " ");
      int a = nums.get(0);
      int b = nums.get(1);
      String dir=Tok.en(line, " ").get(1);

      if (dir.equals("row"))
      {
        int y=a;
        for(int x=0; x<max_x; x++)
        {
          int nx = (x + b) % max_x;
          out.set(nx, y, in.get(x,y));
        }
      }
      if (dir.equals("column"))
      {
        int x=a;
        for(int y=0; y<max_y; y++)
        {
          int ny = (y + b) % max_y;
          out.set(x, ny, in.get(x,y));
        }
      }

    }
    return out;
  }



}
