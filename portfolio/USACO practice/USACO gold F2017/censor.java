/*
ID: 255901
LANG: JAVA
PROG: censor
*/
import java.io.*;
import java.util.*;

public class censor {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("censor.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
    
    
    String STR = f.readLine();
    boolean[] isIn = new boolean[STR.length];
    int[] prefixhashes = new int[STR.length];
    int[] placevalue = new int[STR.length];
    int num = Integer.parseInt(f.readLine());
    String[] censors = new String[num];
    for(int a=0;a<num;a++){
      censors[a] = f.readLine();
    }
    
    for(int a=0;a<)
    
    
    
    
    
    int inp = Integer.parseInt(f.readLine());
    StringTokenizer st = new StringTokenizer(f.readLine());
    int i1 = Integer.parseInt(st.nextToken()); 

    int f1Nal = 0;

    
    out.println(f1Nal);                       
    out.close();                                  
  }
  public static int search(String )
}