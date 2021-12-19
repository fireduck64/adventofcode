

import java.util.*;

public class Net
{
  ArrayList<String> inst = new ArrayList<>();

  TreeMap<String, Long> regs=new TreeMap<>();

  LinkedList<Long> input;
  LinkedList<Long> output;
  int pos;
  int send_cnt;

  public Net(List<String> w, long proc, LinkedList<Long> input, LinkedList<Long> output)
  {
    this.input = input;
    this.output= output;
    inst.addAll(w);
    reset();
    regs.put("p", proc);
  }

  public void reset()
  {
    pos =0;
    regs.clear();
    for(char z='a'; z<='z'; z++)
    {
      regs.put(""+z, 0L);
    }
  }

  // Returns number of operations before blocking
  public long exec()
  {
    long ops = 0;

    while(true)
    {
      if (pos < 0) return ops;
      if (pos >= inst.size()) return ops;
      String line = inst.get(pos);
      List<String> tok = Tok.en(line, " ");
      String cmd = tok.get(0);
      String x = tok.get(1);
      long y = 0;
      if (tok.size() > 2)
      {
        y = getY(tok.get(2));
      }

      if (cmd.equals("snd"))
      {
        output.add(getReg(x));
        send_cnt++;
      }
      else if(cmd.equals("set"))
      {
        regs.put(x, y);
      }
      else if(cmd.equals("add"))
      {
        regs.put(x, getReg(x) + y);
      }
      else if (cmd.equals("mul"))
      {
        regs.put(x, getReg(x) * y);
      }
      else if (cmd.equals("mod"))
      {
        regs.put(x, getReg(x) % y);
      }
      else if (cmd.equals("rcv"))
      {
        if (input.size() == 0) return ops; 
        long v=input.pollFirst();
        regs.put(x,v);
      }
      else if (cmd.equals("jgz"))
      {
        long x_val = getY(x);
        if (x_val > 0)
        {
          pos = pos + (int)y;
        }
        else
        {
          pos++;
        }
      }
      else
      {
        E.er();
      }

      if (!cmd.equals("jgz"))
      {
        pos++;
      }
      ops++;

    }

  }

  public long getY(String y)
  {
    try
    {
      return Integer.parseInt(y);

    }
    catch(Exception e)
    {
      return getReg(y);
    }

  }
  public long getReg(String r)
  {
    if (!regs.containsKey(r)) E.er("Str: " + r);
    return regs.get(r);
    //if (regs.containsKey(r)) return regs.get(r);
    //return 0;

  }


}
