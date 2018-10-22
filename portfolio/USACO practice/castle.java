/*
ID: 255901
LANG: JAVA
PROG: castle
*/
import java.io.*;
import java.util.*;

class castle {
public static int[][] roomludr;
public static int[][] rooms;
public static int[] sizes;
public static int height, width;
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("castle.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    width = Integer.parseInt(st.nextToken());
    height = Integer.parseInt(st.nextToken());
    
    roomludr = new int[height][width];
    rooms = new int[height][width];
    for(int a=0;a<height;a++){
      st = new StringTokenizer(f.readLine());
      for(int b=0;b<width;b++){ 
        roomludr[a][b] = Integer.parseInt(st.nextToken());
        rooms[a][b] = a*width+b;
      }
    }
  
    for(int a=0;a<height;a++)for(int b=0;b<width;b++)
      fillroom(a,b,-a*width-b-1);
    for(int a=0;a<height;a++)for(int b=0;b<width;b++)
      rooms[a][b] = -rooms[a][b] -1;
    
    sizes = new int[width*height];
    for(int a=0;a<height;a++)for(int b=0;b<width;b++)
      sizes[ rooms[a][b] ]++;
    
    int count = 0;
    int large = 0;
    for(int a=0;a<sizes.length;a++){
      if(sizes[a]!=0) count++;
      if(sizes[a]>large) large = sizes[a];
    }
    out.println(count);
    out.println(large);
    
    int maxsize = 0;
    String index = "";
    for(int b=0;b<width;b++) for(int a=height-1;a>=0;a--){
      if(sizeOfRemove(a,b,false)>maxsize){ 
        maxsize = sizeOfRemove(a,b,false);
        index = ""+(a+1)+" "+(b+1)+" N";
      }
      if(sizeOfRemove(a,b,true)>maxsize){ 
        maxsize = sizeOfRemove(a,b,true);
        index = ""+(a+1)+" "+(b+1)+" E";
      }
    }
    out.println(maxsize);
    out.println(index);

    out.close();                                  
  }
  public static void fillroom(int a, int b, int n){
    if(rooms[a][b]==n) return;
    rooms[a][b] = n;
    if((roomludr[a][b]&1)!=1) fillroom(a,b-1,n); // <
    if((roomludr[a][b]&2)!=2) fillroom(a-1,b,n); // ^
    if((roomludr[a][b]&4)!=4) fillroom(a,b+1,n); // >
    if((roomludr[a][b]&8)!=8) fillroom(a+1,b,n); // v
  }
  public static int sizeOfRemove(int a, int b, boolean E){
    if(b==width-1&&E) return 0;
    if(a==0&&(!E)) return 0;
    if(E&&  rooms[a][b]==rooms[a][b+1]) return 0;
    if(!E&& rooms[a][b]==rooms[a-1][b]) return 0;
    return sizes[ rooms[a][b] ] + (E?sizes[ rooms[a][b+1] ]:sizes[ rooms[a-1][b] ]);
  }
  
  
}