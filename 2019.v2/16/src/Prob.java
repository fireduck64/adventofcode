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
  ImmutableList<Integer> base = ImmutableList.of(0, 1, 0, -1);

  public Prob(Scanner scan)
  {
    ArrayList<Integer> input = new ArrayList<>();
    String line = scan.nextLine();
    for(char z : Str.stolist(line))
    {
      input.add(Integer.parseInt("" + z));
    }

    { // part 1
      int phases=100;
      ArrayList<Integer> in = input;
      for(int i=0; i<phases; i++)
      {
        in = proc(in);
      }
      System.out.println(in);
      for(int i=0; i<8; i++)
      {
        System.out.print(in.get(i));
      }
      System.out.println();
    }
    { // part 2
      int skip = Integer.parseInt(line.substring(0,7));
      ArrayList<Integer> in = new ArrayList<>();
      for(int i=0; i<10000; i++)
      {
        in.addAll(input);
      }
      int phases=100;
      System.out.println(in.size());
      System.out.print('#');

      for(int i=0; i<phases; i++)
      {
        in = proc(in);
        System.out.print('.');
      }
      System.out.println();
      for(int i=0; i<8; i++)
      {
        System.out.print(in.get(i+skip));
      }
      System.out.println();
 
      

    }

 

  }
  public ArrayList<Integer> proc(ArrayList<Integer> in)
  {
    ArrayList<Integer> sum_list = new ArrayList<>();
    {
      int sum = 0;
      for(int i = 0; i<in.size(); i++)
      {
        sum_list.add(sum);
        sum+=in.get(i);
      }
      sum_list.add(sum);
    }
    ArrayList<Integer> out = new ArrayList<>();
    for(int i = 0; i<in.size(); i++)
    {
      int sum = 0;
      int base_idx=0;
      final int base_rep=i+1;
      int j =0;
      while(j < in.size())
      {
        int start_sum = sum_list.get(j);

        int end_idx = Math.min(j + base_rep, in.size());
        if ((j==0) && (base_idx==0)) end_idx = Math.min(j + base_rep - 1, in.size());

        int end_sum = sum_list.get(end_idx);
        int m = base.get(base_idx);
        sum += m * (end_sum - start_sum);
        base_idx=(base_idx+1)%4;
        j=end_idx;

      }


      out.add(reduce(sum));
    }
    return out;
  }

  public int reduce(int n)
  {
    return Math.abs(n % 10);
  }

}
