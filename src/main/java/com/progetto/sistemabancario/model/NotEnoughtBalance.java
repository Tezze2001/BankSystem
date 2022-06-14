package com.progetto.sistemabancario.model;

public class NotEnoughtBalance extends Exception {
    
    public NotEnoughtBalance(String msg) {
        super(msg);
    }

    public NotEnoughtBalance() {
        this("Not enought balance in the account");
    }
    
}