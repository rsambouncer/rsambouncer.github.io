public class calculate{
    public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        System.out.print(" QQ to exit: ");
        String ss = kb.nextLine();
        while(!ss.substring(ss.length()-2).equals("QQ")){
            double[] lol = calDouble(ss);
            StringBuffer result = "";
            for(int a=0;a<lol.length;a++)
                result = result + lol[a] + " ";
            System.out.print(" QQ to exit: ");
            System.out.print(result);
            ss = result+kb.nextLine();
        }
        double[] lol = calDouble(ss);
        StringBuffer result = "";
        for(int a=0;a<lol.length;a++)
            result = result + lol[a] + " ";
        System.out.print(" QQ-exiting: "+result);
    }
    public static double[] calDouble(String inputString){
        int c1=0;
        for(int a=0;a<inputString.length();a++)
            if(inputString.charAt(a)==' ')c1++;
        String[] input = new String[c1];
        int a1=0; c1 = 0;
        for(int a=0;a<inputString.length();a++)
            if(inputString.charAt(a)==' '){
                input[c1] = inputString.substring(a1,a);
                c1++; a1 = a+1;
        }
        // String to String[] seperated by spaces
        //now the calculation part IN REVERSE POLISH
        
        Stack<Double> nums = new Stack()<Double>;
        for(int a=0;a<input.length;a++){
            if(input[a].length<2)switch(input[a].charAt(0)){
                case'0':case'1':case'2':case'3':case'4':
                case'5':case'6':case'7':case'8':case'9':
                    Double num = input[a].charAt(0)-48;
                    nums.push(num); break;
                case'+':
                    if(nums.size()<2)break;
                    Double num = nums.pop()+nums.pop();
                    nums.push(num); break;
                case'-':
                    if(nums.size()<2)break;
                    double hh = nums.pop();
                    Double num = nums.pop()-hh;
                    nums.push(num); break;
                case'*':
                    if(nums.size()<2)break;
                    Double num = nums.pop()*nums.pop();
                    nums.push(num); break;
                case'/':
                    if(nums.size()<2)break;
                    double hh = nums.pop();
                    Double num = nums.pop()/hh;
                    nums.push(num); break;
                case'^':
                    if(nums.size()<2)break;
                    double hh = nums.pop();
                    Double num = Math.pow(hh, nums.pop());
                    nums.push(num); break;
            }
            else{ //it must be a number, like -1,207.359 || it could be gibberish
                boolean d = true, p = true, nn = false;
                int bb = 0;
                if(input[a].charAt(0)=='-'){nn=true;bb=1;}
                for(;bb<input[a].length;bb++){
                    char b = input[a].charAt(bb);
                    if(b=='.'){
                        if(p) p=false;
                        else{d=false;break;}}
                    if((b>'9'||b<'0')&&b!='.'&&b!=','){d=false;break;}
                }if(d){
                    double a = 0.0;
                    int b = (nn)?0:1;
                    for(;b<input[a].length;b++){
                        if(input[a].charAt(b)=='.')break;
                        if(input[a].charAt(b)!=','){
                            a*=10;
                            a += input[a].charAt(b)-48;
                        }
                    }int c = b;
                    for(b++;b<input[a].length;b++){
                        double e = input[a].charAt(b)-48;
                        e /= Math.pow(10, b-c);
                        a+=e;
                    }
                    if(nn) a*= -1;
                    // a is now a double, converted from String
                    Double num = a;nums.push(num);
                }
            }
        }
        double[] f1Nal = new double[nums.size()];
        for(int a=f1Nal.length-1;a>=0;a--)f1Nal[a]=nums.pop();
        return f1Nal;
    }
}
