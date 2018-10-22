/*
ID: 255901
LANG: JAVA
PROG: stamps
*/
import java.io.*;
import java.util.*;

public class stamps {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("stamps.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int K = Integer.parseInt(st.nextToken());
    int N = Integer.parseInt(st.nextToken());
    int[] dist = new int[K*10000+2]; for(int a=1;a<dist.length;a++) dist[a] = K+1;
    for(int a=0;a<N;a++){
      if(a%15==0) st = new StringTokenizer(f.readLine());
      int P = Integer.parseInt(st.nextToken());
      
      for(int b=0;b+P<dist.length;b++)
        if(dist[b]+1<dist[b+P]) dist[b+P] = dist[b]+1;
    }
    
    for(int a=0;a<dist.length;a++)
      if(dist[a]>K){
        out.println(a-1);
        break;
      }
    
    out.close();                                  
  }
}


