

public class Spot
{
  char z;
  int hp;
  int att;
  int last_move=-1;

  public static int elf_power = 3;

  public Spot(char z)
  {
    this.z = z;
    if (z=='E')
    {
      hp = 200;
      att = elf_power;
    }
    if (z =='G')
    {
      hp = 200;
      att = 3;
    }
  }

  boolean isWalkable()
  {
    return (z=='.');
  }
  boolean isBug()
  {
    return (z=='E') || (z=='G');
  }
  public String toString()
  {
    return "" + z;
  }

}
