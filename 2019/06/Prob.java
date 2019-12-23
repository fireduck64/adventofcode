import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  TreeMap<String, String> om = new TreeMap<>();
  TreeMap<String, Integer> dm = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNext())
    {
      String s = scan.next();
      StringTokenizer stok=new StringTokenizer(s, ")");
      String big = stok.nextToken();
      String little = stok.nextToken();

      om.put(little,big);
    }

    int sum = 0;
    for(String s : om.keySet())
    {
      sum += getDepth(s);
    }
    System.out.println("Orbits: " + sum);
//    System.out.println(getPath("SAN"));
//    System.out.println(getPath("YOU"));

    LinkedList<String> a = getPath("SAN");
    LinkedList<String> b = getPath("YOU");

    while(a.get(0).equals(b.get(0)))
    {
      a.pop();
      b.pop();
    }
    System.out.println(a);
    System.out.println(b);
    int len = a.size() + b.size() - 2;
    System.out.println("Transfers: " + len);
  }


  public int getDepth(String s)
  {
    if (s.equals("COM")) return 0;
    if (dm.containsKey(s)) return dm.get(s);

    String p = om.get(s);

    int depth = getDepth(p) + 1;
    dm.put(s, depth);
    return depth;

  }

  public LinkedList<String> getPath(String start)
  {
    LinkedList<String> path = new LinkedList<>();

    String loc = start;
    while(true)
    {
      path.addFirst(loc);

      if (loc.equals("COM")) break;

      loc = om.get(loc);
    }
    return path;

  }

}
