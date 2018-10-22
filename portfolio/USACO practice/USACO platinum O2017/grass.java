/*
ID: 255901
LANG: JAVA
PROG: grass
*/
import java.io.*;
import java.util.*;

public class grass {
  public static class Field{
    public int grass,st,nd; //[st,nd)
  }
  public static class Path{
    public int f1,f2,length,gi;
    public Path(int a,int b,int c){f1=a; f2=b; length=c;}
    public boolean isD(){return feilds[f1].grass!=feilds[f2].grass;}
  }
  public static class PR implements Comparable<PR>{
    public Path ref;
    public boolean thef1;
    public PR(Path r,boolean f){ref = r;thef1 = f;}
    public int getF(){return thef1?ref.f1:ref.f2;}
    public int compareTo(PR oth){return this.getf1()-oth.getf1();}
  }
  public static final int N,M,P,Q; 
  public static final Path[] globalpaths;
    public static int heaplength = 0;
  public static final PR[] feildpaths;
  public static final Feild[] feilds;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("grass.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("grass.out")));
    
    getInputandSetUp(f);
    for(int z=0;z<Q;z++){
      StringTokenizer st = new StringTokenizer(f.readLine());
      out.println(applyUpdate(st.nextToken(),st.nextToken()));
    }
    
    out.close();                                  
  }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static int applyUpdate(int aaa,int bbb){
    
  }
  public static void removeFromHeap(int num){
    swapG(num,--heaplength);
    if(globalpaths[num].length>globalpaths[heaplength].length){ //down
      
      int nx = globalpaths[num*2+1]
      
    }else{ //up
      
    }
  }
  
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static void addToHeap(int num){
    swapG(num,heaplength);
    num = heaplength++;
    while(num>0&& globalpaths[num].length > globalpaths[(num-1)/2].length ){
      swapG(num,(num-1)/2);
      num = (num-1)/2;
    }
  }
  public static void swapG(int num1,int num2){
    Path hh = globalpaths[num1];
    globalpaths[num1] = globalpaths[num2];
    globalpaths[num2] = hh;
  }
  public static void getInputandSetUp(BufferedReader f) throws IOException{
    /*  Job description: 
          sets up NMPQ, 
          sets up feilds and feildpaths so each feild can access its paths
          sets up globalpaths as a heap (difs) and an unsorted list (sames)
          sets up heaplength
    */
    StringTokenizer st = new StringTokenizer(f.readLine());
    N = Integer.parseInt(st.nextToken()); //number of fields
    M = Integer.parseInt(st.nextToken()); //number of paths
    K = Integer.parseInt(st.nextToken()); //number of types of grass
    Q = Integer.parseInt(st.nextToken()); //number of updates
    
    globalpaths = new Path[M];
    feildpaths = new PR[2*M];
    for(int a=0;a<M;a++){
      st = new StringTokenizer(f.readLine());
      Path aa = new Path(
        Integer.parseInt(st.nextToken())-1,
        Integer.parseInt(st.nextToken())-1,
        Integer.parseInt(st.nextToken())
      );
      globalpaths[a] = aa;
      feildpaths[2*a] = new PR(aa,true);
      feildpaths[2*a+1] = new PR(aa,false);
    }
    Arrays.sort(feildpaths);
    
    feilds = new Feild[N];
    st = new StringTokenizer(f.readLine());
    for(int a=0;a<N;a++){
      feilds[a] = new Feild(); 
      feilds[a].grass = Integer.parseInt(st.nextToken());
    }
    int f = 0; feilds[f].st = 0;
    for(int a=0;a<feildpaths.length;a++){
      if(feildpaths[a].getF()==f) continue;
      feilds[f++].nd = a;
      feilds[f].st = a--;
    }
    feilds[f++].nd = feildpaths.length;
    for(;f<N;f++){
      feilds[f].st = feilds[f].nd = feildpaths.length;
    }
    Arrays.sort(globalpaths, (p1,p2)-> 
      p1.isD()==p2.isD()?p1.length-p2.length:(p1.isD()?-1:1)
    );
    for(int a=0;a<globalpaths.length;a++){
      globalpaths[a].gi = a;
      if(globalpaths[a].isD()) heaplength++;
    }
  }
  
}