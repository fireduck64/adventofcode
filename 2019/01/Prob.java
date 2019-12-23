import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  public Prob(Scanner scan)
  {
    long fuel_sum = 0;
    long fuel_with_fuel = 0;

    while(scan.hasNextLong())
    {
      long mass = scan.nextLong();

      long fuel_cost = mass / 3 - 2;
      fuel_sum += fuel_cost;
      fuel_with_fuel += fuel_cost;


      long added_fuel = fuel_cost;
      while(true)
      {
        long cost = added_fuel /3 -2;
        if (cost <=0 ) break;
        added_fuel = cost;
        fuel_with_fuel += cost;
      
      }
      
    }

    System.out.println("Fuel for modules: " + fuel_sum);

   System.out.println("With fuel: " + fuel_with_fuel);

  }

}
