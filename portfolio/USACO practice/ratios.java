/*
ID: 255901
LANG: JAVA
PROG: ratios
*/
import java.io.*;
import java.util.*;

public class ratios {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ratios.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int[] goal = {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
    int[][] feeds = new int[3][];
    st = new StringTokenizer(f.readLine());
    feeds[0] = new int[]{Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
    st = new StringTokenizer(f.readLine());
    feeds[1] = new int[]{Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
    st = new StringTokenizer(f.readLine());
    feeds[2] = new int[]{Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
    
    
    int s = 300, aa=0,bb=0,cc=0,dd=0;
    
    for(int a=0;a<100;a++) for(int b=0;b<100;b++) for(int c=0;c<100;c++) if(a+b+c<s){
      int t0 = a*feeds[0][0]+b*feeds[1][0]+c*feeds[2][0];
      int t1 = a*feeds[0][1]+b*feeds[1][1]+c*feeds[2][1];
      int t2 = a*feeds[0][2]+b*feeds[1][2]+c*feeds[2][2];
      int d = test(t0,t1,t2,goal[0],goal[1],goal[2]);
      if(d!=0){
        aa = a; bb = b; cc = c; dd=d; s = a+b+c;
      }
    }
    
    if(s==300){
      out.println("NONE");
    }else{
      out.println(aa+" "+bb+" "+cc+" "+dd);
    }
    out.close();                                  
  }
  public static int test(int a,int b,int c,int x,int y,int z){ //0 if not possible, factor if is
    boolean ba,bb,bc=bb=ba=false;
    if(x==0){ba=true;if(a!=0) return 0;}
    if(y==0){bb=true;if(b!=0) return 0;}
    if(z==0){bc=true;if(c!=0) return 0;}
    
    if((ba||a%x==0)&&
       (bb||b%y==0)&&
       (bc||c%z==0)&&
       (a*y==b*x)&&
       (b*z==c*y)&&
       (c*x==a*z)){
         if(!ba) return a/x;
         if(!bb) return b/y;
         if(!bc) return c/z;
       }
    return 0;
  }
}


