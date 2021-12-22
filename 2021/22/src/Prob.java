import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import duckutil.TaskMaster;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;


public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  Map3D<Integer> mapo = new Map3D<Integer>(0);
  TreeMap<Integer, TreeSet<Integer> > edge_x = new TreeMap<>();
  TreeMap<Integer, TreeSet<Integer> > edge_y = new TreeMap<>();
  TreeMap<Integer, TreeSet<Integer> > edge_z = new TreeMap<>();

  ThreadPoolExecutor exec = TaskMaster.getBasicExecutor(64, "prob");


  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      line=line.replace(".."," ");
      line=line.replace("=", " ");
      line=line.replace(",", " ");
      int state = 0;
      if (line.startsWith("on")) state=1;
      List<Integer> lst = Tok.ent(line, " ");

      setBox(state, 
        lst.get(0),
        lst.get(1),
        lst.get(2),
        lst.get(3),
        lst.get(4),
        lst.get(5));
    }
    System.out.println("Part 1: " + mapo.getCounts());
    System.out.println(edge_x.size());
    System.out.println(edge_y.size());
    System.out.println(edge_z.size());

    System.out.println("Part 2: " + getCount());

    test();
  }

  public void test()
  {
    Box a=new Box(1, 0, 10, 0, 10, 0, 10);
    Box b=new Box(1, 0, 9, 0, 9, 0, 9);

    System.out.println(a.contains(b));

  }

  ArrayList<Box> boxes=new ArrayList<Box>();

  public class Box
  {
    int state;
    long x1, x2, y1, y2, z1, z2;
    int box_id;
    public Box(int state, int x1, int x2, int y1, int y2, int z1, int z2)
    {
      
      this.state = state;
      this.x1=Math.min(x1,x2);
      this.x2=Math.max(x1,x2);
      this.y1=Math.min(y1,y2);
      this.y2=Math.max(y1,y2);
      this.z1=Math.min(z1,z2);
      this.z2=Math.max(z1,z2);
    }

    public void setup()
    {
      box_id = boxes.size();
      boxes.add(this);

      addEdge(edge_x, x1, box_id);
      addEdge(edge_x, x2+1, -1);
      addEdge(edge_y, y1, box_id);
      addEdge(edge_y, y2+1, -1);
      addEdge(edge_z, z1, box_id);
      addEdge(edge_z, z2+1, -1);

    }
    public long getVolume()
    {
      return (1 + x2 - x1) * (1 + y2 - y1) * (1 + z2 - z1);

    }
    public String toString()
    {
      return String.format("Box{%d %d %d %d %d %d}",x1,x2,y1,y2,z1,z2);
      
    }
    public boolean contains(Box little)
    {
      if (x1 <= little.x1)
      if (little.x2 <= x2)
      if (y1 <= little.y1)
      if (little.y2 <= y2)
      if (z1 <= little.z1)
      if (little.z2 <= z2)
      {
        //System.out.println(toString() +" check " + little);
        return true;
      }
      return false;


    }
  }

  public void addEdge(TreeMap<Integer, TreeSet<Integer> > e, long val, int box_id)
  {
    int i_val = (int)val;
    if (!e.containsKey(i_val)) e.put(i_val, new TreeSet<Integer>());
    if (box_id >=0)
    {
      e.get(i_val).add(box_id);
    }
  }

  public long getCount()
  {
    ArrayList<Integer> xlst = new ArrayList<>(); xlst.addAll(edge_x.keySet());
    ArrayList<Integer> ylst = new ArrayList<>(); ylst.addAll(edge_y.keySet());
    ArrayList<Integer> zlst = new ArrayList<>(); zlst.addAll(edge_z.keySet());

    AtomicLong count = new AtomicLong(0);

    List<Callable<String> > call_list = new LinkedList<>();

    for(int x_idx_ = 0; x_idx_<xlst.size()-1; x_idx_++)
    {
      int x_idx = x_idx_;
      int x = xlst.get(x_idx);
      call_list.add(new Callable<String>(){
        public String call()
        {
          for(int y_idx = 0; y_idx<ylst.size()-1; y_idx++)
          for(int z_idx = 0; z_idx<zlst.size()-1; z_idx++)
          {
            int y = ylst.get(y_idx);
            int z = zlst.get(z_idx);

            TreeSet<Integer> involved=new TreeSet<>();

            Box little= new Box(1,
                      x, xlst.get(x_idx+1)-1,
                      y, ylst.get(y_idx+1)-1,
                      z, zlst.get(z_idx+1)-1);


            int state=0;
            for(int box_id =0; box_id < boxes.size(); box_id++)
            {
              Box b = boxes.get(box_id);
              if (b.contains(little))
              {
                involved.add(box_id);
                state=b.state;
              }
            }
            if (state==1) count.addAndGet(little.getVolume());
            // Now we go down each channel and see if we are on or not

          }
          return null;
        }
      });
    }

    new TaskMaster<String>(call_list, exec).getResults();

    return count.get();


  }



  public void setBox(int state, int x1, int x2, int y1, int y2, int z1, int z2)
  {
    new Box(state, x1,x2,y1,y2,z1,z2).setup();

    x1=Math.max(x1, -50);
    x2=Math.min(x2, 50);

    y1=Math.max(y1, -50);
    y2=Math.min(y2, 50);

    z1=Math.max(z1, -50);
    z2=Math.min(z2, 50);

    for(int x = x1; x<=x2; x++)
    for(int y = y1; y<=y2; y++)
    for(int z = z1; z<=z2; z++)
    {
      mapo.set(x,y,z,state);

    }


  }


}
