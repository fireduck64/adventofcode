import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import duckutil.DaemonThreadFactory;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  Random rnd=new Random();

  SynchronousQueue<Long> queue=new SynchronousQueue<>();
  AtomicLong count=new AtomicLong(1L);
  StringBuilder src_str=new StringBuilder();
  long max_ops=2000L * 1000000L * 1000000L;

  public Prob(Scanner scan)
    throws Exception
  {
    while(scan.hasNextLine())
    {
      src_str.append(scan.nextLine());
      src_str.append("\n");
    }

    int threads=1;
    System.out.println("Starting threads: " + threads);
    for(int t=0; t<threads; t++)
    {
      new ProcThread().start();
    }


    long v = 0;
    //while(v < 167)
    //while(v < 16777216)
    {
      queue.put(v);
      v++;
      if (v%100000L==0)
      {
        System.out.println(v);
      }
    }
    System.out.println("Shutting down threads");
    for(int i=0; i<threads; i++)
    {
      queue.put(-1L);

    }

  }

  public class ProcThread extends Thread
  {
    public void run()
    {
      try
      {
        WristComp wc = new WristComp(new Scanner(src_str.toString()));
        wc.max_ops = max_ops;
        while(true)
        {
          wc.reset();
          long v = queue.take();
          if (v < 0) return;
          wc.r[0]=v;
          //System.out.print('.');
          long c = wc.execute();
          //if ((c > count.get()) && (c < max_ops))
          if (c < max_ops)
          {
            synchronized(count)
            {
              count.set(c);
              System.out.println();
              System.out.println("New winner: " + v + " " + count);
            }
          }
        }
      }
      catch(Exception t)
      {
        t.printStackTrace();
      }

    }


  }


  String printNumbers(long[] v)
  {
    StringBuilder sb=new StringBuilder();
    sb.append("[");
    for(int i=0; i<v.length; i++)
    {
      sb.append("" + v[i]);
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();

  }


}
