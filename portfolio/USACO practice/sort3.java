/*
ID: 255901
LANG: JAVA
PROG: sort3
*/
import java.io.*;
import java.util.*;

public class sort3 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("sort3.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[] ar = new int[len];
    int ca=0,cb=0,cc=0,t1=0,t2=0;
    for(int a=0;a<len;a++){
      ar[a] = Integer.parseInt(f.readLine());
      if(ar[a]==1) ca++;
      if(ar[a]==2) cb++;
      if(ar[a]==3) cc++;
    }
    
    t1=ca; int t3 = 0;
    for(int a=0;a<ca;a++){ 
      if(ar[a]==1) t1--; 
      else if(ar[a]==3) t3--;
    }
    
    t2=0;
    for(int a=len-1;a>=len-cc;a--){
      if(ar[a]==2)t2++;
      else if(ar[a]==1)t3++;
    }
    if(t3<0) t3=0;
    out.println(t1+t2+t3);                       
    out.close();                                  
  }
}