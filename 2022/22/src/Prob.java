import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import duckutil.Pair;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  Map2D<Character> map = new Map2D<Character>(' ');

  // Maps from point and direction to next point and direction
  TreeMap<Pair<Point, Point>, Pair<Point, Point>> seams=new TreeMap<>();

  boolean part2=false;
  int cube_size=50;

  public Point getCubeCorner(int c)
  {
    if (c==1) return new Point(cube_size*1, 0);
    if (c==2) return new Point(cube_size*2, 0);
    if (c==3) return new Point(cube_size*1, cube_size*1);
    if (c==4) return new Point(0, cube_size*2);
    if (c==5) return new Point(cube_size*1, cube_size*2);
    if (c==6) return new Point(0, cube_size*3);

    E.er();
    return null;

  }
  public Point getEdgeStart(Point edge)
  {
    if (edge.x== 1) return new Point(cube_size-1, 0);
    if (edge.x==-1) return new Point(0, cube_size-1);
    if (edge.y== 1) return new Point(cube_size-1, cube_size-1);
    if (edge.y==-1) return new Point(0, 0);

    E.er();
    return null;

  }

  public void writeSeam(int side_a, Point edge_a, int side_b, Point edge_b, boolean rev)
  {
    Point a_dir = Nav.turnRight(edge_a);
    Point b_dir = Nav.turnRight(edge_b);
    Point a_c = getCubeCorner(side_a);
    Point b_c = getCubeCorner(side_b);

    Point a_s = a_c.add(getEdgeStart(edge_a));
    Point b_s = b_c.add(getEdgeStart(edge_b));
    //System.out.println("write seam: " + side_a + " " + side_b);

    for(int i=0; i<cube_size; i++)
    {
      Point a = a_s.add(a_dir.mult(i));
      Point b = b_s.add(b_dir.mult(i));
      if (!rev)
      {
        b = b_s.add(b_dir.mult(cube_size - 1 - i));
      }

      //System.out.println("Seam: " + a + " to " + b);
      seams.put(new Pair<Point, Point>(a, edge_a), new Pair<Point, Point>(b, Nav.rev(edge_b)));
      seams.put(new Pair<Point, Point>(b, edge_b), new Pair<Point, Point>(a, Nav.rev(edge_a)));

    }


  }

  public void edgeDancer()
  {
    Point north=new Point(0,-1);
    Point south = new Point(0, 1);
    Point west=new Point(-1,0);
    Point east=new Point(1,0);


    
    /*writeSeam(1, north, 6, west, false);
    writeSeam(1, west, 4, west, true);
    writeSeam(2, north, 6, south, false);
    writeSeam(2, east, 5, east, true); 
    writeSeam(2, south, 3, east, false);
    writeSeam(3, west, 4, north, false);
    writeSeam(5, south, 6, east, false);*/
    

    writeSeam(1, north, 6, west, false);
    writeSeam(1, west, 4, west, false);
    writeSeam(2, north, 6, south, false);
    writeSeam(2, east, 5, east, false); 
    writeSeam(2, south, 3, east, false);
    writeSeam(3, west, 4, north, false);
    writeSeam(5, south, 6, east, false);

  }

  public Prob(Scanner scan)
  {
    List<String> lines = In.lines(scan);

    String moves = lines.get(lines.size()-1);
    lines.remove(lines.size()-1);

    MapLoad.loadMap( map, In.newScan(lines));

    LinkedList<String> mlst = Tok.smart(moves);



    int p1 = nav(Tok.smart(moves));
    System.out.println("Part 1: " + p1);

    part2=true;
    edgeDancer();
    checkConn();
    
    int p2 = nav(Tok.smart(moves));
    System.out.println("Part 2: " + p2);

  }

  public boolean checkCorner(Point start)
  {
    Point dir = new Point(0, -1); // north
    Point cur = start;

    for(int i=0; i<3; i++)
    {
      for(int j=0; j<5; j++)
      {
        Pair<Point, Point> pp = getNext(cur, dir);

      }
      dir = Nav.turnLeft(dir);

    }
    if (cur.equals(start)) return true;
    return false;
    

  }

  public void checkConn()
  {
    int pass=0;
    int miss=0;
    for(Point p : map.getAllPoints())
    {
      if (map.get(p) != ' ')
      {
      
      if ((p.x % cube_size==2) && (p.y % cube_size==2))
      {
        if (checkCorner(p))
        {
          //pass++;
        }
        else
        {
          miss++;
        }
      }


        Point dir = new Point(1,0);
        for(int i=0; i<4; i++)
        {
          Pair<Point,Point> n = getNext(p, dir);
          if (n==null)
          {
            System.out.println("Miss walk null: " + p + " " + dir);
          }
          Pair<Point, Point> r = getNext(n.getA(), Nav.turnRight(Nav.turnRight(n.getB())));

          if (!p.equals(r.getA()))
          {
            System.out.println("Miss walk from " + p + " to " + dir + " " + n + " " + r);
            miss++;

          }
          else
          {
            pass++;
          }

          {
            Point w_dir=dir;
            Point w_cur = p;
            for(int j=0; j<4*cube_size; j++)
            {
              Pair<Point, Point> w = getNext(w_cur, w_dir);
              w_dir = w.getB();
              w_cur = w.getA();
            }
            if (!w_cur.equals(p))
            {
              miss++;
              System.out.println("Crab walk from " + p + " in " + dir + " ended up at " + w_cur + " " + w_dir);

            }
            else
            {
              pass++;
            }

          }

          dir = Nav.turnRight(dir);
        }
      }

    }
    System.out.println("Pass: " + pass + " miss: " + miss);

  }

  

  public int nav(LinkedList<String> mlst)
  {
    Point dir = new Point(1,0); // right

    Point cur = findStart();

    while(mlst.size() > 0)
    {
      String act = mlst.pollFirst();
      if (act.equals("R")) dir = Nav.turnRight(dir);
      else if (act.equals("L")) dir = Nav.turnLeft(dir);
      else
      {
        int steps = Integer.parseInt(act);
        for(int s = 0; s<steps; s++)
        {
          Pair<Point,Point> n = getNext(cur, dir);

          Point np = n.getA();
          Point nd = n.getB();

          if ((map.get(np) =='.') || (map.get(np) =='X')) 
          {
            //map.set(cur, 'X');
            cur=np;
            dir=nd;
          }
          else if (map.get(np) =='#')
          {
            break;
          }
          else
          {
            E.er("" + map.get(np));
          }
        }

      }
    }

    long score = 1000 * (cur.y + 1) + 4*(cur.x+1);
    if (dir.x==1) score+=0;
    if (dir.x==-1) score+=2;
    if (dir.y==1) score+=1;
    if (dir.y==-1) score+=3;
    //map.rotateL().print();
    return (int)score;

  }
  public Point findStart()
  {
    int i=0;
    while(map.get(i,0) != '.')
    {
      i++;
      if (i > 5000) E.er();
    }

    return new Point(i,0);
  }

  public Pair<Point,Point> getNext(Point cur, Point dir)
  {
    Point n = cur.add(dir);
    if (map.get(n) != ' ') return new Pair<Point, Point>(n, dir);
    // off the wall

    Point search = null;

    // We will just do this and then correct below if needed for p2
    {
    
      // went off to right
      if (dir.x > 0) search = new Point(0, cur.y);
      // went off to left
      if (dir.x < 0) search = new Point(5000, cur.y);
      // went off south
      if (dir.y > 0) search = new Point(cur.x, 0);
      if (dir.y < 0) search = new Point(cur.x, 5000);

    }
    if (part2)
    {
      return seams.get(new Pair<Point, Point>(cur, dir));

      /**
       * Carefully matching seams as in the sample.  Lots of errors checked and fixed with the checkConn().
       * And then the input uses a different input share.  Ha.
       */
      
      /*int cube_size=4;
      if (map.high_y>100) cube_size=50;

      long cube_id=0;
      if (cube_size==4)
      {
      if (cur.y<cube_size) cube_id=1;
      else if (cur.y<cube_size*2)
      { //234
        cube_id = (cur.x / cube_size) + 2;
      }
      else
      { // 56
        cube_id = (cur.x / cube_size) + 3;
      }

      long px = cur.x % cube_size;
      long py = cur.y % cube_size;
      //System.out.println("cube: " + cube_id + " " + dir);

      if (cube_id == 1)
      {
        if (dir.x == 1) { search = new Point(1000, cube_size*3 -1 - py); dir=new Point(-1,0);} 
        else if (dir.y == -1) {search = new Point(cube_size - 1 - px, 0); dir=new Point(0, 1);}
        else if (dir.x == -1) { search = new Point(cube_size + py, 0); dir=new Point(0,1);}
      }
      if (cube_id == 2)
      {
        if (dir.x == -1) { search = new Point(cube_size*4-1-py, 1000); dir=new Point(0,-1); }
        else if (dir.y == -1) { search = new Point(cube_size*3 -1 -px,0); dir=new Point(0,1);}
        else if (dir.y == 1) {search = new Point(cube_size*3 -1 -px,1000); dir=new Point(0,-1);}
      }
      if (cube_id == 3)
      {
        if (dir.y == -1) { search = new Point(0,px); dir=new Point(1,0);}
        else if (dir.y == 1) { search = new Point(0, cube_size*3-1-px); dir=new Point(1,0);}
      }
      if (cube_id == 4)
      {
        if (dir.x == 1) { search = new Point(cube_size*4-1-py, 0); dir=new Point(0,1);}
      }
      if (cube_id == 5)
      {
        if (dir.x == -1) { search = new Point(cube_size*2-1-py, 1000); dir=new Point(0,-1);}
        else if (dir.y == 1) { search = new Point(cube_size-1-px, 1000); dir=new Point(0, -1);}
      }
      if (cube_id == 6)
      {
        if (dir.x == 1) { search = new Point(1000, cube_size-1-py); dir=new Point(-1,0);}
        else if (dir.y == 1) { search = new Point(0, cube_size*2-1-px); dir=new Point(1,0);}
        else if (dir.y == -1) { search = new Point(1000, cube_size*2-1-px); dir=new Point(-1,0);}
      }
      //System.out.println("cube: " + cube_id + " " + dir);
      */
      
      

    }


    while (map.get(search) == ' ')
    {
      if (search.x > 10000) E.er(search.toString() + " " + cur + " " + dir);
      if (search.y > 10000) E.er(search.toString() + " " + cur + " " + dir);
      search=search.add(dir);
    }

    return new Pair<Point, Point>(search,dir);


  }

}
