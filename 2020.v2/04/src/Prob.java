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

  ArrayList<TreeMap<String, String> > entry_list=new ArrayList<>();

  List<String> req_p1 = ImmutableList.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
  Set<String> eyes = ImmutableSet.of("amb","blu","brn","gry","grn","hzl","oth");

  public Prob(Scanner scan)
  {
    List<String> lines = In.lines(scan);
    TreeMap<String, String> entry=new TreeMap<>();
    for(String line : lines)
    {
      if (line.length() == 0)
      {
        if (entry.size() > 0)
        {
          entry_list.add(entry);
          entry=new TreeMap<>();
        }
      }
      else
      {
        String[] s1 = line.split(" ");
        for(String s : s1)
        {
          if (s.trim().length() > 0)
          {
            String[] s2 = s.split(":");
            entry.put(s2[0], s2[1]);
          }

        }

      }

    }
    if (entry.size() > 0)
    {
      entry_list.add(entry);
      entry=new TreeMap<>();
    }

    int part1 = 0;
    int part2 = 0;
    for(TreeMap<String, String> e : entry_list)
    {
      int has = 0;
      for(String f : req_p1)
      {
        if (e.containsKey(f)) has++;
      }
      if (has == req_p1.size()) part1++;

      try
      {
        if (validate(e)) part2++;
      }
      catch(Exception e2)
      {

      }

    }
    System.out.println("Part 1: " + part1);
    System.out.println("Part 2: " + part2);



  }


  public boolean validate(Map<String, String> e)
    throws Exception
  {
    int byr = Integer.parseInt(e.get("byr"));
    if (byr < 1920) return false;
    if (byr > 2002) return false;

    int iyr = Integer.parseInt(e.get("iyr"));
    if (iyr < 2010) return false;
    if (iyr > 2020) return false;

    int eyr = Integer.parseInt(e.get("eyr"));
    if (eyr < 2020) return false;
    if (eyr > 2030) return false;

    String hgt = e.get("hgt");
    if (hgt.endsWith("cm"))
    {
      int h = Integer.parseInt(hgt.replace("cm",""));
      if (h < 150) return false;
      if (h > 193) return false;
    }
    else if (hgt.endsWith("in"))
    {
      int h = Integer.parseInt(hgt.replace("in",""));
      if (h < 59) return false;
      if (h > 76) return false;
    }
    else return false;

    String hcl = e.get("hcl");
    if (hcl.charAt(0) != '#') return false;
    if (hcl.length() != 7) return false;

    for(int i=1; i<7; i++)
    {
      char z = hcl.charAt(i);
      boolean ok=false;
      if (('0' <= z) && (z <= '9')) ok=true;
      if (('a' <= z) && (z <= 'f')) ok=true;

      if (!ok) return false;

    }

    String ecl = e.get("ecl");
    if (!eyes.contains(ecl)) return false;

    String pid = e.get("pid");

    if (pid.length() != 9) return false;
    for(int i=0; i<9; i++)
    {
      char z = pid.charAt(i);
      if (z < '0') return false;
      if (z > '9') return false;
    }


    return true;
  }

}
