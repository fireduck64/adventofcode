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
  Map2D<Character> map=new Map2D<Character>('.');
  final String line;
  int total_grid;

  public Prob(Scanner scan)
    throws Exception
  {
    line = scan.nextLine();
    { // part 1
      IntComp comp = new IntComp(line);
      comp.exec();

      long x=0;
      long y=0;
      for(long v : comp.output)
      {
        if (v==10L) {x=0; y--;}
        else
        {
          map.set(x,y, (char)v);
          x++;
        }
      }
      comp.output.clear();

      map.print();
      long p1 = 0;
      for(Point p : getIntersections())
      {
        
        long v = p.x * Math.abs(p.y);
        p1+=v;
      }
      System.out.println("Part 1: " + p1);
    }
    /*{ // sample map
      map=new Map2D<Character>('.');
      MapLoad.loadMap(map, new Scanner(new FileInputStream("sample.map")));

      System.out.println(findPath());
      System.out.println(explodePath(findPath()));

    }*/

    {
      long n = map.getCounts().get('#');
      total_grid = (int)n;
      Point pos = null;
      Point dir = null;
      for(Point p : map.getAllPoints())
      {
        char z = map.get(p);
        if (z=='^')
        {
          pos = p;
          dir = new Point(0,-1);
        }
        if (z=='v')
        {
          pos = p;
          dir = new Point(0,1);
        }
        if (z=='<')
        {
          pos=p;
          dir = new Point(-1,0);
        }
        if (z=='>')
        {
          pos=p;
          dir = new Point(1,0);
        }
      }

      //Search.search(new SS(ImmutableList.of("B,C,A,A,A,A,A,A,C,C", "9", "5,R,36,L", "1"))); 
      //Search.search(new SS(ImmutableList.of("","","",""))); 
      //Search.searchPara(new SS(ImmutableList.of("","","",""))); 
      //System.out.println(best_score);
      System.out.println(findPath());
      System.out.println(conv(reduce(findPath())));
      System.out.println(conv(reduce(findPath())).length());

      List<List<String> > explode = explodePath(findPath());

      System.out.println(explode);
      for(int i=0; i<4; i++)
      {
        explode.set(i, reduce(explode.get(i)));
      }
      System.out.println(explode);
      long dust = getDust(explode);
      System.out.println("Part 2: " + dust);

      /*System.out.println(explodePath(ImmutableList.of("L")));

      System.out.println("Starting: " + pos + " " + dir);
      
      long total_dust=0;
      for(Point p : map.getAllPoints())
      {
        if (map.get(p)=='#')
        {
          SSPath ss = (SSPath) Search.searchPara(new SSPath(ImmutableList.of(), pos, dir, ImmutableSet.of(), p));
          long dust = getDust(explodePath(ss.path));

          List<String> path_minus = minusPath(ss.path);
          long dust2 = getDust(explodePath(path_minus));
          long d = dust - dust2;
          System.out.println("Dust: " + d + " at " + p);
          total_dust+=d;

        }
        

      }
      System.out.println("Part 2: " + total_dust);*/
    }

  }


  public List<String> minusPath(List<String> path)
  {
    ArrayList<String> out = new ArrayList<String>();
    out.addAll(path);
    int idx = path.size()-1;
    if (isNumber(out.get(idx)))
    {
      int v = Integer.parseInt(out.get(idx));
      if (v==1) out.remove(idx);
      else
      {
        v--;
        out.set(idx, "" + v);
      }

    }
    return out;

  }

  public List<List<String>> explodePath(List<String> path)
  {
    //System.out.println("checking explode on: " + conv(path));
    Collection<List<String> > all = allSubs(path);
    System.out.println("Subs: " + all.size());
    for(List<String> lst : all)
    {
      //System.out.println("  " + conv(lst));
    }
    ArrayList<List<String>> progs = new ArrayList<>();
    for(int i=0; i<4; i++) progs.add(null);

    ArrayList<List<String> > all_lst = new ArrayList<>();
    all_lst.addAll(all);

    for(int i=0; i<all_lst.size(); i++)
    {
      System.out.print('.');
      progs.set(1,all_lst.get(i));
      for(int j=i+1; j<all_lst.size(); j++)
      {
        progs.set(2,all_lst.get(j));
        for(int k=j+1; k<all_lst.size(); k++)
        {
          progs.set(3,all_lst.get(k));
          List<String> main = check(ImmutableList.of(), path, progs);
          if (main != null)
          {
            progs.set(0, main);
            return progs;
          }
     
        }
      }
    }

    System.out.println("Unable to explode path for: " + conv(path)); 
    return null;

  }
  public String conv(List<String> p)
  {
    StringBuilder sb = new StringBuilder();
    boolean first=true;
    for(String s : p)
    {
      if (!first) sb.append(',');
      sb.append(s);
      first=false;
    }
    return sb.toString();
  }
  public List<String> reduce(List<String> p)
  {
    ArrayList<String> out = new ArrayList<>();
    int cnt=0;
    for(String s : p)
    {
      if (isNumber(s))
      {
        cnt+=Integer.parseInt(s);
      }
      else
      {
        if (cnt > 0) out.add("" + cnt);
        cnt=0;
        out.add(s);
      }

    }
    if (cnt > 0) out.add("" + cnt);
    return out;
  }

  public List<String> check(List<String> main, List<String> remaining, List<List<String>> progs)
  {
    //System.out.println("main: " + main + " rem: " + remaining + " prog:" + progs);
    if (conv(main).length() > 20) return null;
    if (remaining.size() == 0) return main;

    for(int i=1; i<4; i++)
    {
      List<String> p = progs.get(i);
      if (p==null) return null;
      int len = p.size();
      if (remaining.size() >= len)
      if (remaining.subList(0, len).equals(p))
      {
        char letter = 'A';
        if (i==2) letter = 'B';
        if (i==3) letter = 'C';
        ArrayList<String> new_main = new ArrayList<>();
        new_main.addAll(main);
        new_main.add("" + letter);

        List<String> main_sub = check(new_main, remaining.subList(len, remaining.size()), progs);
        if (main_sub != null) return main_sub;
      }
    }

    return null;


  }

  public Collection<List<String> > allSubs(List<String> path)
  {
    ArrayList<String> z = new ArrayList<>();
    z.addAll(path);

    HashSet<List<String>> all = new HashSet<>();
    for(int i=0; i<path.size(); i++)
    {
      for(int j=i; j<path.size(); j++)
      {
        List<String> sub = z.subList(i,j+1);
        if (conv(reduce(sub)).length() <= 20)
        {
          all.add(sub);
        }
      }
    }
    return all;

  }


  public class SSPath extends State
  {
    List<String> path;
    Point pos;
    Point dir;
    Set<Point> visited;
		Point dest;

    public SSPath(List<String> path, Point pos, Point dir, Set<Point> visited, Point dest)
    {
      this.path = ImmutableList.copyOf(path);
      this.pos = pos;
      this.dir = dir;
      this.visited = ImmutableSet.copyOf(visited);
      this.dest = dest;

    }

    public String toString()
    {
      return path.toString(); 
      //return "" + pos + " " + dir + " " + visited;
    }

  	public String getHash()
  	{
    	return HUtil.getHash("" + pos + " " + dir + " " + visited);
  	}

    public double getCost(){ return conv(path).length();}
    public double getEstimate() 
    {
      double v = visited.size();
      double rem = total_grid - v;
      return rem / 50.0;

    }
    public boolean isOk()
    {
      if (conv(path).length() > 200) return false;
      return true;
    }
    public boolean isTerm()
    {
      //if (visited.size() < total_grid) return false;
      if (!pos.equals(dest)) return false;
      if (explodePath(path) == null) return false;
      return true;
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<State>();
      if (!isOk()) return lst;

      // straight case
      if ((path.size() ==0) || (!isNumber(path.get(path.size()-1))))
      {
        int step = 1;
        while(map.get(pos.add(dir.mult(step)))=='#')
        {
          int len = step;

          LinkedList<String> new_path = new LinkedList<>();
          new_path.addAll(path);
          new_path.add("" + len);
          HashSet<Point> v = new HashSet<>();
          v.addAll(visited);
          for(int i=1; i<= len; i++)
          {
            v.add(pos.add(dir.mult(i)));
          }
          SSPath nx = new SSPath(new_path, pos.add(dir.mult(len)), dir, v, dest);
          lst.add(nx);
          
          step++;

        }
      }
      { // right
        Point dir_r = Nav.turnRight(dir);
        if (map.get(pos.add(dir_r)) == '#')
        {
          LinkedList<String> new_path = new LinkedList<>();
          new_path.addAll(path);
          new_path.add("R");
          SSPath nx = new SSPath(new_path, pos, dir_r, visited, dest);
          lst.add(nx);
        }
      }
      { // left
        Point dir_l = Nav.turnLeft(dir);
        if (map.get(pos.add(dir_l)) == '#')
        {
          LinkedList<String> new_path = new LinkedList<>();
          new_path.addAll(path);
          new_path.add("L");
          SSPath nx = new SSPath(new_path, pos, dir_l, visited, dest);
          lst.add(nx);
        }
      }


      return lst;

    }

  }

  public boolean isNumber(String v)
  {
    try
    {
      Integer.parseInt(v);
      return true;

    }
    catch(NumberFormatException e)
    {
      return false;
    }

  }

  public List<String> findPath()
  {
    Point pos = null;
    Point dir = null;
    for(Point p : map.getAllPoints())
    {
      char z = map.get(p);
      if (z=='^')
      {
        pos = p;
        dir = new Point(0,-1);
      }
      if (z=='v')
      {
        pos = p;
        dir = new Point(0,1);
      }
      if (z=='<')
      {
        pos=p;
        dir = new Point(-1,0);
      }
      if (z=='>')
      {
        pos=p;
        dir = new Point(1,0);
      }
    }
    LinkedList<String> path=new LinkedList<>();
    int len=0;
    int total=0;
    while(true)
    {
      if (map.get(pos.add(dir))=='#')
      {
        len++;
        pos = pos.add(dir);
        if (len ==2 )
        {
          path.add("2");
          total+=len;
          len=0;
        }
      }
      else
      { // end of road
        if (len > 0)
        {
          total+=len;
          path.add(""+len);
          len=0;
        }
        Point dir_r = Nav.turnRight(dir);
        Point dir_l = Nav.turnLeft(dir);
        if (map.get(pos.add(dir_r)) == '#')
        {
          path.add("R");
          dir = dir_r;
        }
        else if (map.get(pos.add(dir_l)) == '#')
        {
          path.add("L");
          dir = dir_l;
        }
        else
        {
          System.out.println("total path: " + total);
          return path;
        }
      }
    }


  }

  public long getDust(List<List<String>> progs)
  {
    IntComp comp = new IntComp(line);
    comp.code.set(0,2L);
    for(List<String> s : progs)
    {
      comp.writeAscii(conv(s));
    }
    comp.writeAscii("y");
    try
    {
      comp.exec();
    }
    catch(Throwable e)
    {
      System.out.println("Exception on this input: " + progs);
    }


    for(long v : comp.output)
    {
      if (v > 256L)
      {
        return v;
      }
    }
    for(List<String> s : progs)
    {
      System.out.println(conv(s));
    }

    System.out.println(comp.readOutput());
    return 0L;

  }

  Object best_lock = new Object();
  volatile long best_score = 0L;

  public class SS extends State
  {

    public List<String> progs;
    public long score;

    public SS(List<String> progs)
    {
      this.progs = progs;
      if (!okToRun()) return;

      IntComp comp = new IntComp(line);
      comp.code.set(0,2L);
      for(String s : progs)
      {
        comp.writeAscii(s);
      }
      comp.writeAscii("n");
      try
      {
      comp.exec();
      }
      catch(Throwable e)
      {
        //System.out.println("Exception on this input: " + progs);
      }


      for(long v : comp.output)
      {
        if (v > 256L)
        {
          score = v;
          if (score > best_score)
          synchronized(best_lock)
          {
            if (score > best_score)
            {
              best_score = Math.max(best_score, score);
              System.out.println("score: " + v + " " + progs);
            }
          }
        }
      }
      //System.out.println(toString() + " / " + score);

    }
    public SS add(int idx, String add)
    {
      ArrayList<String> new_p = new ArrayList<>();
      new_p.addAll(progs);

      String str = new_p.get(idx);
      if (str.length() > 0) 
      {
        str = str + ",";
        try
        {
          List<String> lst = Tok.en(str, ",");
          Integer.parseInt(add);
          Integer.parseInt(lst.get(lst.size()-1));
          return null;

        }
        catch(NumberFormatException e)
        {}
      }
      str = str + add;
      if (str.length() > 20) return null;
      new_p.set(idx, str);

      return new SS(new_p);

    }

    public double getCost()
    {
      int n = 0;
      for(String s : progs)
      {
        n+=s.length();
      }
      return n;
    }
    @Override
    public double getEstimate()
    {
      return 1e9 - score;
    }
    public String toString()
    {
      return progs.toString();
    }
    public boolean isTerm()
    {
      return false;
    }
    public int firstEmpty()
    {
      for(int i=0; i<4; i++)
      {
        if (progs.get(i).length() ==0) return i;
      }
      return -1;

    }
    public boolean okToRun()
    {
      if (firstEmpty() >= 0) return false;
      if (!isOk()) return false;
      for(String p : progs)
      {
        try
        {
          Integer.parseInt(p);
          return false;

        }
        catch(NumberFormatException e)
        {}

      }

      return true;
    }
    public boolean isOk()
    {
      if (firstEmpty() >= 0) return true;
      for(String p : progs)
      for(String b : getBadList())
      {
        if (p.contains(b)) return false;
      }
      if (score >= 0L) return true;
      return false;
      //return true;
    }
    public List<String> getBadList()
    {
      return ImmutableList.of("R,L","L,R","R,R,R","L,L,L");
    }
    public List<State> next()
    {
      LinkedList<State> lst = new LinkedList<>();
     
      if (!isOk()) return lst;
      int fe = firstEmpty();

      for(int i=0; i<4; i++)
      {
        if ((fe < 0) || (i == fe))
        {

          String p = progs.get(i);
          if (i == 0)
          {
            for(String f : allFunctions())
            {
              SS n = add(i, f);
              if (n != null)
              if (n.isOk())
                lst.add(n);
            }
          }
          else
          {
            for(String m : allMoves())
            {
              SS n = add(i, m);
              if (n != null)
              if (n.isOk())
                lst.add(n);
            }
          }
        }
      }

      return lst;


    }

  }

  public List<String> allFunctions()
  {
    return ImmutableList.of("A","B", "C");
  }
  public List<String> allMoves()
  {
    LinkedList<String> all = new LinkedList<>();
    for(int len = 1; len<40; len++)
    {
      all.add("" + len);
    }
    for(String d : ImmutableList.of("R", "L"))
    {
      all.add(d);
    }
    return all;

  }

  public List<Point> getIntersections()
  {
    ArrayList<Point> inter=new ArrayList<>();
    for(Point p : map.getAllPoints())
    {
      if (map.get(p)=='#')
      {
        int cnt=0;
        for(Point q : map.getAdj(p, false))
        {
          if (map.get(q)=='#') cnt++;
        }
        if (cnt == 4)
        {
          inter.add(p);
          //map.set(p, 'O');
        }

      }


    }
    return inter;

  }

}
