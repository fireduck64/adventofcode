import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();

    line = react(line);

    System.out.println(line + " " + line.length());


    int best_len=line.length();
    
    for(char z='a'; z<='z'; z++)
    {
      char z2 = new String("" + z).toUpperCase().charAt(0);

      String l2 = line.replace("" +z2,"").replace(""+z,"");
      String l3 = react(l2);

      if (l3.length() < best_len)
      {
        best_len = l3.length();
      }
      System.out.println("" + z + " - " + l3.length());
    }
    System.out.println("Part 2 - " + best_len);

  }

  public String react(String line)
  {
    while(true)
    {
      String start = line;

      line = reduce(line);
      if (start.equals(line)) break;

    }
    return line;

  }


  public String reduce(String line)
  {
    for(char z='a'; z<='z'; z++)
    {
      char z2 = new String("" + z).toUpperCase().charAt(0);
      line=line.replace("" +z +z2, "");
      line=line.replace("" +z2 +z, "");
    }


    return line;

  }
}
