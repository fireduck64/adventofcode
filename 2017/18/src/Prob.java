import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    List<String> lines = In.lines(scan);
    Noise n = new Noise(lines);

    n.exec();
    System.out.println("Part 1: " + n.last_recovered);


    LinkedList<Long> q_a_2_b = new LinkedList<>();
    LinkedList<Long> q_b_2_a = new LinkedList<>();

    Net a = new Net(lines, 0, q_b_2_a, q_a_2_b);
    Net b = new Net(lines, 1, q_a_2_b, q_b_2_a);

    while(true)
    {
      long ops = 0;
      ops += a.exec();
      ops += b.exec();
      if (ops == 0) break;
    }
    System.out.println("Part 2: " + b.send_cnt);

  }

}
