/*
ID: 255901
LANG: JAVA
PROG: hoofball
*/
import java.io.*;
import java.util.*;

public class hoofball {
  public static int len;
  public static int[] cows;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("hoofball.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hoofball.out")));
    
    len = Integer.parseInt(f.readLine());
      if(len<3){out.println(1);out.close();return;}
    cows = new int[len]; 
    StringTokenizer strr = new StringTokenizer(f.readLine());
    for(int a=0;a<len;a++){
      cows[a] = Integer.parseInt(strr.nextToken());
    }
    Arrays.sort(cows);
    
    int f1Nal = 0; int st = 0;
    for(int a=1;a<len;a++){
      if(!passRight(a-1)&&passRight(a)){ f1Nal+=numBallsGroup(st,a);st = a;}
    }
    f1Nal+=numBallsGroup(st,len);
    
    out.println(f1Nal);                       
    out.close();                                  
  }
  public static boolean passRight(int cc){
    if(cc==0) return true;
    if(cc==len-1) return false;
    return cows[cc+1]-cows[cc]<cows[cc]-cows[cc-1];
  }
  public static int numBallsGroup(int st, int nd){
    if(nd-st<4) return 1;
    if(!passRight(st+1)) return 1;
    if(passRight(nd-2)) return 1;
    return 2;
  }
  public static boolean isStart(int cc){
    if(cc==0) return passRight(1);
    if(cc==len-1) return !passRight(len-2);
    return (!passRight(cc-1))&&passRight(cc+1);
  }
}