/*
ID: 255901
LANG: JAVA
PROG: dualpal
*/
import java.io.*;
import java.util.*;

class dualpal {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
    
    StringTokenizer snc = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(snc.nextToken());    
    int S = Integer.parseInt(snc.nextToken());    
    
    
    int x = 0;
    while(x<N){
      int nb = 0; S++;
      for(int b=2;b<=10;b++) if(isPal(S,b)) nb++;
      
      if(nb>=2){
        out.println(S);
        x++;
      }
    }
    
    out.close();                                  
  }
  public static boolean isPal(int a,int base){
    return Integer.toString(a,base).equals(new String(new StringBuffer(Integer.toString(a,base)).reverse()));
  }
}