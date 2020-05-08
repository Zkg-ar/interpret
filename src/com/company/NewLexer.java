package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewLexer
{

    private ArrayList<Token> tokens = new ArrayList<>();


    NewLexer(String input)
    {




        ArrayList<String> mas = new ArrayList();


        StringTokenizer st = new StringTokenizer(input, " \t\n\\s\r,.");

        while(st.hasMoreTokens())
            mas.add(st.nextToken());


        for(String str : mas)
        {
            TokType type = check(str);

            if(type == null)
            {
                System.err.println("Doesn't exist");
                break;
            }

            tokens.add(new Token(str, type));
        }



    }


    private TokType check(String string) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Pattern pattern1 = Pattern.compile("^(0|([1-9][0-9]*))$");
        Pattern pattern2 = Pattern.compile("\\+|-|\\*|/");
        Pattern pattern3 = Pattern.compile("^=$");
        Pattern pattern4 = Pattern.compile(">|<|<=|>=");
        Pattern pattern5 = Pattern.compile("\\(");
        Pattern pattern6 = Pattern.compile("\\)");
        Pattern pattern7 = Pattern.compile("\\;");

        Matcher matcher = pattern.matcher(string);
        if(matcher.find())
        {
            switch(string)
            {
                case "var":
                    return TokType.VAR_DECLR;
                case "IF":
                    return TokType.IF;
                case "WHILE":
                    return TokType.WHILE;
                case "DO":
                    return TokType.DO;
                case "BEGIN":
                    return TokType.BEGIN;
                case "END":
                    return TokType.END;
                default:
                    return TokType.VARIABLE;
            }
        }
        else matcher = pattern1.matcher(string);

        if(matcher.find())
            return TokType.DIGIT;

        else matcher = pattern2.matcher(string);

        if(matcher.find())
            return TokType.OP;

        else matcher = pattern3.matcher(string);

        if(matcher.find())
            return TokType.ASSIGN_OP;

        else matcher = pattern4.matcher(string);

        if(matcher.find())
            return TokType.COMP_OP;

        else matcher = pattern5.matcher(string);

        if(matcher.find())
            return TokType.LeftRoundBracket;

        else matcher = pattern6.matcher(string);

        if(matcher.find())
            return TokType.RightRoundBracket;

        else matcher = pattern7.matcher(string);

        if(matcher.find())
            return TokType.EndOfStr;

        return null;
    }



    public ArrayList<Token> get()
    {
        return tokens;
    }



}
