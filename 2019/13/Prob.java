import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }

  long best_score = 0;

  TreeMap<Double, State> state_queue = new TreeMap<>();
  Random rnd = new Random();
  HashSet<String> seen=new HashSet<>(1024,0.5f);


  TreeMap<Long, Character> print_map=new TreeMap<>();

  public Prob(Scanner scan)
  {

    print_map.put(0L,' ');
    print_map.put(1L,'#');
    print_map.put(2L,'B');
    print_map.put(3L,'=');
    print_map.put(4L,'*');

    IntComp compy = new IntComp(scan.nextLine());

    compy.set(0L,2L);

    compy.execute();
    System.out.println(compy.term);
    System.out.println(compy.output_queue);

    Map2D<Long> m = new Map2D<>(0L);

    LinkedList<Long> q = compy.output_queue;

    int blocks = 0;
    long score = 0;

    while(q.size() > 0)
    {
      long x = q.poll();
      long y = q.poll();
      long tile = q.poll();

      m.set(x,y,tile);
      if ((x==-1) && (y==0)) score=tile;

      if (tile == 2L) blocks++;
    }
    
    System.out.println("Blocks: " + blocks);

    State start = new State();
    start.m = m;
    start.comp= compy;
    start.move_cnt = 0;
    start.remaining_blocks = blocks;

    state_queue.put(start.getV(), start);

    while((best_score ==0) && (state_queue.size() > 0))
    {
      while(state_queue.size() > 10000)
      {
        state_queue.pollLastEntry();
      }
      playGame(state_queue.pollFirstEntry().getValue());
    }

    System.out.println("Best score: " + best_score); 
  }

  public void playGame(State state)
  {
    for(long mv=-1; mv<=1; mv++)
    {
      State s2 = new State(state);

      s2.comp.input_queue.add(mv);
      s2.comp.execute();

      LinkedList<Long> q = s2.comp.output_queue;
      long score = 0;
      while(q.size() > 0)
      {
        long x = q.poll();
        long y = q.poll();
        long tile = q.poll();

        if (s2.m.get(x,y) == 2L)
        {
          if (tile != 2L) 
          { 
            s2.remaining_blocks--; 
          }
        }

        s2.m.set(x,y,tile);
        if ((x==-1) && (y==0)) score=tile;
      }
      if (score >0) 
      {
        System.out.println("Score: " + score + " rem: " + s2.remaining_blocks + " q:" + state_queue.size());
        System.out.println(s2.m.getPrintOut(print_map));
      }
      if (s2.remaining_blocks == 0)
      {
        best_score = Math.max(score, best_score);
      }
      if ((!s2.comp.term) && (s2.remaining_blocks > 0))
      {
        String h = s2.getHash();
        if (!seen.contains(h))
        {
          state_queue.put(s2.getV(), s2);
          seen.add(h);
        }
      }
    }

  }

  public class State
  {
    IntComp comp;
    Map2D<Long> m;
    int remaining_blocks;
    int move_cnt;

    public State()
    {

    }

    public State(State src)
    {
      comp = new IntComp(src.comp);
      m = new Map2D<Long>(src.m);
      remaining_blocks = src.remaining_blocks;
      move_cnt = src.move_cnt;
    }

    public double getV()
    {
      return rnd.nextDouble() + remaining_blocks * 1e9;// + move_cnt;
    }

    public String getHash()
    {
      return HUtil.getHash("" + move_cnt + " " + remaining_blocks + " " + comp.getHashState() + " "+ m.getHashState());
    }
  }

}
