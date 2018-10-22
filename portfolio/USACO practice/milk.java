/*
ID: 255901
LANG: JAVA
PROG: milk
*/
import java.io.*;
import java.util.*;

class milk {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
    
    StringTokenizer snc = new StringTokenizer(f.readLine());
    int ttl = Integer.parseInt(snc.nextToken());    
    int len = Integer.parseInt(snc.nextToken());  
    int[] prices = new int[len];
    int[] amount = new int[len];
    for(int a=0;a<len;a++){
      snc = new StringTokenizer(f.readLine());
      prices[a] = Integer.parseInt(snc.nextToken());    
      amount[a] = Integer.parseInt(snc.nextToken());    
    }
    
    for(int a=0;a<len;a++){
      int c = a;
      for(int b=a;b<len;b++) if(prices[b]<prices[c]) c = b;
      int h1 = prices[a];
      int h2 = amount[a];
      prices[a] = prices[c];
      amount[a] = amount[c];
      prices[c] = h1;
      amount[c] = h2;
    }
    int f1Nal = 0;
    int a=0;
    while(ttl>0){
      if(amount[a]<ttl){f1Nal+=amount[a]*prices[a];ttl-=amount[a];}
      else {f1Nal+=ttl*prices[a];ttl=0;}
      a++;
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
}