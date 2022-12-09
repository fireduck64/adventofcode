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

  Point head = new Point(0,0);
  Point tail = new Point(0,0);
  TreeSet<Point> tail_set = new TreeSet<>();
  TreeSet<Point> tail_set2 = new TreeSet<>();

  Point snake[] = new Point[10];

  public Prob(Scanner scan)
  {
    for(int i=0; i<10; i++)
    {
      snake[i] = new Point(0,0);
    }

    while(scan.hasNext())
    {
      String dir = scan.next();
      int dist = scan.nextInt();

      Point delta = null;
      if (dir.equals("R")) delta = new Point(1,0);
      if (dir.equals("L")) delta = new Point(-1,0);
      if (dir.equals("U")) delta = new Point(0,1);
      if (dir.equals("D")) delta = new Point(0,-1);

      for(int i =0; i<dist; i++)
      {
        head = head.add(delta);
        snake[0] = snake[0].add(delta);

        boing();
        for(int z=0; z<9; z++)
        {
          boing2(z,z+1);
        }
        tail_set2.add(snake[9]);
      }

    }
    System.out.println(tail_set.size());
    System.out.println(tail_set2.size());

  }
  public void boing2(int h, int t)
  {
    if (snake[h].getDist2(snake[t]) > 2)
    {
      if (snake[h].x != snake[t].x)
      {
        if (snake[h].x > snake[t].x) snake[t] = new Point(snake[t].x + 1, snake[t].y);
        else snake[t] = new Point(snake[t].x -1 , snake[t].y);
      }
      if (snake[h].y != snake[t].y)
      {
        if (snake[h].y > snake[t].y) snake[t] = new Point(snake[t].x, snake[t].y+1);
        else snake[t] = new Point(snake[t].x , snake[t].y-1);
      }

    }

  }


  public void boing()
  {
    if (head.getDist2(tail) > 2)
    {
      if (head.x != tail.x)
      {
        if (head.x > tail.x) tail = new Point(tail.x + 1, tail.y);
        else tail = new Point(tail.x -1 , tail.y);
      }
      if (head.y != tail.y)
      {
        if (head.y > tail.y) tail = new Point(tail.x, tail.y+1);
        else tail = new Point(tail.x , tail.y-1);
      }


    }
    tail_set.add(tail);

  }



}
