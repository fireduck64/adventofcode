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

  HashMap<String, Module> modules=new HashMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      Module m = new Module(line);

      modules.put(m.name, m);

    }
    System.out.println(modules.keySet());

    p1_simulate(1000);
    p2_simulate();


    
    // 118346392018 too low
    // 458828961853786 to high
  }

  TreeMap<String, Long> ns=new TreeMap<>();

  public void resetAll()
  {
    count_low=0;
    count_high=0;
    count_press=0;
    for(Module m : modules.values())
    {
      m.reset();
    }

  }

  public void p2_simulate()
  {
    resetAll();

    LinkedList<Pulse> queue = new LinkedList<>();

    long cnt = 0;
    while(true)
    {

      queue.add(new Pulse("button","broadcaster", false));
      cnt++;
      count_press++;
      if (cnt % 100000 == 0)System.out.println("Count: " + cnt);

      while(queue.size() > 0)
      {
        Pulse p = queue.pollFirst();

        Module m = modules.get(p.dest);
        if (m == null)
        {
          if (!p.signal)
          {
            System.out.println("Part 2: " + cnt);
            return;
          }
        }
        else
        {
          m.getPulse(p, queue);
        }
      }
    }

  }

  public void p1_simulate(int presses)
  {
    resetAll();

    LinkedList<Pulse> queue = new LinkedList<>();

    for(int cnt=0; cnt<presses; cnt++)
    {

      queue.add(new Pulse("button","broadcaster", false));
      while(queue.size() > 0)
      {
        Pulse p = queue.pollFirst();

        Module m = modules.get(p.dest);
        if (m == null)
        {
          //System.out.println("No module: " + p.dest);

        }
        else
        {
          m.getPulse(p, queue);
        }
      }
    }
    System.out.println("" + count_low + " " + count_high);
    long p1 = count_low * count_high;
    System.out.println("Part 1: " + p1);

  }

  long count_press;
  long count_low;
  long count_high;
  public class Pulse
  {
    final String from;
    final String dest;
    final boolean signal;

    public Pulse(String from, String dest, boolean signal)
    {
      this.from = from;
      this.dest = dest;
      this.signal = signal;

      if (signal) count_high++;
      else count_low++;

      //System.out.println(toString());

    }
    public String toString()
    {
      return "Pulse from " + from + " to " + dest + " " + signal;

    }

  }

  public class Module
  {
    String type;
    String name;
    ArrayList<String> dest=new ArrayList<>();
    TreeMap<String, Boolean> conj_inputs;

    boolean flop_state = false;

    public Module(String line)
    {

      line = line.replace(",","");
      List<String> lst = Tok.en(line, " ");
      name = lst.get(0);
      if (name.equals("broadcaster"))
      {
        type="broad";
      }
      if (name.startsWith("%"))
      {
        type="flop";
        name = name.replace("%","");
      }
      if (name.startsWith("&"))
      {
        type="conj";
        name = name.replace("&","");
      }
      for(int i=2; i<lst.size(); i++)
      {
        dest.add( lst.get(i));
      }

    }

    public void reset()
    {
      flop_state = false;
      if (type.equals("conj"))
      {
        conj_inputs = new TreeMap<>();
        for(Module m : modules.values())
        {
          for(String d : m.dest)
          {
            if (d.equals(name))
            {
              conj_inputs.put(m.name, false);
            }
          }

        }

      }
    }

    public void getPulse(Pulse p, LinkedList<Pulse> queue)
    {
      if (type.equals("broad"))
      {
        for(String d : dest)
        {
          queue.add(new Pulse(name, d, p.signal));
        }

      }
      if (type.equals("flop"))
      {
        if (!p.signal)
        {
          if (flop_state) flop_state=false;
          else flop_state=true;

          for(String d : dest)
          {
            queue.add(new Pulse(name, d, flop_state));
          }

        }

      }
      if (type.equals("conj"))
      {

        // From manual inspection of the input
        // all these inputs to NS need to be true
        // wild ass assumption that they are cyclical from zero
        // so do LCM to get the cycle length
        if (p.signal)
        if (name.equals("ns"))
        {
          if (!ns.containsKey(p.from))
          {
            ns.put(p.from, count_press);
            System.out.println(ns);
            if (ns.size() == conj_inputs.size())
            {
              System.out.println("p2: " + ClownMath.lcm_l(ns.values()));
              System.exit(0);


            }

          }
          

        }
        conj_inputs.put(p.from, p.signal);
        boolean all_high=true;
        for(boolean b : conj_inputs.values())
        {
          if (!b) all_high=false;
        }
        //System.out.println(conj_inputs);

        for(String d : dest)
        {
          queue.add(new Pulse(name, d, !all_high));
        }
        

      }

    }

  }

}
