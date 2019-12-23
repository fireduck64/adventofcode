import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  TreeMap<Integer, Character> print_map=new TreeMap<>();
  Map2D<Integer> map=new Map2D<Integer>(0);
  Random rnd = new Random();
  TreeMap<Double, State> queue = new TreeMap<Double, State>();
  LinkedList<ArrayList<String> > all_path = new LinkedList<>();
  IntComp compy;

  public Prob(Scanner scan)
  {
    print_map.put(35,'#');
    print_map.put(46,'.');
    for(int i=1; i<128; i++)
    {
      print_map.put(i, (char)i);

    }

    compy = new IntComp(scan.nextLine());
    compy.mem[0]=2;

    compy.execute();

    System.out.println("Term: " + compy.term);
    System.out.println(compy.output_queue);
    
    int y =0;
    int x =0;
    for(long vl : compy.output_queue)
    {
      int v = (int)vl;
      if (v == 10)
      {
        y++;
        x=0;
      }
      else
      {
        map.set(x,y,v);
        x++;
      }
    }
    compy.output_queue.clear();

    System.out.println(map.getPrintOut(print_map));

    long align_sum = 0;
    Map.Entry<Long, Long> bot = null;

    HashSet<String> scat_set = new HashSet<>();
    for(Map.Entry<Long,Long> p : map.getAllPoints())
    {
      if (isScat(p.getKey(), p.getValue()))
      {
        scat_set.add("" + p.getKey() +"," + p.getValue());
      }
      if (isInter(p.getKey(), p.getValue()))
      {
        align_sum += p.getKey() * p.getValue();

      }
      if (isBot(p.getKey(), p.getValue()))
      {
        bot = p;
      }
    }
    System.out.println("Alignment sum: " + align_sum);
    System.out.println("Bot: " + bot);

    /*ArrayList<String> path = new ArrayList<>();
    path.add("R");
    getDirectPath(bot.getKey(), bot.getValue(), 1L, 0L, path);
    System.out.println(path);
    System.out.println("Direct path len: " + path.size());

    splitPath(path);*/


    //doTests();

    State s = new State();
    s.x = bot.getKey();
    s.y = bot.getValue();
    s.dir_x = 1;
    s.dir_y = 0;
    s.path.add("R");
    s.scat_set.addAll(scat_set);

    queue.put(s.getCost(), s);

    boolean found = false;

    while(queue.size() >0)
    {
       s = queue.pollFirstEntry().getValue();
       s.expand();

       while (all_path.size() > 0)
       {
         ArrayList<String> p = all_path.poll();
         if (splitPath(p))
         {
           compy.addInputAsciiMode("y");
           compy.execute();
           compy.printOutputAscii();
           System.out.println(compy.term);
           return;
         }
       }
    }
    System.out.println("All paths: " + all_path.size());

  }

  public boolean splitPath(ArrayList<String> path)
  {

    for(int a=2; a<=20; a+=2)
    for(int a_off=0; a_off < path.size(); a_off+=2)
    if (commandOk(path, a_off, a) != null)
    for(int b=0; b<=20; b+=2)
    for(int b_off=a_off+a; b_off < path.size(); b_off+=2)
    if (commandOk(path, b_off, b) != null)
    for(int c=0; c<=20; c+=2)
    for(int c_off=b_off+b; c_off < path.size(); c_off+=2)
    if (commandOk(path, c_off, c) != null)
    {
      //int a_off = 0;
      //int b_off = a;
      //int c_off = b_off + b;
      String a_str = commandOk(path, a_off, a);
      String b_str = commandOk(path, b_off, b);
      String c_str = commandOk(path, c_off, c);
      if (a_str != null)
      if (b_str != null)
      if (c_str != null)
      {
        TreeMap<String, List<String>> cmd_map = new TreeMap<>();
        cmd_map.put("A", path.subList(a_off, a_off+a));
        if (b > 0)
        cmd_map.put("B", path.subList(b_off, b_off+b));
        if (c > 0)
        cmd_map.put("C", path.subList(c_off, c_off+c));

        //System.out.println("Trying commands: " + cmd_map + " " + a_str + " " + b_str + " " + c_str);
        
        if (checkPath(path, cmd_map, 0, ""))
        {
          System.out.println("A: " + a_str);
          System.out.println("B: " + b_str);
          System.out.println("C: " + c_str);
          compy.addInputAsciiMode(a_str);
          compy.addInputAsciiMode(b_str);
          compy.addInputAsciiMode(c_str);
          return true;
        }

      }
    }
    return false;
  }

  public boolean checkPath(ArrayList<String> path, Map<String, List<String> > commands, int offset, String cmd_str)
  {
    //System.out.println(cmd_str + " " + offset );
    if (cmd_str.length() > 21) return false;

    int remaining = path.size() - offset;
    if (remaining <= 0)
    {
      System.out.println("SUCCESS");

      System.out.println(cmd_str);
      compy.addInputAsciiMode(cmd_str);
      return true;
    }
    for(String cn : commands.keySet())
    {
      List<String> cmd_lst = commands.get(cn);

      int len = Math.min(remaining, cmd_lst.size());
      if (len == remaining) len--;

      //if (cmd_lst.size() <= remaining)
      {
        List<String> seg = path.subList(offset, offset + len);
        List<String> comp = cmd_lst.subList(0, len);
        if (seg.equals(comp))
        {
          String nc = cmd_str + "," + cn;
          if (cmd_str.length() == 0) nc = cn;

          if (checkPath(path, commands, offset+cmd_lst.size(), nc))
          {
            return true;
          }
        }

      }

    }

    return false;

  }
  public String commandOk(ArrayList<String> path, int start, int len)
  {
    if (start+len > path.size()) return null;
    if (len > 20) return null;
    if (len < 0) return null;

    StringBuilder sb = new StringBuilder();
    for(int i=0; i<len; i++)
    {
      if (i>0) sb.append(",");
      sb.append(path.get(start+i));

    }

    String s = sb.toString();
    if (s.length() > 20) return null;

    return s;
  }

  public class State
  {
    public long x;
    public long y;
    public long dir_x;
    public long dir_y;
    public ArrayList<String> path;
    public Set<String> scat_set;
    int count;

    public State()
    {
      path=new ArrayList<>();
      scat_set=new HashSet<>();
    }

    public State(State src)
    {
      x=src.x;
      y=src.y;
      dir_x=src.dir_x;
      dir_y=src.dir_y;
      count =src.count;
      path=new ArrayList<>(); path.addAll(src.path);
      scat_set=new HashSet<>(); scat_set.addAll(src.scat_set);
      
    }

    public double getCost()
    {
      return scat_set.size() * 1e6 + path.size() + rnd.nextDouble();    
    }
    public String toString()
    {
      return String.format("(%d + %d, %d + %d) - %s - rem: %d - %d", x,dir_x, y,dir_y, path, scat_set.size(), count);

    }

    public void expand()
    {

      //System.out.println(toString()); 
      

      // 20 chars = 10 command elements
      // 10 commands of things that are 10 commands each = 100
      if (path.size() > 100) return;
      if (scat_set.size() ==0)
      {

        //for(int i=0; i<10; i++)
        {
          int c = count;
          State n = new State(this);
          n.path.add(""+c);
          all_path.add(n.path);
          System.out.println("Found path: " + n.path.size() + " " + n.path);
        }
        return;
      }

      // continue
      if (isScat(x+dir_x, y+dir_y))
      {
        State n = new State(this);
        n.x = x+dir_x;
        n.y = y+dir_y;
        n.count++;
        n.markLoc();

        queue.put(n.getCost(), n);
      }

      if (count > 0)
      {
        if (Math.abs(dir_x)==1)
        {
          if (isScat(x,y-1))
          {
            State n = new State(this);
            n.path.add("" + count);
            if (dir_x==1) n.path.add("L");
            if (dir_x==-1) n.path.add("R");
            n.dir_x = 0;
            n.dir_y= -1;
            n.count = 0;
            queue.put(n.getCost(), n);
          }
          if (isScat(x,y+1))
          {
            State n = new State(this);
            n.path.add("" + count);
            if (dir_x==1)n.path.add("R");
            if (dir_x==-1)n.path.add("L");
            n.dir_x=0;
            n.dir_y=1;
            n.count=0;
            queue.put(n.getCost(), n);
          }
        }
        if (Math.abs(dir_y)==1)
        {
          if (isScat(x-1,y))
          {
            State n = new State(this);
            n.path.add("" + count);
            if (dir_y==1)n.path.add("R");
            if (dir_y==-1)n.path.add("L");
            n.dir_x=-1;
            n.dir_y=0;
            n.count=0;
            queue.put(n.getCost(), n);
          }
          if (isScat(x+1,y))
          {
            State n = new State(this);
            n.path.add("" + count);
            if (dir_y==1)n.path.add("L");
            if (dir_y==-1)n.path.add("R");
            getDirectPath(x,y,+1L,0L,path);
            n.dir_x=1;
            n.dir_y=0;
            n.count=0;
            queue.put(n.getCost(), n);
          }
        }
        }



    }

    private void markLoc()
    {
      scat_set.remove(""+ x+","+y);
    }

  }


  public void getDirectPath(long x, long y, long dir_x, long dir_y, ArrayList<String> path)
  {
    int count = 0;
    while(true)
    {
      x+=dir_x;
      y+=dir_y;
      count++;

      if (!isScat(x,y))
      {
        count--;
        x-=dir_x;
        y-=dir_y;
        break;
      }

    }
    path.add("" + count);

    if (Math.abs(dir_x)==1)
    {
      if (isScat(x,y-1))
      {
        if (dir_x==1)path.add("L");
        if (dir_x==-1)path.add("R");
        getDirectPath(x,y,0L,-1L,path);
        return;
      }
      if (isScat(x,y+1))
      {
        if (dir_x==1)path.add("R");
        if (dir_x==-1)path.add("L");
        getDirectPath(x,y,0L,1L,path);
        return;
      }
    }
    if (Math.abs(dir_y)==1)
    {
      if (isScat(x-1,y))
      {
        if (dir_y==1)path.add("L");
        if (dir_y==-1)path.add("R");
        getDirectPath(x,y,-1L,0L,path);
        return;
      }
      if (isScat(x+1,y))
      {
        if (dir_y==1)path.add("R");
        if (dir_y==-1)path.add("L");
        getDirectPath(x,y,+1L,0L,path);
        return;
      }
    }


  }

  public boolean isInter(long x, long y)
  {
    if (isScat(x,y))
    if (isScat(x-1,y))
    if (isScat(x+1,y))
    if (isScat(x,y+1))
    if (isScat(x,y-1))
    {
      return true;
    }

    return false;

    

  }
  public boolean isScat(long x, long y)
  {
    Integer v = map.get(x,y);
    if (v == null) return false;
    if (v == '#') return true;
    /*if (v == '>') return true;
    if (v == '<') return true;
    if (v == '^') return true;
    if (v == 'v') return true;*/

    return false;
  }

  public boolean isBot(long x, long y)
  {
    Integer v = map.get(x,y);
    if (v == null) return false;
    if (v == '>') return true;
    if (v == '<') return true;
    if (v == '^') return true;
    if (v == 'v') return true;

    return false;

  }



  public void doTests()
  {
    {
      ArrayList<String> test = new ArrayList<String>();
      for(int i=0; i<5; i++)
      {
        test.add("A");
        test.add("" + i);
      }
      for(int i=0; i<5; i++)
      {
        test.add("A");
        test.add("" + i);
      }
      for(int i=0; i<4; i++)
      {
        test.add("B");
        test.add("" + i);
      }
      for(int i=0; i<5; i++)
      {
        test.add("C");
        test.add("" + i);
      }
      for(int i=0; i<4; i++)
      {
        test.add("B");
        if (i==3)
        {
          test.add("" + 1);
        }
        else
        {
          test.add("" + i);
        }
      }

      System.out.println(test);
 
      if (!splitPath(test))
      {
        System.out.println("Failed test");
        System.exit(1);
        return;
      }
    }
    {
    
      ArrayList<String> test = new ArrayList<String>();
      String in = "R,8,R,8,R,4,R,4,R,8,L,6,L,2,R,4,R,4,R,8,R,8,R,8,L,6,L,2";
      StringTokenizer stok = new StringTokenizer(in, ",");
      while(stok.hasMoreTokens())
      {
        test.add(stok.nextToken());
      }
      if (!splitPath(test))
      {
        System.out.println("Failed test");
        System.exit(1);
        return;
      }



    }


  }
}
