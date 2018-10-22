/*
ID: 255901
LANG: JAVA
PROG: frac1
*/
import java.io.*;
import java.util.*;

public class frac1 {
  public static class fraction implements Comparable<fraction>{
    public int n,d;
    public fraction(int a,int b){n=a;d=b;}
    public int compareTo(fraction oth){
      int dif = getDif(oth);
      if(dif!=0) return dif;
      return d-oth.d;
    }
    public int getDif(fraction oth){
      return n*oth.d - d*oth.n;
    }
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("frac1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
    
    int num = Integer.parseInt(f.readLine());
    
    fraction[] fractions = new fraction[num+(num+1)*num/2];
    int c = 0;
    for(int b=1;b<=num;b++) for(int a=0;a<=b;a++) fractions[c++] = new fraction(a,b);
    Arrays.sort(fractions);
    
    out.println("0/1");
    for(int a=1;a<fractions.length;a++){
      if(fractions[a].getDif(fractions[a-1])!=0) out.println(fractions[a].n+"/"+fractions[a].d);
    }
                        
    out.close();                                  
  }
}