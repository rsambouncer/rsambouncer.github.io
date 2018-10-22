/*
ID: 255901
LANG: JAVA
PROG: money
*/
import java.io.*;
import java.util.*;

public class money {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("money.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("money.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int V = Integer.parseInt(st.nextToken()); 
    int N = Integer.parseInt(st.nextToken());
    int[] coins = new int[V];
    st = new StringTokenizer(f.readLine());
    for(int a=0;a<V;a++){ 
      if(!st.hasMoreTokens()) st = new StringTokenizer(f.readLine());
      coins[a] = Integer.parseInt(st.nextToken());
    }
    
    long[] map = new long[N+1]; map[0] = 1;
    for(int a=0;a<V;a++) for(int b=0;b<N;b++) if(coins[a]+b<=N) map[coins[a]+b]+=map[b];

    
    out.println(map[N]);                       
    out.close();                                  
  }
}