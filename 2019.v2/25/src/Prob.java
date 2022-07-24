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
  String line;

  // lol - bad
  Set<String> no_take = ImmutableSet.of("infinite loop", "giant electromagnet");

  public Prob(Scanner scan)
  {
    line = scan.nextLine();
    //interactive();
    Search.search(new SS(new IntComp(line), 0, new TreeSet<String>(), new Point(0,0), null));
  }
  public void interactive()
  {

    IntComp comp = new IntComp(line);

    Scanner keyboard = new Scanner(System.in);
    while(!comp.term)
    {
      comp.exec();
      while (comp.output.size() > 0) System.out.println(comp.readNextLine());

      String c = keyboard.nextLine();
      comp.writeAscii(c);
    }
  }

  public class RoomInfo
  {
    String name;
    String desc;
    TreeSet<String> doors=new TreeSet<>();
    TreeSet<String> items= new TreeSet<>();

    public String toString()
    {
      return name + "/" + desc + "/" + doors;

    }

  }

  public class SS extends State
  {
    IntComp comp;
    int steps;
    TreeSet<String> inv;
    Point loc;

    boolean fin=false;

    RoomInfo room;

    public SS(IntComp comp, int steps, TreeSet<String> inv, Point loc, RoomInfo room)
    {
      this.comp = comp.copy();
      this.steps = steps;
      this.inv = new TreeSet<>(); this.inv.addAll(inv);
      this.loc = loc;
      this.room = room;
    }
    public String toString()
    {
      return "" + loc + "/" + room + "/" + inv;

    }
    public double getCost(){return steps; }

    public boolean isTerm()
    {
      return fin;
    }
    public List<State> next()
    {
      process();
      if (room!=null)
      if (room.name.equals("Pressure-Sensitive Floor"))
      {
        System.out.println("whoop " + inv);
        //System.exit(0);
      }

      LinkedList<State> lst = new LinkedList<>();
      if (comp.term) return lst;
      
      for(String s : inv)
      {
        
        IntComp c2 = comp.copy();
        c2.writeAscii("drop " + s);
        TreeSet<String> inv2 = new TreeSet<>();
        inv2.addAll(inv);
        inv2.remove(s);
        lst.add(new SS(c2, steps+1, inv2, loc, room));

      }
      if (room != null)
      for(String s : room.doors)
      {
        Point ndiff = null;
        if (s.equals("north")) ndiff=new Point(0,-1);
        if (s.equals("south")) ndiff=new Point(0,1);
        if (s.equals("east")) ndiff=new Point(1,0);
        if (s.equals("west")) ndiff=new Point(-1,0);
        if (ndiff==null)
        {
          System.out.println("Unknown direction: " + ndiff);
        }
        IntComp c2 = comp.copy();
        c2.writeAscii(s);
        lst.add(new SS(c2, steps+1, inv, loc.add(ndiff), room));
      }
      if (room != null)
      for(String s : room.items)
      {
        if (!no_take.contains(s))
        {
          IntComp c2 = comp.copy();
          c2.writeAscii("take " + s);
          TreeSet<String> inv2 = new TreeSet<>();
          inv2.addAll(inv);
          inv2.add(s);
          lst.add(new SS(c2, steps+1, inv2, loc, room));
        }
      }

      for(State s : lst)
      {
        SS ss = (SS) s;
        ss.process();
      }

      return lst;
    }
    private void process()
    {
      //System.out.println("----------------------------");
      comp.exec();
      boolean in_doors=false;
      boolean in_items=false;
      boolean in_desc=false;
      
      while(comp.output.size() > 0)
      {
        String o = comp.readNextLine().trim();
        //System.out.println(o);
        if (o.length() == 0)
        {
          in_doors=false;
          in_items=false;
          in_desc=false;
        }
        else if (o.equals("Command?"))
        {

        }
        else if (o.startsWith("=="))
        {
          room = new RoomInfo();
          room.name = o.replace("==","").trim();
          room.desc = "";
          in_desc=true;
        }
        else if (o.equals("Doors here lead:"))
        {
          in_doors=true;
          in_items=false;
        }
        else if (o.equals("Items here:"))
        {
          in_items=true;
          in_doors=false;
        }
        else if (o.startsWith("- "))
        {
          String x = o.replace("- ","").trim();
          if (in_doors) room.doors.add(x);
          else if (in_items) room.items.add(x);
          else 
          {
            System.out.println("Unexpected item: " + o);

          }
        }
        else if (in_desc)
        {
          room.desc += o +"\n";
        }
        else if (o.startsWith("You drop the"))
        {

        }
        else if (o.startsWith("You take the"))
        {

        }
        else if (o.contains("you are ejected back to the checkpoint"))
        {
          comp.term=true;
        }
        else if (o.startsWith("You don't see that item here."))
        {
          comp.term=true;
        }
        else if (o.contains("You should be able to get in by typing"))
        {
          System.out.println(o);
          fin=true;
        }
        else
        { 
          if (!comp.term)
          System.out.println("Unexpected: " + o);

        }

      }

    }




  }

}
