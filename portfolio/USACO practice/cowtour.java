/*
ID: 255901
LANG: JAVA
PROG: cowtour
*/
import java.io.*;
import java.util.*;

public class cowtour {
  public static class Point{
    public Point(int a,int b){
      x=a;y=b;
    }
    int x,y;
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("cowtour.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));
    
    int len = Integer.parseInt(f.readLine());
    Point[] nodes = new Point[len];
    for(int a=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      nodes[a] = new Point(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
    }
    double[][] dist = new double[len][len];
    for(int a=0;a<len;a++){
      String str = f.readLine();
      for(int b=0;b<len;b++) if(str.charAt(b)=='1'){
        Point p1 = nodes[a];
        Point p2 = nodes[b];
        dist[a][b] = Math.sqrt( (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y) );
      }else if(a!=b){
        dist[a][b] = -1;
      }
    }
    
    for(int c=0;c<len;c++) for(int a=0;a<len;a++) for(int b=0;b<len;b++){
      if(dist[a][c]!=-1&&dist[b][c]!=-1) if(dist[a][b]==-1||dist[a][b]>dist[a][c]+dist[b][c])
        dist[a][b] = dist[a][c]+dist[b][c];
    }
    
    double[] max = new double[len];
    for(int a=0;a<len;a++) for(int b=0;b<len;b++) if(dist[a][b]>max[a]) max[a] = dist[a][b];
    double[] maxg = new double[len];
    for(int a=0;a<len;a++) for(int b=0;b<len;b++) if(dist[a][b]!=-1&&max[b]>maxg[a]) maxg[a] = max[b];
    //maxg is the current diameter of the feild that a node is in
    
    double f1Nal = -1;
    for(int r1=0;r1<len;r1++) for(int r2=r1+1;r2<len;r2++) if(dist[r1][r2]==-1){
      Point p1 = nodes[r1];
      Point p2 = nodes[r2];
      double dd = Math.sqrt( (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y) );
      double total = Math.max(Math.max(maxg[r1],maxg[r2]),max[r1]+dd+max[r2]);
      if(f1Nal==-1||total<f1Nal) f1Nal = total;
    }
    f1Nal = (Math.round(f1Nal*1000000)/1000000.0);
    String outp = f1Nal+"00000";
    out.println(outp.substring(0,outp.indexOf(".")+7));                       
    out.close();                            
  }
}