package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class RPN {

    private ArrayList<Token> tokens;
    private ArrayList<Token> OutputList = new ArrayList<>();
    Stack<Token> stack = new Stack<>();
    Stack<Token> st_calculate = new Stack<>();
    public RPN(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }


    public ArrayList<Token> reverse_polish_notation() {
        for(Token token : this.tokens){
            System.out.print(token.getValue() + " ");
        }
        System.out.println("");

        for(Token token : tokens){
            if(token.getType().equals(TokType.VAR_DECLR) ||
                    token.getType().equals(TokType.VARIABLE ) ||
                    token.getType().equals(TokType.ASSIGN_OP) ||
                    token.getType().equals(TokType.DIGIT)){

                OutputList.add(token);

            } else if(token.getType().equals(TokType.OP) ||
                    token.getType().equals(TokType.LeftRoundBracket) ||
                    token.getType().equals(TokType.RightRoundBracket)){



                if ((!stack.empty()) && (!token.getType().equals((TokType.LeftRoundBracket)))) {
                    if (token.getType() == (TokType.RightRoundBracket)) {
                        Token opInStack = stack.pop();

                        while(!opInStack.getType().equals((TokType.LeftRoundBracket))){
                            OutputList.add(opInStack);
                            opInStack = stack.pop();
                        }
                    } else {
                        if(prioritet(token) > prioritet(stack.peek())){
                            stack.add(token);
                        } else {
                            while (!stack.empty() && prioritet(token) <= prioritet(stack.peek())) {
                                OutputList.add(stack.pop());
                            }
                            stack.add(token);
                        }
                    }
                } else {
                    stack.add(token);
                }
            }
        }

        if (!stack.empty()) {
            for (Token token : stack) {
                OutputList.add(token);

            }

        }

        System.out.println();
        System.out.println("Выходной список");

        for(Token token : OutputList){
            System.out.print(token.getValue() + " ");
        }
        System.out.println();

        System.out.println("Результат вычислений : ");
        System.out.println("var a = " + calculate(OutputList));

        return OutputList;
    }

    public int calculate(ArrayList<Token>RPN) {

        int operand1 = 0;
        int operand2 = 0;
        String operation = "";
        int res = 0;



        for (Token token : RPN) {
            if (token.getType().equals(TokType.DIGIT)) {
                st_calculate.push(token);
//
            } else if (token.getType().equals(TokType.OP)) {
                operation = token.getValue();
                operand2 = Integer.parseInt(st_calculate.pop().getValue());
                operand1 = Integer.parseInt(st_calculate.pop().getValue());

                switch (operation) {
                    case "+":
                        res = operand1 + operand2;
                        break;
                    case "-":
                        res = operand1 - operand2;
                        break;
                    case "*":
                        res = operand1 * operand2;
                        break;
                    case "/":
                        res = operand1 / operand2;
                        break;
                    default:
                        System.out.println("Error");
                        break;

                }
                st_calculate.push(new Token(Integer.toString(res),TokType.DIGIT));
            }
            //System.out.println(res);
        }
     return  res = Integer.parseInt(st_calculate.peek().getValue());
    }

    public int prioritet(Token token){
        if(token.getValue().equals("*")) return 3;
        if(token.getValue().equals("/")) return 3;
        if(token.getValue().equals("+")) return 2;
        if(token.getValue().equals("-")) return 2;

        return 0;
    }


}
