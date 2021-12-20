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
  String start_img_str=".#./..#/###";

  TreeMap<String, Map2D<Character> > rule_map = new TreeMap<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      String rule_in = Tok.en(line, " ").get(0);
      String rule_out = Tok.en(line, " ").get(2);

      Map2D<Character> m_in = loadMap(rule_in);
      Map2D<Character> m_out = loadMap(rule_out);

      for(String f : getAllForms(m_in))
      {
        rule_map.put(f, m_out);
      }
    }
    System.out.println("total rules: " + rule_map.size());

    Map2D<Character> mapo = loadMap(start_img_str);
    for(int i=0; i<5; i++)
    {
      mapo = cycle(mapo);
    }
    System.out.println("Part 1: " + mapo.getCounts());
    for(int i=0; i<(18-5); i++)
    {
      mapo = cycle(mapo);
    }
    System.out.println("Part 2: " + mapo.getCounts());


  }

  public Map2D<Character> cycle(Map2D<Character> in)
  {
    int chunk_size = -1;
    if (in.getSize() % 2 == 0) chunk_size=2;
    else chunk_size=3;

    int out_chunk_size = chunk_size+1;

    Map2D<Character> out = new Map2D<Character>('.');

    for(long cx=0; cx < in.getSize()/chunk_size; cx++)
    for(long cy=0; cy < in.getSize()/chunk_size; cy++)
    {
      Map2D<Character> search = new Map2D<Character>('.');
      for(long x=0; x<chunk_size; x++)
      for(long y=0; y<chunk_size; y++)
      {
        search.set(x,y, in.get(cx*chunk_size+x, cy*chunk_size+y));
      }
      Map2D<Character> r = rule_map.get(search.getPrintOut());

      for(long x=0; x<out_chunk_size; x++)
      for(long y=0; y<out_chunk_size; y++)
      {
        out.set(cx*out_chunk_size+x, cy*out_chunk_size+y, r.get(x,y));
      }
    }
    return out;

  }

  public TreeSet<String> getAllForms(Map2D<Character> in)
  {
    TreeSet<String> out = new TreeSet<>();

    Map2D<Character> m = in;
    for(int r=0; r<4; r++)
    {
      out.add(m.getPrintOut());
      out.add(m.flipH().getPrintOut());
      out.add(m.flipV().getPrintOut());
      out.add(m.flipV().flipH().getPrintOut());

      m = m.rotateL();
    }

    return out;
  }

  public Map2D<Character> loadMap(String in)
  {
    Map2D<Character> m = new Map2D<>('.');
    
    int y=0;
    for(String row : Tok.en(in, "/"))
    {
      for(int x=0; x<row.length(); x++)
      {
        m.set(x,y,row.charAt(x));
      }
      y++;
    }
    return m;

  }

}
