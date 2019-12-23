import java.io.FileInputStream;
import java.util.*;

public class Prob
{ 
  public static void main(String args[]) throws Exception
  { 
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  Map2D<Set<String> > map = new Map2D<Set<String> >(new TreeSet<String>());

  public Prob(Scanner scan)
  {
    lineToMap("A", scan.nextLine()); 
    lineToMap("B", scan.nextLine());

    int best=-1;

    for(Map.Entry<Integer, Integer> point : map.getAllPoints())
    {
      int x = point.getKey();
      int y = point.getValue();

      Set<String> set = map.get(point.getKey(), point.getValue());
      if (set.size() == 2)
      {

        int dist = Math.abs(x) + Math.abs(y);
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
        addMap(loc_x, loc_y, name);
      }

    }
  
  }
  private void addMap(int x,int y, String s)
  {
    TreeSet<String> set = new TreeSet<>();
    set.addAll( map.get(x,y));

    set.add(s);
    map.set(x,y,set);
    //System.out.println("Adding " + s + " to " + x + " " + y);
  }

}

