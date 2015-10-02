import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by jmitch on 10/1/2015.
 */
public class LR1 {

    static List<String> tokens = new ArrayList<>();
    static int index = 0;

    static Stack<String> mainStack = new Stack<>();
    static Stack<String> stackTracer = new Stack<>();

    public static void main(String[] args){

        if(args==null || args.length==0 || args[0]==null || args[0].isEmpty()) {
            System.out.println("LR1 parser requires equation arguments.");
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer(args[0]+"$","()+*$",true);
        while(tokenizer.hasMoreTokens()){
            tokens.add(tokenizer.nextToken());
        }
        //Done with tokenizer
        try{
            int answer = computeAnswer();
            System.out.println("Your equation successfully computes to: "+answer);
        } catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public static int computeAnswer() throws Exception{
        int answer = 0;


        while(!tokens.get(index).equals("$")){

        }


        p0();


        return answer;
    }

    public static int performStackOperation(){
        return 0;
    }

    public static void p0() throws Exception{
        try{
            int n = Integer.parseInt(tokens.get(index));
            pushPhaseAndIncrement(0);
        } catch (Exception e){
            if(tokens.get(index).equals("(")){
                pushPhaseAndIncrement(4);
            } else {
                throw throwSyntaxException();
            }
        }
    }

    public static Exception throwSyntaxException(){
        return new Exception("Syntax error: Bad Token \'"+tokens.get(index)+"\' at character "+index);
    }

    public static int p1() throws Exception{
        return 0;
    }

    public static int p2() throws Exception{
        return 0;
    }

    public static int p3() throws Exception{
        return 0;
    }

    public static int p4() throws Exception{
        return 0;
    }

    public static int p5() throws Exception{
        return 0;
    }

    public static int p6() throws Exception{
        return 0;
    }

    public static int p7() throws Exception{
        return 0;
    }

    public static int p8() throws Exception{
        return 0;
    }

    public static int p9() throws Exception{
        return 0;
    }

    public static int p10() throws Exception{
        return 0;
    }

    public static int p11() throws Exception{
        return 0;
    }



    /**
        Return just the token part given
        in stack string format '[token:phase]'
     */
    public static String getStackToken(String stackToken){
        return "" + stackToken.charAt(1);
    }

    /**
         Return just the phase part given
         in stack string format '[token:phase]'
     */
    public static String getStackMethod(String stackString){
        return "" + stackString.charAt(3);
    }

    public static void pushPhaseAndIncrement(String phase){
        mainStack.push("["+tokens.get(index)+":"+phase+"]");
        index++;
    }

    public static void pushPhaseAndIncrement(int phase){
        mainStack.push("["+tokens.get(index)+":"+phase+"]");
        index++;
    }


    public static void printStackTrace(){
        while(!mainStack.empty()){
            stackTracer.push(mainStack.pop());
        }

        StringBuilder builder = new StringBuilder();
        while(!stackTracer.empty()){
            String tempString = stackTracer.pop();
            builder.append(tempString);
            mainStack.push(tempString);
        }

        builder.append("\t");
        for(int i = index; i < tokens.size();i++){
            builder.append(tokens.get(i));
        }

        System.out.println(builder.toString());
    }


}
