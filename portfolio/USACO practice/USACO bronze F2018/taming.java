/*
ID: 255901
LANG: JAVA
PROG: taming
*/
import java.io.*;
import java.util.*;

public class taming {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("taming.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[] log = new int[len];
    boolean[] dBr = new boolean[len];
    boolean[] dNo = new boolean[len];
    
    StringTokenizer strr = new StringTokenizer(f.readLine());
    for(int a=0;a<len;a++){
      log[a] = Integer.parseInt(strr.nextToken());
      dBr[a] = false;
      dNo[a] = false;
    }
    
    for(int a=0;a<len;a++){
      if(log[a]==-1) continue;
      dBr[a-log[a]] = true;
      for(int b=log[a]-1;b>=0;b--){
        dNo[a-b] = true;
      }
    }
    dBr[0] = true;
    
    int max = 0; int min = 0;
    for(int a=0;a<len;a++){
      if(dBr[a]&&dNo[a]){out.println(-1);out.close();return;}
      if(!dNo[a]) max++;
      if(dBr[a]) min++;
    }

    
    out.println(min + " " + max);                       
    out.close();                                  
  }
}