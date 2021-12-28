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
  //String start="abcde";
  String start="abcdefgh";
  String target="fbgdceah";

  public Prob(Scanner scan)
  {
    List<String> input = In.lines(scan);

    {

      String code = start;
      for(String line : input)
      {
        code = doOp(line, code);
      }
      System.out.println("Part 1: " + code);
    }

    // Fuck it
    // Who needs next perumutation
    List<Character> in_list = Str.stolist(start);
    while(true)
    {
      Collections.shuffle(in_list);
      String code = Str.listtos(in_list);
      String in = code;
      for(String line : input)
      {
        code = doOp(line, code);
      }
      if (code.equals(target))
      {
        System.out.println("Part 2: " + in);
        break;
      }


    }

  }

  public String doOp(String line, String in)
  {
    List<String> parts = Tok.en(line, " ");
    List<Integer> vals = Tok.ent(line, " ");
    String code = null;
    if (line.startsWith("swap position"))
    {
      int p1 = vals.get(0);
      int p2 = vals.get(1);
      byte b[]=in.getBytes();
      byte z = b[p1];
      b[p1] = b[p2];
      b[p2] = z;
      code = new String(b);

    }
    else if (line.startsWith("swap letter"))
    {
      char a=parts.get(2).charAt(0);
      char b=parts.get(5).charAt(0);
      code = in.replace(a, '_').replace(b,a).replace('_',b);

    }
    else if (line.startsWith("rotate based"))
    {
      char x = parts.get(6).charAt(0);
      int idx = in.indexOf(x);
      int rot=idx + 1;
      if (idx >= 4) rot++;
      code = rotate(in, -rot);

    }
    else if (line.startsWith("rotate"))
    {
      int mult=1;
      if (parts.get(1).equals("right")) mult=-1;
      int steps = vals.get(0);
      code = rotate(in, steps * mult);

    }
    else if (line.startsWith("reverse"))
    {
      code=in;
      int p1=vals.get(0);
      int p2=vals.get(1);
      code = in.substring(0, p1) + rev(in.substring(p1, p2+1)) + in.substring(p2+1);

    }
    else if (line.startsWith("move"))
    {
      int p1=vals.get(0);
      int p2=vals.get(1);
      code=in;
      char grab = in.charAt(p1);
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<in.length(); i++)
      {
        // We haven't removed p1 yet, 
        // so to get grab at index p2, we put it in first
        if (p1 > p2)
        if (i == p2)
        {
          sb.append(grab);
        }

        // As long as we are not p1,
        // append
        if (i != p1)
        { 
          sb.append(in.charAt(i));
        }
        
        // if we removed p1 already, put in 
        // grab after the i append
        if (p1 < p2)
        if (i == p2)
        {
          sb.append(grab);
        }
      }
      code=sb.toString();

    }
    else
    {
      E.er(line);
    }


    return code;

  }

  // Rotates to the left
  public String rotate(String in, int rot)
  {
    int len = in.length();
    if (rot < 0)
    {
      return rotate(in, len+rot); 
    }
    rot = rot % len;
    if (rot == 0) return in;
    StringBuilder sb = new StringBuilder();
    
    // ABCDE
    // rotate 1 left
    // BCDEA
    // XXXYY
    for(int i=rot; i<len; i++)
    {
      sb.append(in.charAt(i));
    }
    for(int i=0; i<rot; i++)
    {
      sb.append(in.charAt(i));
    }
    return sb.toString();

  }
  public String rev(String in)
  {
    StringBuilder sb = new StringBuilder();
    for(int i=in.length() -1; i>= 0; i--)
      sb.append(in.charAt(i));

    return sb.toString();

  }

}
