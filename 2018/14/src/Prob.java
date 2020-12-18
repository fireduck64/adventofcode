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

  int elf_a = 0;
  int elf_b = 1;

  ArrayList<Integer> recipes = new ArrayList<>();


  public Prob(Scanner scan)
  {

    String target_str = scan.next();
    int cycles = Integer.parseInt(target_str);


    // Part 1
    recipes.clear();
    recipes.add(3);
    recipes.add(7);
    elf_a = 0;
    elf_b = 1;

    for(int i=0; i<cycles+20; i++)
    {
      createRecipies();
    }

    System.out.println();
    for(int i=0; i<10; i++)
    {
      System.out.print(recipes.get(i + cycles));
    }
    System.out.println();

    // Part 2

    recipes.clear();
    recipes.add(3);
    recipes.add(7);
    elf_a = 0;
    elf_b = 1;

    int cnt =0;

    ArrayList<Integer> target = new ArrayList<>();
    for(int i=0; i<target_str.length(); i++)
    {
      target.add( Integer.parseInt("" + target_str.charAt(i) ));
    }
    System.out.println("Target: " + target);

    while(true)
    {
      createRecipies();
      cnt++;

      if (recipes.size() == 20229831)
      {
        printTail();
      }

      if (testTarget( recipes.size() - target.size(), target)) break;
      if (testTarget( recipes.size() - target.size() -1, target)) break;
      
    }
    System.out.println("Count : " + cnt);

    //System.out.println(recipes);


  }
  public boolean testTarget(int idx, List<Integer> target)
  {
    if (idx < 0) return false;
    if (recipes.size() < target.size()) return false;
    for(int i=0; i<target.size(); i++)
    {
      if (recipes.get(idx +i) != target.get(i)) return false;

    }
    System.out.println("Target at index: " + idx);
    return true;
  }

  public void printTail()
  {
    for(int i=0; i<40; i++)
    {
      System.out.print(recipes.get( recipes.size()-40+i));

    }
    System.out.println();
  }

  public void createRecipies()
  {
    int digit_sum =  recipes.get(elf_a) + recipes.get(elf_b);

    String s = "" + digit_sum;

    for(int i=0; i<s.length(); i++)
    {
      int r = Integer.parseInt("" + s.charAt(i));
      recipes.add(r);
      //System.out.print(r);
    }

    elf_a = (elf_a + recipes.get(elf_a) + 1) % recipes.size();
    elf_b = (elf_b + recipes.get(elf_b) + 1) % recipes.size();
    
  }

}
