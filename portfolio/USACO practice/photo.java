/*
ID: 255901
LANG: JAVA
PROG: photo
*/
import java.io.*;
import java.util.*;

class photo {
  public static int[][] photos;
  public static int[] ids;
  public static int[] idscore;
  public static int len;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("photo.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("photo.out")));
    
    len = Integer.parseInt(f.readLine());

    photos = new int[5][len];
    for(int a=0;a<5;a++) for(int b=0;b<len;b++){
      photos[a][b] = Integer.parseInt(f.readLine());
    }
    ids = Arrays.copyOf(photos[0],len); Arrays.sort(ids);
    idscore = new int[len];
    
    int[] f1Nal = Arrays.copyOf(ids,len);
    mySpecialSort(f1Nal,0,len);
    
    for(int a=0;a<len;a++) out.println(f1Nal[a]);
    out.close();                                  
  }
  public static void mySpecialSort(int[] ar, int st, int nd){
    //basically a quicksort of the ids
    //however, all values have to be compared to pivot at once
    
    if(nd-st<2) return;
    updateScores(ar[st]);
    int aa=st+1,bb=nd-1;
    
    while(aa<bb){
      if(idscore[gI(ar[aa])]<0) aa++;
      else if(idscore[gI(ar[bb])]>0) bb--;
      else{
        int hh = ar[aa];
        ar[aa] = ar[bb];
        ar[bb] = hh;
      }
    }
    if(idscore[gI(ar[aa])]<0){
      int hh = ar[aa];
      ar[aa] = ar[st];
      ar[st] = hh;
    }
    else{
      int hh = ar[--aa];
      ar[aa] = ar[st];
      ar[st] = hh;
    }

    
    mySpecialSort(ar,st,aa);
    mySpecialSort(ar,aa+1,nd);
  }
  public static int gI(int idd){    //getindex
    return Arrays.binarySearch(ids, idd);
  }
  public static void updateScores(int idd){ 
    for(int a=0;a<len;a++) idscore[a] = 0;
    for(int z=0;z<5;z++){
      int inc = -1;
      for(int a=0;a<len;a++){
        if(photos[z][a]==idd) inc = 1;
        else idscore[gI(photos[z][a])]+=inc;
      }
    }
  }
}