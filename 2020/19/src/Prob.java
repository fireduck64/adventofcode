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
  HashMap<Integer, Rule> rules=new HashMap<>();

  public Prob(Scanner scan)
  {
    while(true)
    {
      String line = scan.nextLine().trim();
      if (line.length() == 0) break;

      line=line.replace(":","");
      List<String> tokens = Tok.en(line, " ");

      int rule = Integer.parseInt(tokens.get(0));


      
      if (tokens.get(1).startsWith("\""))
      { // read literal rule
        char z = tokens.get(1).charAt(1);
        rules.put(rule, new RuleLiteral(z));
      }
      else if (line.indexOf("|") > 0)
      { // read or rule with two lists
        List<Integer> a = new LinkedList<>();
        List<Integer> b = null;
        for(int i = 1; i<tokens.size(); i++)
        {
          String s = tokens.get(i);
          if (s.equals("|"))
          {
            b=new LinkedList<>();
          }
          else
          {
            int r = Integer.parseInt(s);
            if (b != null) b.add(r);
            else a.add(r);
          }

        }
        rules.put(rule, new RuleOr(ImmutableList.of(new RuleList(a), new RuleList(b))));

      }
      else
      { // read list rule
        
        List<Integer> lst = new LinkedList<>();
        for(int i = 1; i<tokens.size(); i++)
        {
          lst.add(Integer.parseInt(tokens.get(i)));
        }
        rules.put(rule, new RuleList(lst) );

      }
    }


    int matches=0;
    while(scan.hasNext())
    {
      String line = scan.next();

      Set<StringRead> sr = rules.get(0).match(new StringRead(line, 0));
      for(StringRead s : sr)
      {
        if (s.atEnd())
        {
          matches++;
          break;
        }
      }
    }

    System.out.println(matches);


  }


  public abstract class Rule
  {
    // Starts from the reading location in StringRead,
    // return the set of unique matching results with this rule
    public abstract Set<StringRead> match(StringRead sr);
  }

  public class RuleList extends Rule
  {
    public List<Integer> lst;

    public RuleList(List<Integer> lst)
    {
      this.lst = lst;
    }

    // Takes the matches from each rule and gives it as input to the next rule
    // return whatever we have left
    public Set<StringRead> match(StringRead sr)
    {
      Set<StringRead> in = new HashSet<>();
      in.add(sr);
      for(int i : lst)
      {
        Rule r = rules.get(i);
        Set<StringRead> out = new HashSet<>();

        for(StringRead ir : in)
        {
          out.addAll( r.match(ir) );
        }
        in = out;
      }
      return in;
    }
  }


  public class RuleOr extends Rule
  {
    public List<Rule> lst;

    // Takes a list of rules to OR togather.
    // Take them as a rules because they are kinda internal and don't
    // have numbers.  Example: 23 45 | 13 12
    // This comes int as List(RuleList(23,24), RuleList(13,12))
    // Whatever
    public RuleOr(List<Rule> lst)
    {
      this.lst = lst;
    }

    public Set<StringRead> match(StringRead sr)
    {
      Set<StringRead> matches= new HashSet<StringRead>();

      for(Rule r : lst)
      {
        matches.addAll(r.match(sr));
      }

      return matches;
    }

  }

  public class RuleLiteral extends Rule
  {
    public final char z;

    public RuleLiteral(char z)
    {
      this.z = z;
    }

    public Set<StringRead> match(StringRead sr)
    {
      Set<StringRead> matches= new HashSet<StringRead>();

      if (!sr.atEnd())
      {
        if (sr.read()==z)
        {
          matches.add(new StringRead(sr.str, sr.loc+1));
        }
      }

      return matches;
    }

  }

  // Basically an immutable pointer class.
  // Why the hell is this so long?  WTF am I doing.
  public class StringRead
  {
    public final String str;
    public final int loc;

    public StringRead(String str, int loc)
    {
      this.str = str;
      this.loc = loc;

    }
    public char read()
    {
      return str.charAt(loc);
    }
    public boolean atEnd()
    {
      return (loc >= str.length());
    }

    public int hashCode()
    {
      return loc + str.hashCode();
    }
    public boolean equals(Object o)
    {
      return toString().equals(o.toString());  
    }
    public String toString()
    {
      return str + "/" + loc;
    }

  }


}
