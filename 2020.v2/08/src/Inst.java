
import java.util.Scanner;

public class Inst
{
  String op;
  long val;

  public Inst(String line)
  {
    Scanner scan = new Scanner(line);
    op = scan.next();
    val = scan.nextLong();
  }

  public Inst swap()
  {
    if (op.equals("jmp"))
    {
      return new Inst("nop 0");
    }
    else if (op.equals("nop"))
    {
      return new Inst("jmp " + val);
    }
    else
    {
      return this;
    }

  }


}
