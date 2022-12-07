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

  Dir root = new Dir(null);
  Dir current = root;

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      List<String> p = Tok.en(line, " ");
      if (p.get(0).equals("$"))
      {
        if (p.get(1).equals("cd"))
        {
          String tar = p.get(2);
          if (tar.equals("..")) current = current.parent;
          else if (tar.equals("/")) current = root;
          else
          {
            current = current.subdirs.get(tar);
            
          }
          if (current == null) E.er();


        }
        if (p.get(1).equals("ls"))
        {
          // who cares

        }

      }
      else
      {
        if (p.get(0).equals("dir"))
        {
          String dir = p.get(1);
          // has a subdir
          if (!current.subdirs.containsKey(dir))
            current.subdirs.put(dir, new Dir(current));

        }
        else
        {
          long size = Long.parseLong(p.get(0));
          String name = p.get(1);

          current.files.put(name, size);


        }

      }

    }

    List<Dir> all_below = root.getAllBelow(100000);
    long p1 = 0;
    for(Dir d : all_below) p1+=d.getSum();
    System.out.println("PArt 1: " + p1);

    long total = 70000000;
    long target_free = 30000000;
    long used = root.getSum();
    long target = total - target_free;


    long needed = used - target;

    all_below = root.getAllBelow(used*2);
    Dir best = null;
    long best_size = 0;
    for(Dir d : all_below)
    {
      long sz = d.getSum();
      if (sz >= needed)
      {
        if ((best == null) || (sz < best_size))
        {
          best = d;
          best_size = sz;

        }


      }
    }
    System.out.println("Part 2: " + best_size);

  }

  public class Dir
  {

    public Dir(Dir parent)
    {
      this.parent = parent;
      this.subdirs = new TreeMap<>();
      this.files = new TreeMap<>();
    }
    Dir parent;
    TreeMap<String, Dir> subdirs;
    TreeMap<String, Long> files;


    public long getSum()
    {
      long sum = 0;
      for(Dir d : subdirs.values()) sum+=d.getSum();

      for(long v : files.values()) sum+=v;

      return sum;

    }

    public List<Dir> getAllBelow(long x)
    {
      LinkedList<Dir> l = new LinkedList<>();

      long v = getSum();
      if (v < x)
      {
        l.add(this);
      }

      for(Dir d : subdirs.values()) l.addAll(d.getAllBelow(x));
      
      return l;

    }

  }

}
