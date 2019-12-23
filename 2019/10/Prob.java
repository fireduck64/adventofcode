import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  TreeMap<Integer, Character> print_map = new TreeMap<>();

  public Prob(Scanner scan)
  {

    print_map.put(0, '.');
    print_map.put(1, '#');

    Map2D<Integer> map = new Map2D<Integer>(0);
    int y=0;
    int rock_count = 0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      for(int x=0; x<line.length(); x++)
      {
        if (line.charAt(x)=='#')
        {
          map.set(x,y,1);
          rock_count++;
        }
      }
      y++;
    }
    
    System.out.println(map.getPrintOut(print_map));

    Map.Entry<Long, Long> best = null;
    int best_count = 0;
    TreeMap<Long, TreeMap<Long, Map.Entry<Long, Long> > > best_dist_map = null;

    for(Map.Entry<Long, Long> s : map.getAllPoints())
    {
      int detected = 0;
      TreeSet<Long> angle_set = new TreeSet<>();
      TreeMap<Long, TreeMap<Long, Map.Entry<Long, Long> > > dist_map = new TreeMap<>();
    
      for(Map.Entry<Long, Long> a : map.getAllPoints())
      {
        long dx= s.getKey() - a.getKey();
        long dy= s.getValue() - a.getValue();
        long dist = dx*dx + dy*dy;

        if ((dx !=0) || (dy != 0))
        {
          double angle = Math.atan2(-dx,dy);//intentionally using wrong terms to make circle start where I want

          if (angle < 0.0) angle=angle+2.0*Math.PI;

          long angle_long = Math.round(angle * 1e12);

          if (!angle_set.contains(angle_long))
          {
            detected++;
            angle_set.add(angle_long);
            
            dist_map.put( angle_long, new TreeMap<Long, Map.Entry<Long, Long> >());
          }
          dist_map.get(angle_long).put(dist, a);

        }
      }

      if (detected > best_count)
      {
        best = s;
        best_count = detected;
        best_dist_map = dist_map;
      }


    }
    System.out.println("Best placement " + best_count + " " + best);

    ArrayList<Map.Entry<Long, Long> > kill_order = new ArrayList<>();

    while(kill_order.size() +1 < rock_count)
    {
      for(long angle : best_dist_map.keySet())
      {
        TreeMap<Long, Map.Entry<Long, Long> > rocks = best_dist_map.get(angle);

        if (rocks.size() > 0)
        {
          Map.Entry< Long, Map.Entry<Long, Long> > close = rocks.pollFirstEntry();

          System.out.println("Angle: " + angle + " " + close.getKey() + " " + close.getValue());

          kill_order.add( close.getValue() );
        }

      }
    }

    System.out.println(kill_order);
    System.out.println(kill_order.get(199));
    

  }

}
