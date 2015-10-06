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

    static Stack<StackObject> mainStack = new Stack<>();
    static Stack<StackObject> stackTracer = new Stack<>();

    static boolean useMethod = false;
    static boolean acceptAnswer = false;

    static class StackObject{

        private String token;
        private int phase;

        private int rememberedValue;

        public StackObject(String token,int phase){
            this.token=token;
            this.phase=phase;
        }


        public void setRememberedValue(int value){

            this.rememberedValue = value;
        }

        public void rememberNumericValue() throws Exception{
            try{
                int i =Integer.parseInt(token);
                this.rememberedValue = i;
            } catch (Exception e){
                throw new Exception("Error, the original token wasn't numeric: "+token);
            }
        }

        public int getPhase(){
            return phase;
        }

        public void setPhase(int phase){
            this.phase=phase;
        }

        public void reduceTo(String token, int phase){
            this.token=token;
            this.phase=phase;
        }

        public int getRememberedNumericValue(){
            return this.rememberedValue;
        }

        public String getToken(){
            return token;
        }

        @Override
        public String toString() {
            return "["+token+":"+phase+"]";
        }
    }

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

        mainStack.push(new StackObject("-",0));

        while(!acceptAnswer){
            interpretStack(mainStack.peek().getPhase());
        }



        return mainStack.peek().getRememberedNumericValue();
    }

    public static void interpretStack(int phase) throws Exception{
        switch(phase){
            case 0:
                p0();
                break;
            case 1:
                p1();
                break;
            case 2:
                p2();
                break;
            case 3:
                p3();
                break;
            case 4:
                p4();
                break;
            case 5:
                p5();
                break;
            case 6:
                p6();
                break;
            case 7:
                p7();
                break;
            case 8:
                p8();
                break;
            case 9:
                p9();
                break;
            case 10:
                p10();
                break;
            case 11:
                p11();
                break;
            default:
                throw new Exception("Stack contains bad phase in interpretStack()");
        }
    }

    public static void p0() throws Exception{
        if(useMethod){
            useMethod=false;
            String method  = mainStack.peek().getToken();
            switch (method){
                case "E":
                    mainStack.peek().setPhase(1);
                    printStackTrace();
                    break;
                case "T":
                    mainStack.peek().setPhase(2);
                    printStackTrace();
                    break;
                case "F":
                    mainStack.peek().setPhase(3);
                    printStackTrace();
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

        switch(tokens.get(index)){
            case "+":
                pushPhaseAndIncrement(6);
                break;
            case "$":
                acceptAnswer=true;
                break;
            default:
                throw throwSyntaxException();
        }
    }

    /*
        We're gonna need to check that the top of the stack has T before
        reducing to E
     */
    public static void p2() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 2");

        switch(tokens.get(index)){
            case "+":
                reduceSingleStackTop("E", "T");
                break;
            case "*":
                pushPhaseAndIncrement(7);
                break;
            case ")":
                reduceSingleStackTop("E", "T");
                break;
            case "$":
                reduceSingleStackTop("E", "T");
                break;
            default:
                throw throwSyntaxException();
        }
    }


    public static void p3() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 3");

        switch(tokens.get(index)){
            case "+":
                reduceSingleStackTop("T", "F");
                break;
            case "*":
                reduceSingleStackTop("T", "F");
                break;
            case ")":
                reduceSingleStackTop("T", "F");
                break;
            case "$":
                reduceSingleStackTop("T", "F");
                break;
            default:
               throw throwSyntaxException();
        }
    }

    public static void p4() throws Exception{
        if(useMethod){
            useMethod=false;
            String method  = mainStack.peek().getToken();
            switch (method){
                case "E":
                    mainStack.peek().setPhase(8);
                    printStackTrace();
                    break;
                case "T":
                    mainStack.peek().setPhase(2);
                    printStackTrace();
                    break;
                case "F":
                    mainStack.peek().setPhase(3);
                    printStackTrace();
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

    public static void p5() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 5");

        switch (tokens.get(index)){
            case "+":
                mainStack.peek().rememberNumericValue();
                reduceSingleStackTop("F", mainStack.peek().getRememberedNumericValue() + "");
                break;
            case "*":
                mainStack.peek().rememberNumericValue();
                reduceSingleStackTop("F", mainStack.peek().getRememberedNumericValue() + "");
                break;
            case ")":
                mainStack.peek().rememberNumericValue();
                reduceSingleStackTop("F", mainStack.peek().getRememberedNumericValue() + "");
                break;
            case "$":
                mainStack.peek().rememberNumericValue();
                reduceSingleStackTop("F", mainStack.peek().getRememberedNumericValue() + "");
                break;
            default:
                throw throwSyntaxException();
        }
    }

    public static void p6() throws Exception{
        if(useMethod){
            useMethod=false;
            String method  = mainStack.peek().getToken();
            switch (method){
                case "T":
                    mainStack.peek().setPhase(9);
                    printStackTrace();
                    break;
                case "F":
                    mainStack.peek().setPhase(3);
                    printStackTrace();
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

    public static void p7() throws Exception{
        if(useMethod){
            useMethod=false;
            String method  = mainStack.peek().getToken();
            switch (method){
                case "F":
                    mainStack.peek().setPhase(10);
                    printStackTrace();
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

    public static void p8() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 8");

        switch(tokens.get(index)){
            case "+":
                pushPhaseAndIncrement(6);
                break;
            case ")":
                pushPhaseAndIncrement(11);
                break;
        }

    }

    public static void p9() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 9");

        switch (tokens.get(index)){
            case "+":
                computeArithmetic("E", "+", "T");
                break;
            case "*":
                pushPhaseAndIncrement(7);
                break;
            case ")":
                computeArithmetic("E","+","T");
                break;
            case "$":
                computeArithmetic("E","+","T");
                break;
            default:
               throw throwSyntaxException();
        }
    }

    public static void p10() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 10");

        switch (tokens.get(index)){
            case "+":
                computeArithmetic("T", "*", "F");
                break;
            case "*":
                computeArithmetic("T", "*", "F");
                break;
            case ")":
                computeArithmetic("T", "*", "F");
                break;
            case "$":
                computeArithmetic("T", "*", "F");
                break;
            default:
                throw throwSyntaxException();
        }
    }

    public static void p11() throws Exception{
        if(useMethod) throw new Exception("Error, no method available at phase 11");

        switch (tokens.get(index)){
            case "+":
                reduceParenthases("E");
                break;
            case "*":
                reduceParenthases("E");
                break;
            case ")":
                reduceParenthases("E");
                break;
            case "$":
                reduceParenthases("E");
                break;
            default:
                throw throwSyntaxException();
        }
    }

    public static void reduceParenthases(String methodToken) throws Exception{
        StackObject[] stackObjects = new StackObject[3];
        for(int i = 0; i < 3; i++){
            stackObjects[i] = mainStack.pop();
        }

        if(!stackObjects[0].getToken().equalsIgnoreCase(")")
                || !stackObjects[1].getToken().equalsIgnoreCase(methodToken)
                || !stackObjects[2].getToken().equalsIgnoreCase("(")
                )
        {
            System.out.println("Throwing exception for parenthases");
            throw throwSyntaxException();
        }

        mainStack.push(stackObjects[2]);
        mainStack.peek().setRememberedValue(stackObjects[1].getRememberedNumericValue());
        reduceSingleStackTop("F", "E");
    }

    public static boolean isNumeric(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static void computeArithmetic(String leftToken, String operator, String rightToken) throws Exception{
        StackObject[] stackObjects = new StackObject[3];
        for(int i = 0; i < 3; i++){
            stackObjects[i] = mainStack.pop();
        }

        if(!leftToken.equalsIgnoreCase(stackObjects[2].getToken())
                || !operator.equalsIgnoreCase(stackObjects[1].getToken())
                || !rightToken.equalsIgnoreCase(stackObjects[0].getToken())
                )
        {
            throw throwSyntaxException();
        }

        int value = 0;
        if(operator.equals("*")){
            value = stackObjects[2].getRememberedNumericValue()
                    * stackObjects[0].getRememberedNumericValue();
        } else if (operator.equals("+")){
            value =  stackObjects[2].getRememberedNumericValue()
                    + stackObjects[0].getRememberedNumericValue();
        } else {
            throw throwSyntaxException();
        }

        stackObjects[2].setRememberedValue(value);
        mainStack.push(stackObjects[2]);
        printStackTrace();
    }

    public static void pushPhaseAndIncrement(int phase){
        //mainStack.push("["+tokens.get(index)+":"+phase+"]");
        mainStack.push(new StackObject(tokens.get(index),phase));
        index++;
        printStackTrace();
    }

    public static void reduceSingleStackTop(String newToken,String oldToken) throws Exception{
        StackObject so = mainStack.pop();

        //if(!oldToken.equals(so.getToken())) throw throwSyntaxException();

       // System.out.println("TOP TOKEN: "+mainStack.peek().getToken());

        //else if(!isNumeric(oldToken) && !oldToken.equalsIgnoreCase(mainStack.peek().getToken())) throw throwSyntaxException();

        so.reduceTo(newToken, mainStack.peek().getPhase());
        mainStack.push(so);
        useMethod=true;
    }

    public static void printStackTrace(){
        while(!mainStack.empty()){
            stackTracer.push(mainStack.pop());
        }

        StringBuilder builder = new StringBuilder();
        while(!stackTracer.empty()){
            StackObject so = stackTracer.pop();
            builder.append(so.toString());
            mainStack.push(so);
        }

        builder.append("\t");
        for(int i = index; i < tokens.size();i++){
            builder.append(tokens.get(i));
        }

        System.out.println(builder.toString());
    }

    public static Exception throwSyntaxException(){

        if(tokens.get(index).equals("$")){
            return new Exception("Syntax error, "+"\'"+tokens.get(index-1)+"\' must be followed by an integer");
        }

        return new Exception("Syntax error: Bad Token \'"+tokens.get(index)+"\' at character "+index);
    }
}
