package com.company;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        String input = "";
        try {
            File file = new File("C:\\Users\\Asus\\Desktop\\уник\\java\\intelliJ\\src\\com\\company\\CODE");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {

                line = reader.readLine();
                if(line != null){
                    input += line;
                }
            }
            try {

                NewLexer lex = new NewLexer(input);
                ArrayList<Token> tokens = lex.get();

                System.out.println("Кол-во токенов:" + tokens.size());
                System.out.println("Список токенов:");
                for (Token t : tokens)
                    System.out.println(t.getValue() + " \t " + t.getType());
                System.out.println("\n");

                System.out.println("Проверка парсера:");

                new Parser(tokens).lang();
                new RPN(tokens).reverse_polish_notation();


            } catch (Exception e) {
                System.out.println("Упало  " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}
