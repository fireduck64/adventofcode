import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])), new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  Map2D<Integer> map = new Map2D<>(0);

  public Prob(Scanner scan, Scanner scan_again)
  {
    int dup =0;
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line = line.replace("#","");
      line = line.replace("@","");
      line = line.replace(","," ");
      line = line.replace(":","");
      line = line.replace("x"," ");

      Scanner s2 = new Scanner(line);
      int id = s2.nextInt();
      int x_off = s2.nextInt();
      int y_off = s2.nextInt();
      int x_sz = s2.nextInt();
      int y_sz = s2.nextInt();

      int clear = 1;

      for(int x=0; x<x_sz; x++)
      for(int y=0; y<y_sz; y++)
      {
        int xx = x + x_off;
        int yy = y + y_off;

        if (map.get(xx,yy) == 1) dup++;
        map.set(xx,yy, map.get(xx,yy) + 1);

        if (map.get(xx,yy) != 1) clear=0;
      }

    }
    while(scan_again.hasNextLine())
    {
      String line = scan_again.nextLine();
      line = line.replace("#","");
      line = line.replace("@","");
      line = line.replace(","," ");
      line = line.replace(":","");
      line = line.replace("x"," ");

      Scanner s2 = new Scanner(line);
      int id = s2.nextInt();
      int x_off = s2.nextInt();
      int y_off = s2.nextInt();
      int x_sz = s2.nextInt();
      int y_sz = s2.nextInt();

      int clear = 1;

      for(int x=0; x<x_sz; x++)
      for(int y=0; y<y_sz; y++)
      {
        int xx = x + x_off;
        int yy = y + y_off;

        if (map.get(xx,yy) != 1) clear=0;
      }
      if (clear==1)
      {
        System.out.println("Clear: " + id);
      }

    }
 
    System.out.println("Dup: " + dup);
    

  }

}
