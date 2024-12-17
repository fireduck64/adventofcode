import java.util.*;
import java.math.BigInteger;


public class Compy
{

  ArrayList<Long> code;
  int inst_ptr=0;

  BigInteger reg_a=null;
  BigInteger reg_b=null;
  BigInteger reg_c=null;
  List<Long> output;

  boolean code_match=false;

  private Compy()
  {

  }

  public Compy(Scanner scan)
  {
    reg_a = BigInteger.valueOf(Tok.enl(scan.nextLine(), " ").get(0));
    reg_b = BigInteger.valueOf(Tok.enl(scan.nextLine(), " ").get(0));
    reg_c = BigInteger.valueOf(Tok.enl(scan.nextLine(), " ").get(0));

    scan.nextLine();

    code = new ArrayList<>();
    code.addAll(Tok.enl(scan.nextLine().replace(","," "), " "));

    inst_ptr=0;

    output = new ArrayList<>();
  }
  public Compy copy()
  {
    Compy c2 = new Compy();
    c2.code = code;
    c2.output=new ArrayList<>();
    //c2.code_match=true;
    c2.inst_ptr=0;
    c2.reg_a = reg_a;
    c2.reg_b = reg_b;
    c2.reg_c = reg_c;
    
    return c2;

  }
  public void reset(long a)
  {
    reset(BigInteger.valueOf(a));
  }
  public void reset(BigInteger a)
  {
    reg_a=a;
    reg_b=BigInteger.ZERO;
    reg_c=BigInteger.ZERO;
    output.clear();
    inst_ptr=0;

  }

  BigInteger mult(BigInteger a, BigInteger v)
  {
    BigInteger n=a;
    BigInteger dem = BigInteger.ONE.shiftLeft(v.intValueExact());
    return n.divide(dem);
  }

  public void exec()
  {
    while(true)
    {
      if (inst_ptr+1 >= code.size()) return;

      long op_code = code.get(inst_ptr);
      long oprand = code.get(inst_ptr+1);
      BigInteger val = null;
      if (oprand < 4) val = BigInteger.valueOf(oprand);
      else if (oprand == 4) val = reg_a;
      else if (oprand == 5) val = reg_b;
      else if (oprand == 6) val = reg_c;
      else if (oprand == 7) E.er();
      else
      {
        E.er();
      }
      
      inst_ptr+=2;

      if (op_code==0)
      {
        reg_a = mult(reg_a,val);

      }
      else if (op_code==1)
      {
        reg_b = reg_b.xor(BigInteger.valueOf(oprand));
      }
      else if (op_code==2)
      {
        reg_b = val.mod(BigInteger.valueOf(8L));
      }
      else if (op_code==3)
      {
        if(!reg_a.equals(BigInteger.ZERO))
        {
          inst_ptr=(int)oprand;
        }
      }
      else if (op_code==4)
      {
        reg_b = reg_b.xor(reg_c);

      }
      else if (op_code==5)
      {
        long o = val.mod(BigInteger.valueOf(8L)).longValueExact();
        output.add(o);
        int idx = output.size()-1;
        if (code_match)
        {
          if (output.size() > code.size()) return;
          long y1 = output.get(idx);
          long y2= code.get(idx);

          if (y1 != y2) return;
        }
      }
      else if (op_code==6)
      {
        reg_b = mult(reg_a,val);
      }
      else if (op_code==7)
      {
        reg_c = mult(reg_a,val);
      }
      else
      {
        E.er();
      }

    }


  }


}
