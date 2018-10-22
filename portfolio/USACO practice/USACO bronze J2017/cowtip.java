/*
ID: 255901
LANG: JAVA
PROG: cowtip
*/
import java.io.*;
import java.util.*;

public class cowtip {
  public static boolean[][] cheese;
  public static int len;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("cowtip.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtip.out")));
    
    len = Integer.parseInt(f.readLine());
    cheese = new boolean[len][len];
    
    
    for(int a=0;a<len;a++){
      String str = f.readLine();
      for(int b=0;b<len;b++)
        cheese[a][b] = str.charAt(b)=='1';
    }

    int f1Nal = 0;
    for(int a=len-1;a>=0;a--){
      for(int b=len-1;b>=0;b--){
        if(cheese[a][b]){f1Nal++;applyTrans(a,b);}
      }
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
  public static void applyTrans(int x, int y){
    for(int a=0;a<=x;a++) for(int b=0;b<=y;b++) cheese[a][b] = !cheese[a][b];
  }
}
