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
    Game g = new Game(scan);


    for(int i=0; i<g.code.size(); i++)
    {
      Game clone = new Game(g);

      Game.Instruction inst = clone.code.get(i);
      if (inst.inst.equals("nop"))
      {
        try
        {
          inst.inst = "jmp";
          
          System.out.println(clone.execute());
          return;
        }
        catch(Throwable t)
        {
        }

      }

      if (inst.inst.equals("jmp"))
      {
        try
        {
          inst.inst = "nop";
          
          System.out.println(clone.execute());
          return;
        }
        catch(Throwable t)
        {
        }



      }



    }

  }

}
