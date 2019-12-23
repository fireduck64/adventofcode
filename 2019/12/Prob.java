import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));
  }

  ArrayList<Moon> moons = new ArrayList<>();

  public class Moon
  {
    long p[];
    long v[];

    public Moon(String line)
    {
      p=new long[3];
      v=new long[3];

      line = line.replaceAll(",","=").replaceAll(">","=");
      String[] s = line.split("=");

      p[0] = Integer.parseInt(s[1]);
      p[1] = Integer.parseInt(s[3]);
      p[2] = Integer.parseInt(s[5]);
    }

    public String toString()
    {
      return String.format("(%d %d %d) (%d %d %d)", p[0], p[1], p[2], v[0], v[1], v[2]);
    }
    public String toStringAxis(int n)
    {
      return String.format("(%d %d)", p[n], v[n]);
    }

    public void applyGravity(Moon other)
    {
      for(int i=0; i<3; i++)
      {
        long d = other.p[i] - p[i];
        if (d > 0)
        {
          v[i]++;
        }
        if (d < 0)
        {
          v[i]--;
        }

      }

    }
    public void updatePosition()
    {
      for(int i=0; i<3; i++)
      {
        p[i] += v[i];
      }
    }
    public long getEnergy()
    {
      long sum_p = 0;
      long sum_v = 0;
      for(int i=0; i<3; i++)
      {
        sum_p+=Math.abs(p[i]);
        sum_v+=Math.abs(v[i]);
      }
      return sum_p*sum_v;

    }
  }

  public void simForward()
  {
    for(Moon i : moons)
    for(Moon j : moons)
    {
      i.applyGravity(j);
    }
    for(Moon i : moons)
    {
      i.updatePosition();
    }

  }

  public long getEnergy()
  {
    long s = 0;
    for(Moon m : moons)
    {
      s += m.getEnergy();
    }
    return s;

  }

  public String getState(int axis)
  {
    StringBuilder sb = new StringBuilder();
    for(Moon m : moons)
    {
      sb.append(m.toStringAxis(axis));
    }
    return sb.toString();
  }

  public Prob(Scanner scan)
  {
    while(scan.hasNextLine())
    {
      moons.add(new Moon(scan.nextLine()));
    }
    System.out.println(moons);

    String start_state = moons.toString();
    String state_a[] = new String[3];
    for(int i=0; i<3; i++) state_a[i] = getState(i);

    long period[] = new long[3];

    long steps = 0;
    int found_p = 0;
    while(true)
    {
      simForward();
      steps++;
      String state = moons.toString();

      for(int i=0; i<3; i++)
      {
        if (period[i] == 0)
        {
          if (state_a[i].equals(getState(i)))
          {
            period[i] = steps;
            found_p++;
            System.out.println("Period of " + i + " is " + (steps));
          }
        }
      }
      if (found_p ==3) break;
      if (start_state.equals(state))
      {
        break;
      }
      //if (steps == 1000000) break;
      if (steps % 1000000 == 0) System.out.println(steps);
    }

    System.out.println(findLCD(period));
    System.out.println("Repeat after: " + steps);

  }


  public long findLCD(long[] p)
  {
    long n[]=new long[3];

    while(true)
    {
      int low_idx = 0;
      long low = n[0];
      for(int i=0; i<3; i++)
      {
        if (n[i]<low)
        {
          low_idx=i;
          low=n[i];
        }
      }
      n[low_idx]+=p[low_idx];

      if ((n[0] == n[1]) && (n[1] == n[2]))
      {
        return n[0];
      }


    }

  }

}
