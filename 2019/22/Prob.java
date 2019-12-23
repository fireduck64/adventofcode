import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public static final int CARDS=10007;
  //public static final int CARDS=10;


  public Prob(Scanner scan)
  {
    ArrayList<Integer> start = new ArrayList<>();
    for(int i=0; i<CARDS; i++)
    {
      start.add(i);
    }

    ArrayList<Integer> deck = start;

    while(scan.hasNextLine())
    {
      deck = doCommand(scan.nextLine(), deck);
    }

    System.out.println(deck);

    for(int i=0; i<deck.size(); i++)
    {
      if (deck.get(i) == 2019)
      {
        System.out.println(i);
      }
    }


  }

  ArrayList<Integer> doCommand(String line, ArrayList<Integer> input)
  {
    ArrayList<Integer> output = new ArrayList<>();
    
    LinkedList<Integer> input_ll = new LinkedList<>();
    input_ll.addAll(input);

    String[] split=line.split(" ");

    if (line.equals("deal into new stack"))
    {
      while(input_ll.size() >0)
      {
        output.add(input_ll.pollLast());
      }
    }
    else if (line.startsWith("cut"))
    {
      int cut_number = Integer.parseInt(split[1]);
      if (cut_number < 0)
      {
        cut_number = input.size() + cut_number;
      }
      output.addAll( input.subList(cut_number, input.size()));
      output.addAll(input.subList(0, cut_number));

    }
    else if (line.startsWith("deal with increment"))
    {
      int inc = Integer.parseInt(split[3]);
      for(int i=0; i<input.size(); i++)
      {
        output.add(-1);
      }

      int idx=0;
      for(int i=0; i<input.size(); i++)
      {
        int old = output.set(idx, input.get(i));
        if (old != -1)
        {
          System.out.println("Inc failed");
        }
        idx = (idx + inc) % input.size();
      }

    }
    else
    {
      System.out.println("Unk: " + line);
      throw new RuntimeException("Unk"); 
    }



    return output;

  }



}
