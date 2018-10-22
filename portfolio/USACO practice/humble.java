/*
ID: 255901
LANG: JAVA
PROG: humble
*/
import java.io.*;
import java.util.*;

public class humble {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("humble.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("humble.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int K = Integer.parseInt(st.nextToken()); 
    int N = Integer.parseInt(st.nextToken());
    int[] primes = new int[K];
    long[] humbles = new long[N+1]; N = 1; humbles[0] = 1;
    int[] mins = new int[K];
    for(int a=0;a<mins.length;a++) mins[a] = 0;
    st = new StringTokenizer(f.readLine());
    for(int a=0;a<K;a++) primes[a] = Integer.parseInt(st.nextToken());
    
    while(N<humbles.length){
      int n = 0;
      for(int a=0;a<primes.length;a++) if(primes[a]*humbles[mins[a]]<primes[n]*humbles[mins[n]]) n=a;
      humbles[N++] = primes[n]*humbles[mins[n]];
      for(int a=0;a<primes.length;a++) if(primes[a]*humbles[mins[a]]==humbles[N-1]) mins[a]++;
    }
    
    out.println(humbles[N-1]);                       
    out.close();
  }
}

