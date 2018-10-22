/*
ID: 255901
LANG: JAVA
PROG: agrinet
*/
import java.io.*;
import java.util.*;

public class agrinet {
  public static class Path implements Comparable<Path>{
    public int f1, f2, d;
    public Path(int f1, int f2, int d){
      this.f1 = f1;
      this.f2 = f2;
      this.d = d;
    }
    public int compareTo(Path oth){
      return this.d - oth.d;
    }
  }
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("agrinet.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));
    
    int len = Integer.parseInt(f.readLine());
    Path[] paths = new Path[len*len]; int pc = 0;
    int[] group = new int[len]; for(int a=0;a<len;a++) group[a] = a;
    int f1Nal = 0;
    for(int a=0,b=0;a<len;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      if(b==len) b=0;
      for(;b<len&&st.hasMoreTokens();b++) paths[pc++] = new Path(a,b,Integer.parseInt(st.nextToken())); 
      if(b!=len) a--;
    }
    Arrays.sort(paths);
    for(int a=0;a<paths.length;a++) if(group[paths[a].f1]!=group[paths[a].f2]){
      int og = group[paths[a].f1];
      for(int b=0;b<len;b++) if(group[b] == og) group[b] = group[paths[a].f2];
      f1Nal+=paths[a].d;
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
}


