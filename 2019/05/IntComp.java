import java.io.FileInputStream;
import java.util.*;

public class IntComp
{

  public int[] mem;
  private int next=0;
  public int input=0;

  public IntComp(String init_line)
  {
    ArrayList<Integer> mem_lst = new ArrayList<>();


    StringTokenizer stok = new StringTokenizer(init_line, ",");

    while(stok. hasMoreTokens())
    {
      mem_lst.add(Integer.parseInt(stok.nextToken()));
    }
    mem = new int[mem_lst.size()];
    
    for(int i=0; i<mem.length; i++)
    {mem[i] = mem_lst.get(i);
    }
  }

  private int readInput(int mode)
  {
    int v = mem[next]; next++;
    
    if (mode == 0)
    {
      return mem[v];
    }
    if (mode == 1)
    {
      return v;
    }
    
    throw new RuntimeException("Unknown input mode: " + mode);
  }

  int getmode(int code, int position)
  {
    code = code / 100;
    for(int i=0; i<position; i++) code = code / 10;

    return code % 10;
  }


  public void execute()
  {

    next = 0;

    while(true)
    {
      if (next >= mem.length) break;

      int code = mem[next]; next++;

      System.out.println("Code: " + code + " " + next);

      int op = code % 100;


      if (code == 99) break;
      else if (op == 1)
      {
        int v1 = readInput(getmode(code, 0));
        int v2 = readInput(getmode(code, 1));
        int v3_loc = mem[next]; next++;

        int s = v1+v2;
        mem[v3_loc] = s;

      }
      else if (op == 2)
      {
        int v1 = readInput(getmode(code, 0));
        int v2 = readInput(getmode(code, 1));
        int v3_loc = mem[next]; next++;

        int s = v1*v2;
        mem[v3_loc] = s;

      }
      else if (op == 3)
      {
        int loc = mem[next]; next++;
        mem[loc] = input;
      }
      else if (op == 4)
      {
        int v = readInput(getmode(code, 0));
        System.out.println("Output: " + v);
      }
      else if (op == 5) // jump if true
      {
        int v = readInput(getmode(code, 0));
        int dest = readInput(getmode(code, 1));
        if (v != 0) next=dest;
      }
      else if (op == 6) // jump if false
      {
        int v = readInput(getmode(code, 0));
        int dest = readInput(getmode(code, 1));
        if (v == 0) next=dest;
      }
      else if (op == 7) // less than
      {
        int v1 = readInput(getmode(code, 0));
        int v2 = readInput(getmode(code, 1));
        int v3_loc = mem[next]; next++;

        if (v1 < v2) mem[v3_loc] = 1;
        else mem[v3_loc] = 0;
      }
      else if (op == 8) // equals
      {
        int v1 = readInput(getmode(code, 0));
        int v2 = readInput(getmode(code, 1));
        int v3_loc = mem[next]; next++;

        if (v1 == v2) mem[v3_loc] = 1;
        else mem[v3_loc] = 0;
      }
 
      else
      {
        System.out.println("Unknown code: " + code);
      }


    }

  }

}
