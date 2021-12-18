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

  String parse_line;
  int parse_pos;

  public Prob(Scanner scan)
  {
    SN sum = null;
    ArrayList<String> lines = new ArrayList<>();
    for(String line : In.lines(scan))
    {
      lines.add(line);
      parse_line = line;
      parse_pos = 0;

      SN sn = parse();
      System.out.println(sn.toString());

      if (sum == null) sum = sn;
      else sum = add(sum, sn);

    reduce(sum);

    }
    reduce(sum);
    
    System.out.println("Final: " + sum);
    System.out.println("Part 1: " + getMag(sum));


    // Everything has side effects in the tree,
    // the nodes are modified by the split and explode operations
    // So the only safe way to do this part is re-parse for each magnitude
    // check
    long max_mag = 0;
    for(int i=0; i<lines.size(); i++)
    for(int j=0; j<lines.size(); j++)
    if (i != j)
    {
      parse_line = lines.get(i);
      parse_pos = 0;
      SN a = parse();

      parse_line = lines.get(j);
      parse_pos = 0;
      SN b = parse();

      sum = add(a,b);
      reduce(sum);
      max_mag = Math.max(max_mag, getMag(sum));

    }
    System.out.println("Part 2: " + max_mag);



    

  }

  public SN add(SN a, SN b)
  {
    SN sn = new SN();
    sn.left = a;
    sn.right = b;

    return sn;
  }
  public SN reduce(SN in)
  {
    while(true)
    {
      //System.out.println("Reduce: " + in);
      SN exp = findExploder(in, 0);
      if (exp != null)
      {

        // Grab a list of left to right traversal of entire tree
        // so we can find where to explode the values to
        ArrayList<SN> regs = new ArrayList<>();      
        getList(in, regs);
        
        //System.out.println("Doing explode on " + exp);
        //System.out.println("regs: " + regs);
        if (exp.left.isPair()) E.er();
        if (exp.right.isPair()) E.er();

        // Use object equality for once in my fucking life
        // find the explode node in the list
        int reg_idx = -1;
        for(int i=0; i<regs.size(); i++)
        {
          if (regs.get(i) == exp) reg_idx = i;
        }
        if (reg_idx == -1) E.er();

        // Ok, so we want the nearest regular numbers
        // to throw values on.  But the regular numbers
        // that are directly under this exploder are right next to it
        // so start the search at -2 and +2
        for(int i = reg_idx - 2; i>=0; i--)
        {
          if (regs.get(i).isReg())
          {
            //System.out.println("Adding left ");
            regs.get(i).reg += exp.left.reg;
            break;
          }
        }
        for(int i = reg_idx + 2; i <regs.size(); i++)
        {
          if (regs.get(i).isReg())
          {
            //System.out.println("Adding right ");
            regs.get(i).reg += exp.right.reg;
            break;
          }
        }

        exp.left = null;
        exp.right = null;
        exp.reg = 0;

        continue;

      }
      SN split = findSplit(in);
      if (split != null)
      {
        //System.out.println("Doing split");
        split.left = new SN();
        split.left.reg = split.reg / 2;
        split.right = new SN();
        split.right.reg = split.reg / 2;
        if (split.reg % 2 == 1) split.right.reg++;
        split.reg =0;
        continue;

      }

      // nothing doing
      break;

    }
    return in;
  }

  /** build in order list */
  public void getList(SN in, ArrayList<SN> lst)
  {
    if (in.isReg())
    {
      lst.add(in);
      return;
    }
    getList(in.left, lst);
    lst.add(in);
    getList(in.right, lst);

  }
  public long getMag(SN in)
  {
    if (in.isReg()) return in.reg;
    return 3L*getMag(in.left) + 2L*getMag(in.right);
  }

  public SN findExploder(SN in, int level)
  {
    if (in.isReg())
    {
      return null;
    }
    
    SN eleft = findExploder(in.left, level+1);
    if (eleft != null)
    {
      return eleft;
    }
    if ((in.isPair()) && (level >= 4))
    if (in.left.isReg())
    if (in.right.isReg())
    {
      return in;
    }

    SN eright = findExploder(in.right, level+1);
    return eright;

  }


  public SN findSplit(SN in)
  {
    if (in.isReg())
    {
      if (in.reg >= 10) return in;
      return null;
    }
    
    SN o = findSplit(in.left);
    if (o != null) return o;

    return findSplit(in.right);

  }
  

  public SN parse()
  {
    SN sn = new SN();
    char n=parse_line.charAt(parse_pos);
    if (n != '[')
    {
      sn.reg = Integer.parseInt("" + n);
      parse_pos++;
      return sn;

    }

    parse_pos++;


    sn.left = parse();

    // expect ,
    if (parse_line.charAt(parse_pos) != ',') E.er();
    parse_pos++;

    sn.right = parse();
    if (parse_line.charAt(parse_pos) != ']') E.er();
    parse_pos++;

    return sn;

  }

  public class SN // Snail number
  {
    SN left;
    SN right;

    int reg;

    public boolean isReg()
    {
      return (left==null);
    }
    public boolean isPair()
    {
      return (left != null);
    }

    public String toString()
    {
      if (isReg()) return "" + reg;

      return String.format("[%s,%s]", left.toString(), right.toString());

    }

    

  }

}
