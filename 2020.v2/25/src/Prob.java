import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.math.BigInteger;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  public Prob(Scanner scan)
  {
    BigInteger door_pub = new BigInteger(scan.next());
    BigInteger card_pub = new BigInteger(scan.next());

    int door_loop = findLoopCount(door_pub, BigInteger.valueOf(7));
    int card_loop = findLoopCount(card_pub, BigInteger.valueOf(7));

    System.out.println("Door loop: " + door_loop);
    System.out.println("Card loop: " + card_loop);

    BigInteger key1 = transform(BigInteger.ONE, door_pub, card_loop);
    BigInteger key2 = transform(BigInteger.ONE, card_pub, door_loop);



    //BigInteger key1 = transform(door_pub, BigInteger.valueOf(7), door_loop);
    //BigInteger key2 = transform(card_pub, BigInteger.valueOf(7), door_loop);

    System.out.println("Keys: " + key1 + " " + key2);

  }
  BigInteger mod_val = BigInteger.valueOf(20201227);


  public int findLoopCount(BigInteger val, BigInteger subject)
  {
    BigInteger v = BigInteger.ONE;
    int loop_no=0;
    while(!v.equals(val))
    {
      v = transform(v, subject, 1);
      loop_no++;
    }
    return loop_no;

  }


  public BigInteger transform(BigInteger start, BigInteger subject, long loop_no)
  {
    BigInteger v = start;
    for(int i=0; i<loop_no; i++)
    {
      v = v.multiply(subject).mod(mod_val);
    }

    return v;
  }

}
