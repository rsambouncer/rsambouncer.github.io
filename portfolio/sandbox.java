public class hi{
    
    public int[] rotateLeft3(int[] nums) {
      int[][] ar = new int[2][nums.length]; //nums[0] is answer, nums[1] is order
      for(int a=0;a<nums.length;a++){ar[0][a]=nums[a];ar[1][a]=a;}
      long mm = ff(nums.length);
      for(long a=0;a<mm;a++){
        next(ar);
        if(isrr(ar[0], nums)) return ar[0];
      }
      //bottom line should NEVER run
      return new int[]{999};
    
    }
    private long ff(int a){if(a<2)return 1;return a*ff(a-1);}
    private boolean isrr(int[] nums, int[] ar){
      if(nums.length!=ar.length)return false;
      if(nums.length<2) return true;
      for(int a=0;a<nums.length-2;a++){
        if(nums[a]!=ar[a+1]) return false;
      }
      return nums[nums.length-1]==ar[0];
    }
    private void next(int[][] ar){
      int a = ar[1].length-1;
      while(a>0&&ar[1][a]<ar[1][a-1])a--;
      int b = 0;
      while(b<(ar[1].length-a)/2){ //this loop caused me so much pain...
        int h = ar[1][a+b]; int d = ar[0][a+b];
        ar[1][a+b] = ar[1][ar[1].length-1-b];ar[0][a+b] = ar[0][ar[1].length-1-b];
        ar[1][ar[1].length-1-b] = h;ar[0][ar[1].length-1-b] = d;
        b++;
      }
      if(a>0){
        b = ar[1].length-1;
        while(b>0&&ar[1][b]<ar[1][a-1])b--;
        int h=ar[1][a-1];int d=ar[0][a-1];
        ar[1][a-1]=ar[1][b];ar[0][a-1]=ar[0][b];
        ar[1][b] = h;ar[0][b] = d;
      }
    }
    
}


public int find_prime(int n){
  //range(0-2000000)
  
  if(n>2000000) return 0-1;
  if(n<6){
    if(n<1) return 0;
    if(n==1) return 2;
    if(n==2) return 3;
    if(n==3) return 5;
    if(n==4) return 7;
    if(n==5) return 11;
  }
  int[] every_100000th = {
    0,1299709,2751059,4256233,5800079,
    7368787,8960453,10570841,12195257,13834103,
    15485863,17144489,18815231,20495843,22182343,
    23879519,25582153,27290279,29005541,30723761,
    32452843 //2000000th prime
  };
  if(n%100000==0) return every_100000th[n/100000];
  
  int lowerprime = every_100000th[n/100000];
  int rrrr = Math.min((int)(
    n*Math.log(n)+Math.log(Math.log(n))            
    ),every_100000th[n/100000+1]);
  if(lowerprime==0){
    return makeprimes(rrrr)[n-1];
  }
  int[] primes = makeprimes((int)Math.sqrt(rrrr)+1);
  rrrr-=lowerprime;
  boolean[] nums = new boolean[rrrr]; //prime to upper bound -1
  for(int a=0;a<rrrr;a+=2) nums[a] = true;
  
  //the task at hand is to find the primes in this range
  //then count them
  //nums[a] = lowerprime+a;
  
  for(int a:primes){
    for(int b = a-lowerprime%a;b<rrrr;b+=a)nums[b]=false;
  }
  int cc = 0;n%=100000;
  for(int a=2;;a++){
    if(nums[a])cc++;
    if(cc==n) return lowerprime+a;
  }
}


public int[] makeprimes(int n){
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