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

  TreeMap<String, Workflow> workflows=new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(true)
    {
      String line = scan.nextLine();
      if (line.trim().length() == 0) break;

      Workflow wf = new Workflow(line);

      workflows.put(wf.flow_name, wf);

    }

    long p1=0;

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line=line.replace("{"," ");
      line=line.replace("}"," ");
      line=line.replace("="," ");
      line=line.replace(","," ");

      TreeMap<String, Long> part = new TreeMap<>();
      List<Long> num = Tok.enl(line, " ");

      part.put("x", num.get(0));
      part.put("m", num.get(1));
      part.put("a", num.get(2));
      part.put("s", num.get(3));

      Part p = new Part(part);

      String r = runPart(p);
      if (r.equals("A"))
      {
        for(long v : p.vals.values())
        {
          p1 += v;

        }

      }
    }
    System.out.println("PArt 1: " + p1);

    TreeMap<String, TreeSet<Integer> > allows = new TreeMap<>();
    allows.put("x", new TreeSet<>());
    allows.put("m", new TreeSet<>());
    allows.put("a", new TreeSet<>());
    allows.put("s", new TreeSet<>());
    for(int i=1; i<=4000; i++)
    {
      allows.get("x").add(i);
      allows.get("m").add(i);
      allows.get("a").add(i);
      allows.get("s").add(i);
    }

    long p2 = rec(allows, "in", 0);
    System.out.println("Part 2: " + p2);

  }

  public String runPart(Part p)
  {
    String next = "in";

    while(true)
    {
      next = workflows.get(next).apply(p);

      if (next.equals("A")) return next;
      if (next.equals("R")) return next;
    }

  }

  public class Workflow
  {
    String flow_name;
    ArrayList<FlowStep> steps = new ArrayList<>();
    public Workflow(String line)
    {
      flow_name = line.split("\\{")[0];

      String step_list = line.split("\\{")[1].replace("}","");

      for(String ss : Tok.en(step_list, ","))
      {
        steps.add(new FlowStep(ss));
      }
    }

    public String apply(Part p)
    {
      for(FlowStep step : steps)
      {
        String r = step.apply(p);
        if (r != null) return r;
      }

      E.er();
      return null;


    }

  }

  public class Part
  {
    TreeMap<String, Long> vals=new TreeMap<>();

    public Part(Map<String, Long> v)
    {
      this.vals.putAll(v);
    }
  }



  public class FlowStep
  {
    boolean conditional;
    String v;
    String op;
    long value;

    String result;

    public FlowStep(String v, String op, long value)
    {
      conditional=true;
      this.v =v ;
      this.op = op;
      this.value = value;

    }

    public FlowStep(String str)
    {
      if (str.indexOf(':') > 0)
      {
        conditional = true;
        v = "" + str.charAt(0);
        op = "" + str.charAt(1);

        value = Long.parseLong( str.split(":")[0].substring(2) );
        result = str.split(":")[1];

      }
      else
      {
        conditional=false;
        result = str.trim();
      }
    }

    public String apply(Part p)
    {
      if (!conditional) return result;

      long p_val = p.vals.get(v);

      if (op.equals("<"))
      {
        if (p_val < value) return result;
      }

      if (op.equals(">"))
      {
        if (p_val > value) return result;
      }
      return null;
    }

    public void applyLimit(TreeSet<Integer> opt_set)
    {
     
      // m > 10
      if (op.equals(">"))
      {
        for(int i=1; i<=value; i++) opt_set.remove(i);
      }
      // m < 10
      if (op.equals("<"))
      {
        for(int i=(int)value; i<=4000; i++) opt_set.remove(i);
      }
    }
  }


  // The inputs are the set of currently allowed numbers for each value
  // so all 4000 at the start.
  public long rec(TreeMap<String, TreeSet<Integer> > allowed, String next, int step)
  {
    // Reject
    if (next.equals("R")) return 0L;

    // If we get Allow, then product of all the number of allowed
    if (next.equals("A"))
    {
      long opt = 1L;
      for(TreeSet<Integer> opt_set : allowed.values())
      {
        opt = opt * opt_set.size();
      }
      return opt;
    }

    // If any of our sets are zero, we can stop
    for(TreeSet<Integer> opt_set : allowed.values())
    {
      // in testing, we didn't actually need this, but whatevers
      if (opt_set.size() == 0) return 0L;
    }

    
    Workflow fw = workflows.get(next);
    FlowStep fs = fw.steps.get(step);

    // If it isn't conditional, just do it
    if (!fs.conditional)
    {
      return rec(allowed, fs.result, 0);
    }

    // So we have a conditional step
    long opt = 0L;

    { // meet limit
      TreeMap<String, TreeSet<Integer> > a2 = new TreeMap<>();
      a2.putAll(allowed);

      // Apply the limit to the set we pass on
      TreeSet<Integer> opt_set = new TreeSet<>();
      opt_set.addAll(a2.get(fs.v));
      fs.applyLimit(opt_set);
      a2.put(fs.v, opt_set);

      opt+= rec(a2, fs.result, 0);

    }
    
    // smash limit 
    {
      TreeMap<String, TreeSet<Integer> > a2 = new TreeMap<>();
      a2.putAll(allowed);

      // Apply the limit
      TreeSet<Integer> opt_limit = new TreeSet<>();
      opt_limit.addAll(a2.get(fs.v));
      fs.applyLimit(opt_limit);

      // Then take anything that wasn't in that limit as allowed
      TreeSet<Integer> opt_set = new TreeSet<>();
      opt_set.addAll(a2.get(fs.v));
      opt_set.removeAll(opt_limit);

      a2.put(fs.v, opt_set);

      opt+= rec(a2, next, step+1);

    }



    return opt;



  }


}
