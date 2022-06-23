package com.progetto.sistemabancario.model;

public class NotEnoughtBalanceException extends Exception {
    
    public NotEnoughtBalanceException(String msg) {
        super(msg);
    }

    public NotEnoughtBalanceException() {
        this("Not enought balance in the account");
    }
    
}