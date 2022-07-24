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
  Map2D<Character> map = new Map2D<Character>(' ');

  // Maps from string labels to the walkable point closest to the label
  // Note: string labels are always in alphabetical order
  TreeMap<String, List<Point> > label_map = new TreeMap<>();
  TreeSet<Point> outer_portals=new TreeSet<>();

  boolean p2_mode=false;

  public Prob(Scanner scan)
  {
    MapLoad.loadMap(map, scan);

    map.print();
    markLabels();
    for(String label : label_map.keySet())
    {
      //System.out.println("  " + label + " " + label_map.get(label));
    }

    SS p1 = (SS) Search.search(new SS(label_map.get("AA").get(0), 0, ImmutableList.of()));
    System.out.println("Part 1: " + p1.steps);
    System.out.println(outer_portals);

    p2_mode=true;
    SS p2 = (SS) Search.search(new SS(label_map.get("AA").get(0), 0, ImmutableList.of()));
    System.out.println("Part 2: " + p2.steps);
    //System.out.println("Part 2: " + p2.pos);
    //System.out.println("Part 2: " + p2.path);
  
  }

  public String getLabel(Point p)
  {
    char z = map.get(p);
    if (isLabel(z))
    {
      String label="";
    
      for(Point q : map.getAdjMagicOrder(p))
      {
        char o=map.get(q);
        if (isLabel(o))
        {
          label+=o;
        }
      }
      return label;
    }
    return null;
  }

  public void markLabels()
  {
    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      if (isLabel(z))
      {
        String label="";
        Point dot=null;
      
        int count_dot=0;
        int count_label=0;
        for(Point q : map.getAdjMagicOrder(p))
        {
          char o=map.get(q);
          if (o=='.')
          {
            count_dot++;
            dot=q;
          }
          if (isLabel(o))
          {
            label+=o;
            count_label++;
          }
        }
        if ((count_dot==1) && (count_label==2))
        {
          String label_str = label;
          if (!label_map.containsKey(label_str))
          {
            label_map.put(label_str, new ArrayList<Point>());
          }
          label_map.get(label_str).add(dot);

          if ((dot.x <= 4) || (dot.y <= 4) || (dot.x + 4 >= map.high_x) || (dot.y + 4 >= map.high_y))
          {
            outer_portals.add(dot);
          }


        }

      }
    }

  }
  public class SS extends State
  {
    Point pos;
    int steps;
    List<Point> path;

    public SS(Point pos, int steps, List<Point> path)
    {
      this.pos = pos;
      this.steps = steps;
      this.path = new ArrayList<>();
      this.path.addAll(path);
      this.path.add(pos);
    }
    public String toString(){return pos.toString();}

    public double getCost(){return steps;}
    public boolean isTerm()
    {
      return label_map.get("ZZ").get(0).equals(pos);
    }
    public List<State> next()
    {
      List<State> lst = new LinkedList<>();

      for(Point p : map.getAdj(pos, false))
      {
        p = new Point(p.x, p.y, pos.z);
        if (map.get(p)=='.')
        {
          lst.add(new SS(p, steps+1, path));
        }
        if (isLabel(map.get(p)))
        {
          String label = getLabel(p);
          for(Point q : label_map.get(label))
          {
            if (Math.abs(q.x-pos.x) + Math.abs(q.y-pos.y) != 0)
            {
              if (!p2_mode)
              {
                lst.add(new SS(q, steps+1, path));

              }
              else
              {
                Point delta_z=null;
                Point out_check = new Point(pos.x, pos.y);
                if (outer_portals.contains(out_check))
                {
                  delta_z=new Point(0,0,pos.z-1);
                  if (pos.z == 0) delta_z=null;
                }
                else
                {
                  delta_z=new Point(0,0,pos.z+1);
                }
                if(delta_z != null)
                {
                  Point n = q.add(delta_z);
                  //System.out.println("Portal from " + pos + " to " + n + " via " + label);
                  lst.add(new SS(n, steps+1, path));
                }

              }

            }
          }

        }

      }
      return lst;
    }


  }

  public boolean isLabel(char z)
  {
    if ('A' <= z)
    if (z <= 'Z')
    {
      return true;
    }
    return false;

  }

}
