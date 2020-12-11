import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  Scanner scan;

  public Prob(Scanner scan)
  {
    this.scan = scan;

    //System.out.println("Part 1: " + read());
    System.out.println("Part 2: " + read2());

  }

  public int read()
  {
    int children = scan.nextInt();
    int meta = scan.nextInt();
    int sum = 0;
    for(int c=0; c<children; c++)
    {
      sum += read();
    }
    for(int m=0; m<meta; m++)
    {
      sum+= scan.nextInt();
    }
    return sum;
  }
  
  public int read2()
  {
    int children = scan.nextInt();
    int meta = scan.nextInt();

    ArrayList<Integer> child_val = new ArrayList<>();
    for(int c=0; c<children; c++)
    {
      child_val.add( read2() );
    }

    int sum =0;

    for(int m=0; m<meta; m++)
    {
      int z = scan.nextInt();
      if (children == 0) sum+=z;
      else
      {
        z--;
        if ((z >=0) && (z < children))
        {
          sum+=child_val.get(z);
        }

      }

    }

    return sum;


  }

}
