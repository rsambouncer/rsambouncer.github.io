/*
ID: 255901
LANG: JAVA
PROG: wormhole
*/
import java.io.*;
import java.util.*;

class wormhole {
  public static class Worm implements Comparable<Worm>{
    public int x,y;
    public Worm(int x,int y){this.x=x;this.y=y;}
    public int compareTo(Worm wrowegno){
      if(this.y-wrowegno.y!=0) return this.y-wrowegno.y;
      else return this.x - wrowegno.x;
    }
  }
  public static int len; 
  public static Worm[] worms;
  public static int[] order;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
    
    len = Integer.parseInt(f.readLine());
    worms = new Worm[len];
    order = new int[len]; for(int a=0;a<len;a++) order[a]=a;
    for(int a=0;a<len;a++){
      StringTokenizer snc = new StringTokenizer(f.readLine());
      worms[a] = new Worm(
        Integer.parseInt(snc.nextToken()), 
        Integer.parseInt(snc.nextToken())
      );
    }
    Arrays.sort(worms);
    

    out.println(solveAll(1));
    out.close();                          
  }
  public static boolean isLoop(){
    //order is all set up
    for(int a=0;a<len;a++){
      //test if this one starts a loop
      boolean broken = false;
      int c = a;
      for(int b=0;b<len;b++){
        if(c==len-1||worms[c].y!=worms[c+1].y){broken=true;break;}
        else{
          int d = 0; while(order[d]!=c+1)d++;
          if(d%2==0) c = order[d+1];
          else c = order[d-1];
        }
      }
      
      if(!broken) return true;
    }
    return false;
  }
  public static int solveAll(int ind){
    if(ind>=len){
      return isLoop()?1:0;
    }else{
      int f1Nal = 0;
      for(int a=0;a<len-ind;a++){
        int h = order[ind];
        order[ind] = order[ind+a];
        order[ind+a] = h;
        f1Nal+=solveAll(ind+2);
      }
      for(int a=len-ind-1;a>=0;a--){
        int h = order[ind];
        order[ind] = order[ind+a];
        order[ind+a] = h;
      }
      return f1Nal;
    }
  }
}



