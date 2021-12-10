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
  ArrayList<Group> groups=new ArrayList<>();

  String input_data;

  public Prob(Scanner scan)
  {
    StringBuilder sb = new StringBuilder();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      sb.append(line); sb.append('\n');
    }
    input_data = sb.toString();

    part1();
    part2();
  }

  public void readInput()
  {
    groups.clear();
    String side=null;
    Scanner scan = new Scanner(input_data);
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      if (line.startsWith("Immune")) side="immune";
      else if (line.startsWith("Infection")) side="infection";
      else if (line.trim().length() == 0)
      {
      }
      else
      {
        Group g = (new Group(side, line));

        groups.add(g);
      }
      
    }
  }

  public void part1()
  {

    readInput();
    battle();

    System.out.println("Part 1 - Battle result: " + getCounts());

  }

  public void part2()
  {
    int b = 0;
    while(true)
    {

      readInput();
      applyBoost(b);
      battle();
      if (getCounts().containsKey("immune")) 
      if (getCounts().size() == 1)
        break;

      b++;
    }

    System.out.println("Boost size: " + b);
    System.out.println("Part 2 - Battle result: " + getCounts());

  }
  public void battle()
  {
    while (!isDone())
    {
      resetTargets();
      selectTargets();
      int removed = attack();
      if (removed == 0)
      if (!isDone())
      {
        // draw
        return;

      }
    }

  }

  public void applyBoost(int boost)
  {
    for(Group g : groups)
    {
      if (g.side.equals("immune")) g.attack+=boost; 
    }
  }

  public void selectTargets()
  {

    TreeMap<Double, Group> select_order=new TreeMap<>();
    for(Group g : getLive())
    {
      select_order.put( g.getTargetingPriority(), g);
    }
    while(select_order.size() > 0)
    {
      Group attacker = select_order.pollLastEntry().getValue();

      TreeMap<Double, Group> targets = new TreeMap<>();
      for(Group g : getLive())
      {
        if (!g.side.equals(attacker.side))
        if (g.targeted_by == null)
        {
          double val = g.getDamage( attacker.attack_type, attacker.getEffectivePower());
          if (val > 0)
          {
            val += g.getEffectivePower() / 1e9;
            val += g.init / 1e12;
            targets.put(val, g);
          }
        }
      }
     
      if (targets.size() > 0)
      { 
        Group target = targets.pollLastEntry().getValue();
        attacker.target = target;
        target.targeted_by = attacker;
      }
    }

  }
  public int attack()
  {
    int units_removed=0;
    TreeMap<Integer, Group> attack_order=new TreeMap<>();
    for(Group g : getLive())
    {
      if (g.target != null)
      {
        attack_order.put( g.init, g);
      }
    }
    while(attack_order.size() > 0)
    {
      Group attacker = attack_order.pollLastEntry().getValue();
      if (attacker.isLive())
      {
        long dmg = attacker.target.getDamage( attacker.attack_type, attacker.getEffectivePower() );
        units_removed += attacker.target.takeDamage(dmg);
      }
    }
    return units_removed;

  }

  public void resetTargets()
  {
    for(Group g : groups) g.resetTargets();
  }

  public boolean isDone()
  {
    return (getCounts().size() == 1); 
  }
  public TreeMap<String, Integer> getCounts()
  {
    TreeMap<String, Integer> map = new TreeMap<>();
    for(Group g : getLive())
    {
      if (!map.containsKey(g.side))
      {
        map.put(g.side, 0);
      }
      map.put(g.side, map.get(g.side) + g.units);

    }
    return map;

  }
  public List<Group> getLive()
  {
    LinkedList<Group> lst = new LinkedList<>();
    for(Group g : groups)
    {
      if (g.isLive()) lst.add(g);

    }
    return lst;
  }

}
