/*
ID: 255901
LANG: JAVA
PROG: msquare
*/
import java.io.*;
import java.util.*;

public class msquare {
  public static int[] powers = {1,1,2,6,24,120,720,5040,40320};
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("msquare.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    String[] positions = new String[powers[8]];
    int[] tAs = new int[powers[8]];
    int[] tBs = new int[powers[8]];
    int[] tCs = new int[powers[8]];
    int goal = makeInt(new int[]{Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1,
                            Integer.parseInt(st.nextToken())-1});
    
    for(int a=0;a<positions.length;a++){
      tAs[a] = transformA(a);
      tBs[a] = transformB(a);
      tCs[a] = transformC(a);
    }
    
    positions[40319] = "";
    String f1Nal = "";
    
    for(int n=0;;n++){
      for(int a=0;a<positions.length;a++)
        if(positions[a]!=null&&positions[a].length()==n){
          String sA = positions[tAs[a]];
          String sB = positions[tBs[a]];
          String sC = positions[tCs[a]];
          String org = positions[a];
          if(sA==null||sA.length()==n+1&&sA.compareTo(org+"A")>0) positions[tAs[a]] = org+"A";
          if(sB==null||sB.length()==n+1&&sB.compareTo(org+"B")>0) positions[tBs[a]] = org+"B";
          if(sC==null||sC.length()==n+1&&sC.compareTo(org+"C")>0) positions[tCs[a]] = org+"C";
        }
        if(positions[goal]!=null){
          f1Nal = positions[goal].length()+"\n"+positions[goal];
          break;
        }
    }

    out.println(f1Nal);
    out.close();
  }
  public static int makeInt(int[] input){
    int f1Nal = 0;
    for(int a=0;a<input.length;a++){
      int m = 0;
      int f = 0;
      while(input[m+f]!=a) if(input[m+f]>a) m++; else f++;
      f1Nal+=powers[a]*(f);
    }
    return f1Nal;
  }
  public static int[] makeAr(int num){
    int[] f1Nal = {0,0,0,0,0,0,0,0};
    for(int a=f1Nal.length-1;a>=0;a--){
      int p = (num%powers[a+1])/powers[a];
      for(int b=0;b<=p;b++) if(f1Nal[b]>a) p++;
      f1Nal[p] = a;
    }
    return f1Nal;
  }
  public static int transformA(int num){
    return powers[8]-num-1;
  }
  public static int transformB(int num){
    int[] ar = makeAr(num);
    int hh = ar[3];
    ar[3] = ar[2];
    ar[2] = ar[1];
    ar[1] = ar[0];
    ar[0] = hh;
    hh = ar[4];
    ar[4] = ar[5];
    ar[5] = ar[6];
    ar[6] = ar[7];
    ar[7] = hh;
    return makeInt(ar);
  }
  public static int transformC(int num){
    int[] ar = makeAr(num);
    int hh = ar[1];
    ar[1] = ar[6];
    ar[6] = ar[5];
    ar[5] = ar[2];
    ar[2] = hh;
    return makeInt(ar);
  }
}


