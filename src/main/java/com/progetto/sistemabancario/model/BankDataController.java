package com.progetto.sistemabancario.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;

public class BankDataController {
    private Map<UUID, Account> accounts;
    private Map<UUID, Transaction> transactions;

    public BankDataController() {
        accounts = new TreeMap<>();
        transactions = new TreeMap<>();
    }

    public UUID addAccounts(Account a) {
        accounts.put(a.getUuid(), a);
        return a.getUuid();
    }

    public Set<UUID> getAllTransactionFrom(UUID id) {
        Set<UUID> t = new TreeSet<>();
        for (Entry<UUID, Transaction> entry : transactions.entrySet()) {
            if (entry.getValue().getSender().getUuid().equals(id)) {
                t.add(entry.getKey());
            }
        }
        return t;
    }

    public Account getAccount(UUID id) {
        return accounts.get(id);
    }

    public Map<String, Object> getAccountInfo(UUID id){
        Map<String, Object> fields = new TreeMap<>();
        fields.put("name", accounts.get(id).getName());
        fields.put("surname", accounts.get(id).getSurname());
        fields.put("balance", accounts.get(id).getBalance());
        fields.put("transactions", getAllTransactionFrom(id));
        return fields;
    }

    public Map<UUID, Account> getAllAccounts(){
        return accounts;
    }

    public Account deleteAccount(UUID id) {
        return accounts.remove(id);
    }

    public Transaction withdraw(UUID id, double amount) {
        Account a = accounts.get(id);
        Transaction t = Transaction.withdrawTransaction(a, amount);
        if (t == null) {
            return null;
        }
        return transactions.put(UUID.randomUUID(), t);
    }
    
    public Transaction deposit(UUID id, double amount) {
        Account a = accounts.get(id);
        Transaction t = Transaction.depositTransaction(a, amount);
        transactions.put(t.getUuid(), t);
        return t;
    }
}
