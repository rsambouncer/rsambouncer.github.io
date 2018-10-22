/*
ID: 255901
LANG: JAVA
PROG: comehome
*/
import java.io.*;
import java.util.*;

public class comehome {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("comehome.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));
    
    int[][] dist = new int[52][52];
    for(int a=0;a<52;a++) for(int b=0;b<52;b++) if(a!=b) dist[a][b]=-1;
    int numpaths = Integer.parseInt(f.readLine());
    for(int a=0;a<numpaths;a++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      char f1 = st.nextToken().charAt(0); int x1 = f1-71;
      char f2 = st.nextToken().charAt(0); int x2 = f2-71;
      int dd = Integer.parseInt(st.nextToken());
      if(f1>='A'&&f1<='Z') x1+=6;
      if(f2>='A'&&f2<='Z') x2+=6;
      if(dist[x1][x2]==-1||dd<dist[x1][x2]) dist[x1][x2] = dist[x2][x1] = dd;
    }
    
    for(int c=0;c<52;c++) for(int a=0;a<52;a++) for(int b=0;b<52;b++){
      if(dist[a][c]!=-1&&dist[b][c]!=-1) if(dist[a][b]==-1||dist[a][c]+dist[b][c]<dist[a][b])
        dist[a][b] = dist[a][c]+dist[b][c];
    }
    
    int firstcow = -1;
    for(int a=0;a<25;a++) if(dist[25][a]!=-1){
      if(firstcow==-1||dist[25][a]<dist[25][firstcow]) firstcow = a;
    }
    
    char outp = (char)(firstcow+65);
    
    out.println(outp+" "+dist[25][firstcow]);                       
    out.close();                                  
  }
}