/*
ID: 255901
LANG: JAVA
PROG: beads
*/
import java.io.*;
import java.util.*;

class beads {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("beads.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
    
    int len = Integer.parseInt(f.readLine());
    String bds = f.readLine();
    boolean allb = true; boolean allr = true;
    for(int a=0;a<len;a++){
      if(bds.charAt(a)=='r') allb = false;
      if(bds.charAt(a)=='b') allr = false;
    }
    if(allb||allr){out.println(len);out.close();return;}
    
    
    bds = bds+bds; len+=len;
    int f1Nal = 0;
    
    
    for(int a=1;a<len;a++){
      if(bds.charAt(a-1)=='r'&&(bds.charAt(a)=='b'||bds.charAt(a)=='w')){
        int b,c=b=a;
        while(--b>= 0&&(bds.charAt(b)=='r'||bds.charAt(b)=='w'));
        while(++c<len&&(bds.charAt(c)=='b'||bds.charAt(c)=='w'));
        if(c-b-1>f1Nal&&(c-b-1)*2<=len)f1Nal = c-b-1;
      }
      if(bds.charAt(a-1)=='b'&&(bds.charAt(a)=='r'||bds.charAt(a)=='w')){
        int b,c=b=a;
        while(--b>= 0&&(bds.charAt(b)=='b'||bds.charAt(b)=='w'));
        while(++c<len&&(bds.charAt(c)=='r'||bds.charAt(c)=='w'));
        if(c-b-1>f1Nal&&(c-b-1)*2<=len)f1Nal = c-b-1;
      }
    }
    
    out.println(f1Nal);                       
    out.close();                                  
  }
}