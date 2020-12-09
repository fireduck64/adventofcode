import java.util.*;

public class Game
{

  ArrayList<Instruction> code = new ArrayList<>();

  public Game(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      code.add(new Instruction(scan.nextLine()));
    }
  }

  public Game(Game o)
  {
    for(Instruction inst : o.code)
    {
      code.add( new Instruction(inst) );
    }

  }

  public int execute()
  {
    int acc = 0;
    int exec_count[] = new int[code.size()];

    int next = 0;
    while(true)
    {
      Instruction inst = code.get(next);
      //System.out.println("S next: " + next + " acc: " + acc);

      exec_count[next]++;
      if (exec_count[next] == 2) throw new RuntimeException("loop");
      if (inst.inst.equals("acc")) acc += inst.oprand;
      if (inst.inst.equals("nop")) {}
      if (inst.inst.equals("jmp"))
      {
        next += inst.oprand;
      }
      else
      {
        next++;
      }

      //while(next >= code.size()) next -= code.size();
      //while(next <0) next += code.size();

      //System.out.println("E next: " + next + " acc: " + acc);
      if (next == code.size()) return acc;
    }
    



  }


  public class Instruction
  {
    String inst;
    int oprand;

    public Instruction(String line)
    {
      line = line.replace("+","");
      Scanner scan =new Scanner(line);

      inst = scan.next();
      oprand = scan.nextInt();
    }

    public Instruction(Instruction o)
    {
      inst = o.inst;
      oprand = o.oprand;
  
    }


  }


}
