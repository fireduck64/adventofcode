
import java.util.*;

public class IntComp
{
  ArrayList<Long> code;
  int pos=0;
  boolean term=false;

  public IntComp(String input)
  {
    List<Long> c = Tok.enl(input, ",");
    code = new ArrayList<>();
    code.addAll(c);

  }

  LinkedList<Long> input = new LinkedList<>();
  LinkedList<Long> output = new LinkedList<>();

  public int exec()
  {
    if (term) return 0;
    while(true)
    {
      long op_data = readCode(pos);
      long op = op_data % 100;
      long op_mod = op_data / 100;
      if (op == 99) {term=true; return 0;}
      else if (op == 1)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = readCode(pos+3);

        writeVal(c, a+b);
        pos+=4;

      }
      else if (op == 2)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = readCode(pos+3);

        writeVal(c, a*b);
        pos+=4;
      }
      else if (op == 3)
      {
        long a = readCode(pos+1);
        if (input.size() ==0) return 1; // Waiting for more input

        long v = input.pollFirst();
        writeVal(a, v);

        pos+=2;

      }
      else if (op == 4)
      {
        long a = readValue(op_mod, pos+1);
        output.add(a);
        //System.out.println(a);
        pos+=2;
      }
      else if (op==5)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        if (a != 0)
        {
          pos = (int)b;
        }
        else
        {
          pos+=3;
        }
      }
      else if (op==6)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        if (a == 0)
        {
          pos = (int)b;
        }
        else
        {
          pos+=3;
        }
      }
      else if (op==7)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = readCode(pos+3);

        long v = 0;
        if (a < b) v = 1;

        writeVal(c, v);
        pos+=4;
      }
      else if (op==8)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = readCode(pos+3);

        long v = 0;
        if (a == b) v = 1;

        writeVal(c, v);
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

  public long readValue(long mod, long p)
  {
    mod = mod % 10;
    if (mod == 0)
    {
      return readCode(readCode(p));
    }
    return readCode(p);
  }

  public long readCode(long p)
  {
    if (p < 0) throw new RuntimeException("Neg pos read");
    if (p >= code.size()) throw new RuntimeException("Off end");

    return code.get((int)p);

  }

}
