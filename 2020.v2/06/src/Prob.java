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

  ArrayList<ArrayList<String> > groups = new ArrayList<>();

  public Prob(Scanner scan)
  {
    ArrayList<String> group = new ArrayList<>();
    for(String line : In.lines(scan))
    {
      if (line.trim().length() == 0)
      {
        if (group.size() > 0)
        {
          groups.add(group);
          group=new ArrayList<>();
        }
      }
      else
      {
        group.add(line);
      }

    }
    if (group.size() > 0)
    {
      groups.add(group);
      group=new ArrayList<>();
    }

    int p1=0;
    int p2=0;
    for(ArrayList<String> grp : groups)
    {
      TreeSet<Character> s = new TreeSet<>();

      TreeSet<Character> p2s = null;
      for(String str : grp)
      {
        s.addAll( Str.stolist(str));

        if (p2s == null)
        {
          p2s = new TreeSet<>();
          p2s.addAll( Str.stolist(str));
        }
        else
        {
          p2s.retainAll( Str.stolist(str));
        }

      }
      p1+=s.size();
      p2+=p2s.size();
    }
    System.out.println("Part 1: " + p1);
    System.out.println("Part 2: " + p2);


  }

}
