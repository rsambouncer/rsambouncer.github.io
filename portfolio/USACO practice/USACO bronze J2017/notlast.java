/*
ID: 255901
LANG: JAVA
PROG: notlast
*/
import java.io.*;
import java.util.*;

public class notlast {
  public static String[] names = {"Bessie", "Elsie", "Daisy", "Gertie", "Annabelle", "Maggie", "Henrietta"};
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("notlast.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("notlast.out")));
    
    int[] cows = new int[7];
    
    int len = Integer.parseInt(f.readLine());
    for(int a=0;a<len;a++){
      StringTokenizer snc = new StringTokenizer(f.readLine());
      String name = snc.nextToken();
      cows[getInd(name)]+=Integer.parseInt(snc.nextToken());
    }
    
    int[] copy = Arrays.copyOf(cows,7);
    Arrays.sort(copy);
    
    int a=0;
    for(;a<6;a++){
      if(copy[a]!=copy[a+1]) break;
    }
    int b = -1;
    if(a==6){out.println("Tie");out.close();return;}
    if(a==5) b = copy[6];
    else{
      b = copy[a+1];
      if(copy[a+1]==copy[a+2]){out.println("Tie");out.close();return;}
    }
    
    for(a=0;a<7;a++){
      if(cows[a]==b) out.println(names[a]);
    }
    out.close();                                  
  }
  public static int getInd(String name){
    for(int a=0;a<7;a++){
      if(name.equals(names[a])) return a;
    }
    return -1;
  }
}