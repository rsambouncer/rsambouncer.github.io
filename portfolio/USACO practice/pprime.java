/*
ID: 255901
LANG: JAVA
PROG: pprime
*/
import java.io.*;
import java.util.*;

class pprime {
  public static int[] primes;
  public static int lowerb;
  public static int upperb;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("pprime.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    lowerb = Integer.parseInt(st.nextToken());
    upperb = Integer.parseInt(st.nextToken());
    
    primes = makeprimes((int)Math.sqrt(upperb)+2);
    
    
    if(5>=lowerb&&5<=upperb) out.println(5);
    if(7>=lowerb&&5<=upperb) out.println(7);
    
    for(int a=1;;a*=10){
        for(int b=a;b<10*a;b++){
          int num = Integer.parseInt(""+b+new StringBuffer(Integer.toString(b)).reverse());
          if(num>upperb){out.close();return;}
          if(isPrime(num)&&num>=lowerb) out.println(num);
        }
        for(int b=a;b<10*a;b++)for(int c=0;c<10;c++){
          int num = Integer.parseInt(""+b+c+new StringBuffer(Integer.toString(b)).reverse());
          if(num>upperb){out.close();return;}
          if(isPrime(num)&&num>=lowerb) out.println(num);
        }
    }
    
  }
  /**********************************************************************
  Note: I wrote the makeprimes function a long time ago,
  it's one of those handy things i keep around
  **********************************************************************/
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




