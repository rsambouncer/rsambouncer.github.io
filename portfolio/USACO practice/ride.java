/*
ID: 255901
LANG: JAVA
PROG: ride
*/
import java.io.*;
import java.util.*;

class ride {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ride.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
    

    String str1 = f.readLine();    
    String str2 = f.readLine();    
    out.println(strh(str1)==strh(str2)?"GO":"STAY");                       
    
    
    out.close();                                  
  }
  public static int strh(String str){
    int result = 1;
    for(int a=0;a<str.length();a++){
      result*=str.charAt(a)-64;
    }
    return result%47;
  }
}