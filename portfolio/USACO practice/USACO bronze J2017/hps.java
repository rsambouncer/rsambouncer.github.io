/*
ID: 255901
LANG: JAVA
PROG: hps
*/
import java.io.*;
import java.util.*;

public class hps {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("hps.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
    
    int len = Integer.parseInt(f.readLine());
    int mm = 0;
    int pp = 0;
    
    for(int a=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      int x = Integer.parseInt(st.nextToken());    
      int y = Integer.parseInt(st.nextToken());    
      if(x==1&&y==2||x==2&&y==3||x==3&&y==1) mm++;
      if(x==1&&y==3||x==2&&y==1||x==3&&y==2) pp++;
    }

    
    out.println(Math.max(mm,pp));                       
    out.close();                                  
  }
}