/*
ID: 255901
LANG: JAVA
PROG: reststops
*/
import java.io.*;
import java.util.*;

public class reststops {
  /*public static class Rstop implements Comparable<Rstop>{
    int x;
    int c;
    public Rstop(int x,int c){this.x = x;this.c = c;}
    public int compareTo(Rstop oth){return this.x-oth.x;}
  }*/
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("reststops.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("reststops.out")));
    
    StringTokenizer strr = new StringTokenizer(f.readLine());
    int TLEN = Integer.parseInt(strr.nextToken());
    int len = Integer.parseInt(strr.nextToken());
    int rF = Integer.parseInt(strr.nextToken());
    int rB = Integer.parseInt(strr.nextToken());
    
    int[] xs = new int[len];
    int[] cs = new int[len];
    for(int a=0;a<len;a++){
      strr = new StringTokenizer(f.readLine());
      xs[a] = Integer.parseInt(strr.nextToken());
      cs[a] = Integer.parseInt(strr.nextToken());
    }
    
    int big = cs[len-1];
    for(int a=len-2;a>=0;a--){
      if(cs[a]>big) big = cs[a];
      else cs[a] = big;
    }
    
    int prev = 0;
    long f1Nal = 0;
    for(int a=0;a<len;a++){
      f1Nal+= (xs[a]-prev)*(cs[a]);
      prev = xs[a];
    }
    
    f1Nal*=rF-rB;
    out.println(f1Nal);                       
    out.close();                                  
  }
}