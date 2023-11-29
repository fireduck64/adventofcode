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

  ArrayList<Field> fields = new ArrayList<>();
  ArrayList<Integer> my_ticket;

  ArrayList<ArrayList<Integer> > other_tickets=new ArrayList<>();
  ArrayList<ArrayList<Integer> > valid_tickets = new ArrayList<>();

  public Prob(Scanner scan)
  {
    LinkedList<String> lines = new LinkedList<>();
    lines.addAll(In.lines(scan));

    while(true)
    {
      String line = lines.poll();
      if (line.trim().length()==0) break;

      fields.add(new Field(line));
    }

    {
      String line = lines.poll();
      if (!line.equals("your ticket:")) throw new RuntimeException(line);
    }
    {
      String line = lines.poll();
      my_ticket = new ArrayList<>();
      my_ticket.addAll( Tok.ent(line, ","));
      lines.poll();
    }

    {
      String line = lines.poll();
      if (!line.equals("nearby tickets:")) throw new RuntimeException(line);
    }
    while(lines.size()>0)
    {
      String line = lines.poll();
      if (line.trim().length()==0) break;

      ArrayList<Integer> ticket = new ArrayList<>();
      ticket.addAll( Tok.ent(line, ","));

      other_tickets.add(ticket);
    }

    int p1=0;
    
    for(ArrayList<Integer> ticket : other_tickets)
    {
      boolean all_valid=true;
      for(Integer v : ticket)
      {
        int valid=0;

        for(Field f : fields)
        {
          if (f.check(v)) valid++;
        }
        
        if (valid==0) p1+=v;
        if (valid==0) all_valid=false;
      }
      if (all_valid) valid_tickets.add(ticket);

    }
    System.out.println("Part 1: " + p1);
    valid_tickets.add(my_ticket);

    System.out.println("Valid tickets: " + valid_tickets.size());

    TreeSet<Integer> mapped_fields=new TreeSet<>();
    //TreeSet<Integer> mapped_cols = new TreeSet<>();
    TreeMap<Integer, Integer> f_to_c=new TreeMap<>();

    TreeMap<Integer, TreeSet<Integer> > p_f_to_c=new TreeMap<>();
    //TreeMap<Integer, TreeSet<Integer> > p_c_to_f=new TreeMap<>();

    for(int f = 0; f<fields.size(); f++) p_f_to_c.put(f, new TreeSet<Integer>());
    //for(int c = 0; c<my_ticket.size(); c++) p_c_to_f.put(c, new TreeSet<Integer>());

    for(int f = 0; f<fields.size(); f++)
    for(int c = 0; c<my_ticket.size(); c++)
    {
      if (checkMap(f,c))
      {
        p_f_to_c.get(f).add(c);
        //p_c_to_f.get(c).add(f);
      }

    }

    while(true)
    {
      int added=0;
      for(int f = 0; f<fields.size(); f++)
      {
        if (!mapped_fields.contains(f))
        {
          if (p_f_to_c.get(f).size()==1)
          {
            int c = p_f_to_c.get(f).first();

            mapped_fields.add(f);
            f_to_c.put(f,c);
            //for(int cc = 0; cc<my_ticket.size(); cc++) p_c_to_f.get(cc).remove(f);
            for(int ff = 0; ff<fields.size(); ff++) p_f_to_c.get(ff).remove(c);
            added++;
          }
        }
      }

      if (added==0) break;
    }

    System.out.println("Mapped: " + f_to_c.size());
    long p2=1;
    for(int f =0; f<fields.size(); f++)
    {
      Field ff = fields.get(f);
      if (ff.label.startsWith("departure"))
      {
        int c = f_to_c.get(f);
        long v = my_ticket.get(c);
        p2 = p2 * v;


      }
    }
    System.out.println("Part 2: " + p2);



  }


  public class Field
  {
    String label;
    ArrayList<Integer> vals=new ArrayList<>();
    public Field(String line)
    {
      label = Tok.en(line, ":").get(0);

      vals.addAll(Tok.ent(line.replace("-", " "), " "));
    }

    public boolean check(int v)
    {
      if ((vals.get(0) <= v) && (v <= vals.get(1))) return true;
      if ((vals.get(2) <= v) && (v <= vals.get(3))) return true;

      return false;
    }
  }


  public boolean checkMap(int field, int col)
  {
    Field f = fields.get(field);
    for(ArrayList<Integer> ticket : valid_tickets)
    {
      int v = ticket.get(col);
      if (!f.check(v)) return false;
    }
    return true;
  }

}
