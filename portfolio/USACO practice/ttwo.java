/*
ID: 255901
LANG: JAVA
PROG: ttwo
*/
import java.io.*;
import java.util.*;

public class ttwo {
  public static class Mover{
    int a,b,d;
    public Mover(int aa, int bb){ //d = 0,1,2,3 = N,E,S,W
      a=aa;b=bb;d=0;
    }
    public void move(){
      if(d==0){
        if(a==0||map[a-1][b]=='*') d=1;
        else a--;
      }else if(d==1){
        if(b==9||map[a][b+1]=='*') d=2;
        else b++;
      }else if(d==2){
        if(a==9||map[a+1][b]=='*') d=3;
        else a++;
      }else if(d==3){
        if(b==0||map[a][b-1]=='*') d=0;
        else b--;
      }
    }
  }
  public static char[][] map = new char[10][];
  public static boolean[][][] coords = new boolean[100][100][16];
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ttwo.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));
    
    
    Mover fj = new Mover(0,0), cows = new Mover(9,9);
    for(int a=0;a<10;a++){
      String str = f.readLine();
      map[a] = str.toCharArray();
      if(str.indexOf("F")!=-1) fj = new Mover(a,str.indexOf("F"));
      if(str.indexOf("C")!=-1) cows = new Mover(a,str.indexOf("C"));
    }
    
    int f1Nal = 0;
    while(fj.a!=cows.a||fj.b!=cows.b){
      if(coords[fj.a*10+fj.b][cows.a*10+cows.b][fj.d*4+cows.d]){f1Nal = 0;break;}
      coords[fj.a*10+fj.b][cows.a*10+cows.b][fj.d*4+cows.d] = true;
      fj.move();
      cows.move();
      f1Nal++;
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
}