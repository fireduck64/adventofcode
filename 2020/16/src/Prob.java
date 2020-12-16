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

  TreeMap<String, TicketRange> rules=new TreeMap<>();
  ArrayList<ArrayList<Integer> > nv = new ArrayList<>();

  public Prob(Scanner scan)
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

    while(solve())
    {
    }

    long product = 1;
    for(String s : rules.keySet())
    {
      if (s.startsWith("departure"))
      {
        int c = found_label.get(s);

        product = product * your_ticket.get(c);


      }
    }
    System.out.println(product);


  }

  TreeMap<String, Integer> found_label = new TreeMap<>();
  TreeSet<Integer> used_col = new TreeSet<>();
  int cc;

  public boolean solve()
  {
    for(String label : rules.keySet())
    {
      if (!found_label.containsKey(label))
      { 
        int c = tryRule(rules.get(label));
        if (c >= 0)
        {
          System.out.println("Found: " + label + " " + c);
          found_label.put(label, c);
          used_col.add(c);
          return true;
        }
      }

    }
    return false; 

  }
  public int tryRule(TicketRange tr)
  {
    TreeSet<Integer> possible_cols = new TreeSet<>();
    for(int c =0; c<cc; c++)
    {
      if (!used_col.contains(c)) possible_cols.add(c);
    }

    for(ArrayList<Integer> lst : nv)
    {
      TreeSet<Integer> rm = new TreeSet<>();
      for(int c : possible_cols)
      {
        if (!tr.inRange( lst.get(c)))
        {
          rm.add(c);
        }
      }
      possible_cols.removeAll(rm);

    }
    if (possible_cols.size() == 1)
    return possible_cols.first();

    return -1;
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
