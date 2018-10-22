/*
ID: 255901
LANG: JAVA
PROG: teleport
*/
import java.io.*;
import java.util.*;

public class teleport {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("teleport.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("teleport.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    int aa = Integer.parseInt(st.nextToken());
    int bb = Integer.parseInt(st.nextToken());
    int xx = Integer.parseInt(st.nextToken());
    int yy = Integer.parseInt(st.nextToken());
    
    if(aa>bb){
      int hh = aa;
      aa = bb;
      bb = hh;
    }
    if(xx>yy){
      int hh = xx;
      xx = yy;
      yy = hh;
    }
    
    out.println(Math.min(  
      bb - aa,
      Math.abs(xx-aa)+Math.abs(yy-bb)
    ));                       
    out.close();                                  
  }
}