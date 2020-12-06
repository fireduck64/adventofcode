import java.security.MessageDigest;

import java.util.*;

public class HUtil
{
  public static String getHash(String input)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] b = md.digest(input.getBytes());

      return Base64.getEncoder().encodeToString(b);

    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }


  }
}
