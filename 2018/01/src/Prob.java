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
    int sum = 0;
    HashSet<Integer> reached = new HashSet<>();
    LinkedList<Integer> lst = new LinkedList<>();

    while(scan.hasNextInt())
    {
      int v = scan.nextInt();
      lst.add(v);
    }

    while(true)
    {
      for(int v : lst)
      {
      sum += v;

      if (reached.contains(sum))
      {
        System.out.println("Repeat: " + sum);
        return;
      }
      reached.add(sum);
      }
    }


  }

}
