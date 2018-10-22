/*
ID: 255901
LANG: JAVA
PROG: checklist
*/
import java.io.*;
import java.util.*;

public class checklist {
  public static class Cow{
    public int x, y;
    public Cow(int x,int y){this.x=x;this.y=y;}
  }
  public static int dist(Cow a,Cow b){
    return (a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y);
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("checklist.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int lenH = Integer.parseInt(st.nextToken());
    int lenG = Integer.parseInt(st.nextToken());
    cows = new Cow[2][];
      cows[0] = new Cow[lenH];
      cows[1] = new Cow[lenG];
    
    for(int a=0;a<lenH;a++){ 
      st = new StringTokenizer(f.readLine());
      cows[0][a] = new Cow(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
    }
    for(int a=0;a<lenG;a++){ 
      st = new StringTokenizer(f.readLine());
      cows[1][a] = new Cow(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
    }
    
    arr = new long[2][lenH][lenG]; //up to cow #a
    for(int a=0;a<lenH;a++) for(int b=0;b<lenG;b++) arr[0][a][b] = arr[1][a][b] = -1;
    
    arr[1][0][0] = dist(cows[0][0],cows[1][0]);
    for(int a=1;a<lenG;a++)
      arr[1][0][a] = arr[1][0][a-1]+dist(cows[1][a],cows[1][a-1]);
    for(int a=0;a<lenG;a++)
      arr[0][1][a] = arr[1][0][a] + dist(cows[0][1],cows[1][a]);
    for(int a=1,m=0;a<lenH;a++){
      m+=dist(cows[0][a],cows[0][a-1]);
      arr[1][a][0] = m+dist(cows[0][a],cows[1][0]);
    }
    
    
    out.println(arr(0,lenH-1,lenG-1));
    out.close();
  }
  
  public static long[][][] arr;
  public static Cow[][] cows;
  public static long arr(int b,int hh,int gg){
    if(arr[b][hh][gg]!=-1) return arr[b][hh][gg];
    if(b==0) arr[b][hh][gg] = Math.min(
      arr(0,hh-1,gg)+dist(cows[0][hh],cows[0][hh-1]),
      arr(1,hh-1,gg)+dist(cows[0][hh],cows[1][gg])
    );
    else arr[b][hh][gg] = Math.min(
      arr(0,hh,gg-1)+dist(cows[1][gg],cows[0][hh]),
      arr(1,hh,gg-1)+dist(cows[1][gg],cows[1][gg-1])
    );
    return arr[b][hh][gg];
  }
  
  
}


