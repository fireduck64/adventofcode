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

  final int sz=10;
  final int grid;
  
  Map2D<Character> fin = new Map2D<Character>(' ');
  Map2D<Integer> grid_nums = new Map2D<Integer>(0);
  TreeMap<Integer, TileOptions > tiles = new TreeMap<>();

  Map2D<Character> seamonster = new Map2D<Character>(' ');

  public Prob(Scanner scan)
    throws Exception
  {
    MapLoad.loadMap(seamonster, new Scanner(new FileInputStream("seamonster")));

    while(scan.hasNextLine())
    {
      String line = scan.nextLine().trim();
      line=line.replace(":","");
      int tile_id = Integer.parseInt(Tok.en(line, " ").get(1));


      tiles.put(tile_id, makeOptions(readTile(scan)));
    }
    System.out.println("Read tiles: " + tiles.size());

    grid = (int)Math.round(Math.sqrt(tiles.size()));
    System.out.println("Grid size: " + grid);

    TreeSet<Integer> used = new TreeSet<>();
    boolean solution = rec(0,0,used);
    System.out.println(solution);

    long prod=1;
    prod *= grid_nums.get(0,0);
    prod *= grid_nums.get(grid-1,0);
    prod *= grid_nums.get(0,grid-1);
    prod *= grid_nums.get(grid-1,grid-1);
    System.out.println(prod);

    System.out.println( fin.getPrintOut(null) );
    Map2D<Character> trim = trimBorder();
    System.out.println("Trim:");
    System.out.println( trim.getPrintOut(null) );

    for(Map2D<Character> f : makeOptions(trim).rots)
    {

      int found = countAndMark(f);
      if (found > 0)
      {System.out.println( f.getPrintOut(null) );
      }
      System.out.print ("Found: " + found);
      

      int sea=0;
      for(Point p : f.getAllPoints())
      {
        if (f.get(p)=='#') sea++;
      }
      
      System.out.println(" sea: " + sea);

    }

  }

  public boolean rec(int grid_x, int grid_y, TreeSet<Integer> used)
  {
    //System.out.println("rec: " + grid_x + "," + grid_y);
    //System.out.println( fin.getPrintOut(null));
    if (grid_y == grid) return true;

    int gn_y = grid_y;
    int gn_x = grid_x + 1; 
    if (gn_x == grid)
    {
      gn_x=0;
      gn_y++;
    }

    for(int tile_id : tiles.keySet())
    {
      if (!used.contains(tile_id))
      {
        used.add(tile_id);
        for(Map2D<Character> tile : tiles.get(tile_id).rots)
        {
          if (checkAndPlace(grid_x, grid_y, tile))
          {
            grid_nums.set(grid_x, grid_y, tile_id);
            if (rec(gn_x, gn_y, used)) return true;
          }

        }

        used.remove(tile_id);

      }

    }

    for(int x=0; x<sz; x++)
    for(int y=0; y<sz; y++)
    {
      fin.set( grid_x*sz + x, grid_y*sz+y, ' ');
    }
    

    return false;

  }

  public boolean checkAndPlace(int grid_x, int grid_y, Map2D<Character> tile)
  {
    for(int x=0; x<sz; x++)
    for(int y=0; y<sz; y++)
    {
      char z = tile.get(x,y);
      if ((x==0) && (grid_x>0))
      {
        if (fin.get(grid_x*sz-1, grid_y*sz+y) != z) return false;
      }
      if ((y==0) && (grid_y>0))
      {
        if (fin.get(grid_x*sz+x, grid_y*sz-1) != z) return false;
      }

      fin.set( grid_x*sz + x, grid_y*sz+y, z);
    }

    return true;

  }

  public TileOptions makeOptions(Map2D<Character> in)
  {
    TileOptions to = new TileOptions();

    to.rots.add(in);

    Map2D<Character> m = in;
    for(int i=0; i<3; i++)
    {
      m = rotate(m);
      to.rots.add(m);
    }

    m = flip(in);
    to.rots.add(m);
    for(int i=0; i<3; i++)
    {
      m = rotate(m);
      to.rots.add(m);
    }


    //m = rotate(m);


    /*if (m.getHashState() != in.getHashState())
    {
      System.out.println(m.getPrintOut(null));
      System.out.println(in.getPrintOut(null));

      throw new RuntimeException("fail rotate");
    }*/

    return to;
  }

  public Map2D<Character> rotate(Map2D<Character> in)
  {
    int wid = (int)in.high_y;
    Map2D<Character> m = new Map2D<Character>(' ');
    for(Point p : in.getAllPoints())
    {
      Point n = new Point(p.y, wid-p.x);
      m.set(n, in.get(p));

    }

    return m;

  }
  public Map2D<Character> flip(Map2D<Character> in)
  {
    int wid = (int)in.high_y;
    Map2D<Character> m = new Map2D<Character>(' ');
    for(Point p : in.getAllPoints())
    {
      Point n = new Point(wid-p.x, p.y);
      m.set(n, in.get(p));

    }

    return m;

  }


  public Map2D<Character> readTile(Scanner scan)
  {
    Map2D<Character> m = new Map2D<Character>(' ');
    int y=0;
    while(true)
    {
      String line = scan.nextLine().trim();
      if (line.length()==0) break;

      for(int x=0; x<line.length(); x++)
      {
        m.set(x,y,line.charAt(x));
      }
      y++;

    }
    return m;

  }

  public Map2D<Character> trimBorder()
  {
    Map2D<Character> out = new Map2D<Character>(' ');
    int small_g=8;

    for(int gx=0; gx<grid; gx++)
    for(int gy=0; gy<grid; gy++)
    {
      for(int x=0; x<small_g; x++)
      for(int y=0; y<small_g; y++)
      {
        char z = fin.get(gx*sz + 1 + x, gy*sz+1+y);

        out.set( gx*small_g + x, gy*small_g+y, z); 

      }

    }
    return out;
  }

  public int countAndMark(Map2D<Character> in)
  {
    int found = 0;

    for(Point p : in.getAllPoints())
    {
      boolean monster = true;
      for(Point q : seamonster.getAllPoints())
      {
        if (seamonster.get(q)=='#')
        {
          char z = in.get(p.x + q.x, p.y + q.y);
          if ((z != '#') && (z != 'O'))
          {
            monster=false;
            break;

          }
        }
      }
      if(monster)
      {
       for(Point q : seamonster.getAllPoints())
        {
        if (seamonster.get(q)=='#')
        {
          in.set(p.x + q.x, p.y + q.y, 'O');
        }
        }
       
      
        found++;
      }


    }


    return found;

  }

  public class TileOptions
  {
    LinkedList<Map2D<Character> > rots = new LinkedList<>();

  }

}
