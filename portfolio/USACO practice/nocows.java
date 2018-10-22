/*
ID: 255901
LANG: JAVA
PROG: nocows
*/
import java.io.*;
import java.util.*;

public class nocows {
  public static int[][] posT;
  public static int[][] posF;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("nocows.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken()); 
    int K = Integer.parseInt(st.nextToken()); 
    posT = new int[N+1][K+1];
    posF = new int[N+1][K+1];
    for(int a=0;a<=N;a++) for(int b=0;b<=K;b++){posF[a][b] = -1;posT[a][b] = -1;}
    out.println(N%2==0?0:pos(N,K,true));                       
    out.close();                                  
  }
  public static int pos(int N, int K, boolean E){ //E? exactly K height : K or less height
    if(K==1) return N==1?1:0;
    if(N==1) return E?0:1;
    
    int sum = 0;
    if(E){
      if(posT[N][K]!=-1) return posT[N][K];
      for(int a=1;a<N;a+=2){
        int p1 = pos(a,K-1,true)*pos(N-a-1,K-1,false);
        int p2 = pos(a,K-1,false)*pos(N-a-1,K-1,true);
        int p3 = pos(a,K-1,true)*pos(N-a-1,K-1,true);
        sum = (sum+p1+p2-p3)%9901;
      }
    }else{
      if(posF[N][K]!=-1) return posF[N][K];
      for(int a=1;a<N;a+=2){
        int p1 = pos(a,K-1,false)*pos(N-a-1,K-1,false);
        sum = (sum+p1)%9901;
      }
    }
    return update(N,K,E,sum);
  }
  public static int update(int N, int K, boolean E, int sum){
    if(E) posT[N][K] = sum;
    else  posF[N][K] = sum;
    return sum;
  }
}