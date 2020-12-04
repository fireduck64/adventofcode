import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {

    TreeSet<String> req = new TreeSet<String>();
    req.add("byr");
    req.add("iyr");
    req.add("eyr");
    req.add("hgt");
    req.add("hcl");
    req.add("ecl");
    req.add("pid");
    //req.add("cid");
    int valid=0;

    while(scan.hasNextLine())
    {
      Map<String, String> p = loadPassport(scan);

      int missing = 0;
      for(String s : req)
      {
        if (!p.containsKey(s)) missing++;
      }
      if (missing == 0) 
      {
        if (isValid(p))
        {
          valid++;
        }
      }



    }

    System.out.println(valid);
    

  }

  public boolean isValid(Map<String, String> p)
  {
    try
    {
      int byr = Integer.parseInt(p.get("byr"));
      int iyr = Integer.parseInt(p.get("iyr"));
      int eyr = Integer.parseInt(p.get("eyr"));

      String hgt = p.get("hgt");
      if (hgt.endsWith("in"))
      {
        hgt=hgt.replace("in","");
        int h = Integer.parseInt(hgt);
        if (h<59) return false;
        if (h>76) return false;
      }
      else if (hgt.endsWith("cm"))
      {
        hgt=hgt.replace("cm","");
        int h = Integer.parseInt(hgt);

        if (h<150) return false;
        if (h>193) return false;
      }
      else
      {
        return false;
      }

      TreeSet<String> eye = new TreeSet<>();
      eye.add("amb");
      eye.add("blu");
      eye.add("brn");
      eye.add("gry");
      eye.add("grn");
      eye.add("hzl");
      eye.add("oth");
      String ecl = p.get("ecl");
      if (!eye.contains(ecl)) return false;


      String hcl = p.get("hcl");
      if (!hcl.startsWith("#")) return false;
      if (hcl.length() != 7) return false;
      hcl=hcl.substring(1);
      for(int i=0; i<6; i++)
      {
        char z = hcl.charAt(i);
        if ((('0' <= z) && (z <= '9')) || (('a' <= z) && (z <= 'f')))
        {

        }
        else
        {
          return false;
        }
      }



      String pid = p.get("pid");
      long pid_l = Long.parseLong(pid);
      if (pid.length() != 9) return false;

      if (byr<1920) return false;
      if (byr>2002) return false;
      if (iyr<2010) return false;
      if (iyr>2020) return false;
      if (eyr<2020) return false;
      if (eyr>2030) return false;

      return true;




    }
    catch(Exception e)
    {
      return false;
    }
    


  }

  public Map<String,String> loadPassport(Scanner scan)
  {
    Map<String, String> m = new TreeMap<>();
    while(scan.hasNextLine())
    {
      String line = scan.nextLine();
      line = line.trim();
      if (line.length() == 0) return m;

      Scanner s2 = new Scanner(line);
      while(s2.hasNext())
      {
        String item = s2.next();
        StringTokenizer stok = new StringTokenizer(item, ":");
        String key = stok.nextToken();
        String value = stok.nextToken();
        m.put(key, value);
      }

    }

    return m;

  }

}
