import java.io.FileInputStream;
import java.util.*;

public class ProbB
{
  public static void main(String args[]) throws Exception
  {
    new ProbB(new Scanner(new FileInputStream(args[0])));

  }

  private String code_line;

  int best_thrust = -100000000;
  LinkedList<Integer> best_seq = null;

  public ProbB(Scanner scan)
  {
    code_line = scan.nextLine();
   
    rec(new LinkedList<Integer>(), new TreeSet<Integer>(), 0);

    System.out.println(best_seq);
    System.out.println(best_thrust);
  }

  public void rec(LinkedList<Integer> seq, TreeSet<Integer> used_phases, int amp)
  {
    if (amp == 5)
    {
      IntComp compy[]=new IntComp[5];

      for(int i=0; i<5; i++)
      {
        compy[i] = new IntComp(code_line);
        compy[i].input_queue.add(seq.get(i));

      }
      compy[0].input_queue.add(0);

      while(true)
      {
        for(int i=0; i<5; i++)
        {
          compy[i].execute();
          int j = (i +1)%5; 

          if ((i==4) && (compy[i].term))
          {
            int thrust = compy[i].output_queue.pop();
            if ((best_seq == null) || (thrust > best_thrust))
            {
              best_seq = new LinkedList<>();
              best_seq.addAll(seq);
              best_thrust = thrust;
            }
            return;

          }
 
          while(compy[i].output_queue.size()>0)
          {
            compy[j].input_queue.add(compy[i].output_queue.poll());
          }

        }

      }
    }

    for(int i=5; i<10; i++)
    {
      if (!used_phases.contains(i))
      {

        seq.add(i);
        used_phases.add(i);
        rec(seq,used_phases,amp+1);

        seq.removeLast();
        used_phases.remove(i);

      }
      

    }


  }

}
