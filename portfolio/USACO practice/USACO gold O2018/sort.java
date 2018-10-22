/*
ID: 255901
LANG: JAVA
PROG: sort
*/
import java.io.*;
import java.util.*;

public class sort {
  public static int[] ar;
  public static int[] sar;
  public static int len;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("sort.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
    
    len = Integer.parseInt(f.readLine());
    ar = new int[len];
    for(int a=0;a<len;a++){
      ar[a] = Integer.parseInt(f.readLine());
    }
    
    out.println(cowsort());                       
    out.close();                                  
  }
  public static int cowsort(){
    boolean sorted = false;
    int moo = 0;
    while(!sorted){
      sorted = true;
      moo++;
      int max = 0, hh=0;
      for(int a=1;a<len;a++){
        if(ar[a]>ar[max]){
          hh = ar[max];
          for(int b=max+1;b<a;b++) ar[b-1] = ar[b];
          ar[a-1] = hh;
          max = a;
        }
      }
      hh = ar[max];
      for(int b=max+1;b<len;b++) ar[b-1] = ar[b];
      ar[len-1] = hh;
      
      int min = len-1;
      for(int a=len-2;a>=0;a--){
        if(ar[a]<ar[min]){
          hh = ar[min];
          for(int b=min-1;b>a;b--) ar[b+1] = ar[b];
          ar[a+1] = hh;
          min = a;
        }
      }
      hh = ar[min];
      for(int b=min-1;b>=0;b--) ar[b+1] = ar[b];
      ar[0] = hh;
      
      for(int a=1;a<len;a++){
        if(ar[a]<ar[a-1]){sorted = false;break;}
      }
    }
    return moo;
  }
}