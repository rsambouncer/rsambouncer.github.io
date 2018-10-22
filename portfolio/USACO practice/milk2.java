/*
ID: 255901
LANG: JAVA
PROG: milk2
*/
import java.io.*;
import java.util.*;

class milk2 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
    
    int len = Integer.parseInt(f.readLine());
      if(len<1){out.println(0);out.close();return;}
    int[] startys = new int[len];
    int[] stoppys = new int[len];
    for(int a=0;a<len;a++){
      StringTokenizer snc = new StringTokenizer(f.readLine());
      startys[a] = Integer.parseInt(snc.nextToken());    
      stoppys[a] = Integer.parseInt(snc.nextToken());    
    }
    Arrays.sort(startys);
    Arrays.sort(stoppys);

    int f1NalM=0,f1NalL=0,st=startys[0];
    for(int a=1;a<len;a++){
      if(startys[a]<=stoppys[a-1]) continue;
      if(stoppys[a-1]-st>f1NalM) f1NalM = stoppys[a-1]-st;
      if(startys[a]-stoppys[a-1]>f1NalL) f1NalL = startys[a]-stoppys[a-1];
      st = startys[a];
    }
    if(stoppys[len-1]-st>f1NalM) f1NalM = stoppys[len-1]-st;

    
    out.println(f1NalM+" "+f1NalL);                       
    out.close();                                  
  }
}