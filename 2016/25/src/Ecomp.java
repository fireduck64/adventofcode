import java.util.*;

public class Ecomp
{
  List<String> inst;

  int pos;
  TreeMap<String, Long> regs=new TreeMap<>();
  long last_tx = 1;
  long tx_count=0;

  public Ecomp(List<String> inst)
  {
    this.inst=new ArrayList<String>();
    this.inst.addAll(inst);

    reset();

  }
  public void reset()
  {
    pos=0;
    regs.clear();
    regs.put("a",0L);
    regs.put("b",0L);
    regs.put("c",0L);
    regs.put("d",0L);

  }

  TreeSet<String> states=new TreeSet<>();

  public long exec()
  {
    long op_count =0;
    while(true)
    {
      if (pos <0) return -1;
      if (pos >= inst.size()) return -1;

      String line = inst.get(pos);
      op_count++;
      //System.out.println("Line: "+pos + "/" + line  + "\n  " + regs);
      List<String> parts = Tok.en(line, " ");
      List<Integer> nums = Tok.ent(line, " ");
      String cmd = parts.get(0);
      if (cmd.equals("cpy"))
      {
        long v = getVal(parts.get(1));
        String b = parts.get(2);
        if (regs.containsKey(b)) 
        {
          regs.put(b, v);
        }
      }
      else if (cmd.equals("out"))
      {
        long v = getVal(parts.get(1));
        if (v < 0) return -1;
        if (v > 1) return -1;
        if (v == last_tx) return -1;
        last_tx=v;
        //System.out.println(v);
        tx_count++;
        if (tx_count > 8)
        {
          String state = pos + "/" + regs.toString();
          if (states.contains(state)) return 0;
          states.add(state);

        }
      }
      else if (cmd.equals("add"))
      {
        long v = getVal(parts.get(1));
        String b = parts.get(2);
        if (regs.containsKey(b))
        {
          regs.put(b, v);
          regs.put(b, regs.get(b) + v);
        }
      }
      else if (cmd.equals("inc"))
      {
        String a = parts.get(1);
        regs.put(a, regs.get(a) + 1L);
      }
      else if (cmd.equals("dec"))
      {
        String a = parts.get(1);
        regs.put(a, regs.get(a) - 1L);
      }
      else if (cmd.equals("jnz"))
      {

        // If we are doing a jump -2 on X
        // and dec x is one of the previous two lines
        // and the other one is an inc on y
        // then we add y = y +x and set x=0 and done.
        if (pos >= 2)
        if (parts.get(2).equals("-2"))
        {
          String x = parts.get(1);
          String y = null;
          long mult=1;

          if (inst.get(pos-1).equals("dec " +x))
          if (inst.get(pos-2).startsWith("inc"))
          {
            y = Tok.en(inst.get(pos-2), " ").get(1);
          }
          if (inst.get(pos-2).equals("dec " +x))
          if (inst.get(pos-1).startsWith("inc"))
          {
            y = Tok.en(inst.get(pos-1), " ").get(1);
          }

          if (inst.get(pos-1).equals("dec " +x))
          if (inst.get(pos-2).startsWith("dec"))
          {
            y = Tok.en(inst.get(pos-2), " ").get(1);
            mult=-1;
          }
          if (inst.get(pos-2).equals("dec " +x))
          if (inst.get(pos-1).startsWith("dec"))
          {
            y = Tok.en(inst.get(pos-1), " ").get(1);
            mult=-1;
          }

          if (y != null)
          { // We are in fun short case
           // System.out.println("Shorting " + regs.get(x) + " loops");
            regs.put(y, regs.get(y) + regs.get(x) * mult);
            regs.put(x, 0L);
          }
        }
        
        {

          long x = getVal(parts.get(1));
          long y = getVal(parts.get(2));
          if (x != 0) pos+=y;
          else pos++;
        }
      }
      else if (cmd.equals("tgl"))
      {
        int x= pos+ (int)getVal(parts.get(1));
        //System.out.println("toggle " + x);

        if (x >= 0)
        if (x < inst.size())
        {
          String old = inst.get(x);
          String ncmd = null;
          List<String> oparts = Tok.en(old, " ");
          String ocmd= oparts.get(0);

          if (ocmd.equals("jnz")) ncmd="cpy";
          else if (ocmd.equals("inc")) ncmd="dec";
          else if (oparts.size() ==3) ncmd="jnz";
          else if (oparts.size() == 2) ncmd="inc";
          else E.er();

          
          StringBuilder sb = new StringBuilder();
          sb.append(ncmd);
          for(int i=1; i<oparts.size(); i++)
          {
            sb.append(" ");
            sb.append(oparts.get(i));
          }


          inst.set(x, sb.toString());

        }


      }
      else
      {
        E.er(cmd);
      }
      if (!cmd.equals("jnz")) pos++;

    }

  }
  public long getVal(String src)
  {
    if (regs.containsKey(src)) return regs.get(src);

    return Long.parseLong(src);

  }


}
