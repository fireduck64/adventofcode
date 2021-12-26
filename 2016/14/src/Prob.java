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
  String input;
  String seed;

  List<Character> hex = Str.stolist("0123456789abcdef");

  TreeMap<Character, TreeSet<Long> > v5 = new TreeMap<>();
  
  long v5_look=0;
  int hashes=0;

  public Prob(Scanner scan)
  {
    input = scan.nextLine();

    System.out.println(findPad());

    v5.clear();
    v5_look=0L;
    hashes=2016;
    System.out.println(findPad());

  }
  public String getHash(String in)
  {
      String md5 = HUtil.getHash(in);
      for(int i=0; i<hashes; i++)
      {
        md5 = HUtil.getHash(md5);
      }
      return md5;
    

  }

  public long findPad()
  {
    int found = 0;
    long idx=0;
    while(found < 64)
    {
      String md5 = getHash(input + idx);
      updateV5(idx+1000);

      char first_z='_';
      int z_loc=1000000;

      for(char z : hex)
      {
        String zz = "" + z + z + z;
        int z_idx = md5.indexOf(zz);
        if (z_idx >= 0)
        {
          if (z_idx < z_loc)
          {
            first_z=z;
            z_loc=z_idx;
          }
        }
      }
      char z = first_z;
      if (v5.containsKey(z))
      {
        if (v5.get(z).subSet(idx+1, idx+1002).size() > 0)
        {
          found++;
        }
      }
      if (found == 64) return idx;

      idx++;
    }
    return idx;

  }

  public void updateV5(long target)
  {
    for(long x = v5_look; x<=target; x++)
    {
      String md5 = getHash(input + x);

      for(char z : hex)
      {
        String zz = "" + z + z + z + z + z;
        if (md5.indexOf(zz) >= 0)
        {
          if (!v5.containsKey(z)) v5.put(z, new TreeSet<Long>());

          v5.get(z).add(x);

        }

      }
      

    }
    v5_look = target;

  }

}
