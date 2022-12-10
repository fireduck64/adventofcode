import java.util.*;

public class Clocko
{
  int x=1;
  int exec_ptr = 0;
  int cycle = 0;
  List<String> lines;

  Map2D<Character> map = new Map2D<>(' ');

  Map<Integer, Integer> vals = new TreeMap<>();


  public Clocko(List<String> lines)
  {
    this.lines = lines;

  }

  private void draw()
  {
      int crt_pos_x = cycle % 40;
      int crt_pos_y = cycle / 40;
      int dist = Math.abs( x - crt_pos_x);

      //System.out.println(String.format("%d %d %d %d %d", cycle, crt_pos_x, crt_pos_y, low, x));

      if (dist <= 1)
      {
        map.set(crt_pos_x, crt_pos_y, '#');
      }
      else
      {
        map.set(crt_pos_x, crt_pos_y, ' ');

      }


  }

  public void run()
  {
    draw();
    while(true)
    {
    
      String line = lines.get(exec_ptr);
      List<Integer> nums = Tok.ent(line, " ");

      if (line.startsWith("noop"))
      {
        cycle++;
        draw();
        vals.put(cycle, x);
        
      }
      else if (line.startsWith("addx"))
      {
        int val = nums.get(0);

        cycle+=1;
        vals.put(cycle, x);
        draw();
        cycle+=1;
        vals.put(cycle, x);
        x+=val;
        draw();

      }

      exec_ptr++;

      if (cycle >= 240) break;

    }




  }


}
