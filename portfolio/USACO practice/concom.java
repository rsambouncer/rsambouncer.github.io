/*
ID: 255901
LANG: JAVA
PROG: concom
*/
import java.io.*;
import java.util.*;

public class concom {
  public static int[][] companies = new int[101][101];
  public static boolean[][] owns = new boolean[101][101];
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("concom.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
    
    int len = Integer.parseInt(f.readLine());
    for(int a=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      int x = Integer.parseInt(st.nextToken()); 
      int y = Integer.parseInt(st.nextToken()); 
      companies[x][y] = Integer.parseInt(st.nextToken());
    }
    for(int a=0;a<101;a++) fill(a);
    for(int a=0;a<101;a++) for(int b=0;b<101;b++) if(a!=b&&owns[a][b]) out.println(a+" "+b);
    
    out.close();                                  
  }
  public static void fill(int x){
    for(int a=0;a<101;a++) owns[x][a] = companies[x][a]>50;
    owns[x][x] = true;
    complete(x);
  }
  public static void complete(int x){
    boolean f1Nal = false;
    for(int a=0;a<101;a++) if(!owns[x][a]){
      int sum = 0;
      for(int b=0;b<101;b++) if(owns[x][b]) sum+= companies[b][a];
      if(sum>50){
        owns[x][a] = true;
        f1Nal = true;
      }
    }
    if(f1Nal) complete(x);
  }
}