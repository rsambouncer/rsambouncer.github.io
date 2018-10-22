/*
ID: 255901
LANG: JAVA
PROG: butter
*/
import java.io.*;
import java.util.*;

public class butter {
  public static class Heap<T extends DD>{
    public T[] ar; public int N; 
    public Heap(T[] arr){ // (a,b)-> a.n-b.n is min heap
      ar = arr; N=0;
    }
    public int add(T node){
      int p = N++;
      while(p>0&&node.a<ar[(p-1)/2].a){
        ar[p] = ar[(p-1)/2];
        p = (p-1)/2;
      }
      ar[p] = node;
      return p;
    }
    public T remove(int p){
      T f1Nal = ar[p];
      if(ar[--N].a<ar[p].a){ //trickle up
        while(p>0&&(ar[N].a<ar[(p-1)/2].a)){
          ar[p] = ar[(p-1)/2];
          p = (p-1)/2;
        }
      }else{                              //trickle down
        while(2*p+1<N){
          int q = 2*p+1;
          if(2*p+2<N&&ar[2*p+1].a>ar[2*p+2].a) q = 2*p+2;
          if(ar[N].a<=ar[q].a) break;
          ar[p] = ar[q];
          p = q;
        }
      }
      ar[p] = ar[N];
      return f1Nal;
    }
  }
  public static class DD{
    public int a, b;
    public DD(int a, int b){this.a=a;this.b=b;}
  }
  public static class Edge{
    public int d,a,b;
    public Edge(int a,int b,int d){this.a=a;this.b=b;this.d=d;}
  }
  
  
  
  
  
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("butter.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
    

    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken());
    int P = Integer.parseInt(st.nextToken());
    int C = Integer.parseInt(st.nextToken());
    
    int[] cows = new int[P];
    int[][] dist = new int[P][P];
    Edge[] edges = new Edge[C*2];
    int[] starts = new int[P+1];
    Heap<DD> queue = new Heap<DD>(new DD[C]);
    for(int a=0;a<P;a++) for(int b=0;b<P;b++) if(a!=b) dist[a][b] = -1;
    for(int a=0;a<N;a++) cows[Integer.parseInt(f.readLine())-1]++;
    for(int a=0;a<C;a++){
      st = new StringTokenizer(f.readLine());
      int x = Integer.parseInt(st.nextToken())-1; 
      int y = Integer.parseInt(st.nextToken())-1;
      int d = Integer.parseInt(st.nextToken());
      edges[2*a+0] = new Edge(x,y,d);
      edges[2*a+1] = new Edge(y,x,d);
    }
    Arrays.sort(edges,(e1,e2)-> e1.a-e2.a);
    if(C>0){
      int b=0;
      for(int a=0;a<2*C;a++)
        while(edges[a].a>b) starts[++b] = a;
      while(P>b) starts[++b] = 2*C;
    }
    
    
    for(int a=0;a<P;a++) if(cows[a]!=0){
      //fill in dist[a][]
      for(int b=starts[a];b<starts[a+1];b++)
        if(dist[a][edges[b].b]==-1) 
          queue.add(new DD(edges[b].d,edges[b].b));
      while(queue.N>0){
        DD curr = queue.remove(0);
        if(dist[a][curr.b]==-1){ //otherwise, should be less than distance found here
          dist[a][curr.b] = curr.a;
          for(int b=starts[curr.b];b<starts[curr.b+1];b++)
            if(dist[a][edges[b].b]==-1) 
              queue.add(new DD(edges[b].d+curr.a,edges[b].b));
        }
      }
    }
    
    
    int min = -1;
    for(int a=0;a<P;a++){
      int sum = 0;
      boolean good = true;
      for(int b=0;b<P;b++)
        if(dist[b][a]==-1&&cows[b]!=0){ good = false; break;}
        else sum+=dist[b][a]*cows[b];
      if(good&&(sum<min||min==-1)) min = sum;
    }
    
    out.println(min);                       
    out.close();                                  
  }
}


