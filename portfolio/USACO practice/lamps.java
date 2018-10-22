/*
ID: 255901
LANG: JAVA
PROG: lamps
*/
import java.io.*;
import java.util.*;

public class lamps {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("lamps.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));
    
    int N = Integer.parseInt(f.readLine());
    int C = Integer.parseInt(f.readLine()); if(C>6) C=6;
    String[] pos = possible(N,C);
    Arrays.sort(pos);
    
    int[] ons = new int[N]; int len1 = 0;
    int[] ofs = new int[N]; int len2 = 0;
    StringTokenizer st = new StringTokenizer(f.readLine());
    while(len1<N){
      ons[len1] = Integer.parseInt(st.nextToken())-1; 
      if(ons[len1]!=-2) len1++;
      else break;
    }
    st = new StringTokenizer(f.readLine());
    while(len2<N){
      ofs[len2] = Integer.parseInt(st.nextToken())-1; 
      if(ofs[len2]!=-2) len2++;
      else break;
    }
    
    int cc = 0;
    if(matches(pos[0],ons,len1,ofs,len2)){cc++;out.println(pos[0]);}
    for(int a=1;a<pos.length;a++){
      if(!pos[a].equals(pos[a-1])&&matches(pos[a],ons,len1,ofs,len2)){cc++;out.println(pos[a]);}
    }
    
    if(cc==0) out.println("IMPOSSIBLE");                       
    out.close();
  }
  public static String[] possible(int N,int C){
    if(C==0){
      char[] aa = new char[N];
      for(int a=0;a<N;a++) aa[a] = '1';
      return new String[]{ new String(aa) };
    }
    String[] prev = possible(N,C-1);
    String[] f1Nal = new String[prev.length*4];
    for(int a=0;a<prev.length;a++){
      f1Nal[4*a+0] = flip(prev[a], 1, 0);
      f1Nal[4*a+1] = flip(prev[a], 2, 0);
      f1Nal[4*a+2] = flip(prev[a], 2, 1);
      f1Nal[4*a+3] = flip(prev[a], 3, 0);
    }
    return f1Nal;
  }
  public static String flip(String str, int f, int g){
    char[] chars = str.toCharArray();
    for(int a=0;a<str.length();a++) if(a%f==g)
      chars[a] = (char)(97-chars[a]);
    return new String(chars);
  }
  public static boolean matches(String str, int[] ons, int len1, int[] ofs, int len2){
    for(int a=0;a<len1;a++)
      if(str.charAt(ons[a])=='0') return false;
    for(int a=0;a<len2;a++)
      if(str.charAt(ofs[a])=='1') return false;
    return true;
  }
}