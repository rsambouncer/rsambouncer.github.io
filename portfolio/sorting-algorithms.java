public final class sortTest{
    private static class sortB{
        void sortB(){}
        private boolean test = true;
            public boolean test(){return test;}
            public boolean test(int[] ra){
                for(int aa=1;aa<ra.length;aa++){
                    if(ra[aa]<ra[aa-1]){test = false;return false;}
                }
                test = true; return true;
            }
        long time = 0;
        seet setUp;
            interface seet{int[] setset(int[] hi);}
        int[] run(int[] a){return setUp.setset(a);}
    }
    public static void main(String[] args){
        sortB[] sorts = {new sortB(),new sortB(),new sortB(),new sortB()};
            sorts[0].setUp = new sortB.seet(){public int[] setset(int[] hi){return sort.quick(hi);}};
            sorts[1].setUp = new sortB.seet(){public int[] setset(int[] hi){return sort.pmerge(hi);}};
            sorts[2].setUp = new sortB.seet(){public int[] setset(int[] hi){return sort.bubble(hi);}};
            sorts[3].setUp = new sortB.seet(){public int[] setset(int[] hi){return sort.select(hi);}};
      
        long tt = System.currentTimeMillis();
        while(System.currentTimeMillis()-tt<4000){
            for(int a=0;a<sorts.length;a++){
                int l = (int)(Math.random()*1000); int[] ra = new int[l];
                for(int b=0;b<l;b++){ra[b] = (int)(Math.random()*1000);}
                if(sorts[a].test()){
                    long st = System.currentTimeMillis();
                    ra = sorts[a].run(ra);
                    sorts[a].time += (System.currentTimeMillis()-st);
                    sorts[a].test(ra);
                }
            }
        }
        for(int a=0;a<sorts.length;a++){
            System.out.println(sorts[a].test()+", "+sorts[a].time);
        }
    }
}

public final class sort{
    public static void main(String[] args){
        int sorts = 4;
        System.out.println(sorts);
    }
    public static int[] quick(int[] ar){
        if(ar.length<2) return ar;
        boolean[] bar = new boolean[ar.length+2];
        for(int a=bar.length-3;a>-1;a--) bar[a] = false;
        bar[bar.length-1] = false; bar[bar.length-2] = true;
        
        int a=0;
        while(a<bar.length-1){
            int b=a+1; while(!bar[b])b++;
            if(b-a==1) bar[a] = true;
        /**/else{
                b--; int c=a+1;
                while(c<b){
                    if(ar[b]>=ar[a])b--;
                    else if(ar[c]<=ar[a])c++;
                    else {int h=ar[b];ar[b]=ar[c];ar[c]=h;}
                }
                if(ar[a]>ar[c]){int h=ar[a];ar[a]=ar[c];ar[c]=h;bar[c] = true;}
                else bar[a] = true;
        /**/}
            while(bar[a]) a++;
        }
        return ar;
    }
    public static int[] select(int[] ar){
        int s = 1;
        for(int z=ar.length-1;z>0;z--){
            while(s<=z&&ar[s]>=ar[s-1])s++;
            if(s>z)return ar;
            int a=s;
            int b=a-1;
            while(a<z){if(ar[a]>ar[b]){b=a;}a++;}
            if(ar[b]>ar[z]){
                int h=ar[b];ar[b]=ar[z];ar[z]=h;
                if(b<s)s--;
            }
        }
        return ar;
    }
    public static int[] bubble(int[] ar){
        for(int a=0;a<ar.length-1;a++){
            int b=ar.length-1;
            while(b>a){
                if(ar[b]<ar[b-1])break;
                b--;
            }if(b==a)return ar;
            while(b>a){
                if(ar[b]<ar[b-1]){int h=ar[b];ar[b]=ar[b-1];ar[b-1]=h;}
                b--;
            }
        }
        return ar;
    }
    
    public static int[] pmerge(int[] ar){
        for(int g=1;g<ar.length;g*=2){
            for(int z=g;z>1;z--){
                int a=g;
                while(a<ar.length){
                    if(ar[a-z]>ar[a]){int h=ar[a];ar[a]=ar[a-z];ar[a-z]=h;}    
                    if((a+g-z+1)%(g*2)==0) a+=2*g-z+1;
                    else a++;
                }
            }
            int a=g;
            while(a<ar.length){
                if(ar[a-1]>ar[a]){int h=ar[a];ar[a]=ar[a-1];ar[a-1]=h;}
                a+=2*g;
            }
        }
        return ar;
    }
    
