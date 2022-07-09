
import java.util.*;

public class IntComp
{
  ArrayList<Long> code;

  public IntComp(String input)
  {
    List<Long> c = Tok.enl(input, ",");
    code = new ArrayList<>();
    code.addAll(c);

  }

  public void exec()
  {
    int pos=0;
    while(true)
    {
      long op = readCode(pos);
      if (op == 99) return;
      else if (op == 1)
      {
        long a = readCode(readCode(pos+1));
        long b = readCode(readCode(pos+2));
        long c = readCode(pos+3);

        writeVal(c, a+b);
        pos+=4;

      }
      else if (op == 2)
      {
        long a = readCode(readCode(pos+1));
        long b = readCode(readCode(pos+2));
        long c = readCode(pos+3);

        writeVal(c, a*b);
        pos+=4;
      }
      else
      {
        throw new RuntimeException("Unknown opcode: " + op + " at pos: " + pos);
      }

    }

  }

  public void writeVal(long p, long v)
  {
    if (p < 0) throw new RuntimeException("Neg pos write");
    if (p >= code.size()) throw new RuntimeException("Off end");

    code.set((int)p, v);

  }

  public long readCode(long p)
  {
    if (p < 0) throw new RuntimeException("Neg pos read");
    if (p >= code.size()) throw new RuntimeException("Off end");

    return code.get((int)p);

  }

}
