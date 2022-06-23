package com.progetto.sistemabancario.model;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Account implements Comparable<Account>, Serializable {
    private final ExeID id = new ExeID();
    private String name;
    private String surname;
    private double balance;

    public Account(String name, String surname, double amount) throws IllegalArgumentException {
        if (name == null || surname == null || amount < 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.surname = surname;
        this.balance = amount;
    }

    public Account(String name, String surname) throws IllegalArgumentException {
        this(name, surname, 0f); 
    }


    public void setSurname(String surname) throws IllegalArgumentException {
        if (surname == null) {
            return;
        }
        if (this.surname != null &&
            Pattern.matches("^([a-zA-Z]+)|([a-zA-Z\s]+)$", surname)) {
            this.surname = surname;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (name == null) {
            return;
        }
        if (this.name != null  && 
            Pattern.matches("^([a-zA-Z]+)|([a-zA-Z\s]+)$", name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ExeID getId() {
        return id;
    }

    public boolean disponibility(double amount){
        return getBalance() - amount >= 0;
    }

    public void withdraw(double amount) throws NotEnoughtBalanceException {
        if (!disponibility(amount)) {
            throw new NotEnoughtBalanceException();
        }
        setBalance(getBalance() - amount);
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    public void delete() {
        this.name = null;
        this.surname = null;
        this.balance = 0;
    }

    public boolean isDeleted() {
        return this.name == null &&
            this.surname == null &&
            this.balance == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public int compareTo(Account a) {
        return id.compareTo(a.getId());
    }

}
