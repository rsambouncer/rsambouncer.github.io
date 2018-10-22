/*
ID: 255901
LANG: JAVA
PROG: transform
*/
import java.io.*;
import java.util.*;

class transform {
  public static int len = 0;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("transform.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
    
    len = Integer.parseInt(f.readLine());
      if(len<1){out.println(0);out.close();return;}
    boolean[][] mat1 = new boolean[len][len];
    boolean[][] mat2 = new boolean[len][len];

    for(int a=0;a<len;a++){
      String row = f.readLine();
      for(int b=0;b<len;b++)
        mat1[a][b] = row.charAt(b)=='@';
    }
    for(int a=0;a<len;a++){
      String row = f.readLine();
      for(int b=0;b<len;b++)
        mat2[a][b] = row.charAt(b)=='@';
    }
    
    
    if(len==0)out.println("1");
    else if(isAck(rotate(mat1),mat2))          out.println("1");
    else if(isAck(rotate(mat1),mat2))          out.println("2");
    else if(isAck(rotate(mat1),mat2))          out.println("3");
    else if(isAck(flipH(rotate(mat1)),mat2))   out.println("4");
    else if(isAck(rotate(mat1),mat2))          out.println("5");
    else if(isAck(rotate(mat1),mat2))          out.println("5");
    else if(isAck(rotate(mat1),mat2))          out.println("5");
    else if(isAck(flipH(rotate(mat1)),mat2))   out.println("6");
    else out.println("7"); 
  
    out.close();                                  
  }
  public static boolean[][] rotate(boolean[][] mat){
    for(int k=len-1,b=0;k*2>=len;k--,b++)
    for(int a=b;a<k;a++){
      boolean h = mat[b][a];                
      mat[b][a] = mat[k-a+b][b];       
      mat[k-a+b][b] = mat[k][k-a+b];
      mat[k][k-a+b] = mat[a][k];
      mat[a][k] = h;
    }
    return mat;
  }
  public static boolean[][] flipH(boolean[][] mat){
    for(int b=0;b<len;b++)
    for(int a=0;a*2<len;a++){
      boolean h = mat[b][a];
      mat[b][a] = mat[b][len-a-1];
      mat[b][len-a-1] = h;
    }
    return mat;
  }
  public static boolean isAck(boolean[][] mat1,boolean[][] mat2){
    for(int a=0;a<len;a++)
    for(int b=0;b<len;b++){
      if(mat1[a][b]!=mat2[a][b]) return false;
    }
    return true;
  }
  
}