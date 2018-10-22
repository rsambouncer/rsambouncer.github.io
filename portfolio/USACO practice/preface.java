/*
ID: 255901
LANG: JAVA
PROG: preface
*/
import java.io.*;
import java.util.*;

public class preface {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("preface.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));
    
    int N = Integer.parseInt(f.readLine());
    int[] nums    = {0,  0,  0,  0,  0,  0,  0  };
    String[] strs = {"I","V","X","L","C","D","M"};

    for(int a=1;a<=N;a++) addToNums(nums,a,0);
    
    for(int a=0;a<7;a++) if(nums[a]!=0) out.println(strs[a]+" "+nums[a]);
    
    out.close();                                  
  }
  public static void addToNums(int[] nums, int num, int off){
    if(num==0) return;
    
    switch(num%10){
      case 0: break;
      case 1: nums[0+off]++; break;
      case 2: nums[0+off]+=2; break;
      case 3: nums[0+off]+=3; break;
      case 4: nums[0+off]++; nums[1+off]++; break;
      case 5: nums[1+off]++; break;
      case 6: nums[0+off]++; nums[1+off]++; break;
      case 7: nums[0+off]+=2; nums[1+off]++; break;
      case 8: nums[0+off]+=3; nums[1+off]++; break;
      case 9: nums[0+off]++; nums[2+off]++; break;
    }
    
    num/=10;
    addToNums(nums,num,off+2);
  }
}