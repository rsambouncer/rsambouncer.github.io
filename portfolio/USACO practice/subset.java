/*
ID: 255901
LANG: JAVA
PROG: subset
*/
import java.io.*;
import java.util.*;

public class subset {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("subset.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
    
    int N = Integer.parseInt(f.readLine());
    if((N*(N+1))%4!=0){out.println("0");out.close();return;}

    out.println(numsets(N,N*(N+1)/4)/2);                       
    out.close();                                  
  }
  public static long numsets(int N,int goal){
    long[] ways = new long[goal+1];
    ways[0] = 1;
    for(int a=1;a<=N;a++)
      for(int b=goal;b>=0;b--)
        if(ways[b]>0&&a+b<=goal) ways[a+b]+=ways[b];
    return ways[goal];
  }
}