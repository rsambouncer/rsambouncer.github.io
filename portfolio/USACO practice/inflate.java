/*
ID: 255901
LANG: JAVA
PROG: inflate
*/
import java.io.*;
import java.util.*;

public class inflate {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("inflate.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inflate.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int len = Integer.parseInt(st.nextToken()); 
    int nc = Integer.parseInt(st.nextToken()); 
    int[] cp = new int[nc];
    int[] ct = new int[nc];
    int[] map = new int[len+1];
    for(int a=0;a<nc;a++){
      st = new StringTokenizer(f.readLine());
      cp[a] = Integer.parseInt(st.nextToken());
      ct[a] = Integer.parseInt(st.nextToken());
    }
    
    for(int a=0;a<nc;a++) 
      for(int b=0;ct[a]+b<=len;b++) 
        if(map[b+ct[a]]<map[b]+cp[a])
          map[b+ct[a]] = map[b]+cp[a]; 
    
    out.println(map[len]);                       
    out.close();                                  
  }
}


