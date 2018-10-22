/*
ID: 255901
LANG: JAVA
PROG: contact
*/
import java.io.*;
import java.util.*;

public class contact {
  public static int A,B,N;
  public static int[][] counts;
  public static int[] maxes;
  public static boolean[] arr;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("contact.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    A = Integer.parseInt(st.nextToken()); 
    B = Integer.parseInt(st.nextToken()); 
    N = Integer.parseInt(st.nextToken()); 

    counts = new int[B-A+1][];
    for(int a=0;a<counts.length;a++) counts[a] = new int[1<<(A+a)];
    maxes = new int[N];
    
    String[] inp = new String[2500];
    int len = 0;
    while(len<=2500){
      inp[len++] = f.readLine();
      if(inp[len-1]==null) break;
    }
    len--;
    int blen = (len-1)*80+inp[len-1].length();
    arr = new boolean[blen]; int c=0;
    for(int a=0;a<len;a++) for(int b=0;b<inp[a].length();b++){
      arr[c++] = inp[a].charAt(b)=='1';
    }
    
    for(int a=0;a<counts.length;a++) fillCount(a);
    
    for(int[] aa:counts) for(int a:aa) addtoMaxes(a);
    Arrays.sort(maxes);
    
    for(int a=maxes.length-1;a>=0;a--){
      if(maxes[a]==0) break;
      out.println(maxes[a]);
      String str = "";
      int d = 0;
      for(int b=0;b<counts.length;b++)
        for(c=0;c<counts[b].length;c++)
          if(counts[b][c]==maxes[a]) str = str+((d++)%6==0?"\n":" ")+makeSeq(b,c);
      out.println(str.substring(1));
    }
    
    out.close();                                  
  }
  public static void fillCount(int x){ //fill in counts[x][]
    int L = x+A;
    if(arr.length<L) return;
    int nn = 0; for(int a=0;a<L;a++) nn = nn*2+(arr[a]?1:0);
    counts[x][nn]++;
    
    for(int a=L;a<arr.length;a++){
      nn = (nn*2+(arr[a]?1:0))%counts[x].length;
      counts[x][nn]++;
    }
  }
  public static void addtoMaxes(int x){
    if(x<=maxes[0]) return;
    for(int a=0;a<maxes.length;a++) if(maxes[a]==x) return;
    maxes[0] = x;
    int mini = 0;
    for(int a=0;a<maxes.length;a++) if(maxes[a]<maxes[mini]) mini = a;
    maxes[0] = maxes[mini];
    maxes[mini] = x;
  }
  public static String makeSeq(int x, int y){
    int L = x+A;
    String f1Nal = "";
    for(int a=0;a<L;a++) f1Nal= ((y>>a)&1)+f1Nal;
    return f1Nal;
  }
}


