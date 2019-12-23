import java.io.FileInputStream;
import java.util.*;

public class ProbB
{

  public static void main(String args[]) throws Exception
  {
    new ProbB(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public static final long CARDS=119315717514047L;


  public static final long SHUFFLES=101741582076661L;

  public ProbB(Scanner scan)
  {
    LinkedList<String> commands = new LinkedList<>();

    while(scan.hasNextLine())
    {
      commands.addFirst(scan.nextLine());
    }

    long x = 2020;
    HashSet<Long> f = new HashSet<>();
    while(true)
    {
      long y = x;
      for(String line : commands)
      {
        x = doCommand(line, x);
      }

      long delta = y - x;
      if (f.contains(delta))
      {
        System.out.println("dloop");
        return;
      }
      f.add(delta);
      if (f.contains(x))
      {
        System.out.println("loop");
        return;
      }
      f.add(x);
      System.out.println("Value at 2020: " + x);

    }


  }

  
  // The card at location "input", where did it come from?
  long doCommand(String line, long input)
  {
    String[] split=line.split(" ");

    if (line.equals("deal into new stack"))
    {
      return CARDS - input - 1;
    }
    else if (line.startsWith("cut"))
    {
      long cut_number = Long.parseLong(split[1]);
      if (cut_number < 0)
      {
        cut_number = CARDS + cut_number;
      }
      long v = input + cut_number;
      if (v < 0) v+=CARDS;
      if (v >= CARDS) v-=CARDS;

      return v;

    }
    else if (line.startsWith("deal with increment"))
    {

      long inc = Long.parseLong(split[3]);

      long offset_per_pass = CARDS % inc;
      
      long idx = 0;
      long x = 0;
      
      while (idx != input )
      { 
        //System.out.println(String.format("idx:%d x:%d input:%d", idx, x, input));
        long target = input;
        if (idx > input)
        {
          target = input + CARDS;
        }
        long jump = (target - idx) / inc;
        if (jump == 0) jump=1;
        x+=jump;
        idx = (idx + jump*inc) % CARDS;
        
      }

      return x;

      // (inc * x) % CARDS == input

    }
    else
    {
      System.out.println("Unk: " + line);
      throw new RuntimeException("Unk"); 
    }




  }



}
