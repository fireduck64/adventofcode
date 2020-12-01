import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();


  ArrayList<Integer> lst = new ArrayList<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextInt())
    {
      lst.add(scan.nextInt());
    }
    System.out.println(lst.size());

    for(int i=0; i<lst.size()-2; i++)
    for(int j=i+1; j<lst.size()-1; j++)
    for(int k=j+1; k<lst.size(); k++)
    {
      if (lst.get(i) + lst.get(j) + lst.get(k) == 2020)
      {
        long v = lst.get(i) * lst.get(j) * lst.get(k);
        System.out.println(v);
      }

    }
    

  }

}
