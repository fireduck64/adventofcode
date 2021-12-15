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
  TreeMap<Integer, Integer> ring = new TreeMap<>();
  int skip_size=0;
  int pos = 0;

  public Prob(Scanner scan)
  {
    String input_line = scan.nextLine();
    List<Integer> input = Tok.ent(input_line,",");

    List<Integer> append = ImmutableList.of(17, 31, 73, 47, 23);

    reset();
    runRound(input);

    System.out.println("Part 1: " + (ring.get(0) * ring.get(1)));

    List<Integer> p2_input = new LinkedList<>();
    for(char z : Str.stolist(input_line))
    {
      p2_input.add((int)z);
    }
    p2_input.addAll(append);

    reset();
    for(int i=0; i<64; i++)
    {
      runRound(p2_input);

    }
    System.out.println("Part 2:");
    System.out.println(getDense());

  }

  public String getDense()
  {
    ArrayList<Integer> vals = new ArrayList<Integer>();
    vals.addAll(ring.values());
    StringBuilder sb = new StringBuilder();

    for(int b=0; b<vals.size(); b+=16)
    {
      int v = 0;
      for(int i=0; i<16; i++)
      {
        v = v ^ vals.get(i+b);
      }

      String vs = Integer.toString(v, 16);
      if (vs.length() < 2) vs = "0" + vs;
      sb.append(vs);


    }
    return sb.toString();


  }


  public void reset()
  {
    ring.clear();
    for(int i=0; i<256; i++) ring.put(i,i);
    skip_size=0;
    pos=0;
  }
  public void runRound(List<Integer> lens)
  {

    for(int len : lens)
    {
      LinkedList<Integer> stack = new LinkedList<>();
      // Build stack
      for(int i=0; i<len; i++)
      {
        int loc = (pos + i) % 256;
        stack.add(ring.get(loc));
      }
      // Apply reversed
      for(int i=0; i<len; i++)
      {
        int loc = (pos + i) % 256;
        ring.put(loc, stack.pollLast());
      }
      // update pos
      pos = (pos + len + skip_size) % 256;
      skip_size++;

    }

 
  }

 



}
