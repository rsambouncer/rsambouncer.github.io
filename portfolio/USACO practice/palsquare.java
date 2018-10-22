/*
ID: 255901
LANG: JAVA
PROG: palsquare
*/
import java.io.*;
import java.util.*;

class palsquare {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("palsquare.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
    
    int base = Integer.parseInt(f.readLine());
    
    for(int a=1;a<=300;a++){
      if(Integer.toString(a*a,base).equals(
        new String(new StringBuffer(Integer.toString(a*a,base)).reverse())
        )) out.println(Integer.toString(a,base).toUpperCase()+" "+Integer.toString(a*a,base).toUpperCase());
    }
    
    out.close();                                  
  }
}