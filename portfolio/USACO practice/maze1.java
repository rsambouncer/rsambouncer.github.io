/*
ID: 255901
LANG: JAVA
PROG: maze1
*/
import java.io.*;
import java.util.*;

public class maze1 {
  public static class Block{
    int d,a,b; //on them haters
    boolean[] ludr;
    public Block(int x, int a, int b){
      d=x;
      this.a = a;
      this.b = b;
      ludr = new boolean[]{true,true,true,true};
    }
  }
  public static int W,H;
  public static Block[][] maze;
  public static Block[] starts;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("maze1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    W = Integer.parseInt(st.nextToken()); 
    H = Integer.parseInt(st.nextToken());
    maze = new Block[H][W];
    starts = new Block[2]; int c = 0;
    for(int a=0;a<maze.length;a++) for(int b=0;b<maze[a].length;b++) maze[a][b] = new Block(W*H+1,a,b);
    for(int a=0;a<=2*H;a++) c = handleStringInput(a,f.readLine(),c);
    
    fill(starts[0].a,starts[0].b,1);
    fill(starts[1].a,starts[1].b,1);
    
    int max = 1;
    for(int a=0;a<H;a++) for(int b=0;b<W;b++) if(maze[a][b].d>max) max = maze[a][b].d;
    
    out.println(max);                       
    out.close();                                  
  }
  public static void fill(int aa, int bb, int dd){
    if(dd>=maze[aa][bb].d) return;
    maze[aa][bb].d = dd;
    if(maze[aa][bb].ludr[0]) fill(aa,bb-1,dd+1);
    if(maze[aa][bb].ludr[1]) fill(aa-1,bb,dd+1);
    if(maze[aa][bb].ludr[2]) fill(aa+1,bb,dd+1);
    if(maze[aa][bb].ludr[3]) fill(aa,bb+1,dd+1);
  }
  public static int handleStringInput(int a, String str, int c){
    if(a==0){
      for(int b=1;b<2*W;b+=2){
        maze[a/2][b/2].ludr[1] = false;
        if(str.charAt(b)!='-') starts[c++] = maze[a/2][b/2];
      }
    }else if(a==2*H){
      for(int b=1;b<2*W;b+=2){
        maze[a/2-1][b/2].ludr[2] = false;
        if(str.charAt(b)!='-') starts[c++] = maze[a/2-1][b/2];
      }
    }
    else if(a%2==0){
      for(int b=1;b<2*W;b+=2){
        if(str.charAt(b)=='-'){
          maze[a/2-1][b/2].ludr[2] = false;
          maze[a/2][b/2].ludr[1] = false;
        }
      }
    }else{
      if(str.charAt(0)=='|') maze[a/2][0].ludr[0] = false;
      else{ maze[a/2][0].ludr[0] = false;   starts[c++] = maze[a/2][0];}
      if(str.charAt(2*W)=='|') maze[a/2][W-1].ludr[3] = false;
      else{ maze[a/2][W-1].ludr[3] = false; starts[c++] = maze[a/2][W-1];}
      
      for(int b=2;b<2*W;b+=2){
        if(str.charAt(b)=='|'){
          maze[a/2][b/2-1].ludr[3] = false;
          maze[a/2][b/2].ludr[0] = false;
        }
      }
    }
    return c;
  }
}