/*
ID: 255901
LANG: JAVA
PROG: fact4
*/
import java.io.*;
import java.util.*;

public class fact4 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("fact4.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
    
    int N = Integer.parseInt(f.readLine());
    int f1Nal = 1;
    int fill2 = 0;
    for(int a=1;a<=N;a++){
      int x = a;
      while(x%2==0){fill2++;x/=2;}
      while(x%5==0){fill2--;x/=5;}
      f1Nal = (f1Nal*x)%10;
    }
    
    while(fill2>0){f1Nal = (f1Nal*2)%10;fill2--;}
    

    out.println(f1Nal);                       
    out.close();                                  
  }
}


