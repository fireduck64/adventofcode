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

  long card_pub;
  long door_pub;
  long sub = 7;

  long card_loop;
  long door_loop;

  public Prob(Scanner scan)
  {
    card_pub = scan.nextLong();
    door_pub = scan.nextLong();

    card_loop = getLoopSize(card_pub);
    door_loop = getLoopSize(door_pub);
    System.out.println("Loopsizes: " + card_loop + " " + door_loop);
    
    long key = doTransforms(card_pub, card_pub, door_loop);
    //long key = doTransforms(sub, door_loop);
    //System.out.println("Should be: " + door_pub);

    System.out.println("Key: " + key);

  }

  public long doTransforms(long sub, long x, long loops)
  {
    for(long i=0; i<loops-1; i++)
    {
      x=transform(x,sub);
    }
    return x;

  }

  public long getLoopSize(long pub)
  {
    long x = sub;
    long loops=1;
    while(true)
    {
      x = transform(x, 7);
      loops++;
      if (x == pub) return loops;
    }

  }

  public long transform(long x, long sub)
  {
    long v = x * sub;
    v = v % 20201227;
    return v;
  }

}
