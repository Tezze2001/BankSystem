package com.progetto.sistemabancario.model;

public class NotAccountFoundException extends Exception{

    public NotAccountFoundException(String msg) {
        super(msg);
    }

    public NotAccountFoundException() {
        this("Not account found in the db");
    }
    
}
