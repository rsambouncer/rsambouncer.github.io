/*
ID: 255901
LANG: JAVA
PROG: milk3
*/
import java.io.*;
import java.util.*;

class milk3 {
  public static int capA,capB,capC,len;
  public static boolean[] stateCheck;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk3.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    capA = Integer.parseInt(st.nextToken());
    capB = Integer.parseInt(st.nextToken());
    capC = Integer.parseInt(st.nextToken());
    len = (capA+1)*(capB+1)*(capC+1);
    stateCheck = new boolean[len];

    solve(0,0,capC);
    
    String f1Nal = "";
    for(int c=Math.max(0,capC-capB);c<=capC;c++){
      if(stateCheck[stateInd(0,capC-c,c)]){
        f1Nal+=" "+c;
      }
    }
    
    out.println(f1Nal.substring(1));
    out.close();                                  
  }
  public static int stateInd(int a,int b,int c){
    return a*(capB+1)*(capC+1)+b*(capC+1)+c;
  }
  public static void solve(int a,int b,int c){
    if(stateCheck[stateInd(a,b,c)]) return;
    stateCheck[stateInd(a,b,c)] = true;
    
    //A->B
    if(a+b>capB) solve(a+b-capB,capB,c); else solve(0,a+b,c);
    //A->C
    if(a+c>capC) solve(a+c-capC,b,capC); else solve(0,b,a+c);
    //B->A
    if(b+a>capA) solve(capA,b+a-capA,c); else solve(b+a,0,c);
    //B->C
    if(b+c>capC) solve(a,b+c-capC,capC); else solve(a,0,b+c);
    //C->A
    if(c+a>capA) solve(capA,b,c+a-capA); else solve(a+c,b,0);
    //C->B
    if(c+b>capB) solve(a,capB,c+b-capB); else solve(a,c+b,0);
    
  }
}




