import java.security.MessageDigest;

import java.util.*;
import org.apache.commons.codec.binary.Hex;

public class HUtil
{
  private static MessageDigest md;

  public static String getHash(String input)
  {
    try
    {
      if (md == null) md = MessageDigest.getInstance("MD5");
      byte[] b = null;
      synchronized(md)
      {
        b = md.digest(input.getBytes());
      }

          Hex h = new Hex();
       return new String(h.encodeHex(b));

    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
