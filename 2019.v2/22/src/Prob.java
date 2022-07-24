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
  ArrayList<String> commands = new ArrayList<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      commands.add(scan.nextLine());
    }
    // Part 1
    {
       LinkedList<Integer> lst = factory(10007);
       lst = runCommands(lst);
       System.out.println(lst);
       int pos =0;
       for(int v : lst)
       {
        if (v==2019) System.out.println("Part 1: " + pos);
        pos++;
       }
    }

  }

  public LinkedList<Integer> runCommands(LinkedList<Integer> input)
  {

    LinkedList<Integer> lst = input;
    for(String s : commands)
    {
      System.out.println(s);
      if (s.equals("deal into new stack"))
      {
        lst = dealStack(lst);
      }
      else if (s.startsWith("cut"))
      {
        List<Integer> num = Tok.ent(s, " ");
        cut(lst, num.get(0));
      }
      else if(s.startsWith("deal with"))
      {
        List<Integer> num = Tok.ent(s, " ");
        lst = dealN(lst, num.get(0));
      }
      else
      {
        throw new RuntimeException("Unknown command: " + s);
      }
    }
    return lst;

  }


  public void testGroup()
  {
    LinkedList<Integer> lst = factory(10);
    System.out.println(lst);
    lst = dealStack(lst);
    System.out.println(lst);

    lst=factory(10);
    cut(lst,3);
    System.out.println(lst);

    lst=factory(10);
    cut(lst,-4);
    System.out.println(lst);

    lst=factory(10);
    lst = dealN(lst, 3);
    System.out.println(lst);
  }

  public void cut(LinkedList<Integer> lst, int n)
  {
    if (n>=0)
    {
    for(int i=0; i<n; i++)
    {
      int v = lst.poll();
      lst.add(v);
    }
    }

    if (n<0)
    {
      n = Math.abs(n);
      for(int i=0; i<n; i++)
      {
        int v = lst.pollLast();
        lst.addFirst(v);
      }
    }
  }

  public LinkedList<Integer> dealStack(LinkedList<Integer> in)
  {
    LinkedList<Integer> out = new LinkedList<>();
    while(!in.isEmpty())
    {
      out.addFirst(in.poll());
    }
    return out;

  }
  public LinkedList<Integer> dealN(List<Integer> in, int n)
  {
    ArrayList<Integer> o = new ArrayList<Integer>(in.size());
    int sz = in.size();
    for(int i=0; i<sz; i++) o.add(-1);
    int pos=0;
    for(int v : in)
    {
      o.set(pos, v);
      pos = (pos + n) % sz;
    }
    LinkedList<Integer> out = new LinkedList<>();
    out.addAll(o);
    return out;

  }

  public LinkedList<Integer> factory(int n)
  {
    LinkedList<Integer> lst = new LinkedList<>();
    for(int i=0; i<n; i++)
    {
      lst.add(i);
    }
    return lst;

  }

}
