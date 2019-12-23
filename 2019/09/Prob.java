import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  public Prob(Scanner scan)
  {
    IntComp compy = new IntComp(scan.nextLine());

    compy.input_queue.add(2L);
    
    compy.execute();
    System.out.println(compy.term);
    System.out.println(compy.output_queue);


  }

}
