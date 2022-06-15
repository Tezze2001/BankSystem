package com.progetto.sistemabancario.model;

public class InvalidParameterTransactionException extends Exception {

    public InvalidParameterTransactionException(String msg) {
        super(msg);
    }

    public InvalidParameterTransactionException() {
        this("Invalid parameter for transaction");
    }
}
