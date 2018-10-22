/*
ID: 255901
LANG: JAVA
PROG: art
*/
import java.io.*;
import java.util.*;

public class art {
  private static class Color{
    public int x1,y1,x2,y2; // [1,2]
    public boolean setyet = false;
    public void setXY(int x,int y){
      if(!setyet){ x1 = x2 = x; y1 = y2 = y; setyet = true; return;}
      if(x<x1) x1 = x;
      if(x>x2) x2 = x;
      if(y<y1) y1 = y;
      if(y>y2) y2 = y;
    }
    public boolean canbeonbottom = true;
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("art.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("art.out")));
    
    int len = Integer.parseInt(f.readLine());
    int[][] canvas = new int[len][len];
    for(int a=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      for(int b=0;b<len;b++) canvas[a][b] = Integer.parseInt(st.nextToken());
    }
    
    Color[] colors = new Color[len*len+1]; 
    for(int a=0;a<colors.length;a++) colors[a] = new Color();
    for(int a=0;a<len;a++) for(int b=0;b<len;b++){
      colors[ canvas[a][b] ].setXY(a,b);
    }
    
    int cc = 0;
    for(int a=1;a<colors.length;a++){
      if(colors[a].setyet) cc++;
    }
    if(cc==1){out.println(colors.length-2);out.close();return;}
    
    for(int a=1;a<colors.length;a++){
      if(!colors[a].setyet) continue;
      for(int x=colors[a].x1;x<=colors[a].x2;x++)
      for(int y=colors[a].y1;y<=colors[a].y2;y++){
        if(canvas[x][y]!=a) 
          colors[ canvas[x][y] ].canbeonbottom = false;
      }
    }
    
    int f1Nal = 0;
    for(int a=1;a<colors.length;a++){
      if(colors[a].canbeonbottom) f1Nal++;
    }
    out.println(f1Nal);                       
    out.close();                                  
  }
}