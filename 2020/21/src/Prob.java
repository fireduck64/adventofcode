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


  // Maps algen to part
  TreeMap<String, String> al_p = new TreeMap<>();

  // Maps part to alergen
  TreeMap<String, String> p_al = new TreeMap<>();

  TreeSet<String> clear_lst = new TreeSet<>();


  TreeSet<String> all_alergen = new TreeSet<>();
  TreeSet<String> all_parts = new TreeSet<>();


  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      foods.add(
        new Food(scan.nextLine())
      );

    }
    while(progress()) {}

    //Count clear

    int clear_count = 0;
    for(Food f : foods)
    {
      for(String p : f.parts)
      {
        if (clear_lst.contains(p)) clear_count++;

      }

    }
    System.out.println("Clear: " + clear_count);
    
    String line = al_p.values().toString().replace(" ","");
    System.out.println(line);

  }



  public boolean progress()
  {
    // Try to map things to things
    for(String alergen : all_alergen)
    {
      if (!al_p.containsKey(alergen))
      {
        TreeSet<String> common=new TreeSet<>();
        common.addAll(all_parts);
        common.removeAll(p_al.keySet());
        common.removeAll(clear_lst);

        ArrayList<Food> flst = new ArrayList<Food>();
        for(Food f : foods)
        {
          if (f.alergen.contains(alergen))
          {
            flst.add(f);
            TreeSet<String> rmlst = new TreeSet<>();
            for(String p : common)
            {
              if (!f.parts.contains(p)) rmlst.add(p);
            }
            common.removeAll(rmlst);
          }
        }

        if (common.size() == 1)
        {
          saveMap(common.first(), alergen);
          return true;

        }

      }
    }
    for(Food f : foods)
    {
      TreeSet<String> wild = new TreeSet<>();
      TreeSet<String> parts = new TreeSet<>();
      parts.addAll(f.parts);
      parts.removeAll(clear_lst);
      parts.removeAll(p_al.keySet());

      for(String a : f.alergen)
      {
        if (al_p.containsKey(a))
        {
          parts.remove(al_p.get(a));
        }
        else
        {
          wild.add(a);
        }

      }
      if ((wild.size() ==0) && (parts.size() > 0))
      {
        System.out.println("Clear: " + parts);
        clear_lst.addAll(parts);
        return true;
      }


    }

    return false;
  }

  public void saveMap(String part, String alergen)
  {
    System.out.println("Part: " + part + " is " + alergen);
    al_p.put(alergen, part);
    p_al.put(part, alergen);
  }


  public class Food
  {
    TreeSet<String> parts=new TreeSet<>();
    TreeSet<String> alergen=new TreeSet<>();

    public Food(String line)
    {
      line = line.replace(",", "").replace("(","").replace(")","");

      boolean in_a=false;
      for(String tok : Tok.en(line," "))
      {
        if (tok.equals("contains")) in_a=true;
        else
        {
          if (in_a)
          {
            alergen.add(tok);
          }
          else
          {
            if (parts.contains(tok)) System.out.println("dup");
            parts.add(tok);
          }

        }
      }

      all_parts.addAll(parts);
      all_alergen.addAll(alergen);
    }
  }

}
