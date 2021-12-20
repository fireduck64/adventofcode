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
  Map2D<Character> mapo = new Map2D<Character>('.');
  String img_e;

  public Prob(Scanner scan)
  {
    img_e = scan.nextLine();
    if (img_e.length() != 512) E.er();
    scan.nextLine();

    MapLoad.loadMap(mapo, scan);

    //mapo.print();

    for(int i=0; i<2; i++)
    {
      runPass();
      //mapo.print();
    }
    System.out.println("Part 1: " + mapo.getCounts());
    for(int i=0; i<48; i++)
    {
      runPass();
      //mapo.print();
    }
    System.out.println("Part 2: " + mapo.getCounts());


  }

  public void runPass()
  {
    
    Map2D<Character> in = mapo;


    char new_default='.';
    // Define default
    {
      // Decide what a field of infinity will be in the new world
      Map2D<Character> def_check_in = new Map2D<Character>(in.default_value);
      Map2D<Character> def_check_out = new Map2D<Character>('.');
      writePixel(def_check_in, def_check_out, new Point(0,0));
      new_default=def_check_out.get(0,0);
      //System.out.println("new default value: " + new_default);

    }



    Map2D<Character> out = new Map2D<Character>(new_default);
    int buff=2;
    for(long x = in.low_x-buff; x<=in.high_x+buff; x++)
    for(long y = in.low_y-buff; y<=in.high_y+buff; y++)
    {
      writePixel(in, out, new Point(x,y));

    }
    mapo = out;

  }

  public void writePixel(Map2D<Character> in, Map2D<Character> out, Point p)
  {
    StringBuilder sb = new StringBuilder();

    //System.out.println(map.getAdj(p,true));

    for(Point q : in.getAdj(p, true))
    {
      sb.append(in.get(q));
    }
    String b = sb.toString().replace("#","1").replace(".","0");

    int idx = Integer.parseInt(b,2);

    char out_z = img_e.charAt(idx);
    //if (out_z =='#') 
    out.set(p, out_z);


  }

}
