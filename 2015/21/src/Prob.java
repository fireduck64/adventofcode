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
  ArrayList<Item> weapons = new ArrayList<>();
  ArrayList<Item> armor = new ArrayList<>();
  ArrayList<Item> rings = new ArrayList<>();

  public Prob(Scanner scan)
    throws Exception
  {
    int b_hp = Tok.ent(scan.nextLine(), " ").get(0);
    int b_dmg = Tok.ent(scan.nextLine(), " ").get(0);
    int b_armor = Tok.ent(scan.nextLine(), " ").get(0);
    loadStore();


    int best_lose = 0;
    int best_price = 1000000;
    for(Item weap : weapons)
    for(Item arm : armor)
    {
      for(int i=0; i<rings.size()-1; i++)
      for(int j=i+1; j<rings.size(); j++)
      {
        Item r1 = rings.get(i);
        Item r2 = rings.get(j);

        int p_dmg = weap.dmg + arm.dmg + r1.dmg + r2.dmg;
        int p_armor = weap.armor + arm.armor + r1.armor + r2.armor;
        int price = weap.price + arm.price + r1.price + r2.price;
        if (fight(100, p_dmg, p_armor, b_hp, b_dmg, b_armor))
        {
          if (price < best_price)
          {
            best_price = price;
          }
        }
        else
        {
          if (price > best_lose) best_lose =price;

        }

      }

    }
    System.out.println("Part 1: " + best_price);
    System.out.println("Part 2: " + best_lose);


  }


  public void loadStore()
    throws Exception
  {
    Scanner scan = new Scanner(new FileInputStream("store"));

    ArrayList<Item> read_list = null;
    for(String line : In.lines(scan))
    {
      if (line.startsWith("Weapons:")) read_list=weapons;
      else if (line.startsWith("Armor:")) read_list=armor;
      else if (line.startsWith("Rings:")) read_list=rings;
      else
      {
        if (line.trim().length() > 0)
        {
          read_list.add(new Item(line));
        }
      }
    }
  }


  /**
   * Return true if the player wins
   */
  boolean fight(int p_hp, int p_dmg, int p_armor, int b_hp, int b_dmg, int b_armor)
  {
    while(true)
    {
      b_hp -= Math.max(1, p_dmg -b_armor);
      if (b_hp <= 0) return true;

      p_hp -= Math.max(1, b_dmg - p_armor);
      if (p_hp <= 0) return false;
    }
  }

  public class Item
  {
    String name;
    int price;
    int dmg;
    int armor;

    public Item(String line)
    {
      name = line.substring(0,11).trim();
      line = line.substring(11);
      List<Integer> lst = Tok.ent(line, " ");
      price = lst.get(0);
      dmg = lst.get(1);
      armor = lst.get(2);

    }
    public String toString()
    {
      return String.format("%s %d %d %d", name, price, dmg, armor);
    }

  }
}
