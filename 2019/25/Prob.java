import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  HashSet<String> danger_list=new HashSet<>();


  public Prob(Scanner scan)
  {
    danger_list.add("giant electromagnet");
    danger_list.add("infinite loop");

    IntComp comp = new IntComp(scan.nextLine());

    PS s = (PS) Search.search(new PS(comp));

    System.out.println(s);
    System.out.println(s.fin_line);


    //comp.goInteractive();

  }

  public class PS extends State
  {
    TreeSet<String> inv;
    Point p;
    String location;
    int steps;
    IntComp comp;
    boolean done=false;
    String fin_line=null;

    TreeSet<String> dirs = new TreeSet<>();
    TreeSet<String> floor = new TreeSet<>();

    public PS(IntComp comp)
    {
      p = new Point(0,0);
      this.comp = comp;
      inv = new TreeSet<>();
      comp.execute();
      processOutput();
    }

    public PS(PS src, String command)
    {
      inv =new TreeSet<>();
      inv.addAll(src.inv);
      comp = new IntComp(src.comp);
      location = src.location;
      steps = src.steps;
      p = src.p;

      dirs.addAll(src.dirs);
      floor.addAll(src.floor);

      steps++;

      System.out.println("Command: " + command);
      comp.addInputAsciiMode(command);
      if (command.equals("west")) p = new Point(p.x-1, p.y);
      if (command.equals("east")) p = new Point(p.x+1, p.y);
      if (command.equals("north")) p = new Point(p.x, p.y-1);
      if (command.equals("south")) p = new Point(p.x, p.y+1);
      comp.execute();
      processOutput();
    }

    private void processOutput()
    {

      int mode=0;

      while(true)
      {
        String line = comp.getOutputLine();
        if (line == null) break;
        if (done)
        {
          fin_line += line;
        }
        System.out.println(line);

        if (line.startsWith("=="))
        {
          location = line.replace('=',' ').trim();
          if (location.equals("Security Checkpoint")) p = new Point(0, -5, 0);

        }
        if (line.startsWith("Doors here lead"))
        {
          dirs.clear();
          mode=1;
        }
        if (line.startsWith("Items here"))
        {
          floor.clear();
          mode=2;
        }

        if (line.startsWith("- "))
        {
          String str = line.substring(2).trim();
          if (mode==1)
          {
            dirs.add(str);
          }
          if (mode==2)
          {
            floor.add(str);
          }
          
        }
        if (line.startsWith("You take the "))
        {
          String str = line.substring( "You take the ".length()).replace('.',' ').trim();
          inv.add(str);
          System.out.println("Got: " + str);
        }
        if (line.startsWith("You drop the "))
        {
          String str = line.substring( "You drop the ".length()).replace('.',' ').trim();
          inv.remove(str);
          System.out.println("Dropped: " + str);
        }
        if (line.startsWith("Santa notices your small droid"))
        {
          done=true;
        }

 
      }

    }


    public boolean isTerm(){return done;}
    public List<State> next()
    {
      List<State> lst = new LinkedList<>();
      if (comp.term)
      {
        return lst;
      }

      for(String d : dirs)
      {
        lst.add(new PS(this, d));
      }
      for(String item : floor)
      {
        if (!danger_list.contains(item))
        {
          lst.add(new PS(this, "take " + item));
        }
      }
      for(String item : inv)
      {
        lst.add(new PS(this, "drop " + item));
      }
      return lst;
    
    }
    public double getCost(){return steps; }
    public String toString(){return "" + p + " - " + location + " - " + inv;}
    public String getHash(){return "" + p + " - " + inv;}

  }

}
