import java.util.*;

public class Calc
{
  ArrayList<Inst> insts;
  long acc;
  int pos;

  public Calc(ArrayList<Inst> insts)
  {
    this.insts = insts;

    acc=0;
    pos=0;


  }

  public int run()
  {
    TreeSet<Integer> runs=new TreeSet<>();

    while(true)
    {

      while (pos < 0) pos = pos + insts.size();
      while (pos >= insts.size()) pos = pos - insts.size();
      
      if (runs.contains(pos))
      {
        if (pos == 0) return 0;
        return 1;
      }

      runs.add(pos);
      Inst i = insts.get(pos);

      if (i.op.equals("nop"))
      {
        pos++;

      }
      else if (i.op.equals("acc"))
      {
        acc += i.val;
        pos++;
      }
      else if (i.op.equals("jmp"))
      {
        pos+=i.val;
      }
      else
      {
        throw new RuntimeException("unknown op: " + i.op);
      }



    }

  }

}
