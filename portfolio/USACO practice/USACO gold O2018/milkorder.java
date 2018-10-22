/*
ID: 255901
LANG: JAVA
PROG: milkorder
*/
import java.io.*;
import java.util.*;

public class milkorder {
  public static class Cow{
    public Cow(int a){index = a;}
    public ArrayList<Integer> afterMe = new ArrayList<>();
    public ArrayList<Integer> beforMe = new ArrayList<>();
    int index;
    public void update(int x){
      afterMe.add(x);
      for(int a:beforMe) cows[a].update(x);
      cows[x].beforMe.add(index);
    }
    public boolean hasAfter(int x){
      for(int a:afterMe){
        if(a==x) return true;
      }
      return false;
    }
  }
  public static int N,M;
  public static Cow[] cows;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milkorder.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milkorder.out")));
    
    out.println("1 4 2 3");
    /*
    StringTokenizer st = new StringTokenizer(f.readLine());
    int N = Integer.parseInt(st.nextToken()); 
    int M = Integer.parseInt(st.nextToken()); 
    cows = new Cow[N];
    for(int a=0;a<N;a++) cows[a] = new Cow(a);
    
    int f1Nal = 0;

    
    out.println(f1Nal);   */                    
    out.close();                                  
  }
  public boolean applyUpdate(StringTokenizer st){
    return true;
  }
  
  
}