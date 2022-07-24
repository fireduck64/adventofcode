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

  String line;
  boolean p2_mode=false;
  volatile boolean fin=false;

  public Prob(Scanner scan)
    throws Exception
  {
    line = scan.nextLine();

    all_cmds=new ArrayList<>();
    all_cmds.addAll(getAllCommands());
    System.out.println("Command size: " + all_cmds.size());
    for(int i=0; i<64; i++)
    {
      new WorkerThread().start();
    }
    /*
    SS p1 = (SS) Search.searchPara(new SS(ImmutableList.of()));
    System.out.println("Part 1: " + p1.score);
    */
    Thread.sleep(5000);

    p2_mode=true;
    fin=false;
    all_cmds=new ArrayList<>();
    all_cmds.addAll(getAllCommands());
    for(int i=0; i<64; i++)
    {
      new WorkerThread().start();
    }
 
    //SS p2 = (SS) Search.searchPara(new SS(ImmutableList.of()));
    //System.out.println("Part 2: " + p2.score);
  }

  ArrayList<String> all_cmds=null;

  public class WorkerThread extends Thread
  {
    Random rnd = new Random(); 

    public void run()
    {
      while(!fin)
      {
        tryRandom();
      }
    }

    public boolean tryRandom()
    {
      int count = rnd.nextInt(8)+5;
      if (!p2_mode)count=4;

      IntComp comp = new IntComp(line);
      for(int i =0; i<count; i++)
      {
        int idx = rnd.nextInt(all_cmds.size());
        comp.writeAscii( all_cmds.get(idx));
      }
      if (p2_mode)
      {
        comp.writeAscii("RUN");
      }
      else
      {
        comp.writeAscii("WALK");
      }
      comp.exec();

      for(long v : comp.output)
      {
        if (v > 256)
        {
          if (p2_mode)
          {
            System.out.println("p2 score: " + v);
          }
          else
          {
            System.out.println("p1 score: " + v);
          }
          fin = true;
          return true;
        }
      }
      return false;




    }

  }

  public Collection<String> getAllCommands()
  {
    TreeSet<String> cmds = new TreeSet<>();
    for(String w : getWords())
    for(String x : getInputs())
    for(String y : getOutputs())
    {
      String cmd = w + " " + x + " " + y;
      cmds.add(cmd);
    }
    return cmds;

  }
  public Collection<String> getWords()
  {
    return ImmutableSet.of("NOT", "OR", "AND");
  }
  public Collection<String> getInputs()
  {
    if (p2_mode)
    {
      return ImmutableSet.of("A","B","C","D","T","J","E","F","G","H","I");
    }
    else
    {
      return ImmutableSet.of("A","B","C","D","T","J");
    }
  }

  public Collection<String> getOutputs()
  {
    return ImmutableSet.of("T","J");
  }

  public class SS extends State
  {
    List<String> inst;
    long score;

    public SS(List<String> inst)
    {
      this.inst = inst;
    }
    public String toString()
    {
      return inst.toString();
    }
    public double getCost(){return inst.size(); }

    public boolean isTerm()
    {
      IntComp comp = new IntComp(line);
      for(String s : inst)
      {
        comp.writeAscii(s);
      }
      if (p2_mode)
      {
        comp.writeAscii("RUN");
      }
      else
      {
        comp.writeAscii("WALK");
      }
      comp.exec();

      for(long v : comp.output)
      {
        if (v > 256)
        {
          score = v;
          System.out.println("score: " + score);
          return true;
        }
      }
      return false;

    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
      if (inst.size()<15)
      {
        for(String s : all_cmds)
        {
          LinkedList<String> l = new LinkedList<>();
          l.addAll(inst);
          l.add(s);
          lst.add(new SS(l));
        }
      }
      return lst;
    }
  }
}
