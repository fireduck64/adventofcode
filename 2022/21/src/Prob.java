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

  HashMap<String, Monkey> monk=new HashMap<>(5000, 0.5f);

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      Monkey m = new Monkey(line);
      monk.put(m.id, m);
    }

    resolve();

    System.out.println("Part 1: " + monk.get("root").val);


    for(Monkey m : monk.values()) m.clear();

    monk.get("root").op="=";

    /*long val = 7012559479583L/2L;
    long group = 8;
    long pass = 0;
    while(true)
    {
      if (pass % 1000 == 0) System.out.println(val);
      for(Monkey m : monk.values()) m.clear();
      monk.get("humn").num = val;
      monk.get("humn").val = val;

      resolve();
      if (monk.get("root").val == 1L)
      {
        System.out.println("Part 2: " + val);
        break;
      }
      Monkey root = monk.get("root");
      long diff = monk.get(root.a).val - monk.get(root.b).val;
      System.out.println("" + val + " " + diff + " " + monk.get(root.a).val + " " + monk.get(root.b).val);

      val+=group;
      pass++;

    }*/

    System.out.println(search(0, 100000000000000L));


  }

  // Discovered that one of root's inputs was fixed
  // and the other seemed to scale with humn
  // so binary search
  public long search(long low, long high)
  {
    //System.out.println(" search: " + low + " " + high);
    if (low> high) E.er();
      for(Monkey m : monk.values()) m.clear();
      long val = (low + high + 1) / 2;
      monk.get("humn").num = val;
      monk.get("humn").val = val;

      resolve();
      if (monk.get("root").val == 1L)
      {
        System.out.println("Part 2: " + val);
        return val;
      }
      Monkey root = monk.get("root");

     
      if (monk.get(root.a).val > monk.get(root.b).val)
      {
        return search(val+1, high);
      }
      else
      {
        return search(low, val-1);
      }
  }


  public void resolve()
  {
    LinkedList<String> queue = new LinkedList<>();

    queue.add("root");

    while(queue.size() > 0)
    {
      String r = queue.pop();
      Monkey m = monk.get(r);
      if (!m.resolved)
      {
        if ((monk.get(m.a).resolved) && (monk.get(m.b).resolved))
        {
          m.solve();
        }
        else
        {
          queue.push(r);
          queue.push(m.a);
          queue.push(m.b);
        }
      }
    }

  }

  public class Monkey
  {
    String id;
    long num=-1;
    String a;
    String b;
    String op;

    boolean resolved=false;
    long val;

    public Monkey(String line)
    {
      line = line.replace(":","");
      List<String> tok = Tok.en(line, " ");
        
      id = tok.get(0);
      if (tok.size() == 2)
      {
        num = Long.parseLong( tok.get(1));
        resolved=true;
        val = num;
      }
      else
      {
        a = tok.get(1);
        op = tok.get(2);
        b = tok.get(3);
      }
    }
    public void clear()
    {
      if (op != null)
      {
        resolved=false;
        val=0L;
      }
    }

    void solve()
    {
      if (!monk.get(a).resolved) E.er();
      if (!monk.get(b).resolved) E.er();
      long v1 = monk.get(a).val;
      long v2 = monk.get(b).val;

      if (op.equals("+")) val = v1 + v2;
      else if (op.equals("*")) val = v1 * v2;
      else if (op.equals("/")) val = v1 / v2;
      else if (op.equals("-")) val = v1 - v2;
      else if (op.equals("="))
      {
        if (v1 == v2) val =1;
        else val=0;
      }
      else E.er(op);

      resolved=true;

    }

  }



}
