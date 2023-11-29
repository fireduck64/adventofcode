import java.io.FileInputStream;
import java.util.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.File;

public class Prob
{

  public static void main(String args[]) throws Exception
  {
    new Prob(Integer.parseInt(args[0]));
  }

  Random rnd=new Random();

  public Prob(int year) throws Exception
  {
    File path = new File("data", "" + year);
    for(int day=1; day<=25; day++)
    {
      File in = new File(path, ""+day);
      if (in.exists())
      {
        processFile(in);

      }
    }
    System.out.println("Total users: " + users.size());

    LinkedList<UserData> lst = new LinkedList<>();
    lst.addAll(users.values());
    Collections.sort(lst);
    int rank = 1;
    for(UserData u : lst)
    {
      System.out.println("" + rank + " - " + u.toString());
      rank++;
    }
  }

  TreeMap<Integer, UserData> users = new TreeMap<>();

  public void processFile(File in)
    throws Exception
  {
     // <div class="leaderboard-entry" data-user-id="415650"><span class="leaderboard-position">  1)</span> <span class="leaderboard-time">Dec 23  00:10:03</span>  <a href="https://github.com/tckmn" target="_blank"><span class="leaderboard-userphoto"><img src="https://avatars.githubusercontent.com/u/2389333?v=4" height="20"/></span>tckmn</a></div>
    Scanner scan = new Scanner(new FileInputStream(in));
    int line_count =0;

    int rank_total = 0;
    int last_rank = 0;

    for(String line : In.lines(scan))
    {
      List<Integer> vals = Tok.ent(line, " >\")");
      //System.out.println(vals);
      int user_id = vals.get(0);
      int rank = vals.get(1);
      rank_total += rank;
      int points = 101 - rank;
      if (rank != last_rank + 1)
      {
        E.er("" + rank + " " + last_rank);
      }
      last_rank = rank;
      if (last_rank == 100) last_rank=0;

      int loc = line.indexOf("leaderboard-time");
      loc = line.indexOf("</span>", loc);
      loc = line.indexOf("<span", loc);
      String html = line.substring(loc);
      if (html.endsWith("</div>"))
      {
        html = html.substring(0, html.length()-6);
      }
      UserData ud =new UserData(user_id);
      ud.html = html;
      
      //System.out.println(html);
      if (!users.containsKey(user_id))
      {
        users.put(user_id, ud);
      }
      users.get(user_id).score+=points;
      line_count++;
    }
    if (line_count != 200) E.er("wrong line count");
    if (rank_total != 10100) E.er("wrong total rank");


  }

  public class UserData implements Comparable<UserData>
  {
    int id;
    int score;
    String html;

    public UserData(int id)
    {
      this.id = id;
    }

    public int compareTo(UserData o)
    {
      if (score > o.score) return -1;
      if (score < o.score) return 1;

      if (id < o.id) return -1;
      if (id > o.id) return 1;

      return 0;

    }

    public String toString()
    {
      return "" + id + " " + score + " " + html;
    }


  }



}
