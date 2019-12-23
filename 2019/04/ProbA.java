public class ProbA
{
  public static void main(String args[]) throws Exception
  {
    new ProbA(172930, 683082);

  }

  String start_str;
  String end_str;

  public ProbA(int start, int end)
  {
    start_str = "" +start;
    end_str = "" + end;
    long count = rec("",0);
    System.out.print(count);

  }

  private long rec(String str_in, int last_digit)
  {
    if (str_in.length() > 0)
    {
      String c = start_str.substring(0,str_in.length());
      if (str_in.compareTo(c) < 0) return 0L;
      if (str_in.compareTo(end_str) > 0) return 0L;
    }

    if (str_in.length()==6)
    {
      boolean found_double = false;
      for(int i=0; i<5; i++)
      {
        if (str_in.charAt(i)==str_in.charAt(i+1)) found_double=true;
      }
      if (found_double)
      {
        System.out.println(str_in);
        return 1L;
      }
      else return 0L;
    }

    long r = 0;
    for(int n = last_digit; n<10; n++)
    {
      r+=rec(str_in + n, n);
    }
    return r;
  }

}