    public static int[] tripi(int[] ar){
            if(ar.length<2) return ar;
            boolean[] bar = new boolean[ar.length+2];
            for(int a=bar.length-3;a>-1;a--) bar[a] = false;
            bar[bar.length-1] = false; bar[bar.length-2] = true;
      
            int a=0;int h;int c;int d;int e;
            while(a<bar.length-1){ 
                int b=a+1; while(!bar[b])b++;
                if(--b==a)bar[a] = true;
              
                else if(b-a>15){////////////////////////////////////////////////
                  if(ar[a]>ar[a+1]){h=ar[a];ar[a]=ar[a+1];ar[a+1]=h;}
                    if(ar[a+1]>ar[a+2]){h=ar[a+1];ar[a+1]=ar[a+2];ar[a+2]=h;}
                    if(ar[a]>ar[a+1]){h=ar[a];ar[a]=ar[a+1];ar[a+1]=h;}
                    c=a+3;d=c;e=b;           
                  
                    while(d<=e){
                        if(ar[d]>ar[a+1]){
                          while(ar[e]>=ar[a+1]&&e>a){
                            if(ar[e]>ar[a+2]){h=ar[e];ar[e]=ar[b];ar[b--]=h;}
                            e--;
                          }
                          if(d<e){
                            h=ar[d];ar[d]=ar[e];ar[e]=h;             
                            if(ar[e]>ar[a+2]){h=ar[e];ar[e]=ar[b];ar[b--]=h;}
                            e--;
                          }
                          else break;
                        }
                        if(ar[d]<ar[a]){h=ar[d];ar[d]=ar[c];ar[c++]=h;}
                        d++;
                    }
                    d--;
                    
                    if(c==a+4){h=ar[a+3];ar[a+3]=ar[a+2];ar[a+2]=ar[a+1];ar[a+1]=ar[a];ar[a]=h;}
                    else if(c==a+5){h=ar[a+4];ar[a+4]=ar[a+2];ar[a+2]=ar[a];ar[a]=ar[a+3];ar[a+3]=ar[a+1];ar[a+1]=h;}
                    else{
                      h=ar[a];ar[a]=ar[c-3];ar[c-3]=h;
                      h=ar[a+1];ar[a+1]=ar[c-2];ar[c-2]=h;
                      h=ar[a+2];ar[a+2]=ar[c-1];ar[c-1]=h;
                    }
                    if(d==c){h=ar[d];ar[d]=ar[c-1];ar[c-1]=ar[c-2];ar[c-2]=h;}
                    else{
                      h=ar[c-2];ar[c-2]=ar[d-1];ar[d-1]=h;
                      h=ar[c-1];ar[c-1] = ar[d];ar[d] = h;
                    }
                    h=ar[d];ar[d]=ar[b];ar[b]=h;
                  
                  
                    bar[c-3] = true;
                    bar[d-1] = true;
                    bar[b] = true;
                  
                }///////////////////////////////////////////////////////////////
                else{
                    while(a<b){
                      c = a;
                      for(d=a+1;d<=b;d++)if(ar[d]<ar[c])c=d;
                        h=ar[a];ar[a]=ar[c];ar[c]=h;
                        a++;
                    }
                    a++;  
                }
                while(bar[a]) a++;
            }
            return ar;
    }
      
