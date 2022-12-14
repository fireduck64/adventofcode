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
    System.out.println("Part 1: " + p1);

    process(idx+1, "[[2]]", "[[6]]");

    Element e1=train.get(train.size()-1);
    Element e2=train.get(train.size()-2);

    Collections.sort(train);

    int p2 = 1;
    for(int i=0; i<train.size(); i++)
    {
      if (train.get(i) == e1) p2*=(i+1);
      if (train.get(i) == e2) p2*=(i+1);
      //System.out.println(train.get(i));

    }
    System.out.println("Part 2: " + p2);

    Tok.demo();
    

  }
  int p1=0;

  public void process(int idx, String a, String b)
    throws Exception
  {
    Element ea = new Element(Tok.smart(a));
    Element eb = new Element(Tok.smart(b));

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
    public Element(LinkedList<String> in)
    {
      String n = in.poll();
      if (n.equals("["))
      {  // read list
        list = new ArrayList<>();

        if (!in.peek().equals("]"))
          list.add(new Element(in));

        while(in.poll().equals(","))
        {
          list.add(new Element(in));
        }
      }
      else 
      {
        num = Integer.parseInt(n);
      }

    }

    public String toString()
    {
      if (isNumber()) return "" + num;
      return list.toString();
    }

  }

  
  

}
