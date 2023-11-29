import java.util.*;

public class In
{
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

  public static ArrayList<Long> numbers(Scanner scan)
  {
    ArrayList<Long> lst = new ArrayList<>();
    while(scan.hasNextLong())
    {
      lst.add(scan.nextLong());
    }

    return lst;

  }

}
