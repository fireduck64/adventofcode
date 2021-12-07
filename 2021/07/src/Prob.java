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

  ArrayList<Integer> crabs=new ArrayList<>();

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();
    line = line.replace(","," ");
    Scanner s2 = new Scanner(line);
    while(s2.hasNextInt())
    {
      crabs.add(s2.nextInt());
    }

    int min = 1000000;
    int max= 0;
    for(int i : crabs)
    {
      min = Math.min(i, min);
      max = Math.max(i, max);
    }

    int min_fuel=1000000000;
    for(int p=min; p<=max; p++)
    {
      int fuel=0;
      for(int i : crabs)
      {
        fuel += getCost(Math.abs(i - p));
      }
      min_fuel = Math.min(min_fuel, fuel);


    }
    System.out.println(min_fuel);

  }

  TreeMap<Integer, Integer> cost_map=new TreeMap<>();
  public int getCost(int delta)
  {
    if (delta == 0) return 0;
    if (delta == 1) return 1;

    if (cost_map.containsKey(delta)) return cost_map.get(delta);

    int cost = delta + getCost(delta-1);

    cost_map.put(delta, cost);
    return cost;
  }

}
