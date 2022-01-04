import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  List<String> inst;
  int pos;
  TreeMap<String, Long> regs=new TreeMap<>();

  public Prob(Scanner scan)
  {
    inst = In.lines(scan);
    reset();

    exec();
    System.out.println("Part 1: " + regs.get("b"));

    reset();
    regs.put("a", 1L);
    exec();
    System.out.println("Part 2: " + regs.get("b"));

    

  }

  public void reset()
  {
    pos = 0;
    regs.clear();
    regs.put("a", 0L);
    regs.put("b", 0L);


  }

  public long exec()
  {
    long ops=0;

    while(true)
    {

      if (pos < 0) return ops;
      if (pos >= inst.size()) return ops;
      ops++;

      String line = inst.get(pos).replace(",", "");
      List<String> parts = Tok.en(line, " ");
      List<Integer> vals = Tok.ent(line, " ");
      String cmd = parts.get(0);

      boolean jumpy=false;

      if (cmd.equals("inc"))
      {
        String reg = parts.get(1);
        regs.put(reg, regs.get(reg) + 1L);
      }
      else if (cmd.equals("hlf"))
      {
        String reg = parts.get(1);
        regs.put(reg, regs.get(reg) / 2L);
      }
      else if (cmd.equals("tpl"))
      {
        String reg = parts.get(1);
        regs.put(reg, regs.get(reg) * 3L);
      }
      else if (cmd.equals("hlf"))
      {
        String reg = parts.get(1);
        regs.put(reg, regs.get(reg) / 2L);
      }
      else if (cmd.equals("jmp"))
      {
        int offset = vals.get(0);
        pos += offset;
        jumpy=true;
      }
      else if (cmd.equals("jie"))
      {
        int offset = vals.get(0);
        String reg = parts.get(1);
        if (regs.get(reg) % 2 == 0)
        {
          jumpy=true;
          pos += offset;
        }
      }
      else if (cmd.equals("jio"))
      {
        int offset = vals.get(0);
        String reg = parts.get(1);
        if (regs.get(reg) == 1)
        {
          jumpy=true;
          pos += offset;
        }
      }
      else
      {
        E.er(parts.toString());
      }

      if (!jumpy) pos++;

    }


  }




}
