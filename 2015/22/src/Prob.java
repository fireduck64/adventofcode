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
  boolean hard_mode=false;

  public Prob(Scanner scan)
    throws Exception
  {
    int b_hp = Tok.ent(scan.nextLine(), " ").get(0);
    int b_dmg = Tok.ent(scan.nextLine(), " ").get(0);

    SS fin = (SS)Search.search(new SS(b_hp, b_dmg));

    System.out.println(fin);
    System.out.println("P1 Mana Spent: " + fin.spent);
    System.out.println(fin.spells);

    hard_mode=true;
    fin = (SS)Search.search(new SS(b_hp, b_dmg));

    System.out.println(fin);
    System.out.println("P2 Mana Spent: " + fin.spent);
    System.out.println(fin.spells);
 
  }

  public class SS extends State
  {
    int b_hp;
    int b_dmg;
    int p_hp;

    int mana;
    int spent;

    // If non-zero the number of remaining turns in effect
    int shield;
    int poison;
    int recharge;
    List<String> spells;

    public SS(int b_hp, int b_dmg)
    {
      this.b_hp = b_hp;
      this.b_dmg = b_dmg;
      p_hp=50;
      mana=500;
      spells=new LinkedList<>();
    }
    private SS()
    {
      spells=new LinkedList<>();
    }

    public SS copy()
    {
      SS c = new SS();
      c.b_hp = b_hp;
      c.b_dmg = b_dmg;
      c.p_hp = p_hp;
      c.mana = mana;
      c.shield = shield;
      c.poison = poison;
      c.recharge = recharge;
      c.spent = spent;
      c.spells.addAll(spells);

      return c;

    }

    public void applyCrabs()
    {
      if (poison > 0)
      {
        b_hp -= 3;
        poison--;
      }
      if (recharge > 0)
      {
        mana += 101;
        recharge--;
      }
      if (shield > 0) shield--;
    }
    public void applyBoss()
    {
      int armor =0;
      if (shield > 0) armor=7;
      if (b_hp > 0)
      {
        p_hp -= Math.max(1, b_dmg - armor);
      }

    }


    public double getCost(){return spent;}

    public String toString()
    {
      return "b" + b_hp + ".d" + b_dmg + ".p" + p_hp + ".m" + mana + ".s" + shield + ".p" + poison + ".r" + recharge;
    }

    public boolean isTerm()
    {
      if (p_hp >0)
      if (b_hp <=0)
        return true;

      if (poison > 0)
      if (b_hp <= 3)
      if (p_hp >0)
        return true;

      return false;
    }

    public List<State> next()
    {
      List<State> lst = new LinkedList<>();

      SS n = copy();

      n.applyCrabs();
      if (hard_mode)
      {
        n.p_hp --;
      }

      if (mana < 53) return lst;
      if (p_hp <= 0) return lst;
      if (b_hp <= 0) return lst;

      if (n.mana >= 53)
      { // magic missle
        SS n2 = n.copy();
        n2.mana -= 53;
        n2.spent += 53;
        n2.b_hp -= 4;
        n2.spells.add("missle");

        n2.applyCrabs();
        n2.applyBoss();
        lst.add(n2);
      }
      if (n.mana >= 73)
      { // Drain
        SS n2 = n.copy();
        n2.mana -= 73;
        n2.spent += 73;
        n2.b_hp -= 2;
        n2.p_hp += 2;
        n2.spells.add("drain");

        n2.applyCrabs();
        n2.applyBoss();
        lst.add(n2);
      }
      if (n.mana >= 113)
      if (n.shield == 0)
      { // shield
        SS n2 = n.copy();
        n2.mana -= 113;
        n2.spent += 113;
        n2.shield = 6;
        n2.spells.add("shield");
        n2.applyCrabs();
        n2.applyBoss();
        lst.add(n2);
      }
      if (n.mana >= 173)
      if (n.poison == 0)
      { // poison
        SS n2 = n.copy();
        n2.mana -= 173;
        n2.spent += 173;
        n2.spells.add("poison");
        n2.poison=6;
        n2.applyCrabs();
        n2.applyBoss();
        lst.add(n2);
      }
      if (n.mana >= 229)
      if (n.recharge == 0)
      { // recharge
        SS n2 = n.copy();
        n2.mana -= 229;
        n2.spent += 229;
        n2.spells.add("recharge");
        n2.recharge=5;
        n2.applyBoss();
        n2.applyCrabs();
        lst.add(n2);
      }

      return lst;

    }

  }
}
