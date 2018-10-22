/*
ID: 255901
LANG: JAVA
PROG: spin
*/
import java.io.*;
import java.util.*;

public class spin {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("spin.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
    
    boolean[][] wheels = new boolean[5][360];
    int[] speeds = new int[5];
    
    for(int a=0;a<5;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      speeds[a] = Integer.parseInt(st.nextToken()); 
      int N = Integer.parseInt(st.nextToken());
      for(int b=0;b<N;b++){
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        for(int c=x;c<=x+y;c++) wheels[a][c%360] = true;
      }
    }
    
    for(int t=0;t<360;t++){
      for(int a=0;a<360;a++){
        if( wheels[0][((a-t*speeds[0])%360+360)%360]
          &&wheels[1][((a-t*speeds[1])%360+360)%360]
          &&wheels[2][((a-t*speeds[2])%360+360)%360]
          &&wheels[3][((a-t*speeds[3])%360+360)%360]
          &&wheels[4][((a-t*speeds[4])%360+360)%360]){
            out.println(t);
            out.close();
            return;
          }
      }
    }
    
    
    out.println("none");                       
    out.close();                                  
  }
}


