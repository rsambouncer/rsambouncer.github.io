/*
ID: 255901
LANG: JAVA
PROG: closing
*/
import java.io.*;
import java.util.*;

public class closing {
  public static class Barn{
    int p = 0;
    boolean open = false;
    Barn[] bs;
    
  }
  public static Barn[] barns;
  public static int[] orderclose;
  public static boolean[] connected;
  public static boolean[][] Matrix;
  public static int numgroups;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("closing.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("closing.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken()); 
      orderclose = new int[N];
      connected = new boolean[N];
    int M = Integer.parseInt(st.nextToken()); 
    
    barns = new Barn[N]; for(int a=0;a<N;a++) barns[a] = new Barn();
    int[] paths1 = new int[M];
    int[] paths2 = new int[M];
    for(int a=0;a<M;a++){
      st = new StringTokenizer(f.readLine());
      paths1[a] = Integer.parseInt(st.nextToken())-1;
      paths2[a] = Integer.parseInt(st.nextToken())-1;
      barns[paths1[a]].p++;
      barns[paths2[a]].p++;
    }
    for(int a=0;a<N;a++){ 
      barns[a].bs = new Barn[barns[a].p]; barns[a].p = 0;
      barns[a].index = a;
    }
    for(int a=0;a<M;a++){
      Barn bb1 = barns[paths1[a]];
      Barn bb2 = barns[paths2[a]];
      bb1.bs[bb1.p++] = bb2;
      bb2.bs[bb2.p++] = bb1;
    }
    
    
    for(int a=0;a<N;a++) orderclose[a] = Integer.parseInt(f.readLine())-1;
    for(int a=N-1;a>=0;a--){
      connected[a] = gudOpen(orderclose[a]);
    }
    for(int a=0;a<N;a++) out.println(connected[a]?"YES":"NO");
    
    out.close();                                  
  }
  public static boolean gudOpen(int num){
    
    
    
  }
}