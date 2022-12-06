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
  {
    // Front of stack is top of stack
    TreeMap<Integer,LinkedList<Character> > stacks = new TreeMap<>();
    TreeMap<Integer,LinkedList<Character> > stacks2 = new TreeMap<>();

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.trim().startsWith("1")) break;

      int idx=0;
      for(char z : Str.stolist(line))
      {
        if (idx%2==1)
        {
          if (z != ' ')
          {
            int st = idx/4+1;
            if (!stacks.containsKey(st)) stacks.put(st, new LinkedList<Character>());
            stacks.get(st).add(z);

            if (!stacks2.containsKey(st)) stacks2.put(st, new LinkedList<Character>());
            stacks2.get(st).add(z);
          }


        }
        idx++;

      }
    }
    System.out.println(stacks);

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<Integer> nlst = Tok.ent(line, " ");
      if (nlst.size() == 0) continue;
      int n = nlst.get(0);
      int from = nlst.get(1);
      int to = nlst.get(2);
      System.out.println(line + " " + nlst);

      LinkedList<Character> tmp = new LinkedList<>();

      for(int i=0; i<n; i++)
      {
        char z = stacks.get(from).poll();
        stacks.get(to).addFirst(z);
      }
      for(int i=0; i<n; i++)
      {
        char z = stacks2.get(from).poll();
        tmp.add(z);
      }
      for(int i=0; i<n; i++)
      {
        stacks2.get(to).addFirst(tmp.pollLast());

      }

    }
    System.out.println(stacks);
    System.out.println("Part 1");
    for(LinkedList<Character> l : stacks.values())
    {
      System.out.print(l.peek());
    }

    System.out.println();
    System.out.println("Part 2");
    for(LinkedList<Character> l : stacks2.values())
    {
      System.out.print(l.peek());
    }
    System.out.println();

  }

}
