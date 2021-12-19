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

  Map3D<Integer> fin_map = new Map3D<Integer>(0);
  Map3D<Integer> sensor_map =  new Map3D<Integer>(-1);

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
    popDistMap();
    // Add first scanner to fin_map
    {
      int start_scan=31;
      start_scan=4;
      if (scan_map.containsKey(31)) start_scan=31;

      System.out.println("Using sensor " + start_scan);
      ArrayList<Point> lst = scan_map.get(start_scan);
      for(Point p : lst)
      {
        fin_map.set(p, 1);
      }
      sensor_map.set(0,0,0,start_scan);
      scan_map.remove(start_scan);
    }
    expandPossibles();

    while(scan_map.size() > 0)
    {
      applyBestMatch();
      System.out.println("Remaining to assign: " + scan_map.size());
    }
    System.out.println(fin_map.getCounts());
    System.out.println(sensor_map.getCounts());

  }

  TreeMap<Integer, ArrayList<Map3D<Integer> > > possibles = new TreeMap<>();

  Object best_lock = new Object();
  int best_matches=1;
  int best_sensor=-1;
  Map3D<Integer> best_map=null;
  Point best_delta=null;

  public void applyBestMatch()
  {
    best_matches=0;
    best_sensor=-1;
    best_map=null;
    best_delta=null;

    List<Callable<String> > call_list = new LinkedList<>();

    for(int sensor : scan_map.keySet())
    {
      call_list.add(new Callable<String>(){
        
        public String call()
        {
          ArrayList<Map3D<Integer> > map_lst = possibles.get(sensor);
          for(Map3D<Integer> map : map_lst)
          {
            for(Point p : map.getAllPoints())
            {
              for(Point q : fin_map.getAllPoints())
              {
                // Transform that when added to p gets q
                Point delta = new Point(q.x - p.x, q.y - p.y, q.z - p.z);
                int matches = getMatchCount(delta, map);
                if (matches == 0) E.er();
                synchronized(best_lock)
                {
                  if (best_matches >= 12) return null;
                  if (matches > best_matches)
                  {
                    System.out.println("New best: " + matches);
                    best_matches = matches;
                    best_sensor = sensor;
                    best_map = map;
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
    new TaskMaster<String>(call_list, exec).getResults();

    System.out.println("Applying sensor: " + best_sensor);
    for(Point p : best_map.getAllPoints())
    {
      Point q = p.add(best_delta);
      fin_map.set(q, 1);
    }
    sensor_map.set(best_delta, best_sensor);
    scan_map.remove(best_sensor);



  }

  public int getMatchCount(Point delta, Map3D<Integer> map)
  {
    int match=0;
    for(Point p : map.getAllPoints())
    {
      if (fin_map.get( p.add(delta)) > 0) match++;
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
 
  public void expandPossibles()
  {
    for(int sensor : scan_map.keySet())
    {
      Map3D<Integer> base = new Map3D<Integer>(0);
      for(Point p : scan_map.get(sensor))
      {
        base.set(p, 1);
      }
      ArrayList<Map3D<Integer> > map_lst = expandPossibles(base);

      possibles.put(sensor, map_lst);
    }
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
