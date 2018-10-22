/*
ID: 255901
LANG: JAVA
PROG: talent
*/
import java.io.*;
import java.util.*;

public class talent {
  public static class Cow{
    public int w,t;
    public Cow(int ww, int tt){w=ww;t=tt;}
  }
  public static Cow[] cows;
  public static int len,www;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("talent.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
        len = Integer.parseInt(st.nextToken());
        www = Integer.parseInt(st.nextToken());
        
        cows = new Cow[len];
        int ttlt = 0;
        int ttlw = 0;
        for(int a=0;a<len;a++){
          st = new StringTokenizer(f.readLine());
          cows[a] = new Cow(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
          ttlt+=cows[a].t;
          ttlw+=cows[a].w;
        }
        //Arrays.sort(cows,(a,b)->a.t*b.w-b.t*a.w);
        
        out.println(solve(0,ttlt,ttlw));                       
        out.close();                                  
      }
      public static int solve(int st, int pt, int pw){
        int aa = 1000*pt/pw;
        int n = -1;
        for(int a=st;a<len;a++){
            if(pw-cows[a].w<www) continue;
            if(1000*(pt-cows[a].t)/(pw-cows[a].w)>aa){
                n = a;
                aa = 1000*(pt-cows[a].t)/(pw-cows[a].w);
            }
        }
        if(n==-1) return aa;
        Cow hh = cows[st];
        cows[st] = cows[n];
        cows[n] = hh;
        return solve(st,pt-cows[st].t,pw-cows[st].w);
      }
}