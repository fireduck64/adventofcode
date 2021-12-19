

import java.util.*;

public class Noise
{
  ArrayList<String> inst = new ArrayList<>();

  TreeMap<String, Long> regs=new TreeMap<>();
  long last_sound = 0;
  long last_recovered = 0;

  public Noise(List<String> input)
  {
    inst.addAll(input);
    reset();
  }

  public void reset()
  {
    regs.clear();
    last_sound = 0;
    last_recovered = 0;
    for(char z='a'; z<='z'; z++)
    {
      regs.put(""+z, 0L);
    }
  }

  public void exec()
  {
    int pos = 0;

    while(true)
    {
      if (pos < 0) return;
      if (pos >= inst.size()) return;
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
        last_sound = getReg(x);
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
        long x_val = getY(x);
        if (x_val != 0) 
        {
          last_recovered = last_sound;
          return;
        }
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
