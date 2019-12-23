import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  private String code_line;

  int best_thrust = -100000000;
  LinkedList<Integer> best_seq = null;

  public Prob(Scanner scan)
  {
    code_line = scan.nextLine();

   
    rec(new LinkedList<Integer>(), new TreeSet<Integer>(), 0, 0);

    System.out.println(best_seq);
    System.out.println(best_thrust);
  }

  public void rec(LinkedList<Integer> seq, TreeSet<Integer> used_phases, int amp, int input)
  {
    if (amp == 5)
    {
      if ((best_seq == null) || (input > best_thrust))
      {
        best_seq = new LinkedList<>();
        best_seq.addAll(seq);
        best_thrust = input;
      }
      return;
    }

    for(int i=0; i<5; i++)
    {
      if (!used_phases.contains(i))
      {

        IntComp compy = new IntComp(code_line);
        compy.input_queue.add(i);
        compy.input_queue.add(input);
        compy.execute();
        int output = compy.output;

        seq.add(i);
        used_phases.add(i);
        rec(seq,used_phases,amp+1, output);

        seq.removeLast();
        used_phases.remove(i);

      }
      

    }


  }

}
