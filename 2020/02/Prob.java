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
    int valid = 0;

    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line=line.replace("-"," ");
      Scanner s2 = new Scanner(line);
      int min = s2.nextInt();
      int max = s2.nextInt();
      String ch = s2.next();
      String pass = s2.next();
      
      char cz = ch.charAt(0);

      System.out.println(" " + min + " " + max + " " + cz + " " + pass);
      if (isValid(min, max, cz, pass))
      {
        valid++;
      }
    }

    System.out.println(valid);

    

  }
 
  public boolean isValid(int min, int max, char cz, String pass)
  {
  
    int c = count(min-1, max-1, cz, pass);
    /*if (c >= min)
    if (c <= max)
      return true;

    return false;*/

    return (c==1);


  }

  public int count(int min, int max, char cz, String pass)
  {
    int c =0;
    for(int i=0; i<pass.length(); i++)
    {
      if ((i==min) || (i==max))
      if (pass.charAt(i) == cz) c++;
    }

    return c;
  }

}
