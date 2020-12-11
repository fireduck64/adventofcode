import java.io.FileInputStream;
import java.util.*;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();
  int players;
  int last_marble;

  int next_player=0;
  int next_marble=1;

  int current_marble=0;

  List<Integer> circle=new LinkedList<>();
  //List<Integer> circle=new ArrayList<>();


  public Prob(Scanner scan)
  {
    players = scan.nextInt();
    last_marble = scan.nextInt();

    circle.add(0);

    long[] scores=new long[players];

    ListIterator<Integer> it = circle.listIterator(0);

    while(next_marble <= last_marble)
    {
      if (next_marble % 23 == 0)
      {
        scores[next_player] += next_marble;

        for(int i=0; i<7; i++) it = reverse( it );

        
        //int remove_loc = correct_index(current_marble - 7);

        //int removed = circle.remove(remove_loc);
        int removed = it.next();
        it.remove();
        scores[next_player]+=removed; 

        //current_marble = remove_loc;

      }
      else
      {
        it = advance( it );
        it = advance( it );
        it.add( next_marble );
        it = reverse( it );
        //int insert_loc = correct_index(current_marble + 2);

        /*if (insert_loc == 0)
        {
          circle.add(next_marble);
          current_marble = circle.size() -1;
        
        }
        else
        {
          circle.add(insert_loc, next_marble);
          current_marble = correct_index(insert_loc + 0);
        }*/


      }

      //System.out.println("Placed: " + next_marble + " player: " + (next_player+1));
      //System.out.println("    " + circle);

      next_marble++;

      next_player++;
      next_player = next_player % players;

    }

    long high_score =0;
    for(int i=0; i<players; i++)
    {
      high_score = Math.max( high_score, scores[i]);
    }
    System.out.println(high_score);

  }

  ListIterator<Integer> advance(ListIterator<Integer> it)
  {
    if (it.hasNext())
    {
      it.next();
      return it;
    }
    else
    {
      return circle.listIterator(1);
    }
  }
  
  ListIterator<Integer> reverse(ListIterator<Integer> it)
  {
    if (it.hasPrevious())
    {
      it.previous();
      return it;
    }
    else
    {
      return circle.listIterator( circle.size() - 1);
    }
  }

  int correct_index(int idx)
  {
    while(idx < 0) idx+=circle.size();
    while(idx >= circle.size()) idx-=circle.size();
    return idx;
  }

}
