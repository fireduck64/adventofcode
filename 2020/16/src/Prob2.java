import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;

/**
 * Solution using n! recursive assignment
 * would in theory work on more difficult imputs
 * but takes longer.  Possibly forever.
 *
 *
 */
public class Prob2
{

  public static void main(String args[]) throws Exception
  {
    new Prob2(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  TreeMap<String, TicketRange> rules=new TreeMap<>();
  ArrayList<ArrayList<Integer> > nv = new ArrayList<>();

  public Prob2(Scanner scan)
  {
    // read rules
    while(true)
    {
      String line = scan.nextLine();
      if (line.trim().length()==0) break;
      String label = line.substring(0,line.indexOf(':'));
      String rule = line.substring(line.indexOf(':')+1);

      rules.put(label, new TicketRange(rule));
    }
    System.out.println("Read ticket rules: " + rules.size());

    scan.nextLine();// your ticket
    ArrayList<Integer> your_ticket = readTicket(scan.nextLine());
    cc = your_ticket.size();

    scan.nextLine(); // blank
    scan.nextLine(); //nearby

    ArrayList< ArrayList<Integer> > nearby = new ArrayList<>();


    while(scan.hasNextLine())
    {
      nearby.add( readTicket(scan.nextLine()));
    }
    System.out.println("Nearby: " + nearby.size());


    long invalid_sum = 0;

    nv.add(your_ticket);

    for(int i = 0; i<nearby.size(); i++)
    {
      ArrayList<Integer> lst = nearby.get(i);
      boolean ticket_valid=true;

      
      for(int v : lst)
      {
        boolean valid=false;
        for(TicketRange tr : rules.values())
        {
          if (tr.inRange(v)) valid=true;

        }
        if (!valid)
        {
          invalid_sum+=v;
          ticket_valid=false;
        }
      
      }
      if (ticket_valid)
      {
        nv.add(lst);
      }
    }

    System.out.println("invalid sum: " + invalid_sum);
    System.out.println("valid tickets: " + nv.size());


    // In normal order, I find this never completes.
    // with some ordering, it takes a minute.  
    // so let's get stupid
    for(int i=0; i<cc; i++)
    {
      col_order.add(i);
    }
    Collections.shuffle(col_order);

    rec(0, new TreeMap<String, Integer>());


    System.out.println("Solutions: " + sol_list.size());

    long product = 1;
    for(String s : rules.keySet())
    {
      if (s.startsWith("departure"))
      {
        int c = sol_list.get(0).get(s);

        product = product * your_ticket.get(c);
      }
    }
    System.out.println(product);

  }

  LinkedList<Map<String, Integer> > sol_list=new LinkedList<>();
  ArrayList<Integer> col_order = new ArrayList<>();

  final int cc;

  int max_depth=0;

  /**
   * Finds a label to map to the given column.
   * then go on to the next columns if successful.
   */
  public void rec(int col, TreeMap<String, Integer> labels)
  {
    if (sol_list.size() > 0) return;
    if (col == cc )
    {
      sol_list.add(ImmutableMap.copyOf(labels));
      return;
    }
    if (col > max_depth)
    {
      max_depth = col;
      System.out.println("depth: " + col);
    }
    int actual_col = col_order.get(col);
    // Try all labels
    for(String s : rules.keySet())
    {
      if (!labels.containsKey(s))
      {
        if (tryRule( rules.get(s), actual_col ))
        {
          labels.put(s, actual_col);
          rec(col+1, labels);
          labels.remove(s);
        }
      }
    }
  }

  public boolean tryRule(TicketRange tr, int c)
  {
    for(ArrayList<Integer> lst : nv)
    {
      if (!tr.inRange( lst.get(c)))
      {
        return false;
      }
    }
    return true;
  }

  public ArrayList<Integer> readTicket(String line)
  {
    line = line.replace(","," ");
    Scanner scan = new Scanner(line);
    ArrayList<Integer> lst = new ArrayList<>();
    while(scan.hasNextInt())
    {
      lst.add(scan.nextInt());
    }
    return lst;

  }


  public class TicketRange
  {
    int a,b,c,d;

    public TicketRange(String rule)
    {
      rule = rule.replace("-"," ").replace("o","").replace("r","");
      Scanner scan =new Scanner(rule);
      a=scan.nextInt();
      b=scan.nextInt();
      c=scan.nextInt();
      d=scan.nextInt();
    }
    public boolean inRange(int v)
    {
      if(( a<=v) && (v<=b)) return true;
      if(( c<=v) && (v<=d)) return true;

      return false;
    }

  }

}
