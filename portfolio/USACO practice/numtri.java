/*
ID: 255901
LANG: JAVA
PROG: numtri
*/
import java.io.*;
import java.util.*;

class numtri {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("numtri.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[][] mat = new int[len][];
    for(int a=0;a<len;a++){
      mat[a] = new int[a+1];
      StringTokenizer st = new StringTokenizer(f.readLine());
      for(int b=0;b<=a;b++){
        mat[a][b] = Integer.parseInt(st.nextToken());
      }
    }
    
    for(int a=len-2;a>=0;a--){
      for(int b=0;b<=a;b++){
        mat[a][b] += Math.max(mat[a+1][b],mat[a+1][b+1]);
      }
    }
    
    
    out.println(mat[0][0]);
    out.close();                                  
  }
}




