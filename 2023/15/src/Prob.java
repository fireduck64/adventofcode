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

  HashMap<Integer, LinkedList<Lens> > map = new HashMap<>();

  public Prob(Scanner scan)
  {
    String line = scan.nextLine();

    List<String> lst = Tok.en(line, ",");

    long p1 = 0;
    for(String s : lst) p1+=hash(s);

    System.out.println("Part 1: " + p1);

    for(int i=0; i<256; i++) map.put(i, new LinkedList<Lens>());

    for(String s : lst)
    {
      String label = s.replace("-","=").split("=")[0];
      int hash = hash(label);
      if (s.indexOf('=')>=0)
      {
        int val = Integer.parseInt(s.split("=")[1]);
        Lens l = new Lens(label, val);
        boolean rep = false;
        LinkedList<Lens> box = map.get(hash);
        LinkedList<Lens> new_box = new LinkedList<>();
        for(Lens old : box)
        {
          if ((old.label.equals(label)) && (!rep)) { 
            new_box.add(l);
            rep=true;
          }
          else
          {
            new_box.add(old);
          }
        }
        if (!rep)
        {
          new_box.add(l);
        }
          
        map.put(hash,new_box);
      }
      else
      {
        LinkedList<Lens> box = map.get(hash);
        for(Lens l : box)
        {
          if (l.label.equals(label)) { box.remove(l); break; }
          
        }

      }

    }
    long p2 =0;
    for(int i=0; i<256; i++)
    {
      LinkedList<Lens> box = map.get(i);
      long slot =1;
      for(Lens l : box)
      {
        long focus = (1+i) * (slot) * l.val;
        p2+=focus;

        slot++;

      }


    }
    print();
    System.out.println("Part 2: " + p2);


  }

  public void print()
  {

    for(int i=0; i<256; i++)
    {
      LinkedList<Lens> box = map.get(i);
      if (box.size() > 0)
      {
        System.out.print("box " + i + ":" + box.toString());

      }



    }
    System.out.println();
  }


  public int hash(String in)
  {
    int v =0;
    for(int i=0; i<in.length(); i++)
    {
      v += in.charAt(i);
      v *= 17;
      v = v % 256;
    }

    return v;

  }

  public class Lens
  {
    String label;
    int val;
    public Lens(String label, int val)
    {
      this.label = label;
      this.val = val;

    }

    public String toString()
    {
      return "L{" + label + "," + val + "}";
    }


  }

}
