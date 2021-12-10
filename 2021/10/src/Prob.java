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

  int p1_score;

  public Prob(Scanner scan)
  {
    ArrayList<Long> scores = new ArrayList<>();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      long score = parseLine(line);
      if (score > 0)
      scores.add(parseLine(line));

    }

    Collections.sort(scores);
    System.out.println(scores);
    System.out.println(scores.size());

    System.out.println("Part 1: " + p1_score);
    System.out.println("Part 2: " + scores.get(scores.size() / 2));

  }


  public long parseLine(String line)
  {
    LinkedList<Character> open=new LinkedList<>();
    int pos=0;
    

    while(pos < line.length())
    {
      char z = line.charAt(pos);
      if (z =='(') open.add(z);
      else if (z =='{') open.add(z);
      else if (z =='[') open.add(z);
      else if (z =='<') open.add(z);
      else
      {
        if (!checkMatch(z, open))
        {
          // mismatch
          if (z==')') p1_score+=3;
          if (z==']') p1_score+=57;
          if (z=='}') p1_score+=1197;
          if (z=='>') p1_score+=25137;

          return 0;

        }
      }

      pos++;

    }
    if (open.size() > 0)
    {
      long score=0;
      while(open.size() > 0)
      {
        score*=5;
        char r = open.pollLast();
        if (r == '(') score+=1;
        if (r == '[') score+=2;
        if (r == '{') score+=3;
        if (r == '<') score+=4;
      }
      return score;
      //incomplete

    }
    E.er();
    return 0;


  }

  public boolean checkMatch(char z, LinkedList<Character> open)
  {
    if (open.size() == 0) return false;
    char x = open.  peekLast();
    if ((x=='(') && (z==')'))
    {
      open.removeLast();
      return true;
    }
    if ((x=='<') && (z=='>'))
    {
      open.removeLast();
      return true;
    }

    if ((x=='{') && (z=='}'))
    {
      open.removeLast();
      return true;
    }

    if ((x=='[') && (z==']'))
    {
      open.removeLast();
      return true;
    }
    return false;


  }


}
