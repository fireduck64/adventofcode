
import java.util.*;

public class Jumpy
{
  TreeMap<String, Long> regs=new TreeMap<>();
  ArrayList<Inst> inst_lst = new ArrayList<>();
  long highest_val = 0L;

  public Jumpy(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      inst_lst.add(new Inst(line));
    }
  }

  public void run()
  {
    for(Inst in : inst_lst)
    {
      in.exec();
    }


  }


  long getReg(String r)
  {
    if (!regs.containsKey(r)) regs.put(r, 0L);

    return regs.get(r);

  }

  public class Inst
  {
    String reg;
    int op;
    String cond_reg;
    String cond;
    long cond_val;

    public Inst(String line)
    {
      List<String> tok = Tok.en(line, " ");

      reg = tok.get(0);
      op = Integer.parseInt(tok.get(2));
      if (tok.get(1).equals("dec")) op = -op;

      cond_reg = tok.get(4);
      cond = tok.get(5);
      cond_val = Integer.parseInt(tok.get(6));
    }

    public void exec()
    {
      long cond_reg_val = getReg(cond_reg);
      boolean pass=false;
      if (cond.equals(">")) pass = cond_reg_val > cond_val;
      else if (cond.equals("<")) pass = cond_reg_val < cond_val;
      else if (cond.equals(">=")) pass = cond_reg_val >= cond_val;
      else if (cond.equals("<=")) pass = cond_reg_val <= cond_val;
      else if (cond.equals("==")) pass = cond_reg_val == cond_val;
      else if (cond.equals("!=")) pass = cond_reg_val != cond_val;
      else E.er();

      if (pass)
      {
        long v = getReg(reg);
        v+=op;
        regs.put(reg, v);
        highest_val = Math.max(highest_val, v);
      }

    }


  }
}
