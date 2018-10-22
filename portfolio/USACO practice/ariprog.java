/*
ID: 255901
LANG: JAVA
PROG: ariprog
*/
import java.io.*;
import java.util.*;

class ariprog {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
    

    int len = Integer.parseInt(f.readLine());    
    int upb = Integer.parseInt(f.readLine());  
    
    boolean[] isBisquare = new boolean[2*upb*upb+1];
    for(int a=0;a<=upb;a++)for(int b=0;b<=upb;b++)
      isBisquare[a*a+b*b] = true;
    upb = isBisquare.length;  
    
    boolean foundOne = false;
    for(int b=1;b*(len-1)<upb;b++)for(int a=0;a+b*(len-1)<upb;a++){
      boolean isGud = true;
      for(int c=0;c<len;c++){
        if(!isBisquare[a+b*c]){isGud = false;break;}
      }
      if(isGud){out.println(a+" "+b);foundOne = true;}
    }
    
    if(!foundOne) out.println("NONE");                       
    out.close();                                  
  }
}