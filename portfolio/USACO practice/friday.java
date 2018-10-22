/*
ID: 255901
LANG: JAVA
PROG: friday
*/
import java.io.*;
import java.util.*;

class friday {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("friday.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
    
    final int input = Integer.parseInt(f.readLine());
    int[] days = {0,0,0,0,0,0,0}; //and the first day is Sat

    int year = 1900;
    int daycounter = 0; //first 13 is on saturday
    int[] months = {31,28,31,30,31,30,31,31,30,31,30,31};
    
    while(year<1900+input){
      for(int a=0;a<12;a++){
        days[daycounter]++;
        daycounter+=months[a];
        if(a==1&&year%4==0&&(year%100!=0||year%400==0))daycounter++;
        daycounter%=7;
      }
      year++;
    }

    
    out.println(days[0]+" "+days[1]+" "+days[2]+" "+days[3]+" "+days[4]+" "+days[5]+" "+days[6]);                       
    out.close();                                  
  }
}