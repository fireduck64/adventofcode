import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{
  Random rnd=new Random();

  public String Part1(Scanner scan)
    throws Exception
  {
    String line = scan.nextLine();
    //System.out.println(line);

    LinkedList<Integer> lst = expand1(line);

    long sum = 0;
    long pos = 0;

    while(lst.size() > 0)
    {
      int v = lst.pollFirst();

      if (v < 0)
      {
        while (true)
        {
          if (lst.size() == 0) break;
          v = lst.pollLast();
          if (v >= 0) break;
        }
      }
      sum += v * pos;
      pos++;

    }


		return ""+sum;
  }

  public LinkedList<Integer> expand1(String in)
  {
    StringBuilder sb = new StringBuilder();
    LinkedList<Integer> lst = new LinkedList<>();

    for(int i=0; i<in.length(); i++)
    {
      int val = Integer.parseInt("" + in.charAt(i));

      if (i % 2 == 0)
      {
        int file_id = i/2;
        for(int j =0; j<val; j++)
        {
          lst.add(file_id);
        }
      }
      else
      {
        for(int j =0; j<val; j++)
        {
          lst.add(-1);
        }

      }
    }
    return lst;

  }

 
  public TreeMap<Integer, Chunk> expand(String in)
  {
    StringBuilder sb = new StringBuilder();
    TreeMap<Integer, Chunk> chunk_map = new TreeMap<>();

    int last_wrote = 0;

    for(int i=0; i<in.length(); i++)
    {
      int val = Integer.parseInt("" + in.charAt(i));

      if (i % 2 == 0)
      {
        int file_id = i/2;
        Chunk c= new Chunk(last_wrote, file_id, val);
        chunk_map.put(last_wrote, c);
        last_wrote += c.length;
      }
      else
      {
        Chunk c= new Chunk(last_wrote, val);
        chunk_map.put(last_wrote, c);
        last_wrote += c.length;
      }
    }
    return chunk_map;

  }
  public String Part2B(Scanner scan)
    throws Exception
  {
    String line = scan.nextLine();
    //System.out.println(line);

    ArrayList<Integer> lst = new ArrayList<>();

    lst.addAll(expand1(line));

    long sum = 0;
    TreeSet<Integer> done_files=new TreeSet<>();

    for(int pos = lst.size()-1; pos>=0; pos--)
    {
      int file_id = lst.get(pos);
      if (file_id > 0)
      if (!done_files.contains(file_id))
      {
        int start_pos = findStart(lst, pos, file_id);

        int sz = pos +1 - start_pos;
        int emp = findEmpty(lst, sz, start_pos);
        if (emp >= 0)
        {
          //System.out.println("Moving " + file_id + " to " + emp);
          for(int i=0; i<sz; i++)
          {
            lst.set( start_pos + i, -1);
            lst.set(emp+i, file_id);
          }
        }
        done_files.add(file_id);
      }

    }
    for(int pos = 0; pos<lst.size(); pos++)
    {
      long v = lst.get(pos);
      if (v >= 0) sum+=pos*v;

    }
  
    return "" + sum;
  }

  public int findEmpty(ArrayList<Integer> lst, int sz, int max_pos)
  {
    int e_start = -1;
    for(int pos =0; pos<max_pos; pos++)
    {
      if (lst.get(pos) == -1)
      {
        if (e_start < 0) e_start=pos;

        if (pos+1 - e_start >= sz) return e_start;
      }
      else
      {
        e_start=-1;
      }
    }

    return -1;

  }

  public int findStart(ArrayList<Integer> lst, int pos, int file_id)
  {
    while(true)
    {
      if (pos == 0) return pos;
      if (lst.get(pos-1) == file_id)
      {
        pos--;
      }
      else
      {
        return pos;
      }
    }

  }


  public String Part2(Scanner scan)
    throws Exception
  {
    String line = scan.nextLine();

    TreeMap<Integer, Chunk> chunk_map = expand(line);


    TreeMap<Integer, Chunk> files =new TreeMap<>();
    TreeMap<Integer, Chunk> free = new TreeMap<>();

    for(int pos : chunk_map.keySet())
    {
      Chunk c=  chunk_map.get(pos);
      if (c.free)
      {
        free.put(pos, c);
      }
      else
      {
        files.put(c.file_id, c); 
      }

    }
    LinkedList<Integer> file_list = new LinkedList<>();
    file_list.addAll(files.keySet());

    long sum = 0;
    while(file_list.size() > 0)
    {
      int file_id = file_list.pollLast();
      Chunk file_c = files.get(file_id);

      Chunk free_c_select = null;
      for(Chunk free_c : free.values())
      {
        if (free_c.length >= file_c.length)
        {
          free_c_select = free_c;
          break;
        }
      }
      if (free_c_select!=null)
      if (free_c_select.pos < file_c.pos)
      {
        int old_pos = file_c.pos;

        file_c.pos = free_c_select.pos;
        //System.out.println("Moving : " + file_c.file_id + " to " + file_c.pos);

        free.remove(free_c_select.pos);

        if (free_c_select.length > file_c.length)
        {
          int rem = free_c_select.length - file_c.length;

          free.put( file_c.pos + file_c.length, new Chunk(file_c.pos + file_c.length, rem));
        }
        free.put(old_pos, new Chunk(old_pos, file_c.length));
        join(free);
      }
      sum += file_c.getSum();

    }


		return "" + sum;
  }

  public void join(TreeMap<Integer, Chunk> free_map)
  {
      Chunk last = null;
      TreeSet<Integer> to_remove = new TreeSet<>();
      for(Chunk c : free_map.values())
      {
        if (!c.free) E.er();

        if ((last != null) && (last.pos + last.length == c.pos))
        {
          to_remove.add(c.pos);
          last.length += c.length;
        }
        else
        {
          last=c;
        }
      }

      for(int v : to_remove)
      {
        free_map.remove(v);
      }

  }

  public static void main(String args[]) throws Exception
  {
    for(String fn : args)
    {
		  new Prob(fn);
    }
    if (args.length == 0)
    {
      new Prob("sample");
      new Prob("input");
    }
  }

	public Prob(String input)
    throws Exception
	{
    if (new File(input).exists())
    {
      long t1 = System.currentTimeMillis();
      System.out.println("Part 1: " + input);
      System.out.println(Part1(new Scanner(new FileInputStream(input))));
      long t = System.currentTimeMillis() - t1;
      System.out.println("Time: " + t + " ms");
    }
    if (new File(input).exists())
    {
      long t1 = System.currentTimeMillis();
      System.out.println("Part 2: " + input);
      System.out.println(Part2(new Scanner(new FileInputStream(input))));
      long t = System.currentTimeMillis() - t1;
      System.out.println("Time: " + t + " ms");
    }    
    if (new File(input).exists())
    {
      long t1 = System.currentTimeMillis();
      System.out.println("Part 2B: " + input);
      System.out.println(Part2B(new Scanner(new FileInputStream(input))));
      long t = System.currentTimeMillis() - t1;
      System.out.println("Time: " + t + " ms");
    }    

  }

  public class Chunk
  {
    int pos;
    int file_id;
    boolean free;
    int length;

    public Chunk(int pos, int file_id, int length)
    {
      this.pos = pos;
      this.file_id = file_id;
      this.length = length;
      this.free=false;

    }
    public Chunk(int pos, int length)
    {
      this.pos = pos;
      this.file_id = -1;
      this.length = length;
      this.free=true;

    }
    public String toString()
    {
      return "C{" + free + "," + file_id + "," + length +"}";
    }
    public long getSum()
    {
      long sum = 0;
      if (file_id > 0)
      for(int i=0; i<length; i++)
      {
        long p = pos + i;
        sum += p * file_id;

      }

      return sum;

    }
  }

}
