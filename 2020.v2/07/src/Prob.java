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

  TreeMap<String, Rule> rules= new TreeMap<>();

  public Prob(Scanner scan)
  {
    for(String line : In.lines(scan))
    {
      Rule r = new Rule(line);

      rules.put(r.type, r);
      System.out.println(r);
    }

    int p1=0;
    for(String type : rules.keySet())
    {
      if (getContents(type).containsKey("shiny gold")) p1++;
    }
    System.out.println("Part 1: " + p1);


    long p2=0;
    for(Map.Entry<String, Long> me : getContents("shiny gold").entrySet())
    {
      p2+=me.getValue();
    }
    System.out.println("Part 2: " + p2);



     

  }

  public String readName(Scanner scan)
  {
    return scan.next() + " " + scan.next();

  }

  public class Rule
  {
    String type;
    TreeMap<String, Long> m = new TreeMap<>();

    public Rule(String line)
    {
      line = line.replace(",", "");
      line = line.replace(".", "");

      Scanner scan = new Scanner(line);
  
      type = readName(scan);

      scan.next(); //bag
      scan.next(); //contains
      if (!line.contains("no other bags"))
      while(scan.hasNext())
      {
        long count = scan.nextInt();
        String t = readName(scan);
        scan.next(); //bag

        m.put(t, count);

      }
    }

    public String toString()
    {
      return "" + type + " : " + m.toString();
    }

  }


  HashMap<String, TreeMap<String, Long> > content_cache=new HashMap<>(1024,0.5f);

  public TreeMap<String, Long> getContents(String type)
  {
    if (content_cache.containsKey(type)) return content_cache.get(type);

    Rule r = rules.get(type);

    TreeMap<String, Long> con = new TreeMap<>();

    for(String sub : r.m.keySet())
    {
      long multi = r.m.get(sub);

      TreeMap<String, Long> sub_cont = getContents(sub);

      for(String s : sub_cont.keySet())
      {
        long v = sub_cont.get(s) * multi;
        if (!con.containsKey(s)) con.put(s, 0L);

        con.put(s, con.get(s) + v);
      }
      {
        long v = multi;
        if (!con.containsKey(sub)) con.put(sub, 0L);

        con.put(sub, con.get(sub) + v);

      }

    }

    content_cache.put(type, con);
    return con;

  }

}
