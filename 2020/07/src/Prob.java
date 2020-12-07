import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  TreeMap<String, BagRule> rulemap = new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      BagRule br = new BagRule(scan.nextLine());

      rulemap.put(br.color, br);
    }


    int cnt=0;
    for(String c : rulemap.keySet())
    {
      if (fullDesc(c).contains("shiny gold")) cnt++;
    }
    System.out.println("Part 1");
    System.out.println(cnt);
    
    System.out.println("Part 2");
    System.out.println(fullCount("shiny gold")-1);
  }

  TreeSet<String> fullDesc(String color)
  {
    BagRule br = rulemap.get(color);


    TreeSet<String> set = new TreeSet<>();
    
    for(String c : br.contains.keySet())
    {
      set.addAll(fullDesc(c));
      set.add(c);
    }

    return set;

  }
  int fullCount(String color)
  {
    BagRule br = rulemap.get(color);

    int cnt = 1;
    
    for(String c : br.contains.keySet())
    {
      cnt += fullCount(c) * br.contains.get(c);
    }

    return cnt;
  }

  public class BagRule
  {
    String color;
    TreeMap<String, Integer> contains=new TreeMap<>();

    public BagRule(String line)
    {
      line = line.replace(",","");
      line = line.replace(".","");
      Scanner scan = new Scanner(line);
      color = scan.next() + " " + scan.next();

      if (line.contains("contain no other bags")) return;

      scan.next(); // bags
      scan.next(); // contains
      while(scan.hasNext())
      {
        int cnt = scan.nextInt();
        String c = scan.next() +" "+ scan.next();
        scan.next(); // bags

        contains.put(c, cnt);
      }

    }

  }

}
