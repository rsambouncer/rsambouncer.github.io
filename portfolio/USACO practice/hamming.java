/*
ID: 255901
LANG: JAVA
PROG: hamming
*/
import java.io.*;
import java.util.*;

public class hamming {
  public static int N, B, D;
  public static int[] hams;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("hamming.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    N = Integer.parseInt(st.nextToken()); 
    B = Integer.parseInt(st.nextToken()); 
    D = Integer.parseInt(st.nextToken()); 

    hams = new int[N];
    int c = 1;
    int x = 1;
    while(c<hams.length){
      boolean b = true;
      for(int a=0;a<c;a++) if(!isGood(x,hams[a])) b = false;
      if(b) hams[c++] = x;
      x++;
    }

    for(int a=0;a<hams.length;a+=10){
      String str = "";
      for(int b=0;a+b<hams.length&&b<10;b++) str = str+" "+hams[a+b];
      out.println(str.substring(1));
    }

    out.close();                                  
  }
  public static boolean isGood(int x,int y){
    int num = x^y;
    int c = 0;
    for(int a=0;a<B;a++) if(((num>>a)&1)==1) c++;
    return c>=D;
  }
}