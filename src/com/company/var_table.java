package com.company;

class var_table{

    private final String ID;
    private final Token value;

    var_table(String ID, Token value)
    {
        this.ID = ID;
        this.value = value;
    }

    public Token getValue() {
        return value;
    }
    public String getID() {
        return ID;
    }

}