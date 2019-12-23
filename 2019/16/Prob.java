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
    ArrayList<Integer> input = explode(scan.nextLine());
    ArrayList<Integer> pattern = new ArrayList<>();
    pattern.add(0);
    pattern.add(1);
    pattern.add(0);
    pattern.add(-1);


    ArrayList<Integer> i2 = new ArrayList<>();
    for(int i=0; i<10000; i++)
    {
      i2.addAll(input);
    }

    System.out.println("i2 built");

    //i2 = input;


    ArrayList<Integer> res = runPhases(i2, pattern,100);


    System.out.println("Starting result: " + getNumber(res, 0, 8));

    int skip = Integer.parseInt(getNumber(input, 0, 7));

    System.out.println("After skip: " + getNumber(res, skip, 8));


    //System.out.println(runPhases(input, pattern,1));
    //System.out.println(runPhases(input, pattern,3));
    //System.out.println(runPhases(input, pattern,4));
  }


  public String getNumber(ArrayList<Integer> lst, int start, int cnt)
  {
    StringBuilder sb=new StringBuilder();

    for(int i=0; i<cnt; i++)
    {
      sb.append("" + lst.get(i+start));
    }

    return sb.toString();
  }


  

  public ArrayList<Integer> runPhases(ArrayList<Integer> input, ArrayList<Integer> pattern, int count)
  {
    ArrayList<Integer> g = input;
    for(int i=0; i<count; i++)
    {
      g = phase(g, pattern);
      System.out.print(".");
    }
    System.out.println();

    return g;

  }


  public ArrayList<Integer> explode(String in)
  {
    ArrayList<Integer> lst = new ArrayList<>();
    for(int i=0; i<in.length(); i++)
    {
      lst.add( Integer.parseInt(in.substring(i, i+1)));
    }

    return lst;

  }

  public ArrayList<Integer> phase(ArrayList<Integer> input, ArrayList<Integer> pattern)
  {
    ArrayList<Integer> result = new ArrayList<>();
    ArrayList<Integer> sum = new ArrayList<>();
    int s = 0;
    for(int i=0; i<input.size(); i++)
    {
      s+=input.get(i);
      sum.add(s);
    }

    for(int i=0; i<input.size(); i++)
    {
      result.add(getElement(input, pattern, i, sum));
    }
    return result;
  }

  public int getElement(ArrayList<Integer> input, ArrayList<Integer> pattern, int index, ArrayList<Integer> sum_list)
  {
    int sum =0;


    int i =0;
    while(i <input.size())
    {
        int p_idx = getPatternIndex(index, i, pattern.size());
        int len = getSpanLength(index, i, pattern.size());
        int p = pattern.get(p_idx);

        if (p != 0)
        {
          int v = getSum(sum_list, i, len);
          sum += p*v;

        }
        i+=len;
    }

    return Math.abs(sum) % 10;

  }

  public int getSum(ArrayList<Integer> sum_list, int i, int len)
  {
    
    int start = 0;
    if(i > 0) start = sum_list.get(i-1);
    int end_idx = Math.min(i+len, sum_list.size());

    if (end_idx == 0)
    {
      System.out.println("" + i + " " + len);
    }

    return sum_list.get(end_idx - 1) - start;

    

  }

  public int getPatternIndex(int element, int input_index, int pattern_len)
  {
    int ee = input_index + 1; // to skip first thing in pattern
    int multi = element + 1;
    int p_idx = ee / multi;

    return (p_idx % pattern_len);
  }
  public int getSpanLength(int element, int input_index, int pattern_len)
  {
    int multi = element + 1;
    if (input_index == 0) return Math.max(element,1);
    else return multi;
  }

}
