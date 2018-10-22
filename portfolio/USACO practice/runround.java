/*
ID: 255901
LANG: JAVA
PROG: runround
*/
import java.io.*;
import java.util.*;

public class runround {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("runround.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));
    
    long inp = Long.parseLong(f.readLine())+1;
    

    while(!isRR(inp)) inp++;

    
    out.println(inp);                       
    out.close();                                  
  }
  public static boolean isRR(long num){
    String str = ""+num;
    boolean[] dup = new boolean[10];
    boolean[] ar = new boolean[str.length()];
    int m = 0;
    for(int a=0;a<str.length();a++){
      if(dup[str.charAt(m)-48]) return false;
      else dup[str.charAt(m)-48] = true;
      m = (m+str.charAt(m)-48)%str.length();
      ar[m] = true;
    }
    for(int a=0;a<ar.length;a++) if(!ar[a]) return false;
    return true;
  }
}