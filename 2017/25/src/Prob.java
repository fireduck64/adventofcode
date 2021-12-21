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
  TreeMap<String, Action> table=new TreeMap<>();

  String start_state=null;
  long diag_step;

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine().trim();
      if (line.startsWith("Begin in state"))
      {
        start_state=Tok.en(line, " ").get(3).replace(".","");
        System.out.println("Start: " + start_state);
      }
      if (line.startsWith("Perform a diagnostic checksum"))
      {
        diag_step = Integer.parseInt(Tok.en(line, " ").get(5));
        System.out.println("Diag step: " + diag_step);
  
      }
      if (line.trim().length() ==0) continue;

      if (line.startsWith("In state"))
      {
        String state = Tok.en(line, " ").get(2).replace(":","");
        System.out.println("Reading state: " + state);
        for(int v=0; v<2; v++)
        {
          String l2 = scan.nextLine().trim();
          if (!l2.startsWith("If the current value is "+v)) E.er();
          Action a = new Action(scan);
          String s = getStr(state, v);
          System.out.println("In " + s + " do " + a);
          table.put(s,a);

        }

      }

    }

    reset();
    for(long i=0; i<diag_step; i++)
    {
      runStep();
    }
    System.out.println("Part 1: " + tape.size());

  }

  int cursor;
  String state;
  public void reset()
  {
    tape.clear();
    cursor=0;
    state=start_state;

  }


  public void runStep()
  {
    int val = readTape(cursor);
    String tl = getStr(state, val);
    //System.out.println(tl);
    Action a = table.get(tl);
    if (a==null) E.er();

    writeTape(cursor, a.write);
    cursor += a.move;
    state = a.next;

  }


  TreeMap<Integer, Integer> tape=new TreeMap<>();

  public int readTape(int loc)
  {
    if (tape.containsKey(loc)) return tape.get(loc);
    return 0;
  }
  public void writeTape(int loc, int val)
  {
    if (val == 0) tape.remove(loc);
    else tape.put(loc, val);
  }

  public String getStr(String state, int val)
  {
    return state + "-" + val;
  }

  public class Action
  {
    int write;
    int move;
    String next;

    public Action(Scanner scan)
    {
      {
        String line = scan.nextLine().trim();
        if (!line.startsWith("- Write the value")) E.er();
        line = line.replace(".","");
        write = Integer.parseInt( Tok.en(line, " ").get(4));
      }
      {
        String line = scan.nextLine().trim();
        if (!line.startsWith("- Move one slot to")) E.er();
        if (line.contains("right")) move=1;
        else if (line.contains("left")) move=-1;
        else E.er();
      }
      {
        String line = scan.nextLine().trim();
        if (!line.startsWith("- Continue with state")) E.er();
        line = line.replace(".","");
        next = Tok.en(line, " ").get(4);
      }
    }
    public String toString()
    {
      return String.format("Action{w:%d m:%d n:%s}", write, move, next);
    }
  }
  

}
