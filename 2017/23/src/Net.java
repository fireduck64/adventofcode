

import java.util.*;

public class Net
{
  ArrayList<String> inst = new ArrayList<>();

  TreeMap<String, Long> regs=new TreeMap<>();

  int pos;
  int mul_call;

  public Net(List<String> w)
  {
    inst.addAll(w);
    reset();
  }

  public void reset()
  {
    pos =0;
    mul_call=0;
    regs.clear();
    for(char z='a'; z<='h'; z++)
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
      //System.out.println(line);
      if (pos==17)
      {
        //System.out.println("Pos: " + pos + " " + regs);
      }
      if (pos==20)
      {
        //System.out.println("Pos: " + pos + " " + regs);
      }
      if (pos==25)
      {
        //System.out.println("Pos: " + pos + " " + regs);
      }

      String cmd = tok.get(0);
      String x = tok.get(1);
      long y = 0;
      if (tok.size() > 2)
      {
        y = getY(tok.get(2));
      }

      if(cmd.equals("set"))
      {
        regs.put(x, y);
      }
      else if(cmd.equals("add"))
      {
        regs.put(x, getReg(x) + y);
      }
      else if(cmd.equals("sub"))
      {


        /*if (getReg("a") == 1)
        if ((pos==20) && (getReg(x) == 2))
        { 
          regs.put("d",getReg("b")-1L);
        }*/

        /*if ((pos==25) && (getReg("h") == 2))
        {
          long target = getReg("c")-340L;
          long mult = (target - getReg("b")) / 34;
          regs.put("h", mult + 2L);


          //{a=1, b=109504, c=126300, d=109504, e=109504, f=0, g=0, h=6}

          regs.put("b",target);
          regs.put("d",target);
          regs.put("e",target);
        	System.out.println("Fix Pos: " + pos + " " + regs);
        }*/

        regs.put(x, getReg(x) - y);
      }
      else if(cmd.equals("add"))
      {
        regs.put(x, getReg(x) + y);
      }
      else if (cmd.equals("mul"))
      {
        regs.put(x, getReg(x) * y);
        mul_call++;
      }
      else if (cmd.equals("mod"))
      {
        regs.put(x, getReg(x) % y);
      }
      else if (cmd.equals("jnz"))
      {
        long x_val = getY(x);
        //if ((x_val != 0) && (pos != 19))
        if (x_val != 0)
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
        E.er(cmd);
      }

      if (!cmd.equals("jnz"))
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
