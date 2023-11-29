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

  TreeMap<Integer, Rule> rules=  new TreeMap<>();

  public Prob(Scanner scan)
  {
    while(true)
    {
      String line = scan.nextLine().trim();
      if (line.length() == 0) break;

      Rule r = new Rule(line);
      rules.put(r.number, r);

    }

    int p1=0;
    ArrayList<String> messages=new ArrayList<>();
    while(scan.hasNext())
    {
      String message = scan.next();
      messages.add(message);
      if (matchComplete(message, 0)) p1++;
    }
    System.out.println("Part 1: " + p1);

    rules.put(8, new Rule("8: 42 | 42 8"));
    rules.put(11,new Rule("11: 42 31 | 42 11 31"));
    int p2=0;
    for(String message : messages)
    {
      if (matchComplete(message, 0)) p2++;
    }
    System.out.println("Part 2: " + p2);

  }

  // Determine if there is a way for the rule to match the entire string
  public boolean matchComplete(String in, int rule)
  {
    return (matchRem(in, rule).contains(""));
  }

  // Determine if there is a way for the rule to match the start of in
  // returns list of possible remaining strings after this rule
  public TreeSet<String> matchRem(String in, int rule)
  {
    Rule r = rules.get(rule);
    TreeSet<String> options = new TreeSet<>();
    if (r.isTerm())
    {
      if (in.startsWith(r.term))
      {
        String rem = in.substring(r.term.length());
        options.add(rem);
      }
      
      return options;
    }
    else
    {
      // For each match sequence
      for(ArrayList<Integer> seq : r.match_lists)
      {
        TreeSet<String> action = new TreeSet<>();
        //Start with input string 
        action.add(in);
        

        // For each number in the sequence, see how much can be left
        for(int r2 : seq)
        {
          TreeSet<String> action_out = new TreeSet<>();
          for(String start : action)
          {
            TreeSet<String> sub_out = matchRem(start, r2);
            for(String s : sub_out)
            {
              action_out.add(s);
            }
          }
          action = action_out;

        }

        options.addAll(action);


      }


    }
    return options;

  }


  public class Rule
  {
    int number;
    String term;

    ArrayList<ArrayList<Integer> > match_lists=new ArrayList<>();

    public Rule(String line)
    {
      Scanner scan = new Scanner(line.replace(":",""));
      number = scan.nextInt();

      ArrayList<Integer> acc = new ArrayList<>();
      while(scan.hasNext())
      {
        String n = scan.next();
        if (n.equals("|"))
        {
          match_lists.add(acc);
          acc=new ArrayList<>();
        }
        else if (n.startsWith("\""))
        {
          term = n.replace("\"","").trim();
          return;

        }
        else
        {
          acc.add(Integer.parseInt(n));

        }

      }
      if (acc.size() > 0) match_lists.add(acc);
    }

    public String toString()
    {
      return ("Rule: " + number + " " + term + " " + match_lists);

    }

    public boolean isTerm()
    {
      return term != null;
    }


  }

}
