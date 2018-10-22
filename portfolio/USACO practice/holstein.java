/*
ID: 255901
LANG: JAVA
PROG: holstein
*/
import java.io.*;
import java.util.*;

public class holstein {
  public static class Data{
    public boolean b;
    public int n;
    public String str;
    public Data(boolean q,int a,String s){b=q;n=a;str=s;}
  }
  public static int[] requirement;
  public static int[][] feeds;
  public static int numf, numv;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("holstein.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
    
    numv = Integer.parseInt(f.readLine());
    requirement = new int[numv];
    StringTokenizer st = new StringTokenizer(f.readLine());
    for(int a=0;a<numv;a++) requirement[a] = Integer.parseInt(st.nextToken());
    
    numf = Integer.parseInt(f.readLine());
    feeds = new int[numf][numv];
    for(int a=0;a<numf;a++){
      st = new StringTokenizer(f.readLine());
      for(int b=0;b<numv;b++)
        feeds[a][b] = Integer.parseInt(st.nextToken());
    }
    
    Data f1Nal = result(0,requirement);
    out.println(f1Nal.n+" "+f1Nal.str.substring(0,f1Nal.str.length()-1));                       
    out.close();
  }
  public static Data result(int ind, int[] goal){
    if(ind==numf){
      boolean b = true;
      for(int a=0;a<goal.length;a++) if(goal[a]>0) b = false;
      return new Data(b,0,"");
    }
    
    int[] goal2 = new int[goal.length];
    for(int a=0;a<goal.length;a++) goal2[a] = goal[a] - feeds[ind][a];
    
    Data result1 = result(ind+1,goal);
    Data result2 = result(ind+1,goal2);
      result2.n++;
      result2.str = (ind+1)+" "+result2.str;
    
    if(!result1.b&&!result2.b) return new Data(false,0,"");
    if(!result1.b) return result2;
    if(!result2.b) return result1;
    if(result1.n<result2.n) return result1;
    return result2;
  }
}

