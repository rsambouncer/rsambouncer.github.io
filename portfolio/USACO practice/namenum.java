/*
ID: 255901
LANG: JAVA
PROG: namenum
*/
import java.io.*;
import java.util.*;

class namenum {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
    BufferedReader t = new BufferedReader(new FileReader("dict.txt"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
    
    String inp = f.readLine();
    for(int a=0;a<inp.length();a++){
      char b = inp.charAt(a);
           if(b=='2') b = 'A';
      else if(b=='3') b = 'D';
      else if(b=='4') b = 'G';
      else if(b=='5') b = 'J';
      else if(b=='6') b = 'M';
      else if(b=='7') b = 'P';
      else if(b=='8') b = 'T';
      else if(b=='9') b = 'W';
      
      inp = inp.substring(0,a)+b+inp.substring(a+1);
    }
    
    boolean foundOne = false;
    while(t.ready()){
      String word = t.readLine();
      if(isCool(inp,word)){foundOne = true;out.println(word);}
    }
    if(!foundOne) out.println("NONE");                       
    out.close();                                  
  }
  public static boolean isCool(String ipn,String word){
    if(ipn.length()!=word.length()) return false;
    for(int a=0;a<ipn.length();a++){
      if(ipn.charAt(a)=='P'){
        if(word.charAt(a)!='P'&&word.charAt(a)!='R'&&word.charAt(a)!='S')
          return false;
      }else{
        int b = word.charAt(a)-ipn.charAt(a);
        if(b<0||b>2) return false;
      }
    }
    return true;
  }
}



