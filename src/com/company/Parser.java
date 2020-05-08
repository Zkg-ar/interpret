package com.company;
import java.util.ArrayList;


public class Parser {

    private final ArrayList<Token> tokens;
    int counter = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void lang() throws LangParseException {
        try {
            while(counter < tokens.size()){
                expr();
            }

            System.out.println("Проверка кода прошла успешно. Можно начинать этап компиляции");
        } catch(LangParseException e){
            System.out.println("Поймали ошибку....");
            System.out.println(e);
        }
    }

    private void expr() throws LangParseException {
        int step = counter;

        try {
            assign_expr();
        } catch (LangParseException e) {
            counter = step;
            try{
                if_expr();
            } catch(LangParseException e2){
                counter = step;
                try{
                    while_expr();
                } catch(LangParseException e3){
                    throw e;
                }
            }
        }
    }
    private void assign_expr () throws LangParseException {
        VAR_DECLR();
        var();
        assign_op();
        value_expr();
        EndOfStr();


    }
    private void if_expr () throws LangParseException {

        IF();
        if_while_do_while_head();
        if_while_do_while_body();
    }
    private void logic_comp() throws LangParseException {
        value();
        comp_op();
        value();
    }
    private void while_expr () throws LangParseException {

        int step = counter;

        try {
            while_prec_expr ();
        } catch (LangParseException e) {
            counter = step;
            do_while_expr();
        }
    }
    private void while_prec_expr () throws LangParseException {
        WHILE();
        if_while_do_while_head();
        if_while_do_while_body();
    }
    private void if_while_do_while_head () throws LangParseException {
        LeftRoundBracket();
        logic_comp();
        RightRoundBracket();
    }
    private void if_while_do_while_body () throws LangParseException {
        int step = counter;
        BEGIN();

        try {
            while(!(tokens.get(counter).getType().equals(TokType.END)))

                expr();

            END();
        } catch (LangParseException e){

            counter = step;
            END();
        }
    }
    private void do_while_expr () throws LangParseException {
        DO();
        if_while_do_while_body();
        WHILE();
        if_while_do_while_head ();
    }
    private Token match() {
        Token token = null;

        if (counter < tokens.size()) {
            token = tokens.get(counter);
            counter++;
        }

        return token;
    }


    private boolean flag = false;

    private void value_expr() throws LangParseException
    {

        while (tokens.size() > counter)
        {

            if (tokens.get(counter).getType().equals(TokType.EndOfStr))
                break;
            else if(checkBracket())
                continue;


            if(!flag)
                value();
            else
                flag = false;


            if (tokens.get(counter).getType().equals(TokType.EndOfStr))
                break;

            else if(checkBracket())
            {
                flag = true;
                continue;
            }
            op();

        }
        try {
            --counter;
            value();
        }catch(LangParseException ignore){
            throw new LangParseException("Неккоректный токен");
        }


    }


    private boolean checkBracket()
    {
        if (tokens.get(counter).getType().equals(TokType.LeftRoundBracket)
                || (tokens.get(counter).getType().equals(TokType.RightRoundBracket)))
        {
            ++counter;
            return true;
        }

        return false;
    }


    private void value() throws LangParseException
    {
        int step = counter;


        try
        {
            var();
        }
        catch (LangParseException e)
        {
            counter = step;


            digit();
        }
    }


    /*private void brackets() throws LangParseException {
        LeftRoundBracket();
        value_expr();
        RightRoundBracket();
    }*/

    private void assign_op() throws LangParseException {
        matchToken(match(), TokType.ASSIGN_OP);
    }

    private void var() throws LangParseException {
        matchToken(match(), TokType.VARIABLE);
    }

    private void digit() throws LangParseException {
        matchToken(match(), TokType.DIGIT);
    }
    private void VAR_DECLR() throws LangParseException {
        matchToken(match(), TokType.VAR_DECLR);
    }

    private void op() throws LangParseException {
        matchToken(match(), TokType.OP);
    }
    private void comp_op() throws LangParseException {
        matchToken(match(), TokType.COMP_OP);
    }
    private void IF() throws LangParseException {
        matchToken(match(), TokType.IF);
    }
    private void EndOfStr() throws LangParseException {
        matchToken(match(), TokType.EndOfStr);
    }


    private void LeftRoundBracket() throws LangParseException {
        matchToken(match(), TokType.LeftRoundBracket);
    }
    private void RightRoundBracket() throws LangParseException {
        matchToken(match(), TokType.RightRoundBracket);
    }
    private void BEGIN() throws LangParseException {
        matchToken(match(), TokType.BEGIN);
    }
    private void END() throws LangParseException {
        matchToken(match(), TokType.END);
    }
    private void WHILE() throws LangParseException {
        matchToken(match(), TokType.WHILE);
    }
    private void DO() throws LangParseException {
        matchToken(match(), TokType.DO);
    }


    private void matchToken(Token token, TokType type) throws LangParseException {
        if (!token.getType().equals(type)) {
            throw new LangParseException(type
                    + " expected but "
                    + token.getType().name() + ": " + token.getValue()
                    + " found");

        }
    }



    public class LangParseException extends Exception {
        public LangParseException(String s) {
            super(s);
        }
    }


}