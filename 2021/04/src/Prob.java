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

  ArrayList<Integer> run_list=new ArrayList<>();

  ArrayList<Map2D<Spot> > boards = new ArrayList<>();

  public Prob(Scanner scan)
  {
    {
      String line = scan.nextLine();
      List<String> nlst = Tok.en(line, ",");
      for(String s : nlst)
      {
        run_list.add( Integer.parseInt(s));
      }
    }
    while(scan.hasNextInt())
    {
      Map2D<Spot> b = new Map2D<Spot>(new Spot(-1));
      for(int i=0; i<5; i++)
      for(int j=0; j<5; j++)
      {
        int v = scan.nextInt();
        b.set(i,j, new Spot(v));
      }

      boards.add(b);
    }

    int last_score=0;

    TreeSet<Integer> done_boars=new TreeSet<>();

    for(int call : run_list)
    {
      for(int bi = 0; bi<boards.size(); bi++)
      {
        Map2D<Spot> b = boards.get(bi);
        if (!done_boars.contains(bi))
        {
          for(Point p : b.getAllPoints())
          {
            if (b.get(p).val == call)
            {
              b.get(p).marked=true;
              if (isBingo(b, p))
              {
                done_boars.add(bi);
                System.out.println("bingo: " + getScore(b) * call);
                last_score= getScore(b) * call;

              }
            }

          }
        }

      }

    }
    System.out.println("last: " + last_score);

  }

  public boolean isBingo(Map2D<Spot> board, Point p)
  {
    //check vert
    int marked=0;
    for(int i=0; i<5; i++)
    {
      if (board.get(p.x,i).marked) marked++;
    }
    if (marked==5) return true;

    marked=0;
    //cherk horizontal
    for(int i=0; i<5; i++)
    {
      if (board.get(i,p.y).marked) marked++;
    }
    if (marked==5) return true;
    return false;
    
 
  }
  public int getScore(Map2D<Spot> b)
  {
    int sum = 0;
    for(Point p : b.getAllPoints())
    {
      Spot s = b.get(p);
      if (!s.marked) sum+=s.val;
    }
    return sum;

  }

  public class Spot
  {
    int val;
    boolean marked;

    public Spot(int v)
    {
      val = v;
      marked = false;
    }
  }

}