    private class failed_castle_sort{
      final static int[] twos = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
        2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576,
        2097152, 4194304, 8388608, 16777216, 33554432, 
        67108864, 134217728, 268435456, 536870912, 1073741824};
      public static int[] castle(int[] ar){
      if(ar.length<2) return ar;
      int[] index = new int[ar.length];
      int end = ar.length-1,tnd = end,
      prev,pot = 1,a,b,h;
      
      index[end-1] = end-1;
      for(a=1;a<tnd;a+=2){
        if(ar[a-1]>ar[a]) index[a]=a-1;
        else index[a]=a;
      }
      while(twos[pot]<end){
        prev = twos[pot-1],tnd = end-prev;int incA = twos[pot+1];
        for(a=twos[pot];a<tnd;a+=incA){
          if(ar[index[a-prev]]>ar[index[a+prev]]) index[a]=index[a-prev];
          else index[a]=index[a+prev];
        }if(a<end){
          if(a==end-1){if(ar[index[a-prev]]>ar[index[a]])index[a]=index[a-prev];}
          else{
            b=0;while(a+twos[b]<end)b++;b=a+twos[b-1];
            if(ar[index[a-prev]]>ar[index[b]]) index[a]=index[a-prev];
            else index[a]=index[b];
          }
        }
        pot++;
      }
      pot--;
      //index array is now set up - index[0] and index[n] of n>=end have no purpose
      
      while(end>1){if(ar[end]<ar[index[twos[pot]]]){
        h=ar[end];ar[end]=ar[index[twos[pot]]];ar[index[twos[pot]]]=h;
        h = index[twos[pot]];
        if(h%2==1){if(ar[h-1]>ar[h])index[h]=h-1;}
          else{if(ar[h+1]>ar[h])index[h+1]=h+1;}
        for(a=2;a<pot;a++){
          prev = twos[a-1],tnd = h-h%twos[a]+prev;
          if(tnd+prev>=end){
            b=0;while(tnd+twos[b]<end)b++;b=tnd+twos[b-1];
            if(ar[index[tnd-prev]]>ar[index[b]]) index[tnd]=index[tnd-prev];
            else index[tnd]=index[b];
          }
          if(ar[index[tnd-prev]]>ar[index[tnd+prev]]) index[tnd] = index[tnd-prev];
          else index[tnd] = index[tnd+prev];
        }
        
        
        
      }end--;}
      
      if(ar[0]>ar[1]){h=ar[0];ar[0]=ar[1];ar[1]=h;}
      
      System.out.println(Arrays.toString(ar));
      System.out.println(Arrays.toString(indexes));
      
      return ar;
    }
    }
    public static int[] heap(int[] ar){
      if(ar.length<2) return ar;
      int a=ar.length-1,h,b,c;
      
      if(a%2==1){
        if(ar[a/2]<ar[a]){h=ar[a];ar[a]=ar[a/2];ar[a/2]=h;}
        a--;
      }
      for(;a>0;a-=2){ 
        b = (a-1)/2;
        h = ar[b];
        c = a;
        while(c<ar.length){
          if(ar[c-1]>ar[c])c--;
          if(ar[c]>h)ar[b]=ar[c];
          else{break;}
          b=c;c=2*b+2;
        }
        if(c==ar.length){
          if(ar[--c]>h){ar[b]=ar[c];ar[c]=h;}
          else ar[b]=h;
        }else ar[b]=h;
      }
      
      
      for(a=ar.length-1;a>0;a--){
        h=ar[a];ar[a]=ar[0];
        b=0;c=2;
        while(c<a){
          if(ar[c-1]>ar[c]){
            if(h>=ar[c-1]){ar[b]=h;break;}
            else{ar[b]=ar[c-1];b=c-1;}
          }
          else{
            if(h>=ar[c]){ar[b]=h;break;}
            else{ar[b]=ar[c];b=c;}
          }
          c = 2*b+2;
        }
        if(c==a){ar[b]=ar[c-1];ar[c-1] = h;}
        else if(c>a){
          if(h>ar[b])ar[(b-1)/2]=h;
          else ar[b]=h;
        }
      }
      if(ar[0]>ar[1]){h=ar[0];ar[0]=ar[1];ar[1]=h;}
      
      return ar;
    }
    public static int[] quad_merge(int[] ar1){
      if(ar1.length<2) return ar1;
      int[] ar2 = new int[ar1.length];
      int[] hh;
      boolean copy = false;
      int length=ar1.length,x,p,a,b,c,d,aa,bb,cc,dd;
      
      for(int z=1;z<length;z*=4){ //size of runs to be merged
        for(x=length-length%(4*z)-(4*z);x>=0;x-=(4*z)){
          p=x;a=x;b=a+z;c=b+z;d=c+z;
          aa=b;bb=c;cc=d;dd=d+z;
          while(true){
            if(ar1[a]>ar1[b]){
              if(ar1[b]>ar1[c]){
                if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[c++];if(c==cc){c=a;cc=aa;break;}}
              }else{
                if(ar1[b]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[b++];if(b==bb){b=a;bb=aa;break;}}
              }
            }else{
              if(ar1[a]>ar1[c]){
                if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[c++];if(c==cc){c=a;cc=aa;break;}}
              }else{
                if(ar1[a]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[a++];if(a==aa){          break;}}
              }
            }
          }
          while(true){
            if(ar1[b]>ar1[c]){
              if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=b;dd=bb;break;}}
              else              {ar2[p++]=ar1[c++];if(c==cc){c=b;cc=bb;break;}}
            }else{
              if(ar1[b]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=b;dd=bb;break;}}
              else              {ar2[p++]=ar1[b++];if(b==bb){          break;}}
            }
          }
          while(true){
            if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=c;dd=cc;break;}}
            else              {ar2[p++]=ar1[c++];if(c==cc){          break;}}
          }
          while(d<dd) ar2[p++]=ar1[d++];
        }
        
        p=length-length%(4*z);
        a=p;b=a+z;c=b+z;d=c+z;aa=b;bb=c;cc=d;dd=length;
        if(d<length){ //we got a full house kids
          while(true){
            if(ar1[a]>ar1[b]){
              if(ar1[b]>ar1[c]){
                if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[c++];if(c==cc){c=a;cc=aa;break;}}
              }else{
                if(ar1[b]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[b++];if(b==bb){b=a;bb=aa;break;}}
              }
            }else{
              if(ar1[a]>ar1[c]){
                if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[c++];if(c==cc){c=a;cc=aa;break;}}
              }else{
                if(ar1[a]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=a;dd=aa;break;}}
                else              {ar2[p++]=ar1[a++];if(a==aa){          break;}}
              }
            }
          }
        }else{b=p;c=b+z;d=c+z;bb=c;cc=d;dd=length;}
        if(d<length){
          while(true){
            if(ar1[b]>ar1[c]){
              if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=b;dd=bb;break;}}
              else              {ar2[p++]=ar1[c++];if(c==cc){c=b;cc=bb;break;}}
            }else{
              if(ar1[b]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=b;dd=bb;break;}}
              else              {ar2[p++]=ar1[b++];if(b==bb){          break;}}
            }
          }
        }else{c=p;d=c+z;cc=d;dd=length;}
        if(d<length){
          while(true){
            if(ar1[c]>ar1[d]) {ar2[p++]=ar1[d++];if(d==dd){d=c;dd=cc;break;}}
            else              {ar2[p++]=ar1[c++];if(c==cc){          break;}}
          }
        }else{d=p;dd=length;}
        while(d<dd) ar2[p++]=ar1[d++];
        
        hh=ar1;ar1=ar2;ar2=hh;copy=!copy;
      }
      
      if(copy)for(x=0;x<length;x++)ar2[x]=ar1[x];
      return ar1;
    }
    public static int[] quart_quick(int[] ar){
      //
      //in place version
      //very, complicated, don't know if I should do this.
      //probably going to end up with oscillating version
      //
      if(ar.length<2) return ar;
      boolean[] bar = new boolean[ar.length+2];
      for(int a=bar.length-3;a>-1;a--) bar[a] = false;
      bar[bar.length-1] = false; bar[bar.length-2] = true;

      int a=0,b,c,d,e,f,h,p,prev,curr; //so many pointers! oh no, better explain
      boolean leftclosed = false;
      
      int[] rotate = new int[5]; //NOTE: increase once unit testing is over, the length is hard-coded in and should be changed in all the code. 
      //
      //   [ a,a+1,a+2  _ _ c * * * e_ _ f _ _ d * * b _ _ _  ] 
      //
      // stage 1: so we have our pivots a,a+1,and a+2, and we are sorting 
      //          everything from a+2 to b. the sorted areas are _ _ _
      //          and unsorted areas * * * in my diagram. 
      //          The idea is to do some "rotating" and progressively shrink 
      //          the two unsorted areas. 
      //
      //   [ a,a+1,a+2, _ _ _ _ e c _ _ _ f _ _ d * * b _ _ _ ]
      // 
      // stage 2: one of the two unsorted areas has "closed". The other area
      //          is sorted like normal, with some switching with f when 
      //          is less that the second pivot. 
      //
      // stage 3 is just doing a little bit of switching 
      //          to get the pivots into place
      //
      
      while(a<bar.length-1){ 
          b=a+1; while(!bar[b])b++;
          if(--b==a)bar[a] = true;
        
          else if(b-a>15){////////////////////////////////////////////////
              if(ar[a]>ar[a+1]){h=ar[a];ar[a]=ar[a+1];ar[a+1]=h;}
              if(ar[a+1]>ar[a+2]){h=ar[a+1];ar[a+1]=ar[a+2];ar[a+2]=h;}
              if(ar[a]>ar[a+1]){h=ar[a];ar[a]=ar[a+1];ar[a+1]=h;}
              c=a+3;f = (b-a)/2;e=f;d=f;
              prev = 0; //null vaule
              //curr = 
            
          }///////////////////////////////////////////////////////////////
          else{
              while(a<b){
                c = a;
                for(d=a+1;d<=b;d++)if(ar[d]<ar[c])c=d;
                  h=ar[a];ar[a]=ar[c];ar[c]=h;
                  a++;
              }
              a++;  
          }
          while(bar[a]) a++;
      }
      return ar;
    }

    
} 










