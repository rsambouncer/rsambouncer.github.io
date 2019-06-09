/*
ID: 255901
LANG: JAVA
PROG: cowbasic
*/
import java.io.*;
import java.util.*;

public class cowbasic{                             
  
  public static String[] varnames = new String[100]; 
    //maximum of 100 variables in problem statement
  public static int[][] codevars = new int[100][62]; 
    //in "setline": index of variable being modified, intiger added, vars added, -1 to signify end
    //in "loopline": -1,times to loop, end bracket line
    //end bracket line: -2, begin bracket line
    //return line: -3, return index
  public static int len; 
  public static int numvars;
    //current number of variables
  
  public static void main (String [] args) throws IOException {
    FileReader inputFileReader = new FileReader("cowbasic.in");
    BufferedReader fileInput = new BufferedReader(inputFileReader);
    
    FileWriter outputFileWriter = new FileWriter("cowbasic.out");
    BufferedWriter outputBufferedWriter = new BufferedWriter(outputFileWriter);
    PrintWriter out = new PrintWriter(outputBufferedWriter);
    
    //translateIput number of lines to be proccessed
    len = translateInput(fileInput);
    
    //constants are stored in index 0 - therefore all constants are assigned the 'empty' name
    //this logic is handled in codeToMatrix
    numvars = getInd("");    
    
    Matrix programAsProcessedMatrix = codeToMatrix(0,len);
    int returnedVariable = codevars[len][1];
    int[][] matrixElements = programAsProcessedMatrix.els;
    int finalReturnValue = matrixElements[numvars][returnedVariable];
    out.println(finalReturnValue);                       
    out.close();                                  
  }
  
  //codeToMatrix recursively translates string input into matrix form for proccessing
  //each line can be translated into a matrix; 
  //lines are then multiplied together to combine them
  //loops are represented by matrix powers
  public static Matrix codeToMatrix(int st, int nd){
    if(codevars[st][0]>=0){
      if(nd>st+1){
        Matrix restlines = codeToMatrix(st+1,nd);
        Matrix thisline = lineToMatrix(st);
        return Matrix.multiply(thisline,restlines);
      }else return lineToMatrix(st);
    }
    else if(codevars[st][0]==-1){
      if( codevars[st][2]+1==nd ) 
        return Matrix.power(
          codeToMatrix(st+1,nd-1),
          codevars[st][1]
        );
      else{
        Matrix restlines = codeToMatrix( codevars[st][2]+1,nd );
        Matrix looplines = Matrix.power(
          codeToMatrix(st+1,codevars[st][2]),
          codevars[st][1]
        );
        return Matrix.multiply(looplines,restlines);
      }
    }
    else return null;
  }
  public static Matrix lineToMatrix(int st){
    Matrix thisline = new Matrix(numvars+1,numvars+1);
    for(int c=0;c<=numvars;c++) thisline.els[c][c] = 1;
    int b = codevars[st][0]; thisline.els[b][b] = 0;
    thisline.els[numvars][b] = codevars[st][1];
    
    int a=2;
    while(codevars[st][a]!=-1) thisline.els[ codevars[st][a++] ][b]++;
    return thisline;
  }
  public static int translateInput(BufferedReader f) throws IOException{
    int len = 0; //len is line of return statement
    int[] bracketstack = new int[100]; int bs = 0; //next place to add bracket
    for(int a=0;a<100;a++){ 
      String codeline = f.readLine().trim();
      if(setUpSetLine(a,codeline));
      else if(codeline.charAt(0)>='0'&&codeline.charAt(0)<='9'){
        //begin loop
        bracketstack[bs++] = a;
        codevars[a][0] = -1;
        codevars[a][1] = Integer.parseInt(new StringTokenizer(
                           codeline
                         ).nextToken());
      }
      else if(codeline.charAt(0)=='}'){
        //end bracket
        codevars[a][0] = -2;
        codevars[a][1] = bracketstack[--bs];
        codevars[ codevars[a][1] ][2] = a;
      }
      else{ //return statement
        codevars[a][0] = -3;
          StringTokenizer weofgij = new StringTokenizer(codeline);
            weofgij.nextToken();
        codevars[a][1] = getInd(weofgij.nextToken());
        len = a;
        break;
      }
    }
    return len;
  }
  public static boolean setUpSetLine(int a, String line){
    if(line.charAt(0)>='a'&&line.charAt(0)<='z'){
      StringTokenizer st = new StringTokenizer(line);
      int b = 2;
      codevars[a][0] = getInd(st.nextToken());
      while(st.hasMoreTokens()){
        String aa = st.nextToken();
        if(aa.charAt(0)>='a'&&aa.charAt(0)<='z') 
          codevars[a][b++] = getInd(aa);
        else if(aa.charAt(0)>='0'&&aa.charAt(0)<='9') 
          codevars[a][1]+= Integer.parseInt(aa);
      }
      codevars[a][b] = -1;
      return true;
    } 
    return false;
  }
  public static int getInd(String str){
    int a=0;
    while(varnames[a]!=null){
      if(varnames[a].equals(str)) return a;
      a++;
    }
    varnames[a] = str;
    return a;
  }
  
  private static class Matrix{
    public int[][] els;
      //elements of the matrix
    public int h;
      //matrix height
    public int w;
      //matrix width
    public Matrix(int w,int h){
      els = new int[h][w];
      this.h = h;
      this.w = w;
    }
    public Matrix(int[][] els){
      this.els = els;
      this.h = els.length;
      this.w = els[0].length;
    }
    public static Matrix add(Matrix mat1, Matrix mat2){
      if(mat1.w!=mat2.w||mat1.h!=mat2.h) return null;
      int[][] sum = new int[mat1.w][mat1.h];
      for(int a=0;a<mat1.w;a++)for(int b=0;b<mat1.h;b++) 
        sum[a][b] = mat1.els[a][b]+mat2.els[a][b];
      return new Matrix(sum);
    }
    public static Matrix multiply(Matrix mat1, Matrix mat2){
      if(mat1.w!=mat2.h) return null;
      int[][] product = new int[mat1.h][mat2.w];
      for(int a=0;a<mat1.h;a++) for(int b=0;b<mat2.w;b++){
        long sum = 0;
        for(int c=0;c<mat1.w;c++){
          sum += ((long)mat1.els[a][c])*mat2.els[c][b];
          sum%= 1_000_000_007;
        }
        product[a][b] = (int)sum;
      }
      return new Matrix(product);
    }
    
    //Matrix.power recursively calculates mat raised to the
    //power p by repeated squaring
    public static Matrix power(Matrix mat, int p){
      if(mat.w!=mat.h)
      {
        throw new RuntimeException("Only square matrices can be raised to a power");
      }
      if(p<0)
      {
        throw new RuntimeException("Matrix.power can not be used to find inverses");
      }
      if(p==0) //return identity
      {
        int[][] identity = new int[mat.w][mat.w];
        for(int a=0;a<mat.w;a++){
          identity[a][a] = 1;
        }
        Matrix identityMatrix = new Matrix(identity);
        return identityMatrix;
      }
      if(p==1){ 
        return mat;
      }
      Matrix f1Nal = power(mat,p/2);
      f1Nal = multiply(f1Nal,f1Nal);
      if(p%2==1) f1Nal = multiply(f1Nal,mat);
      return f1Nal;
    }
  }
  
}
