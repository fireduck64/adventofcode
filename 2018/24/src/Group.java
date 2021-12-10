
import java.util.*;

public class Group
{
  String side; //immune or infection
  int units;
  int hp; // per unit
  int attack;
  String attack_type;
  int init;

  TreeSet<String> weak = new TreeSet<>();
  TreeSet<String> immune = new TreeSet<>();

  Group target;
  Group targeted_by;

  public long getEffectivePower()
  {
    return units * attack; 
  }

  // Higher targets first
  public double getTargetingPriority()
  {
    double tp = getEffectivePower();
    tp += init / 1e6;
    return tp;
  }

  // Damage this group would take by attack
  public long getDamage(String attack_type, long eff_pow)
  {
    if (weak.contains(attack_type)) return eff_pow*2;
    if (immune.contains(attack_type)) return 0;
    return eff_pow;


  }
  public int takeDamage(long dmg)
  {
    int unit_deed = (int) (dmg / hp);

    int units_left = Math.max( 0, units - unit_deed);

    int removed = units - units_left;

    units = units_left;
    return removed;

  }


  public void resetTargets()
  {
    target = null;
    targeted_by = null;
  }
  public boolean isLive()
  {
    return (units > 0);
  }

  public Group(String side, String line)
  {
    this.side = side;
    //System.out.println(side + " - " + line);
    //3574 units each with 12385 hit points (weak to bludgeoning; immune to radiation) with an attack that does 27 radiation damage at initiative 19

    Scanner scan = new Scanner(line);
    units = scan.nextInt();
    scan.next(); scan.next(); scan.next();
    hp = scan.nextInt();
    scan.next(); scan.next();
    while(true)
    {
      String s = scan.next();
      if (s.equals("with")) break;
    }
    scan.next(); scan.next(); scan.next();
    scan.next(); //does
    attack = scan.nextInt();
    attack_type = scan.next();
    scan.next(); //damage
    scan.next(); //at
    scan.next(); //init
    init=scan.nextInt();

    parseSets(line);

  }
  public String toString()
  {
    return String.format("%s - %d u %d hp %d %s attack init %d {weak:%s} {imm:%s}", side, units, hp, attack, attack_type, init, weak, immune);

  }

  private void parseSets(String line)
  {
    if (line.indexOf('(') < 0) return;

    line = line.substring(line.indexOf('(')+1);
    line = line.substring(0, line.indexOf(')'));
    line = line.replace(" to","");
    line = line.replace(";","");
    line = line.replace(",","");
    TreeSet<String> mode = null;
    Scanner scan = new Scanner(line);

    while(scan.hasNext())
    {
      String s = scan.next();
      if (s.equals("immune"))
      {
        mode = immune;
      }
      else if (s.equals("weak"))
      {
        mode = weak;
      }
      else
      {
        mode.add(s);
      }

    }



  }
}

