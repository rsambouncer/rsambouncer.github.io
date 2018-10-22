/*
ID: 255901
LANG: JAVA
PROG: moocast
*/
import java.io.*;
import java.util.*;

public class moocast {
  public static class Edge implements Comparable<Edge>{
    public int a,b,w;
    public Edge(int a,int b,int w){this.a=a;this.b=b;this.w=w;}
    public int compareTo(Edge oth){
      return this.w-oth.w;
    }
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("moocast.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[] xs = new int[len];
    int[] ys = new int[len];
    Edge[] edges = new Edge[(len*(len-1))/2];
    int[] group = new int[len];
    for(int a=0;a<len;a++) group[a] = a;
    for(int a=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      xs[a] = Integer.parseInt(st.nextToken());
      ys[a] = Integer.parseInt(st.nextToken());
    }
    
    int c=0;
    for(int a=0;a<len;a++) for(int b=0;b<len;b++) if(a<b){
      edges[c++] = new Edge(a,b,(xs[a]-xs[b])*(xs[a]-xs[b]) + (ys[a]-ys[b])*(ys[a]-ys[b]));
    }
    
    Arrays.sort(edges);
    
    
    int max = 0;
    for(Edge ee:edges){
      if(group[ee.a]!=group[ee.b]){
        max = ee.w;
        int m = group[ee.a];
        for(int a=0;a<len;a++) if(group[a]==m) group[a] = group[ee.b];
      }
    } 
    
    
    out.println(max);                       
    out.close();                                  
  }
}


