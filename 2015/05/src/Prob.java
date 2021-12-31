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

  public Prob(Scanner scan)
  {
    List<String> input = In.lines(scan);

    int nice = 0;
    int nice2 = 0;
    for(String line : input)
    {
      if (isNice(line)) nice++; 
      if (isNice2(line)) nice2++; 
      


    }
    System.out.println("Part 1: " + nice);
    System.out.println("Part 2: " + nice2);

  }

  public boolean isNice2(String line)
  {
    int trips=0;
    for(int i=0; i<line.length()-2; i++)
    {
      if (line.charAt(i) == line.charAt(i+2)) trips++;
    }
    if (trips==0) return false;

    
    int dubs=0;
    for(int i=0; i<line.length()-1; i++)
    {
      for(int j=i+2; j<line.length()-1; j++)
      {
        if (line.charAt(i) == line.charAt(j))
        if (line.charAt(i+1) == line.charAt(j+1))
        {
          dubs++;
        }
      }
    }
    if (dubs==0) return false;
    

    return true;

  }

  public boolean isNice(String line)
  {
    if (line.contains("ab")) return false;
    if (line.contains("cd")) return false;
    if (line.contains("pq")) return false;
    if (line.contains("xy")) return false;

    int doubles=0;
    for(int i=0; i<line.length()-1; i++)
    {
      if (line.charAt(i) == line.charAt(i+1)) doubles++;
    }
    if (doubles==0) return false;

    int vowels=0;
    for(int i=0;  i<line.length(); i++)
    {
      char z = line.charAt(i);
      if (z=='a') vowels++;
      if (z=='e') vowels++;
      if (z=='i') vowels++;
      if (z=='o') vowels++;
      if (z=='u') vowels++;

    }
    if (vowels<3) return false;

    return true;

  }

}
