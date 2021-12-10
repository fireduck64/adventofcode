
import java.util.*;

public class WristComp
{
  int inst_reg;
  long inst_ptr=0;
  long r[]=new long[6];

  ArrayList<Op> op_list = new ArrayList<>();
  TreeMap<String, Op> op_map = new TreeMap<>();
  ArrayList<Instruction> instructions = new ArrayList<>();
  long max_ops;

  public WristComp(WristComp src)
  {
    this();
    instructions = src.instructions;
  }

  public WristComp()
  {
    op_list.add(new Addr());
    op_list.add(new Addi());
    op_list.add(new Mulr());
    op_list.add(new Muli());
    op_list.add(new Banr());
    op_list.add(new Bani());
    op_list.add(new Borr());
    op_list.add(new Bori());
    op_list.add(new Setr());
    op_list.add(new Seti());
    op_list.add(new Gtir());
    op_list.add(new Gtri());
    op_list.add(new Gtrr());
    op_list.add(new Eqir());
    op_list.add(new Eqri());
    op_list.add(new Eqrr());

    for(Op op : op_list)
    {
      op_map.put(op.getName(), op);
    }
    reset();
  }
  public void reset()
  {

    inst_ptr=0;
    r=new long[6];
  }

  public WristComp(Scanner scan)
  {
    this();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.startsWith("#ip"))
      {
        inst_reg = Integer.parseInt(Tok.en(line, " ").get(1));
      }
      else
      {
        if (line.trim().length() > 0)
        if (!line.startsWith("#"))
        {
          instructions.add(new Instruction(line));
        }

      }

    }
  }

  public long execute()
    throws Exception
  {
    long op_count=0;
    long op_cmap[]=new long[instructions.size()];
    HashSet<String> states=new HashSet<String>(20480,0.5f);
    TreeMap<Long,Long> found_r4 = new TreeMap<>();
    long last_found = 0;

    while(true)
    {
      r[inst_reg]=inst_ptr;

      if ((inst_ptr < 0) || (inst_ptr >= instructions.size()))
      {
        //System.out.println(printNumbers(op_cmap));
        // Halted
        return op_count;
      }

      // do instruction
      Instruction inst = instructions.get((int)inst_ptr);
      op_cmap[(int)inst_ptr]++;

      long nc = inst.op.doOp(inst.a, inst.b);
      r[(int)inst.c] = nc;
      //System.out.println("" + inst_ptr + " " +inst);

      op_count++;
      if (op_count >= max_ops) return op_count;
      if (inst_ptr == 28)
      {
        long f = r[4];
        if (!found_r4.containsKey(f))
        {
          if (found_r4.size() == 0)
          {
            System.out.println("Part 1 solution: " + f);
          }
          found_r4.put(f, op_count);
          last_found=f;
          //System.out.println("New found: " + f + " at " + op_count);
        }
        else
        {
          System.out.println("Repeat: " + f);
          System.out.println("(Part 2 solution) last: " + last_found);
          return op_count;
        }
      }

      /*String state = printNumbers(r);
      if (states.contains(state)) 
      {
        System.out.println("Repeat at " + op_count);
        return op_count;
      }
      states.add(state);*/

      //if (op_count % 10000==0)
      {
        //System.out.println(printNumbers(r));
      }

      inst_ptr = r[inst_reg];
      inst_ptr++;
    }
    
  }

  public class Instruction
  {
    Op op;
    long a;
    long b;
    long c;

    public Instruction(String line)
    {
      Scanner scan = new Scanner(line);
      String op_name = scan.next();
      op=op_map.get(op_name);
      a=scan.nextLong();
      b=scan.nextLong();
      c=scan.nextLong();
    }
    public String toString()
    {
      return op.getName() + " " + a + " " + b + " " +c;
    }
  }

  public abstract class Op
  {
    public abstract long doOp(long a, long b) throws RegBoundFail;
    public abstract String getName();
    boolean debug;
    public void setDebug(boolean d){debug=d;}
  }


  public class Addr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (debug)
      {
        System.out.println("addr " + getR(a) + " " + getR(b));
      }
      return getR(a) + getR(b);
    }
    public String getName(){return "addr";}
  }

  public class Addi extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) + b;
    }
    public String getName(){return "addi";}
  }

  public class Mulr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) * getR(b);
    }
    public String getName(){return "mulr";}
  }

  public class Muli extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) * b;
    }
    public String getName(){return "muli";}
  }
  public class Banr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) & getR(b);
    }
    public String getName(){return "banr";}
  }

  public class Bani extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) & b;
    }
    public String getName(){return "bani";}
  }

  public class Borr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) | getR(b);
    }
    public String getName(){return "borr";}
  }

  public class Bori extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a) | b;
    }
    public String getName(){return "bori";}
  }

  public class Setr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return getR(a);
    }
    public String getName(){return "setr";}
  }

  public class Seti extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      return a;
    }
    public String getName(){return "seti";}
  }

  public class Gtir extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (a > getR(b)) return 1;
      return 0;
    }
    public String getName(){return "gtir";}
  }
  public class Gtri extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (getR(a) > b) return 1;
      return 0;
    }
    public String getName(){return "gtri";}
  }
  public class Gtrr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (debug)
      {
        System.out.println("gtrr "  + getR(a) + " > " + getR(b));
      }
      if (getR(a) > getR(b))
      {
        return 1;
      }
      return 0;
    }
    public String getName(){return "gtrr";}
  }


  public class Eqir extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (a == getR(b)) return 1;
      return 0;
    }
    public String getName(){return "eqir";}
  }
  public class Eqri extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (getR(a) == b) return 1;
      return 0;
    }
    public String getName(){return "eqri";}
  }
  public class Eqrr extends Op
  {
    public long doOp(long a, long b) throws RegBoundFail
    {
      if (getR(a) == getR(b))
      { 
        //System.out.println("eqrr "  + getR(a) + " == " + getR(b));
        return 1;
      }
      return 0;
    }
    public String getName(){return "eqrr";}
  }

  public long getR(long e) throws RegBoundFail
  {
    if (e < 0) throw new RegBoundFail();
    if (e > 5) throw new RegBoundFail();
    return r[(int)e];
  }

  public class RegBoundFail extends Exception
  {
  
  }

  String printNumbers(long[] v)
  {
    StringBuilder sb=new StringBuilder();
    sb.append("[");
    for(int i=0; i<v.length; i++)
    {
      sb.append("" + v[i]);
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();

  }

}
