import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  public Prob(Scanner scan)
  {
    ArrayList<Integer> mem_lst = new ArrayList<>();

    while(scan.hasNextInt())
    {
      mem_lst.add(scan.nextInt());
    }

    for(int a=0; a<100; a++)
    for(int b=0; b<100; b++)
    {

      int[] mem = new int[mem_lst.size()];
      
      for(int i=0; i<mem.length; i++)
      {mem[i] = mem_lst.get(i); }

      int next = 0;
      mem[1] = a;
      mem[2] = b;

      while(true)
      {
        if (next >= mem.length) break;

        int code = mem[next]; next++;

        if (code == 99) break;
        else if (code == 1)
        {
          int v1_loc = mem[next]; next++;
          int v2_loc = mem[next]; next++;
          int v3_loc = mem[next]; next++;

          int v1 = mem[v1_loc];
          int v2 = mem[v2_loc];

          int s = v1+v2;
          mem[v3_loc] = s;

        }
        else if (code == 2)
        {
          int v1_loc = mem[next]; next++;
          int v2_loc = mem[next]; next++;
          int v3_loc = mem[next]; next++;

          int v1 = mem[v1_loc];
          int v2 = mem[v2_loc];

          int s = v1*v2;
          mem[v3_loc] = s;

        }
        else
        {
          System.out.println("Unknown code: " + code);
        }


      }
      if (mem[0] == 19690720)
      {
        int s = 100*a + b;
        System.out.println(s);
      }

    }

  }

}
