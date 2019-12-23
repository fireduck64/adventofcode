public class ProbB
{
  public static void main(String args[]) throws Exception
  {
    new ProbB(172930, 683082);

  }

  String start_str;
  String end_str;

  public ProbB(int start, int end)
  {
    start_str = "" +start;
    end_str = "" + end;
    long count = rec("",0);
    System.out.print(count);

  }

  private int floodCount(String str, int n)
  {
    char v = str.charAt(n);
    int found =1;

    int a = n-1;
    while((a >= 0) && (str.charAt(a)==v))
    {
      found++;
      a--;
    }
    int b = n+1;
    while((b < 6) && (str.charAt(b)==v))
    {
      found++;
      b++;
    }

    return found;
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
        int n = floodCount(str_in, i);
        if (n == 2) found_double=true;

        /*if (str_in.charAt(i)==str_in.charAt(i+1))
        {
          int j = i+2;
          if ((j >= 6) || (str_in.charAt(j) != str_in.charAt(i)))
            found_double=true;
        }*/
        
      }

      /*for(int i=0; i<4; i++)
      {
        if (str_in.charAt(i)==str_in.charAt(i+1))
        if (str_in.charAt(i)==str_in.charAt(i+2))
          found_double=false;
      }*/


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
