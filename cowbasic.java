/*
ID: 255901
LANG: JAVA
PROG: cowbasic
*/
import java.io.*;
import java.util.*;

public class cowbasic
{ 
  public static LinkedList<String> varnames = new LinkedList<String>();
    //varnames stores the names of each of the variables
    //so they can be referenced when converting code from 
    //string form to matrix form
  public static int[][] codevars = new int[100][62]; 
    //in "setline": index of variable being modified, intiger added, vars added, -1 to signify end
    //in "loopline": -1,times to loop, end bracket line
    //end bracket line: -2, begin bracket line
    //return line: -3, return index
  public static int len; 
  public static int numvars;
    //current number of variables
  
  public static void main (String [] args) throws IOException {
    String inputfilename = "cowbasic.in";
    String outputfilename = "couwbasic.out";
    
    FileReader inputFileReader = new FileReader(inputfilename);
    BufferedReader fileInput = new BufferedReader(inputFileReader);
    
    FileWriter outputFileWriter = new FileWriter(outputfilename);
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
  
  //codeToMatrix recursively translates string input into matrix form for processing
  //each line can be translated into a matrix; 
  //lines are then multiplied together to combine them
  //loops are represented by matrix powers
  public static Matrix codeToMatrix(int st, int nd)
  {
    if(codevars[st][0]>=0)
    {
      /*this "line" in the cowbasic code sets a variable
        has a matrix of the form (for example):
      .
                    | 0 0 0 4 0 |
                    | 0 0 0 1 0 |
      (identity) +  | 0 0 0 2 0 |
                    | 0 0 0 0 0 |
                    | 0 0 0 1 0 |
       .             
       this would be equivelent to a cowbasic line c = 4 + a + b + d + b
      */
      if(nd>st+1)
      {
        Matrix restlines = codeToMatrix(st+1,nd);
        Matrix thisline = lineToMatrix(st);
        return Matrix.multiply(thisline,restlines);
      }
      else
      { 
        return lineToMatrix(st);
      }
    }
    else if(codevars[st][0]==-1)
    {
      if( codevars[st][2]+1==nd )
      {
        return Matrix.power(
          codeToMatrix(st+1,nd-1),
          codevars[st][1]
        );
      }
      else
      {
        Matrix restlines = codeToMatrix( codevars[st][2]+1,nd );
        Matrix looplines = Matrix.power(
          codeToMatrix(st+1,codevars[st][2]),
          codevars[st][1]
        );
        return Matrix.multiply(looplines,restlines);
      }
    }
    else
    {
      return null;
    }
  }
  public static Matrix lineToMatrix(int st)
  {
    Matrix thisline = new Matrix(numvars+1,numvars+1);
    //the +1 is because hard-coded integers are stored
    //in the "blank" variable
    
    for(int c=0;c<=numvars;c++)
    { 
      thisline.els[c][c] = 1;
    }
    int b = codevars[st][0]; 
    thisline.els[b][b] = 0;
    thisline.els[numvars][b] = codevars[st][1];
    
    int a=2;
    while(codevars[st][a]!=-1)
    { 
      int ind1 = codevars[st][a];
      a++;
      thisline.els[ind1][b]++;
    }
    return thisline;
  }
  public static int translateInput(BufferedReader f) throws IOException
  {
    int len = 0; 
    //len is line of return statement
    int[] bracketstack = new int[100];
    //program is at most 100 lines long,
    //according to problem statement
    int bs = 0; 
    //bs is next place to add bracket
    for(int a=0;a<100;a++)
    { 
      String codeline = f.readLine();
      codeline = codeline.trim();
      boolean isSetLine = setUpSetLine(a,codeline);
      if(isSetLine)
      {
        continue;
      }
      else if(isNumber(codeline.charAt(0)))
      {
        //begin loop
        bracketstack[bs] = a;
        bs++;
        codevars[a][0] = -1;
        codevars[a][1] = Integer.parseInt(new StringTokenizer(
                           codeline
                         ).nextToken());
      }
      else if(codeline.charAt(0)=='}')
      {
        //end bracket
        codevars[a][0] = -2;
        bs--;
        codevars[a][1] = bracketstack[bs];
        int indOfBeginBracket = codevars[a][1];
        codevars[indOfBeginBracket][2] = a;
      }
      else
      { 
        //return statement
        codevars[a][0] = -3;
        StringTokenizer weofgij = new StringTokenizer(codeline);
        weofgij.nextToken();
        //we can ignore first word since we know it's "return"
        codevars[a][1] = getInd(weofgij.nextToken());
        len = a;
        break; //after return statement, we are done
      }
    }
    return len;
  }
  
  //a "set line" is a lone of the type "<var> = <expression>"
  //setUpSetLine will process the line if it is a set line, 
  //or return false if the line is not a set line
  public static boolean setUpSetLine(int a, String line){
    if(isLetter(line.charAt(0)))
    {
      //since "<expression>" is just of the form of constants and variables to add,
      //we can loop through it without having to use recursion
      StringTokenizer st = new StringTokenizer(line);
      int b = 2; 
      //b is index of the current variable to add
      codevars[a][0] = getInd(st.nextToken()); 
      //variable name
      while(st.hasMoreTokens())
      {
        String aa = st.nextToken();
        if(isLetter(aa.charAt(0)))
        { 
          codevars[a][b] = getInd(aa);
          b++;
        }
        else if(isNumber(aa.charAt(0)))
        {
          codevars[a][1]+= Integer.parseInt(aa);
        }
      }
      codevars[a][b] = -1;
      //-1 to end the line regardless
      return true;
    }
    else
    {
      //line is a loop or a return statement - can not process here
      return false;
    }
  }
  
  //returns whether a charecter is a letter
  public static boolean isLetter(char ch)
  {
    return ch>='a' && ch<='z';
  }
  
  //returns whater a charecter is a number
  public static boolean isNumber(char ch)
  {
    return ch>='0' && ch<='9';
  }
  
  //getInd returns the index that corresponds with a variable name
  //if the variable name has not been added yet, it adds it
  //and then returns it's index
  //implementation uses a LinkedList because LinkedList allows for expandable memory
  //and has an indexOf method to find names with
  public static int getInd(String str)
  {
    int ind = varnames.indexOf(str);
    if(ind>=0)
    {
      return ind;
    }
    else
    {
      varnames.add(str);
      return varnames.size()-1;
    }
  }
  
  private static class Matrix
  {
    public int[][] els;
      //elements of the matrix
    public int h;
      //matrix height
    public int w;
      //matrix width
    
    //creates empty matrix with given dimensions
    public Matrix(int w,int h)
    {
      els = new int[h][w];
      this.h = h;
      this.w = w;
    }
    
    //creates Matrix with the given int[][]
    public Matrix(int[][] els)
    {
      this.els = els;
      this.h = els.length;
      this.w = els[0].length;
    }
    
    public static Matrix identity(int d)
    {
      if(d<=0)
      {
        String errmsg = "Matrix dimensions have to be positive";
        throw new RuntimeException(errmsg);
      }
      int[][] identity = new int[d][d];
      for(int a=0;a<d;a++)
      {
        identity[a][a] = 1;
      }
      Matrix identityMatrix = new Matrix(identity);
      return identityMatrix;
    }
    
    //adds two Matrices, according to the standard formula
    public static Matrix add(Matrix mat1, Matrix mat2)
    {
      if(mat1.w!=mat2.w||mat1.h!=mat2.h)
      {
        String errmsg = "Dimensions of matrices have to match to be added";
        throw new RuntimeException(errmsg);
      }
      int[][] sum = new int[mat1.w][mat1.h];
      for(int a=0;a<mat1.w;a++)
      {
        for(int b=0;b<mat1.h;b++)
        {
          sum[a][b] = mat1.els[a][b]+mat2.els[a][b];
          sum[a][b] %= 1_000_000_007;
          //problem statement specifies that all addition is done mod 10^9+7,
          //to keep numbers in int range 
          //10^9+7 is just under half of 2.1 billion
        }
      }
      Matrix answer = new Matrix(sum);
      return answer;
    }
    
    //subtracts two Matrices, according to the standard formula
    //subtract(mat1, mat2) is defined as mat1 - mat2
    public static Matrix subtract(Matrix mat1, Matrix mat2)
    {
      if(mat1.w!=mat2.w||mat1.h!=mat2.h)
      {
        String errmsg = "Dimensions of matrices have to match to be subtracted";
        throw new RuntimeException(errmsg);
      }
      int[][] diff = new int[mat1.w][mat1.h];
      for(int a=0;a<mat1.w;a++)
      {
        for(int b=0;b<mat1.h;b++)
        {
          diff[a][b] = mat1.els[a][b]-mat2.els[a][b];
          diff[a][b] %= 1_000_000_007;
          //problem statement specifies that all addition is done mod 10^9+7,
          //to keep numbers in int range 
          //10^9+7 is just under half of 2.1 billion
        }
      }
      Matrix answer = new Matrix(diff);
      return answer;
    }
    
    //multiplies the matrix by the given integer scalar value
    public static Matrix multiplyByScalar(Matrix mat, int scalar)
    {
      if(mat.h==0||mat.w==0)
      {
        return mat;
      }
      int[][] newMat = new int[mat.h][mat.w];
      for(int a=0;a<mat.h;a++)
      {
        for(int b=0;b<mat.w;b++)
        {
          newMat[a][b] = mat.els[a][b]*scalar;
          newMat[a][b] %= 1_000_000_007;
          //problem statement specifies that all addition is done mod 10^9+7,
          //to keep numbers in int range 
          //10^9+7 is just under half of 2.1 billion
        }
      }
      Matrix answer = new Matrix(newMat);
      return answer;
    }
    
    //multiplies two Matrices, according to the standard formula
    public static Matrix multiply(Matrix mat1, Matrix mat2)
    {
      if(mat1.w!=mat2.h)
      { 
        String errmsg = "Inner dimensions of matrices have to match to be multiplied";
        throw new RuntimeException(errmsg);
      }
      int[][] product = new int[mat1.h][mat2.w];
      for(int a=0;a<mat1.h;a++)
      {
        for(int b=0;b<mat2.w;b++)
        {
          long sum = 0; //just in case
          for(int c=0;c<mat1.w;c++)
          {
            sum += ((long)mat1.els[a][c])*mat2.els[c][b];
            sum %= 1_000_000_007;
            //problem statement specifies that all addition is done mod 10^9+7,
            //to keep numbers in int range 
            //10^9+7 is just under half of 2.1 billion
          }
          product[a][b] = (int)sum;
        }
      }
      Matrix answer = new Matrix(product);
      return answer;
    }
    
    //Matrix.power recursively calculates mat raised to the
    //power p by repeated squaring
    public static Matrix power(Matrix mat, int p)
    {
      if(mat.w!=mat.h)
      {
        String errmsg = "Only square matrices can be raised to a power";
        throw new RuntimeException(errmsg);
      }
      if(p<0)
      {
        String errmsg = "Matrix.power can not be used to find inverses";
        throw new RuntimeException(errmsg);
      }
      if(p==0) //return identity
      {
        int[][] identity = new int[mat.w][mat.w];
        for(int a=0;a<mat.w;a++)
        {
          identity[a][a] = 1;
        }
        Matrix identityMatrix = new Matrix(identity);
        return identityMatrix;
      }
      if(p==1)
      { 
        return mat;
      }
      if(p%2==0) //even power
      {
        Matrix halfPower = power(mat,p/2);
        Matrix answer = multiply(halfPower,halfPower);
        return answer;
      }
      else
      {  
        Matrix halfPower = power(mat,p/2);
        Matrix powerMinusOne = multiply(halfPower,halfPower);
        Matrix answer = multiply(powerMinusOne,mat);
        return answer;
      }
    }
  }
  
}
