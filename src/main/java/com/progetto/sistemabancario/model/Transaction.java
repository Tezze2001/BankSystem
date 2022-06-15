package com.progetto.sistemabancario.model;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    private final UUID uuid = UUID.randomUUID();
    private LocalDateTime time;
    private Account sender;
    private Account receiver;
    private double amount;

    private Transaction(Account sender, Account receiver, double amount)
                                throws InvalidParameterTransactionException {
        time = LocalDateTime.now();
        if (sender == null || receiver == null || amount <= 0) {
            throw new InvalidParameterTransactionException();
        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
    
    public static Transaction transaction(Account sender, Account receiver, double amount) 
                                throws InvalidParameterTransactionException,
                                        NotEnoughtBalance {
        sender.withdraw(amount);
        receiver.deposit(amount);
        return new Transaction(sender, receiver, amount);
    }

    public static Transaction depositTransaction( Account account, double amount) 
                                        throws InvalidParameterTransactionException,
                                                NotEnoughtBalance {
        account.deposit(amount);
        return new Transaction(account, account, amount);
    }

    public static Transaction withdrawTransaction(Account account, double amount) 
                                        throws InvalidParameterTransactionException ,
                                                NotEnoughtBalance {
        account.withdraw(amount);
        return new Transaction(account, account, amount);
    }

    public Account getSender() {
        return sender;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getTime() {
        return time;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public int compareTo(Transaction t) {
        return t.getTime().compareTo(time);
    }

    
}
