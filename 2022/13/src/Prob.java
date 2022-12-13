import java.io.FileInputStream;
import java.util.*;
import java.io.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.nio.CharBuffer;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  ArrayList<Element> train=new ArrayList<>();

  public Prob(Scanner scan)
    throws Exception
  {
    int idx=0;
    while(scan.hasNextLine())
    {
      idx++;
      String a = scan.nextLine();
      String b = scan.nextLine();

      process(idx, a, b);

      if (scan.hasNextLine()) scan.nextLine();

    }
    System.out.println(p1);

    Element e1=train.get(0);
    Element e2=train.get(1);

    Collections.sort(train);

    int p2 = 1;
    for(int i=0; i<train.size(); i++)
    {
      if (train.get(i) == e1) p2*=(i+1);
      if (train.get(i) == e2) p2*=(i+1);
      System.out.println(train.get(i));

    }
    System.out.println(p2);
    

  }
  int p1=0;

  public void process(int idx, String a, String b)
    throws Exception
  {
    Element ea = new Element(CharBuffer.wrap(a));
    Element eb = new Element(CharBuffer.wrap(b));

    train.add(ea);
    train.add(eb);

    if (inOrder(ea, eb)>0) p1+=idx;

  }
  public int inOrder(Element a, Element b)
  {
    if (a.isNumber() && b.isNumber())
    {
      if (a.num < b.num) return 1;
      if (a.num > b.num) return -1;

      return 0;
    }
    if (a.isList() && b.isList())
    {
      int len = Math.min(a.list.size(), b.list.size());
      for(int i = 0; i<len; i++)
      {
        int v = inOrder(a.list.get(i), b.list.get(i));
        if (v != 0) return v;
      }
      if (a.list.size() < b.list.size()) return 1;
      if (a.list.size() > b.list.size()) return -1;

      return 0;
    }
    // one number, one list

    Element a2 = a;
    Element b2 = b;

    if (a.isNumber())
    {
      a2=new Element(a);
    }
    if (b.isNumber())
    {
      b2=new Element(b);
    }
    return inOrder(a2, b2);
  }

  public class Element implements Comparable<Element>
  {
    int num=-1;
    List<Element> list=null;

    public boolean isNumber(){return num>=0;}
    public boolean isList(){return list != null;}

    public Element(Element e)
    {
      list = new ArrayList<>();
      list.add(e);
    }
    public int compareTo(Element o)
    {
      return -inOrder(this, o);
    }
    public Element(CharBuffer in)
      throws Exception
    {
      char n = in.get();
      if (n=='[')
      {  // read list
        list = new ArrayList<>();

        try
        {
          list.add(new Element(in));
        }
        catch(IOException e)
        {
          //System.out.println("Empty list: " + e);
          return;

        }

        while(in.get()==',')
        {
          list.add(new Element(in));
        }


      }
      else if ((n>='0') && (n<='9'))
      {
        num = Integer.parseInt("" + n);
        while((in.charAt(0) >= '0') && (in.charAt(0) <='9')) 
        {
          num *= 10;
          num += Integer.parseInt("" + in.get());
        }
      }
      else if (n==']')
      {
        throw new IOException("End of list");
      }
      else
      {
        E.er("Unexpected " + n);
      }


    }

    public String toString()
    {
      if (isNumber()) return "" + num;
      return list.toString();
    }

  }

  
  

}
