import java.util.List;

/**
 * To do a flood fill, extend this class.
 * Define toString() (for dedup)
 * Define next() (for filling)
 * Make sure to mark whatever you want to mark as part of next()
 *
 * Then do Search.search(new F(...)) where F is your flood class.
 * return will be null because everything is non-terminal
 * but all reached states will have next() called on them exactly once
 */
public abstract class Flood extends State
{

  public double getCost(){return 0;}
  public boolean isTerm(){return false;}

  public abstract String toString();
  public abstract List<State> next();

}
