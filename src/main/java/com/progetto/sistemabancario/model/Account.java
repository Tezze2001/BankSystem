package com.progetto.sistemabancario.model;

import java.util.UUID;

public class Account implements Comparable<Account>{
    private final UUID uuid = UUID.randomUUID();
    private String name;
    private String surname;
    private double balance;

    public Account(String name, String surname, double amount) {
        setName(name);
        setSurname(surname);
        deposit(amount);
    }

    public Account(String name, String surname) {
        this(name, surname, 0f); 
    }


    public void setSurname(String surname) {
        this.surname = surname;
        if (this.surname == null) {
            surname = "";
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
        if (this.name == null) {
            name = "";
        }
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean disponibility(double amount){
        return getBalance() - amount >= 0;
    }

    public void withdraw(double amount) throws NotEnoughtBalance {
        if (!disponibility(amount)) {
            throw new NotEnoughtBalance();
        }
        this.balance -= amount;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }
    
    @Override
    public int compareTo(Account a) {
        return uuid.compareTo(a.getUuid());
    }
}
