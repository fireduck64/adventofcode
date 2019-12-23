
import java.util.List;

public abstract class State
{


  public abstract double getCost();
  public abstract List<State> next();
  public abstract boolean isTerm();

  public String getHash()
  {
    return HUtil.getHash(toString());
  }
  public double getEstimate()
  {
    return 0.0;
  }
 
}
