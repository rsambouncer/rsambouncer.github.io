/*
ID: 255901
LANG: JAVA
PROG: 248
*/
import java.io.*;
import java.util.*;

public class twofoureight {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("248.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[] puzzle = new int[len];
    for(int a=0;a<len;a++) puzzle[a] = Integer.parseInt(f.readLine());
    out.println(solve(puzzle));
    out.close();                   
  }
  public static int solve(int[] puzzle){
    if(puzzle.length==0) return 0;
    int b = 1;
    while(b<len&&puzzle[b-1]>=puzzle[b]) b++;
    if(b==len){
      
    }
  }
}