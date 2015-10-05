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

    static boolean useMethod = false;
    static boolean acceptAnswer = false;


    public static void main(String[] args){

        if(args==null || args.length==0 || args[0]==null || args[0].isEmpty()) {
            System.out.println("LR1 parser requires equation arguments.");
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer("-"+args[0]+"$","()+*$",true);
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

        pushPhaseAndIncrement(0);

        while(!acceptAnswer){
            interpretStack(getStackPhase(mainStack.peek()));
        }



        return answer;
    }

    public static void interpretStack(String phaseString) throws Exception{
        switch(phaseString){
            case "0":
                p0();
                break;
            case "1":
                p1();
                break;
            case "2":
                p2();
                break;
            case "3":
                p3();
                break;
            case "4":
                p4();
                break;
            case "5":
                p5();
                break;
            case "6":
                p6();
                break;
            case "7":
                p7();
                break;
            case "8":
                p8();
                break;
            case "9":
                p9();
                break;
            case "10":
                p10();
                break;
            case "11":
                p11();
                break;
            default:
                throw new Exception("Stack contains bad phase in interpretStack()");
        }
    }

    public static int performStackOperation(){
        return 0;
    }

    public static void p0() throws Exception{
        if(useMethod){
            String method  = getStackToken(mainStack.peek());
            switch (method){
                case "E":
                    pushPhaseOnly(1);
                    break;
                case "T":
                    pushPhaseOnly(2);
                    break;
                case "F":
                    pushPhaseOnly(3);
                    break;
                default:
                    throw new Exception("Couldn't find " +
                            "good method token in phase 0");
            }
        } else {
            try {
                Integer.parseInt(tokens.get(index));
                pushPhaseAndIncrement(5);
            } catch (Exception e) {
                if (tokens.get(index).equals("(")) {
                    pushPhaseAndIncrement(4);
                } else {
                    throw throwSyntaxException();
                }
            }
        }
    }

    public static void p1() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 1");

        switch(getStackToken(mainStack.peek())){
            case "+":
                pushPhaseAndIncrement(6);
                break;
            case "$":
                acceptAnswer=true;
                break;
            default:
                throwSyntaxException();
        }
    }

    public static void p2() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 2");

        switch(getStackToken(mainStack.peek())){
            case "+":

                break;
            case "*":
                pushPhaseAndIncrement(7);
                break;
            case ")":
                break;
            case "$":
                break;
            default:
                throwSyntaxException();
        }
    }


    public static int p3() throws Exception{
        return 0;
    }

    public static int p4() throws Exception{
        return 0;
    }

    public static void p5() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 5");
        switch (getStackToken(mainStack.peek())){
            case "+":
                break;
            case "*":
                break;
            case ")":
                break;
            case "$":
                break;
            default:
                throw throwSyntaxException();
        }
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
    public static String getStackPhase(String stackString){
        return "" + stackString.charAt(3);
    }

    public String popAndPrint(){
        String popped = mainStack.pop();
        printStackTrace();
        return popped;
    }

    public static void pushPhaseOnly(int phase){
        mainStack.push("["+tokens.get(index)+":"+phase+"]");
        printStackTrace();
    }

    public static void pushPhaseAndIncrement(int phase){
        mainStack.push("["+tokens.get(index)+":"+phase+"]");
        index++;
        printStackTrace();
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

    public static Exception throwSyntaxException(){
        return new Exception("Syntax error: Bad Token \'"+tokens.get(index)+"\' at character "+index);
    }

}
