package com.company;

class Token
{

    private String value;
    private TokType type;


    public Token(String value, TokType type)
    {
        this.value = value;
        this.type = type;
    }

    public Token(TokType prevLexem, String substring) {
    }

    public TokType getType() {

        return type;
    }

    public String getValue() {

        return value;
    }

}