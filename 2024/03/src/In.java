import java.util.*;

public class In
{
  public static String oneString(Scanner scan)
  {
    StringBuilder sb = new StringBuilder();
    for(String line : lines(scan))
    {
      sb.append(line);
      sb.append('\n');
    }
    return sb.toString();

  }
  public static List<String> lines(Scanner scan)
  {
    LinkedList<String> lines = new LinkedList<>();
    while(scan.hasNextLine())
    {
      lines.add(scan.nextLine());
    }
    return lines;
  }

  public static Scanner newScan(List<String> lines)
  {
    StringBuilder sb =new StringBuilder();
    for(String l  :lines)
    {
      sb.append(l);
      sb.append('\n');
    }
    return new Scanner(sb.toString());
  }

  public static ArrayList<Long> longs(Scanner scan)
  {
    ArrayList<Long> lst = new ArrayList<>();
    while(scan.hasNextLong())
    {
      lst.add(scan.nextLong());
    }

    return lst;

  }
  public static ArrayList<Integer> ints(Scanner scan)
  {
    ArrayList<Integer> lst = new ArrayList<>();
    while(scan.hasNextInt())
    {
      lst.add(scan.nextInt());
    }

    return lst;

  }


}
