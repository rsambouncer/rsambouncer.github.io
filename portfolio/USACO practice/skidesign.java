/*
ID: 255901
LANG: JAVA
PROG: skidesign
*/
import java.io.*;
import java.util.*;

class skidesign {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[] heights = new int[len];
    for(int a=0;a<len;a++){
      heights[a] = Integer.parseInt(f.readLine());
    }
    
    int f1Nal = -1;
    for(int a=17;a<=100;a++){
      int temp = 0;
      for(int b=0;b<len;b++){
        if(heights[b]>a-17){
          if(heights[b]>a) temp+=(heights[b]-a)*(heights[b]-a);
        }
        else temp+=(heights[b]+17-a)*(heights[b]+17-a);
      }
      if(temp<f1Nal||f1Nal<0) f1Nal = temp;
    }

    out.println(f1Nal);
    out.close();                          
  }
  public static int ff(int a,int b,int len){
    int f1Nal = 0;
    if(a<b){
      if(b-a<5) f1Nal+=a+5-b;
      if(len-b+a<5) f1Nal+= 5+b-len-a;
    }
    else{
      if(a-b<5) f1Nal+=b+5-a;
      if(len-a+b<5) f1Nal+= 5+a-len-b;
    }
    return f1Nal;
  }
}