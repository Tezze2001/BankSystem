package com.progetto.sistemabancario.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>, Serializable {
    public static enum Type {
        TRANSACTION,
        DEPOSIT,
        WITHDRAW
    }

    private final UUID uuid = UUID.randomUUID();
    private LocalDateTime time;
    private Account sender;
    private Account receiver;
    private double amount;
    private Type type;

    private Transaction(Account sender, Account receiver, double amount, Type type)
                                throws InvalidParameterTransactionException {
        time = LocalDateTime.now();
        if (sender == null || receiver == null || amount < 0) {
            throw new InvalidParameterTransactionException();
        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
    }
    
    public static Transaction transaction(Account sender, Account receiver, double amount) 
                                throws InvalidParameterTransactionException,
                                        NotEnoughtBalanceException {
        if (sender == null || receiver == null || amount < 0) {
            throw new InvalidParameterTransactionException();
        }
        sender.withdraw(amount);
        receiver.deposit(amount);
        return new Transaction(sender, receiver, amount, Transaction.Type.TRANSACTION);
    }

    public static Transaction depositTransaction( Account account, double amount) 
                                        throws InvalidParameterTransactionException,
                                                NotEnoughtBalanceException {
        if (account == null || amount < 0) {
            throw new InvalidParameterTransactionException();
        }
        account.deposit(amount);
        return new Transaction(account, account, amount, Transaction.Type.DEPOSIT);
    }

    public static Transaction withdrawTransaction(Account account, double amount) 
                                        throws InvalidParameterTransactionException ,
                                                NotEnoughtBalanceException {
        if (account == null || amount < 0) {
            throw new InvalidParameterTransactionException();
        }
        account.withdraw(amount);
        return new Transaction(account, account, amount, Transaction.Type.WITHDRAW);
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }
    
    public Transaction divert() throws InvalidParameterTransactionException, 
                                            NotEnoughtBalanceException,
                                            UnableOperationException {
        if (sender.equals(receiver) || receiver.isDeleted() || sender.isDeleted()) {
            throw new UnableOperationException();
        }
        return Transaction.transaction(receiver, sender, amount);
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
