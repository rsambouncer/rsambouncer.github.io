/*
ID: 255901
LANG: JAVA
PROG: zerosum
*/
import java.io.*;
import java.util.*;

public class zerosum {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("zerosum.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));
    
    int N = Integer.parseInt(f.readLine());
    int[][] pos = generate(N);
    for(int[] a:pos) if(works(a)) out.println(stringy(a));

    out.close();                                  
  }
  public static int[][] generate(int N){
    if(N==2) return new int[][]{
      new int[]{0},
      new int[]{1},
      new int[]{2}
    };
    int[][] prev = generate(N-1);
    int[][] f1Nal = new int[prev.length*3][N-1];
    for(int a=0;a<prev.length;a++)
      for(int b=0;b<prev[a].length;b++){
        f1Nal[a][b+1] = prev[a][b];
        f1Nal[a+prev.length][b+1] = prev[a][b];
        f1Nal[a+2*prev.length][b+1] = prev[a][b];
      }
    for(int a=0;a<prev.length;a++){
      f1Nal[a][0] = 0;
      f1Nal[a+prev.length][0] = 1;
      f1Nal[a+2*prev.length][0] = 2;
    }
    return f1Nal;
  }
  public static boolean works(int[] ar){
    int len = ar.length+1;
    for(int a=0;a<ar.length;a++) if(ar[a]==0) len--;
    int[] dig = new int[len];
    dig[0] = 1;
    int c=1, d=2;
    for(int a=0;a<ar.length;a++){
      if(ar[a]>0) dig[c++] = d++;
      else{
        dig[c-1] = dig[c-1]*10+d;
        d++;
      }
    }
    int sum = dig[0]; c = 1;
    for(int a=0;a<ar.length;a++){
      if(ar[a]==1) sum+=dig[c++];
      else if(ar[a]==2) sum-=dig[c++];
    }
    return sum==0;
  }
  public static String stringy(int[] ar){
    int c=2;
    String f1Nal = "1";
    for(int a=0;a<ar.length;a++){
      if(ar[a]==0) f1Nal=f1Nal+" "+c;
      else if(ar[a]==1) f1Nal=f1Nal+"+"+c;
      else if(ar[a]==2) f1Nal=f1Nal+"-"+c;
      c++;
    }
    return f1Nal;
  }
}