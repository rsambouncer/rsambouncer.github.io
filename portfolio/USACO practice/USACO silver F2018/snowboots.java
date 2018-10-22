/*
ID: 255901
LANG: JAVA
PROG: snowboots
*/
import java.io.*;
import java.util.*;

public class snowboots {
  public static int[] tileD;
  public static int[] bootS;
  public static int[] bootF;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("snowboots.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
    
    StringTokenizer strr = new StringTokenizer(f.readLine());
    int lenT = Integer.parseInt(strr.nextToken());
    int lenB = Integer.parseInt(strr.nextToken());
    
    tileD = new int[lenT];
    strr = new StringTokenizer(f.readLine());
    for(int a=0;a<lenT;a++){
      tileD[a] = Integer.parseInt(strr.nextToken());
    }
    bootS = new int[lenB];
    bootF = new int[lenB];
    for(int a=0;a<lenB;a++){
      strr = new StringTokenizer(f.readLine());
      bootS[a] = Integer.parseInt(strr.nextToken());
      bootF[a] = Integer.parseInt(strr.nextToken());
    }
    
    //setup all done
    
    out.println(canWear());                      
    out.close();                                  
  }
  public static int canWear(){
    int lenT = tileD.length;
    int lenB = bootS.length;
    boolean[] beOn = new boolean[lenT];
    
    for(int b=0;b<lenB;b++){ 
        int tr = bootF[b];
        for(int t=0;t<lenT;t++){
            if(tr>0){
            if(tileD[t]>bootS[b]) tr--;
            else {beOn[t] = true;tr = bootF[b];}
            }
            else if(beOn[t]) tr = bootF[b];
        }
        if(beOn[lenT-1]) return b;
    }
    
    return 100000;
  }
  
  
  
}