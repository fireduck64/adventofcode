
import java.util.*;

public class IntComp
{
  ArrayList<Long> code;
  int pos=0;
  boolean term=false;
  int rel_base=0;

  public IntComp(String input)
  {
    List<Long> c = Tok.enl(input, ",");
    code = new ArrayList<>();
    code.addAll(c);

  }
  private IntComp()
  {

  }

  LinkedList<Long> input = new LinkedList<>();
  LinkedList<Long> output = new LinkedList<>();

  public IntComp copy()
  {
    IntComp n = new IntComp();
    n.code = new ArrayList<>();
    n.code.addAll(code);
    n.pos = pos;
    n.term = term;
    n.rel_base = rel_base;
    n.input.addAll(input);
    n.output.addAll(output);

    return n;

  }
  public String toString()
  {
    return pos + " " + term + " " + rel_base + " " + input + " " + output + " " + code;
  }
  public String getHash()
  {
    return HUtil.getHash(toString());
  }

  public int exec()
  {
    if (term) return 0;
    while(true)
    {
      long op_data = readCode(pos);
      //System.out.println("op: " + op_data);
      long op = op_data % 100;
      long op_mod = op_data / 100;
      if (op == 99) {term=true; return 0;}
      else if (op == 1)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = getLoc(op_mod, pos+3);

        writeVal(c, a+b);
        pos+=4;

      }
      else if (op == 2)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = getLoc(op_mod, pos+3);

        writeVal(c, a*b);
        pos+=4;
      }
      else if (op == 3)
      {
        long a = getLoc(op_mod, pos+1);
        
        if (input.size() ==0) return 1; // Waiting for more input

        long v = input.pollFirst();
        writeVal(a, v);

        pos+=2;

      }
      else if (op == 4)
      {
        long a = readValue(op_mod, pos+1);
        output.add(a);
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
        long c = getLoc(op_mod, pos+3);

        long v = 0;
        if (a < b) v = 1;

        writeVal(c, v);
        pos+=4;
      }
      else if (op==8)
      {
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        long b = readValue(op_mod, pos+2); op_mod = op_mod / 10;
        long c = getLoc(op_mod, pos+3);

        long v = 0;
        if (a == b) v = 1;

        writeVal(c, v);
        pos+=4;
      }
      else if (op==9)
      {
        
        long a = readValue(op_mod, pos+1); op_mod = op_mod / 10;
        rel_base += a;
        pos+=2;
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
    while (p >= code.size()) 
    {
      code.add(0L);
    }
    code.set((int)p, v);
  }

  public long getLoc(long mod, long p)
  {
    mod = mod % 10;
    if (mod == 0)
    {
      return readCode(p);
    }
    else if (mod == 1)
    {
      return p;
    }
    else if (mod == 2)
    {
      return readCode(p)+rel_base;
    }
    else
    {
      throw new RuntimeException("unknown mode");
    }
  }

  public long readValue(long mod, long p)
  {
    return readCode(getLoc(mod, p));
  }

  public long readCode(long p)
  {
    if (p < 0) throw new RuntimeException("Neg pos read");
    while (p >= code.size()) 
    {
      code.add(0L);
    }

    return code.get((int)p);

  }
  public void writeAscii(String line)
  {
    for(int i = 0; i<line.length(); i++)
    {
      char z = line.charAt(i);
      input.add((long) z);
    }
    input.add(10L);

  }
  public String readOutput()
  {
    StringBuilder sb = new StringBuilder();
    for(long v : output)
    {
      sb.append((char)v);
    }
    return sb.toString();
  }

}
