/*
ID: 255901
LANG: JAVA
PROG: teleport
*/
import java.io.*;
import java.util.*;

public class teleport {
  public static End[] ends;
  public static int z = 0;
  public static class Manure{
    int a; 
    int b;
    
    public Manure(int x,int y){
      if(x<y){a=x;b=y;}
      else   {b=x;a=y;}
    }
    public int getDist(int p){
      if(p<0) return Math.min( Math.abs(a-p)+Math.abs(b), b-a);
      return Math.min( Math.abs(a)+Math.abs(b-p), b-a);
    }
    public void addEnds(){
      if(a<0&&b>0){
        ends[z++] = new End(2*a,-1);
        ends[z++] = new End(a,2);
        ends[z++] = new End(0,-2);
        ends[z++] = new End(b,2);
        ends[z++] = new End(2*b,-1);
      }else if(a>0&&2*a<b){
        ends[z++] = new End(2*a,-1);
        ends[z++] = new End(b,2);
        ends[z++] = new End(2*b-2*a,-1);
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
      }
      else if(b<0&&2*b>a){
        ends[z++] = new End(2*a-2*b,-1);
        ends[z++] = new End(a,2);
        ends[z++] = new End(2*b,-1);
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
      }
      else{
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
        ends[z++] = new End(0,0);
      }
    }
  }
  public static class End implements Comparable<End>{
    int x;
    int s;
    public End(int xx,int ss){x=xx;s=ss;}
    public int compareTo(End oth){return this.x-oth.x;}
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("teleport.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("teleport.out")));
    
    int len = Integer.parseInt(f.readLine());
    Manure[] poopys = new Manure[len];
    ends = new End[len*5];
    
    for(int a=0;a<len;a++){
      StringTokenizer strr = new StringTokenizer(f.readLine());
      poopys[a] = new Manure(Integer.parseInt(strr.nextToken()),Integer.parseInt(strr.nextToken()));
      poopys[a].addEnds();
    }
    Arrays.sort(ends);
    long f1Nal = 0; int slope = ends[0].s;
    for(int a=0;a<len;a++){
      f1Nal+=poopys[a].getDist(ends[0].x);
    }
    long curr = f1Nal;
    for(int a=1;a<ends.length;a++){
      curr+=(ends[a].x-ends[a-1].x)*slope;
      slope+=ends[a].s;
      if(curr<f1Nal) f1Nal = curr;
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
}