/*
ID: 255901
LANG: JAVA
PROG: crypt1
*/
import java.io.*;
import java.util.*;

class crypt1 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
    
    int len = Integer.parseInt(f.readLine());
    StringTokenizer snc = new StringTokenizer(f.readLine());
    int[] digits = new int[len];
    boolean[] isDig = new boolean[10];
    for(int a=0;a<len;a++){
      digits[a] = Integer.parseInt(snc.nextToken());
      isDig[digits[a]] = true;
    }
    int f1Nal = 0;
    
    for(int a:digits)
    for(int b:digits)
    for(int c:digits)
    for(int d:digits)
    for(int e:digits){
      int par1 = (100*a+10*b+c)*(e);
        if(par1>=1000) continue;
      int par2 = (100*a+10*b+c)*(d);
        if(par2>=1000) continue;
      int ttl =  (100*a+10*b+c)*(10*d+e);
        if(ttl>=10000) continue;
        
      if(!isDig[ par1%10      ]) continue;
      if(!isDig[ par1/10%10   ]) continue;
      if(!isDig[ par1/100%10  ]) continue;
      
      if(!isDig[ par2%10      ]) continue;
      if(!isDig[ par2/10%10   ]) continue;
      if(!isDig[ par2/100%10  ]) continue;
      
      if(!isDig[ ttl%10       ]) continue;
      if(!isDig[ ttl/10%10    ]) continue;
      if(!isDig[ ttl/100%10   ]) continue;
      if(!isDig[ ttl/1000%10  ]) continue;
      
      f1Nal++;
    }
    
    
    out.println(f1Nal);
    out.close();                                  
  }
}