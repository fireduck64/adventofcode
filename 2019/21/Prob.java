import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  // A - one tile away
  // B
  // C
  // D - 4 tiles away
  // E
  // F 
  // G 
  // H - landing two
  // I

  // T - temp
  // J - jump

  
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  IntComp base_comp;
  ArrayList<String> write;
  ArrayList<String> cmd;
  ArrayList<String> read;

  public Prob(Scanner scan)
  {
    base_comp = new IntComp(scan.nextLine());
    
    // Do any basic stuff before input
    base_comp.execute();

    write = new ArrayList<>();
    write.add("T");
    write.add("J");

    cmd = new ArrayList<>();
    cmd.add("OR");
    cmd.add("AND");
    cmd.add("NOT");

    read = new ArrayList<>();
    for(char z='A'; z<='I'; z++)
    {
      read.add("" + z);
    }
    read.addAll(write);


    
    /*base_comp.addInputAsciiMode("NOT C T");
    base_comp.addInputAsciiMode("AND D T"); // Set T if cD
    base_comp.addInputAsciiMode("AND G T"); // Set T if cD
    base_comp.addInputAsciiMode("OR T J"); //accum jump*/

    //base_comp.addInputAsciiMode("NOT F T");

    // if double jump is safe with a walkway, do it
    /*base_comp.addInputAsciiMode("OR D T"); 
    base_comp.addInputAsciiMode("AND H T");
    base_comp.addInputAsciiMode("AND I T");
    base_comp.addInputAsciiMode("OR T J"); //accum jump
    base_comp.addInputAsciiMode("AND J T"); //if we are not jumping yet, clear T

    // If there is walkway after jump, do it
    base_comp.addInputAsciiMode("OR D T"); 
    base_comp.addInputAsciiMode("AND E T"); 
    base_comp.addInputAsciiMode("OR T J"); //accum jump
    base_comp.addInputAsciiMode("AND J T"); //if we are not jumping yet, clear T

    //base_comp.addInputAsciiMode("NOT A T"); // Set T if
    //base_comp.addInputAsciiMode("OR T J"); //accum jump

    base_comp.addInputAsciiMode("RUN");
    base_comp.execute();

    base_comp.printOutputAscii();*/

    for(int i=0; i<16; i++)
    {
      new RunThread().start();
    }

  }

  volatile boolean global_done = false;

  public class RunThread extends Thread
  {
    public void run()
    {
      Random rnd = new Random();
      long att = 0;
      while(!global_done)
      {
        if (tryRandom(rnd))
        {
          global_done=true;
        }
        att++;
        if (att % 10000 == 0 ) System.out.println(att);
      }
    }

  }




  public boolean tryRandom(Random rnd)
  {
    IntComp comp = new IntComp(base_comp);

    ArrayList<String> cmds = new ArrayList<>();
    int count = rnd.nextInt(10)+6;

    for(int i=0; i<count; i++)
    {
      String c = cmd.get(rnd.nextInt(cmd.size()));
      String a = read.get(rnd.nextInt(read.size()));
      String b = write.get(rnd.nextInt(write.size()));

      String c2 = String.format("%s %s %s", c, a, b);

      comp.addInputAsciiMode(c2);

      cmds.add(c2);
    }
    comp.addInputAsciiMode("RUN");
    comp.execute();

    ArrayList<String> output = new ArrayList<String>();
    boolean failed=false;
    while(comp.output_queue.size() > 0)
    {
      String line = comp.getOutputLine();
      if (line.equals("Didn't make it across:"))
      {
        failed=true;
        return false;
      }
      output.add(line);
    }


    System.out.println(cmds);
    System.out.println(output);
    
    return true;


  }

}
