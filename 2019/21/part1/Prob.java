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
  // G - landing two

  // T - temp
  // J - jump

  
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  IntComp base_comp;

  public Prob(Scanner scan)
  {
    base_comp = new IntComp(scan.nextLine());

    
    base_comp.addInputAsciiMode("NOT C T");
    base_comp.addInputAsciiMode("AND D T"); // Set T if cD
    base_comp.addInputAsciiMode("OR T J"); //accum jump


    base_comp.addInputAsciiMode("NOT A T"); // Set T if
    base_comp.addInputAsciiMode("OR T J"); //accum jump

    base_comp.addInputAsciiMode("RUN");
    base_comp.execute();

    base_comp.printOutputAscii();



  }

}
