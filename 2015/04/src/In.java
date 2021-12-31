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

}
