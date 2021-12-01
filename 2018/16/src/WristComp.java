
import java.util.*;

public class WristComp
{
    int r[]=new int[4];

    ArrayList<Op> op_list = new ArrayList<>();

    public WristComp()
    {
      op_list.add(new Addr());
      op_list.add(new Addi());
      op_list.add(new Mulr());
      op_list.add(new Muli());
      op_list.add(new Banr());
      op_list.add(new Bani());
      op_list.add(new Borr());
      op_list.add(new Bori());
      op_list.add(new Setr());
      op_list.add(new Seti());
      op_list.add(new Gtir());
      op_list.add(new Gtri());
      op_list.add(new Gtrr());
      op_list.add(new Eqir());
      op_list.add(new Eqri());
      op_list.add(new Eqrr());
    }
    public WristComp(int regs[])
    {
      this();
      for(int i=0; i<4; i++) r[i]=regs[i];
    }

    public interface Op
    {
      public int doOp(int a, int b) throws RegBoundFail;
    }


    public class Addr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) + getR(b);
      }
    }

    public class Addi implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) + b;
      }
    }

    public class Mulr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) * getR(b);
      }
    }

    public class Muli implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) * b;
      }
    }
    public class Banr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) & getR(b);
      }
    }

    public class Bani implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) & b;
      }
    }

    public class Borr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) | getR(b);
      }
    }

    public class Bori implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a) | b;
      }
    }

    public class Setr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return getR(a);
      }
    }

    public class Seti implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        return a;
      }
    }

    public class Gtir implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (a > getR(b)) return 1;
        return 0;
      }
    }
    public class Gtri implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (getR(a) > b) return 1;
        return 0;
      }
    }
    public class Gtrr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (getR(a) > getR(b)) return 1;
        return 0;
      }
    }


    public class Eqir implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (a == getR(b)) return 1;
        return 0;
      }
    }
    public class Eqri implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (getR(a) == b) return 1;
        return 0;
      }
    }
    public class Eqrr implements Op
    {
      public int doOp(int a, int b) throws RegBoundFail
      {
        if (getR(a) == getR(b)) return 1;
        return 0;
      }
    }









    public int getR(int e) throws RegBoundFail
    {
      if (e < 0) throw new RegBoundFail();
      if (e > 3) throw new RegBoundFail();
      return r[e];
    }

    public class RegBoundFail extends Exception
    {
    
    }


}
