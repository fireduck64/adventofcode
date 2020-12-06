import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    int v =0;
    while(scan.hasNextLine())
    {
      v+=readGroup(scan);


    }
    System.out.println(v);

  }

  public int readGroup(Scanner scan)
  {
    TreeMap<Character,Integer> q = new TreeMap<>();

    for(char a='a';a<='z'; a++)
    {
      q.put(a, 0);
    }

    int people =0;

    while(scan.hasNextLine())
    {
      String line = scan.nextLine().trim();
      if (line.length() ==0) break;

      people++;
      for(int i=0; i<line.length(); i++)
      {
        char z = line.charAt(i);
        q.put(z, q.get(z) + 1);
      }

    }

    int cnt =0;
    for(int z : q.values())
    {
      if (z == people) cnt++;
    }
    return cnt;


  }

}
