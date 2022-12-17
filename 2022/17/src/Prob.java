import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileInputStream;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  ArrayList<Map2D<Character>> shapes=new ArrayList<>();
  long p2_rocks=1000000000000L;





  public Prob(Scanner scan)
    throws Exception
  {

    Scanner shape_scan = new Scanner(new FileInputStream("shapes"));

    LinkedList<String> shape_lines = new LinkedList<>();

    while(shape_scan.hasNextLine())
    {
      String line = shape_scan.nextLine();
      if (line.trim().length()==0)
      {
        Map2D<Character> s = new Map2D<Character>('.');
        MapLoad.loadMap(s, In.newScan(shape_lines));
        shapes.add(s);
        shape_lines.clear();
      }
      else
      {
        shape_lines.add(line);
      }
    }

    String moves = scan.nextLine();

    System.out.println(part1(moves));
    System.out.println(part2(moves));


  }

  public int part1(String moves)
  {
    Map2D<Character> cham = new Map2D<Character>('.');
    int rock_idx=0;

    int high_y=-1;
    int move_idx=0;

    for(int i=0; i<7; i++)
    {
      cham.set(i,-1,'#');
    }

    while(rock_idx < 2022)
    {
      Map2D<Character> rock = shapes.get(rock_idx % shapes.size());
      Point pos = new Point(2, high_y+4);

      while(true)
      {
        // jet
        char dir = moves.charAt(move_idx % moves.length()); 
        move_idx++;
        Point move = null;
        if (dir=='<') move = new Point(-1, 0);
        if (dir=='>') move = new Point(1,0);

        if (checkPos(cham, rock, pos.add(move))==0)
        {
          pos = pos.add(move);
        }
        else
        {
          //move_idx--;
        }

        // drop

        move = new Point(0, -1);
        if (checkPos(cham, rock, pos.add(move)) ==0)
        {
          pos = pos.add(move);
        }
        else
        {
          high_y = Math.max( high_y, place(cham, rock, pos));
          break;
        }
      }
      //cham.print();
      rock_idx++;



    }
    return high_y+1;
  }


  // Maps state to high_y at that state
  HashMap<String, StateInfo> cache = new HashMap<>();

  public long part2(String moves)
  {
    Map2D<Character> cham = new Map2D<Character>('.');
    long rock_idx=0;

    long high_y=-1;
    int move_idx=0;
    long extra_y=0;

    for(int i=0; i<7; i++)
    {
      cham.set(i,-1,'#');
    }

    while(rock_idx < 1000000000000L)
    {
      Map2D<Character> rock = shapes.get((int) (rock_idx % shapes.size()));
      Point pos = new Point(2, high_y+4);

      while(true)
      {
        // jet
        char dir = moves.charAt(move_idx % moves.length()); 
        move_idx = (move_idx + 1) % moves.length();
        Point move = null;
        if (dir=='<') move = new Point(-1, 0);
        if (dir=='>') move = new Point(1,0);

        if (checkPos(cham, rock, pos.add(move))==0)
        {
          pos = pos.add(move);
        }
        else
        {
          //move_idx--;
        }

        // drop

        move = new Point(0, -1);
        if (checkPos(cham, rock, pos.add(move)) ==0)
        {
          pos = pos.add(move);
        }
        else
        {
          high_y = Math.max( high_y, place(cham, rock, pos));
          break;
        }
      }
      //cham.print();



      long rock_mod = rock_idx % shapes.size();
      String state = HUtil.getHash(cham.getPrintOut(null, 0, high_y-200, 7, high_y) + " " + move_idx + " " + rock_mod);

      if (cache.containsKey(state))
      {
        if (rock_idx > 20000)
        {
          //System.out.println("Found repeat at: " + rock_idx + " from " + cache.get(state).rock_idx);
          StateInfo past = cache.get(state);
          long high_delta = high_y - past.high_y;
          long rock_delta = rock_idx - past.rock_idx;

          while(rock_idx + rock_delta < p2_rocks)
          {
            rock_idx += rock_delta;
            extra_y += high_delta;
          }
        }

      }
      else
      {
        cache.put(state, new StateInfo(high_y, rock_idx));
      }




      rock_idx++;



    }
    return high_y+1L+extra_y;



  }

  public class StateInfo
  { 
    long high_y;
    long rock_idx;

    public StateInfo(long y, long rock)
    {
      high_y = y;
      rock_idx = rock;
    }

  }


  // 0 - clear
  // 1 - in wall
  // 2 - in rock
  public int checkPos(Map2D<Character> cham, Map2D<Character> rock, Point pos)
  {
    for(Point p : rock.getAllPoints())
    {
      if (rock.get(p).equals('#'))
      {
        Point pp = pos.add(p);
        if (pp.x < 0) return 1;
        if (pp.x >= 7) return 1;
        char z = cham.get(pp);
        if (z!= '.') return 2;

      }
    }
    return 0;

  }
  public int place(Map2D<Character> cham, Map2D<Character> rock, Point pos)
  {
    int high_y =0;
    for(Point p : rock.getAllPoints())
    {
      if (rock.get(p).equals('#'))
      {
        Point pp = pos.add(p);
        cham.set(pp, rock.get(p));
        high_y = Math.max(high_y, (int)pp.y);
      }
    }

    return high_y;
 
  }

}
