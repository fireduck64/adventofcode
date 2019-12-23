import java.io.FileInputStream;
import java.util.*;

public class IntComp
{

  public long[] mem;
  private long next=0;
  public LinkedList<Long> input_queue=new LinkedList<>();
  public LinkedList<Long> output_queue=new LinkedList<>();
  public long rel_base=0;

  public boolean debug=false;
  public boolean term=false;

  public IntComp(String init_line)
  {
    ArrayList<Long> mem_lst = new ArrayList<>();


    StringTokenizer stok = new StringTokenizer(init_line, ",");

    while(stok. hasMoreTokens())
    {
      mem_lst.add(Long.parseLong(stok.nextToken()));
    }
    //mem = new int[mem_lst.size()];
    mem = new long[Math.max(mem_lst.size(), 1024*64)];
    
    for(int i=0; i<mem_lst.size(); i++)
    {
      mem[i] = mem_lst.get(i);
    }
  }

  public IntComp(IntComp src)
  {
    mem = new long[ src.mem.length ];
    for(int i=0; i<mem.length; i++) {mem[i] = src.mem[i];}
    next = src.next;
    input_queue.addAll(src.input_queue);
    output_queue.addAll(src.output_queue);
    rel_base=src.rel_base;
    term = src.term;

  }

  public String getHashState()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(next); sb.append(" ");
    sb.append(rel_base); sb.append(" ");
    sb.append(term);
    for(int i=0; i<mem.length; i++)
    {
      long v = get(i);
      if (v != 0) sb.append(" " + i + "=" + v);
    }
    sb.append(input_queue.toString());
    sb.append(output_queue.toString());

    return HUtil.getHash(sb.toString());

  }

  public void set(long loc, long value)
  {
    int l = (int)loc;
    mem[l] = value;
  }
  private long get(long loc)
  {

    int l = (int)loc;
    return mem[l];
  }

  private long readInput(int mode)
  {
    long v = get(next); next++;
    
    if (mode == 0)
    {
      return get(v);
    }
    if (mode == 1)
    {
      return v;
    }
    if (mode == 2)
    {
      return get(rel_base + v);
    }
    
    throw new RuntimeException("Unknown input mode: " + mode);
  }

  private long readRef(int mode)
  {
    long v = get(next); next++;
    if (debug) System.out.println("  ref mode: " + mode + " value: " + v);

    long res;
    
    if (mode == 0)
    {
      res=v;
    }
    else if (mode == 1)
    {
      throw new RuntimeException("Unknown ref mode: " + mode);
    }
    else if (mode == 2)
    {
      res=rel_base + v;
    }
    else
    {
      throw new RuntimeException("Unknown ref mode: " + mode);
    }
    if (debug) System.out.println("  ref result: " + res);
    return res;
  }
  

  int getmode(long code, int position)
  {
    long start_code = code;
    code = code / 100;
    for(int i=0; i<position; i++) code = code / 10;

    int mode = (int)(code % 10);

    if (debug) System.out.println(String.format("  mode: %d pos %d is %d", start_code, position, mode));

    return mode;
  }


  public void execute()
  {


    while(true)
    {
      if (next >= mem.length) break;

  
      if (debug) System.out.print("At : " + next);
      long code = get(next); next++;


      int op = (int)code % 100;
      if (debug) System.out.print(" " + code);

      if (debug) System.out.println();

      if (op == 99) 
      {
        term=true;
        break;
      }
      else if (op == 1)
      {
        long v1 = readInput(getmode(code, 0));
        long v2 = readInput(getmode(code, 1));
        long v3_loc = readRef(getmode(code, 2));

        long s = v1+v2;
        if (debug) System.out.println(String.format("  math %d + %d = %d", v1,v2,s));
        set(v3_loc, s);

      }
      else if (op == 2)
      {
        long v1 = readInput(getmode(code, 0));
        long v2 = readInput(getmode(code, 1));
        long v3_loc = readRef(getmode(code, 2));

        long s = v1*v2;
        if (debug) System.out.println(String.format("  math %d * %d = %d", v1,v2,s));
        set(v3_loc, s);

      }
      else if (op == 3)
      {
        if (input_queue.size() == 0)
        {
          // no input, exiting until we have more input
          next--;
          return;
        }
        long loc = readRef(getmode(code, 0));
        set(loc, input_queue.poll());
      }
      else if (op == 4)
      {
        long v = readInput(getmode(code, 0));
        output_queue.add(v);
      }
      else if (op == 5) // jump if true
      {
        long v = readInput(getmode(code, 0));
        long dest = readInput(getmode(code, 1));
        if (v != 0) next=dest;
      }
      else if (op == 6) // jump if false
      {
        long v = readInput(getmode(code, 0));
        long dest = readInput(getmode(code, 1));
        if (v == 0) next=dest;
      }
      else if (op == 7) // less than
      {
        long v1 = readInput(getmode(code, 0));
        long v2 = readInput(getmode(code, 1));
        long v3_loc = readRef(getmode(code, 2));

        if (v1 < v2) set(v3_loc,1);
        else set(v3_loc,0);
      }
      else if (op == 8) // equals
      {
        long v1 = readInput(getmode(code, 0));
        long v2 = readInput(getmode(code, 1));
        long v3_loc = readRef(getmode(code, 2));

        if (v1 == v2) set(v3_loc,1);
        else set(v3_loc,0);
      }
      else if (op == 9)
      {
        long v = readInput(getmode(code, 0));
        rel_base += v;
      }
      else
      {
        throw new RuntimeException("Unknown code: " + code);
      }
    }

  }


  public void addInputAsciiMode(String line)
  {

    System.out.println("Sending: " + line);
    for(int i=0; i<line.length(); i++)
    {
      input_queue.add( (long) line.charAt(i));
    }
    input_queue.add(10L);

  }

  public void printOutputAscii()
  {
    while(output_queue.size()>0)
    {
      long v = output_queue.poll();
      if (v > 128) System.out.print(v);
      else System.out.print((char)v);

    }
      System.out.println();

  }

}
