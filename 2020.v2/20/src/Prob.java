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

  TreeMap<Long, Map2D<Character> > tiles=new TreeMap<>();

  TreeMap<String, Map2D<Character> > all_tiles = new TreeMap<>();
  TreeMap<String, Long> tile_id_map = new TreeMap<>();

  Map2D<Character> sol_map = null;

  public Prob(Scanner scan)
    throws Exception
  {
    {
      Map2D<Character> t = null;
      long tile_id=0L;
      int y=0;

      for(String line : In.lines(scan))
      {
        if (line.length() == 0)
        {
          tiles.put(tile_id, t);
          //t.print();
          //t.rotateR().print();
          t = null;
          tile_id = 0L;
        }
        else if (line.startsWith("Tile"))
        {
          tile_id = Tok.enl(line.replace(":", " "), " ").get(0);
          t = new Map2D<Character>(' ');
          y = 0;
        }
        else 
        {
          for(int i=0; i<line.length(); i++)
          {
            t.set(i, y, line.charAt(i));
          }
          y++;
        }
      }

    }
    System.out.println("Tiles: " + tiles.size());

    for(long tile_id : tiles.keySet())
    {
      Map2D<Character> src = tiles.get(tile_id);
      for(int i=0; i<4; i++)
      {
        Map2D<Character> f_h = src.flipH();

        all_tiles.put(f_h.getHashState(), f_h);
        all_tiles.put(src.getHashState(), src);

        tile_id_map.put(f_h.getHashState(), tile_id);
        tile_id_map.put(src.getHashState(), tile_id);


        src = src.rotateL();
      }
    }
    System.out.println("All mods: " + all_tiles.size());

    Map2D<Long> p1 = solveMap(new Map2D<Character>(' '), new Map2D<Long>(-1L), 0,0);
    long p1_val = p1.get(0,0) * p1.get(0,11) * p1.get(11,0) * p1.get(11,11);
    System.out.println("Part 1: " + p1_val);

    Map2D<Character> p2_map = reduceMap();

    Map2D<Character> seamonster = new Map2D<Character>(' ');
    MapLoad.loadMap(seamonster, new Scanner(new FileInputStream("seamonster")));

    seamonster.print();
    Map2D<Character> src = seamonster;
    TreeMap<String, Map2D<Character> > monsters = new TreeMap<>();

    for(int i=0; i<4; i++)
    {
      Map2D<Character> f_h = src.flipH();

      monsters.put(f_h.getHashState(), f_h);
      monsters.put(src.getHashState(), src);
      src = src.rotateL();
    }

    System.out.println("Monsters: " + monsters.size());

    int monster_count = 0;
    for(Point start : p2_map.getAllPoints())
    {
      for(Map2D<Character> m : monsters.values())
      {
        if (checkAndMark(p2_map, m, start)) monster_count++;
      }
    }

    //p2_map.print();

    System.out.println("Monsters found: " + monster_count);

    System.out.println("Part 2: " + p2_map.getCounts().get('#'));



  }

  public boolean checkAndMark(Map2D<Character> img, Map2D<Character> monster, Point start)
  {
    for(Point p : monster.getAllPoints())
    {
      Point q = start.add(p);
      if (monster.get(p)=='#')
      {
        char v = img.get(q);
        if (v == '.') return false;
        if (v == ' ') return false;
      }
    }
    for(Point p : monster.getAllPoints())
    {
      Point q = start.add(p);
      if (monster.get(p)=='#')
      {
        img.set(q, 'O');
      }
    }

    return true;

  }

  public Map2D<Long> solveMap(Map2D<Character> img, Map2D<Long> positions, int p_x, int p_y)
  {
    if (p_y == 12) 
    {
      sol_map = img.copy();
      return positions;
    }
    if (p_x == 12) return solveMap(img, positions, 0, p_y+1);

    //img.print();
    //System.out.println("" + p_y + " " + p_x);

    Set<Long> used = positions.getCounts().keySet();

    for(String hash : tile_id_map.keySet())
    {
      if (!used.contains(tile_id_map.get(hash)))
      { // if tile not used

        Map2D<Character> new_img = checkPos(img, all_tiles.get(hash), p_x, p_y);
        if (new_img != null)
        {
          Map2D<Long> new_positions = positions.copy();
          new_positions.set(p_x, p_y, tile_id_map.get(hash));
          new_positions = solveMap(new_img, new_positions, p_x+1, p_y);
          if (new_positions != null) return new_positions;
        }

      }

    }

    return null;


  }

  public Map2D<Character> checkPos(Map2D<Character> in, Map2D<Character> tile, int p_x, int p_y)
  {

    // check
    for(Point p : tile.getAllPoints())
    {
      Point q = p.add(new Point(p_x*9, p_y*9));

      char z = tile.get(p);
      if (in.get(q) != ' ')
      if (in.get(q) != z)
      {
        return null;
      }

    }

    // copy in
    Map2D<Character> out = in.copy();
    for(Point p : tile.getAllPoints())
    {
      Point q = p.add(new Point(p_x*9, p_y*9));
      char z = tile.get(p);
      out.set(q, z);
    }

    return out;

  }


  public Map2D<Character> reduceMap()
  {
    Map2D<Character> out = new Map2D<Character>(' ');
    for(int p_x=0; p_x<12; p_x++)
    for(int p_y=0; p_y<12; p_y++)
    for(int i=0; i<8; i++)
    for(int j=0; j<8; j++)
    {
      Point src = new Point(p_x * 9 + i + 1, p_y * 9 + j + 1);
      Point dst = new Point(p_x * 8 + i,     p_y * 8 + j);

      out.set(dst, sol_map.get(src));

    }
    return out;


  }

}
