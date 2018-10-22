/*
ID: 255901
LANG: JAVA
PROG: prefix
*/
import java.io.*;
import java.util.*;

public class prefix {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));
    
    int plen = 0;
    String[] prims = new String[200];
    while(true){
      StringTokenizer st = new StringTokenizer(f.readLine());
      String str = st.nextToken();
      if(str.equals(".")) break;
      prims[plen++] = str;
      while(st.hasMoreTokens()) prims[plen++] = st.nextToken();
    }
    char[] seq = new char[200000]; int slen = 0;
    String sequence = f.readLine();
    for(int a=0;a<sequence.length();a++) seq[slen++] = sequence.charAt(a);
    while(f.ready()){ 
      String str = f.readLine();
      if(str==null) break;
      for(int a=0;a<str.length();a++) seq[slen++] = str.charAt(a);
    }
    
    boolean[][] babe = new boolean[11][slen+12];
    for(int a=0;a<plen;a++){
      int h1 = createHash(prims[a]);
      int h2 = createHash(sequence.substring(0,prims[a].length()));
      int r = createRoll(prims[a]);
      for(int b=0;b+prims[a].length()<slen;b++){
        if(h1==h2) babe[prims[a].length()][b] = true;
        //if(matches(seq,prims[a],b)) babe[prims[a].length()][b] = true;
        h2 = rollHash(h2,r,seq[b],seq[b+prims[a].length()]);
      }
      if(h1==h2) babe[prims[a].length()][slen-prims[a].length()] = true;
    }
    babe[0][0] = true;
    for(int a=0;a<slen;a++) 
      if(babe[0][a]) for(int b=1;b<11;b++) 
          if(babe[b][a]) babe[0][a+b] = true;
    
    
    for(int a=slen;a>=0;a--) if(babe[0][a]){ out.println(a);break; }
    out.close();
  }
  public static int createHash(String str){
    int p = 104743;
    int num = 0;
    for(int a=0;a<str.length();a++) num = (num*31+str.charAt(a)-64)%p;
    if(num>=0) return num;
    return p+num;
  }
  public static int createRoll(String str){
    int p = 104743;
    int num = 1;
    for(int a=0;a<str.length();a++) num = (num*31)%p;
    if(num>=0) return num;
    return p+num;
  }
  public static int rollHash(int hash, int roll, char prev, char next){
    int p = 104743;
    int num = (hash*31 - (prev-64)*roll + next-64)%p;
    if(num>=0) return num;
    return p+num;
  }
  public static boolean matches(char[] ar, String str, int b){
    for(int a=str.length()-1;a>=0;a--) if(ar[a+b]!=str.charAt(a)) return false;
    return true;
  }
}