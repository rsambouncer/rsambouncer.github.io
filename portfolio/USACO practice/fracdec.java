/*
ID: 255901
LANG: JAVA
PROG: fracdec
*/
import java.io.*;
import java.util.*;

public class fracdec {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("fracdec.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fracdec.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken()); 
    int D = Integer.parseInt(st.nextToken()); 

    String outp = (N/D)+".";
    N%=D;
    boolean repeats = true;
    int[] result = new int[D]; int r = 0;
    int[] state = new int[D]; for(int a=0;a<D;a++) state[a] = -1;
    int t = 0;
    while(true){
      if(N==0){ repeats = false; break;}
      if(state[N]!=-1){ t-=state[N]; break;}
      state[N] = t;
      result[r++] = N*10/D;
      N = (N*10)%D;
      t++;
    }
    
    if(!repeats){
      if(r==0) outp = outp+"0";
      else{ 
        char[] rest = new char[r];
        for(int a=0;a<r;a++) rest[a] = (char)(result[a]+48);
        outp = outp+new String(rest);
      }
    }else{
      char[] rest1 = new char[r-t];
      for(int a=0;a<r-t;a++) rest1[a] = (char)(result[a]+48);
      char[] rest2 = new char[t];
      for(int a=r-t;a<r;a++) rest2[a-r+t] = (char)(result[a]+48);
      outp = outp+new String(rest1)+"("+new String(rest2)+")";
    }
    
    for(int a=0;a<=outp.length()/76;a++) out.println( outp.substring(a*76,Math.min(outp.length(),a*76+76)) );
    out.close();                                  
  }
}