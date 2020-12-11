
import java.util.List;

public abstract class State implements Comparable<State>
{

  public abstract double getCost();
  public abstract List<State> next();

  /** is the end state we are looking for */
  public abstract boolean isTerm();

  /**
   * Used for visit dedupication
   */
  public String getHash()
  {
    return HUtil.getHash(toString());
  }

  /**
   * A-star estimate
   */
  public double getEstimate()
  {
    return 0.0;
  }

  @Override
  public int compareTo(State o)
  {
    return getHash().compareTo(o.getHash());
  }
 
}
