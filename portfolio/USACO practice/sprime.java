/*
ID: 255901
LANG: JAVA
PROG: sprime
*/
import java.io.*;
import java.util.*;

class sprime {
public static int[] primes;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("sprime.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
    
    int len = Integer.parseInt(f.readLine());
    primes = makeprimes((int)Math.pow(10,len*0.5)+2);
    
    int[] sprimes = getSprimes(len);
    for(int a=0;a<sprimes.length;a++) out.println(sprimes[a]);
    
    out.close();                                  
  }
  public static int[] getSprimes(int len){
    if(len==1) return new int[]{2,3,5,7};
    int[] old = getSprimes(len-1);
    int[] ar = new int[5*old.length];
    int c=0;
    for(int a=0;a<old.length;a++)for(int b=1;b<10;b+=2){
        int num = old[a]*10+b;
        if(isPrime(num)) ar[c++] = num;
    }
    old = new int[c];
    for(int a=0;a<c;a++) old[a] = ar[a];
    return old;
  }
  public static int[] makeprimes(int n){
    boolean[] nums = new boolean[n+1];
    int cc = 1;
    for(int a=3;a<=n;a+=2)nums[a] = true;
    int stop = (int)Math.sqrt(n)+1;if(stop%2==0)stop++;
    for(int a=3;a<stop;a+=2)if(nums[a]){
      cc++;
      for(int b=a*a;b<=n;b+=a)nums[b] = false;
    }
    for(int a=stop;a<=n;a++)if(nums[a])cc++;
    
    int[] primes = new int[cc];
    primes[0] = 2;cc=1;
    for(int a=3;a<=n;a+=2)if(nums[a])primes[cc++]=a;
    
    return primes;
  }
  public static boolean isPrime(int num){
    for(int a=0;a<primes.length&&primes[a]*primes[a]<=num;a++) if(num%primes[a]==0) return false;
    return true;
  }
}