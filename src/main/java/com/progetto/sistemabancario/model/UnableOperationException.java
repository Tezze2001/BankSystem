package com.progetto.sistemabancario.model;

public class UnableOperationException extends Exception{
    public UnableOperationException(String msg) {
        super(msg);
    }

    public UnableOperationException() {
        this("Unable operations");
    }
}
