import java.util.*;

public class Ecomp
{
  List<String> inst;

  int pos;
  TreeMap<String, Long> regs=new TreeMap<>();

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

  public void exec()
  {
    while(true)
    {
      if (pos <0) return;
      if (pos >= inst.size()) return;

      String line = inst.get(pos);
      List<String> parts = Tok.en(line, " ");
      List<Integer> nums = Tok.ent(line, " ");
      String cmd = parts.get(0);
      if (cmd.equals("cpy"))
      {
        long v = getVal(parts.get(1));
        regs.put(parts.get(2), v);
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
        long v = getVal(parts.get(1));
        long y = getVal(parts.get(2));
        if (v != 0) pos+=y;
        else pos++;
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
