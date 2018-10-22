/*
ID: 255901
LANG: JAVA
PROG: gift1
*/
import java.io.*;
import java.util.*;

class gift1 {
  public static String[] names;
  public static int[] balences;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
    
    final int numppl = Integer.parseInt(f.readLine());
    names = new String[numppl];
    balences = new int[numppl];
    for(int a=0;a<numppl;a++){
      names[a] = f.readLine();
    }
    
    while(f.ready()){
      String giv = f.readLine();
      Scanner scn = new Scanner(f.readLine());
      int mm = scn.nextInt();
      int np = scn.nextInt();
      if(np==0) continue;
      mm = mm/np;
      balences[getInd(giv)] -= mm*np;
      for(int a=0;a<np;a++) balences[getInd(f.readLine())]+= mm;
    }
    
    
    for(int a=0;a<numppl;a++)  
      out.println(names[a]+" "+balences[a]);                       
    
    
    out.close();                                  
  }
  public static int getInd(String pp){
    int p = 0;
    while(!names[p].equals(pp))p++;
    return p;
  }
}