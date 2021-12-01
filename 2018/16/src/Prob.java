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

  public Prob(Scanner scan)
    throws Exception
  {
    int three_codes=0;

    TreeSet<Integer> id_code=new TreeSet<>();
    TreeMap<Integer, TreeSet<Integer> > pos_map = new TreeMap<>();
    TreeMap<Integer, TreeSet<Integer> > neg_map = new TreeMap<>();

    ArrayList<String> prog_commands = new ArrayList<String>();

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();

      if (line.startsWith("Before:"))
      {
        int[] before=getNumbers(line);
        int[] command=getNumbers(scan.nextLine());
        int[] after=getNumbers(scan.nextLine());

        /*System.out.println("Before: " + printNumbers(before));
        System.out.println("Command: " + printNumbers(command));
        System.out.println("After: " + printNumbers(after));
        */

        WristComp wc = new WristComp(before);
        int opcode = command[0];
        int a = command[1];
        int b = command[2];
        int c = command[3];

        int matching = 0;
        for(int code_id = 0; code_id < wc.op_list.size(); code_id++)
        {
          WristComp.Op op = wc.op_list.get(code_id);
          try
          {
            int new_c = op.doOp(a,b);
            if (new_c == after[c])
            {
              addMap(pos_map, opcode, code_id);
              matching++;
            }
            else
            {
              addMap(neg_map, opcode, code_id);
            }

          }
          catch(Exception e){System.out.println("e");}
        }
        if (matching >= 3) three_codes++;
      }
      else
      {
        if (line.trim().length() > 0)
        {
          prog_commands.add(line);
        }
      }
    }
    

    System.out.println("Part 1: " + three_codes);
    
    TreeSet<Integer> remaining = new TreeSet<>();
    for(int i=0; i<16; i++) remaining.add(i);

    TreeMap<Integer, Integer> code_map = new TreeMap<>();
    while(code_map.size() < 16)
    {
      for(int a : remaining)
      {
        if (pos_map.containsKey(a))
        if (pos_map.get(a).size() == 1)
        {
          int b = pos_map.get(a).first();
          code_map.put(a, b);
          for(int i=0; i<16; i++)
          {
            rmMap(pos_map,i,b);
          }
        }
      }
      remaining.removeAll(code_map.keySet());
    }
    System.out.println("Resolved all commands");

    WristComp wc = new WristComp();
    for(String line : prog_commands)
    {
      int[] command=getNumbers(line);
      int opcode = command[0];
      int a = command[1];
      int b = command[2];
      int c = command[3];

      int real_opcode = code_map.get(opcode);

      WristComp.Op op = wc.op_list.get(real_opcode);
      int new_c = op.doOp(a,b);
      wc.r[c]=new_c;
    }
    System.out.println("Prog result: " + printNumbers(wc.r));

  }

  public void addMap(TreeMap<Integer, TreeSet<Integer> > map, int a, int b)
  {
    if (!map.containsKey(a))
    {
      map.put(a, new TreeSet<>());
    }
    map.get(a).add(b);
  }
  public boolean hasMap(TreeMap<Integer, TreeSet<Integer> > map, int a, int b)
  {
    if (!map.containsKey(a)) return false;
    return map.get(a).contains(b);
  }
  public void rmMap(TreeMap<Integer, TreeSet<Integer> > map, int a, int b)
  {
    if (!map.containsKey(a)) return;
    map.get(a).remove(b);
  }

  String printNumbers(int[] v)
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

  int[] getNumbers(String line)
  {
    line = line.replace("Before:","");
    line = line.replace("After:", "");
    line = line.replace(",", "");
    line = line.replace("[", "");
    line = line.replace("]", "");
    Scanner scan=new Scanner(line);
    int r[] = new int[4];
    for(int i=0; i<4; i++)
    {
      r[i] = scan.nextInt();
    }
    return r;
  }

}
