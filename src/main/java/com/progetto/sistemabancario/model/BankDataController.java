package com.progetto.sistemabancario.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;

public class BankDataController implements Serializable {
    private Map<UUID, Account> accounts;
    private Map<UUID, Transaction> transactions;

    public BankDataController() {
        BankDataController b = loadDataFromFile();
        if (b == null) {
            accounts = new TreeMap<>();
            transactions = new TreeMap<>();
        } else {
            accounts = b.accounts;
            transactions = b.transactions;
            System.out.println("**Loaded data from file!!!**");
        }
    }

    public UUID addAccounts(Account a) {
        UUID id = a.getUuid();
        accounts.put(id, a);
        saveDataOnFile();
        return id;
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

    public Set<Transaction> getAllTransactionFromInfo(UUID id) {
        Set<UUID> idTransactions = getAllTransactionFrom(id);
        Set<Transaction> t = new TreeSet<>();
        for (UUID uuid : idTransactions) {
            t.add(transactions.get(uuid));
        }
        return t;
    }

    public Account getAccount(UUID id) throws NotAccountFoundException{
        Account found = accounts.get(id);
        if (found == null) {
            throw new NotAccountFoundException();
        }
        return found;
    }

    public Map<String, Object> getAccountInfo(UUID id) throws NotAccountFoundException {
        Map<String, Object> fields = new TreeMap<>();
        Account a = accounts.get(id);
        if (a == null) {
            throw new NotAccountFoundException();
        }
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
        Account rem = accounts.remove(id);
        saveDataOnFile();
        return rem;
    }

    public void setNameAndSurname(UUID id, String name, String surname) throws NotAccountFoundException{
        Account found = getAccount(id);
        found.setName(name);
        found.setSurname(surname);
        saveDataOnFile();
    }

    public void setNameOrSurname(UUID id, String name, String surname) throws NotAccountFoundException{
        Account found = getAccount(id);
        if (name == null) {
            found.setSurname(surname);
        } else {
            found.setName(name);
        }
        saveDataOnFile();
    }

    public Transaction withdraw(UUID id, double amount) 
                                throws NotAccountFoundException, 
                                    InvalidParameterTransactionException,
                                    NotEnoughtBalance{
        Account a = getAccount(id);
        Transaction t = Transaction.withdrawTransaction(a, Math.abs(amount));
        if (t == null) {
            return null;
        }
        transactions.put(t.getUuid(), t);
        saveDataOnFile();
        return t;
    }
    
    public Transaction deposit(UUID id, double amount) 
                                throws NotAccountFoundException,
                                InvalidParameterTransactionException,
                                NotEnoughtBalance{
        Account a = getAccount(id);
        Transaction t = Transaction.depositTransaction(a, amount);
        transactions.put(t.getUuid(), t);
        saveDataOnFile();
        return t;
    }

    public Transaction transfer(UUID idSender, UUID idReceiver, double amount) 
                                            throws NotAccountFoundException,
                                                NotEnoughtBalance,
                                                InvalidParameterTransactionException {
        Account sender = getAccount(idSender);
        Account receiver = getAccount(idReceiver);
        Transaction t = Transaction.transaction(sender, receiver, amount);
        transactions.put(t.getUuid(), t);
        saveDataOnFile();
        return t;
    }

    public Transaction divert(UUID idTransaction) 
                                throws NotAccountFoundException,
                                    NotEnoughtBalance,
                                    InvalidParameterTransactionException {
        Transaction t = transactions.get(idTransaction).divert();
        transactions.put(t.getUuid(), t);
        saveDataOnFile();
        return t;
    }

    private boolean saveDataOnFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/DB.data");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private BankDataController loadDataFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/main/resources/DB.data");
            ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
            BankDataController b = (BankDataController) objectOutputStream.readObject();
            objectOutputStream.close();
            return b;
        } catch (Exception e) {
            return null;
        }
    }

}
