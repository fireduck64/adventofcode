import java.io.FileInputStream;
import java.util.*;
import duckutil.TaskMaster;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  TreeMap<Integer, ArrayList<Point> > scan_map = new TreeMap<>();

  TreeMap<Long, Integer> dist_map=new TreeMap<>();

  ThreadPoolExecutor exec = TaskMaster.getBasicExecutor(64, "prob");

  ArrayList<Map3D<Integer> > comb_maps = new ArrayList<>();

  public Prob(Scanner scan)
  {
    ArrayList<Point> cur_scan = null;
    for(String line : In.lines(scan))
    {
      if (line.startsWith("---"))
      {
        int scan_no = Integer.parseInt(Tok.en(line, " ").get(2));
        cur_scan = new ArrayList<Point>();
        scan_map.put(scan_no, cur_scan);
      }
      else if (line.trim().length() > 0)
      {
        List<Integer> lst = Tok.ent(line, ",");
        Point p = new Point(lst.get(0), lst.get(1), lst.get(2));
        cur_scan.add(p);
      }

    }
    for(int sensor : scan_map.keySet())
    {
      Map3D<Integer> m = new Map3D<Integer>(0);
      for(Point p : scan_map.get(sensor))
      {
        m.set(p, 1);
      }
      m.set(0,0,0,1000+sensor);
      comb_maps.add(m);

    }
    popDistMap();

    while(comb_maps.size() > 1)
    {
      applyBestMatch();
      System.out.println("Remaining maps: " + comb_maps.size());

      for(Map3D<Integer> m : comb_maps)
      {
        System.out.print(" ");
        System.out.print(m.getCounts().get(1));
      }
      System.out.println();
    }
    System.out.println(comb_maps.get(0).getCounts());
    long max_dist =0;
    for(Point a : comb_maps.get(0).getAllPoints())
    for(Point b : comb_maps.get(0).getAllPoints())
    {
      if (comb_maps.get(0).get(a) > 1)
      if (comb_maps.get(0).get(b) > 1)
      {
        max_dist = Math.max( max_dist, a.getDistM(b));
      }

    }
    System.out.println("Part 2: " + max_dist);


  }


  Object best_lock = new Object();
  int best_matches=1;
  int best_map_a=-1;
  int best_map_b=-1;
  Map3D<Integer> best_map_b_rot=null;
  Point best_delta=null;

  public void applyBestMatch()
  {
    best_matches=0;
    best_map_a=-1;
    best_map_b=-1;
    best_map_b_rot=null;
    best_delta=null;

    List<Callable<String> > call_list = new LinkedList<>();
    for(int bi = 0; bi<comb_maps.size(); bi++)
    {
      int b = bi;
      ArrayList<Map3D<Integer> > b_rots = expandPossibles(comb_maps.get(b));
      for(int ai = b+1; ai<comb_maps.size(); ai++)
      {
        int a = ai;
        Map3D<Integer> map_a = comb_maps.get(a);
        for(Map3D<Integer> map_b : b_rots)
        {
          call_list.add(new Callable<String>(){
          
            public String call()
            {
              for(Point p_b : map_b.getAllPoints())
              {
                if (map_b.get(p_b)==1)
                for(Point p_a : map_a.getAllPoints())
                {
                  if (map_a.get(p_a)==1)
                  {
                    // Transform that when added to b gets a
                    // a = b + d
                    // b + d = a
                    // d = a - b
                    Point delta = new Point(p_a.x - p_b.x, p_a.y - p_b.y, p_a.z - p_b.z);
                    int matches = getMatchCount(delta, map_a, map_b);
                    if (matches == 0) E.er();
                    synchronized(best_lock)
                    {
                      if (best_matches >= 12) return null;
                      if (matches > best_matches)
                      {
                        System.out.println("New best: " + matches);
                        best_matches = matches;
                        best_map_a=a;
                        best_map_b=b;
                        best_map_b_rot=map_b;
                        best_delta  = delta;

                      }
                    }
                  }

                }
   
              }
              return null;
            }

          });

        }
      }

    }
  
    new TaskMaster<String>(call_list, exec).getResults();

    if (best_matches<12) E.er();
    System.out.println("Combining: " + best_map_a + " " +best_map_b);
    for(Point p : best_map_b_rot.getAllPoints())
    {
      
      Point q = p.add(best_delta);
      int bv = best_map_b_rot.get(p);
      int av = comb_maps.get(best_map_a).get(q);;
      if (av != 0)
      {
        if (av != bv) E.er();
      }
      comb_maps.get(best_map_a).set(q, best_map_b_rot.get(p));
    }
    comb_maps.remove(best_map_b);



  }

  public int getMatchCount(Point delta, Map3D<Integer> map_a, Map3D<Integer> map_b)
  {
    int match=0;
    for(Point p : map_b.getAllPoints())
    {
      int bv = map_b.get(p);
      int av = map_a.get(p.add(delta));
      if (bv == 1)
      if (av == 1) 
        match++;
    }
    return match;
  }

  public ArrayList<Map3D<Integer> > expandPossibles(Map3D<Integer> base)
  {
    ArrayList<Map3D<Integer> > map_lst = new ArrayList<>();
    for(int x=0; x<4; x++)
    {
      for(int y=0; y<3; y++)
      {
        for(int z=0; z<2; z++)
        {
          map_lst.add( rot(x,y,z, base));
        }
      }
    }
    if (map_lst.size() != 24) E.er();
    return map_lst;
  }
 
  

  public Map3D<Integer> rot(int x, int y, int z, Map3D<Integer> base)
  {
    Map3D<Integer> m = base;
    for(int i=0; i<x; i++) m = m.rotateX();
    for(int i=0; i<y; i++) m = m.rotateY();
    for(int i=0; i<z; i++) m = m.rotateZ();

    return m;

  }



  public void popDistMap()
  {
    for(ArrayList<Point> lst : scan_map.values())
    {
      for(int i=0; i<lst.size(); i++)
      for(int j=i+1; j<lst.size(); j++)
      {
        long d = lst.get(i).getDist2(lst.get(j));
        if (!dist_map.containsKey(d)) dist_map.put(d, 0);
        dist_map.put(d, dist_map.get(d) + 1);

      }
      //System.out.println(dist_map);
      //dist_map.clear();
    }
  }

  

}
