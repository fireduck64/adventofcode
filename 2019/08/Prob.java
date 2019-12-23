import java.io.FileInputStream;
import java.util.*;

public class Prob
{
  public static void main(String args[]) throws Exception
  {
    new Prob(new Scanner(new FileInputStream(args[0])));

  }
 
  int width=25;
  int height=6;
  int layers=100;

  public Prob(Scanner scan)
  {

    int[][][] img = new int[width][height][layers];

    int[][] layer_counts = new int[layers][100];

    String img_str = scan.next();
    
    int ptr = 0;

    for(int k =0; k<layers; k++)
    {
      for(int j=0; j<height; j++)
      {
        for(int i=0; i<width; i++)
        {
          int v =  Integer.parseInt(img_str.substring(ptr,ptr+1));
          img[i][j][k] = v;
          layer_counts[k][v]++;
          ptr++;
        }

      }

    }

    int lowest=1000000;
    int low=-1;
    for(int k=0; k<layers; k++)
    {
      if (layer_counts[k][0] < lowest)
      {
        low=k;
        lowest = layer_counts[k][0];
      }
    }
    System.out.println("Lowest layer: " + low + " with " + lowest);
    int ans = layer_counts[low][1] * layer_counts[low][2];
    System.out.println("Product: " + ans);

    int[][] res = new int[width][height];

    for(int j=0; j<height; j++)
    {
      for(int i=0; i<width; i++)
      {
        for(int k=0; k<layers; k++)
        {
          if (img[i][j][k] != 2)
          {
            res[i][j]=img[i][j][k];
            break;

          }

        }
        if (res[i][j]==1) System.out.print('X');
        else System.out.print(' ');

      }
      System.out.println();
    }



  }

}
