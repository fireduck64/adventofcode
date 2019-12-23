import java.io.FileInputStream;
import java.util.*;

public class ProbB
{ 
  public static void main(String args[]) throws Exception
  { 
    new ProbB(new Scanner(new FileInputStream(args[0])));

  }

  Map2D<Map<String,Integer> > map = new Map2D<Map<String,Integer> >(new TreeMap<String,Integer>());

  public ProbB(Scanner scan)
  {
    lineToMap("A", scan.nextLine()); 
    lineToMap("B", scan.nextLine());

    int best=-1;

    for(Map.Entry<Integer, Integer> point : map.getAllPoints())
    {
      int x = point.getKey();
      int y = point.getValue();

      Map<String,Integer> set = map.get(point.getKey(), point.getValue());
      if (set.size() == 2)
      {

        int dist = set.get("A") + set.get("B");
        if ((best == -1) || (dist < best))
        {
          best = dist;
        }
      }
    }
    System.out.println(best);
  
  }
  

  private void lineToMap(String name, String line)
  {
    StringTokenizer stok = new StringTokenizer(line, ",");
    int loc_x = 0;
    int loc_y = 0;
    int step = 0;


    while(stok.	hasMoreTokens())
    {
      String cmd = stok.nextToken(); 
      int dir_x =0;
      int dir_y =0;
      if (cmd.charAt(0)=='U') dir_y=1;
      if (cmd.charAt(0)=='D') dir_y=-1;
      if (cmd.charAt(0)=='R') dir_x=1;
      if (cmd.charAt(0)=='L') dir_x=-1;

      String count_str = cmd.substring(1);
      int count = Integer.parseInt(count_str);

      for(int i=0; i<count; i++)
      {
        loc_x += dir_x;
        loc_y += dir_y;
        step++;
        addMap(loc_x, loc_y, name, step);
      }

    }
  
  }
  private void addMap(int x,int y, String s, int step)
  {
    TreeMap<String,Integer> set = new TreeMap<>();
    set.putAll( map.get(x,y));

    if (!set.containsKey(s))
    {
      set.put(s, step);
      map.set(x,y,set);

    }
    //System.out.println("Adding " + s + " to " + x + " " + y);
  }

}

