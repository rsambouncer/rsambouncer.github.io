/*
ID: 255901
LANG: JAVA
PROG: kimbits
*/
import java.io.*;
import java.util.*;

public class kimbits {
  public static long[][] numInside;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("kimbits.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken()); 
    int L = Integer.parseInt(st.nextToken()); 
    long I = Long.parseLong(st.nextToken()); 
    
    numInside = new long[N+1][L+1];
    for(int a=0;a<=N;a++) for(int b=0;b<=L;b++){
      if(a==0||b==0) numInside[a][b] = 1;
      else numInside[a][b] = numInside[a-1][b-1]+numInside[a-1][b];
    }
    
    I--;
    long f1Nal = 0;
    for(int a=N;a>=0;a--) if(I>=numInside[a][L]){ f1Nal+= 1<<a; I-= numInside[a][L];L--;}
    
    String str = Long.toBinaryString(f1Nal);
    while(str.length()<N) str = "0"+str;
    out.println(str);                       
    out.close();                                  
  }
  
}


