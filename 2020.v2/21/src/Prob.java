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
  ArrayList<Food> foods = new ArrayList<>();

  // Alergens to food map
  TreeMap<String, String> a2f_map=new TreeMap<>();
  TreeSet<String> all_ing=new TreeSet<>();
  TreeSet<String> all_aler=new TreeSet<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      Food f = new Food(line);
      foods.add(f);

      all_ing.addAll(f.ing);
      all_aler.addAll(f.aler);
    }
    System.out.println("Foods: " + foods.size());
    System.out.println("Ingredients: " + all_ing.size());
    System.out.println("Alergens: " + all_aler.size());

    findMapping(new TreeMap<String, String>());

    int p1 = 0;
    TreeSet<String> used_ing = new TreeSet<>();
    used_ing.addAll(a2f_map.values());
    for(Food f : foods)
    {
      for(String i : f.ing)
      {
        if (!used_ing.contains(i)) p1++;

      }

    }
    System.out.println("Part 1: " + p1);

    String danger = "";
    for(String ing : a2f_map.values())
    {
      if (danger.length() > 0) danger+=",";
      danger+=ing;

    }
    System.out.println(danger);




  }

  public boolean findMapping(TreeMap<String, String> a2f)
  {
    if (a2f.size() == all_aler.size())
    {
      System.out.println("Solution found: " + a2f);
      a2f_map = a2f;
      return true;
    }
    TreeSet<String> used_ing = new TreeSet<>();
    used_ing.addAll(a2f.values());

    TreeSet<String> used_aler = new TreeSet<>();
    used_aler.addAll(a2f.keySet());

    TreeSet<String> remain_aler = new TreeSet<>();
    remain_aler.addAll(all_aler);
    remain_aler.removeAll(used_aler);

    TreeSet<String> remain_ing = new TreeSet<>();
    remain_ing.addAll(all_ing);
    remain_ing.removeAll(used_ing);

    String alergen = remain_aler.first();

    boolean any_found = false;

    for(String ing : remain_ing)
    {
      if (checkPossible(alergen, ing))
      {

        TreeMap<String, String> a2f_new = new TreeMap<>();
        a2f_new.putAll(a2f);
        a2f_new.put(alergen, ing);
        
        boolean found = findMapping(a2f_new);
        if (found) any_found = true;

      }

    }

    return any_found;

    

  }

  public boolean checkPossible(String alergen, String ing)
  {
    for(Food f : foods)
    {
      if (f.aler.contains(alergen))
      {
        if (!f.ing.contains(ing)) return false;
      }
    }
    return true;

  }

  public class Food
  {
    TreeSet<String> ing=new TreeSet<>();
    TreeSet<String> aler=new TreeSet<>();

    public Food()
    {

    }
    public Food(String line)
    {
      line = line.replace("(","").replace(")","").replace(",", "");
      boolean in_aler = false;
      for(String item : Tok.en(line, " "))
      {
        if (item.equals("contains"))
        {
          in_aler=true;
        }
        else
        {
          if (in_aler) aler.add(item);
          else ing.add(item);
        }

      }

    }

  }

}
