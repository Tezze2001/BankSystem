package com.progetto.sistemabancario.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;

public class BankDataController implements Serializable {
    private Map<ExeID, Account> accounts;
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

    public ExeID addAccounts(Account a) {
        ExeID id = a.getId();
        synchronized (accounts) {
            accounts.put(id, a);
        }   
        saveDataOnFile();
        return id;
    }

    public Set<Transaction> getAllTransactionFrom(ExeID id) {
        Set<Transaction> t = new TreeSet<>();
        Set<Entry<UUID, Transaction>> keySet = null;
        synchronized (transactions) {
            keySet = transactions.entrySet();
        }
        for (Entry<UUID, Transaction> entry : keySet) {
            if (entry.getValue().getSender().getId().equals(id)) {
                t.add(entry.getValue());
            }
        }
        return t;
    }

    public Account getAccount(ExeID id) throws NotAccountFoundException{
        Account found = null;
        synchronized (accounts) {
            found = accounts.get(id);
        }
        if (found == null) {
            throw new NotAccountFoundException();
        }
        return found;
    }

    public Map<String, Object> getAccountInfo(ExeID id) throws NotAccountFoundException {
        Map<String, Object> fields = new TreeMap<>();
        Account a = getAccount(id);
        if (a.isDeleted()) {
            throw new NotAccountFoundException();
        }
        fields.put("name", a.getName());
        fields.put("surname", a.getSurname());
        fields.put("balance", a.getBalance());
        fields.put("transactions", getAllTransactionFrom(id));
        return fields;
    }

    public Map<ExeID, Account> getAllAccounts(){
        Map<ExeID, Account> accounts = new TreeMap<>();
        Collection<Account> values = null;
        synchronized (accounts) {
            values = this.accounts.values();
        }
        for (Account a : values) {
            if (!a.isDeleted()) {
                accounts.put(a.getId(), a);
            }
        }
        return accounts;
    }

    public Account deleteAccount(ExeID id) {
        Account rem = null;
        synchronized (accounts) {
            rem = accounts.get(id);
        }
        rem.delete();
        saveDataOnFile();
        return rem;
    }



    public void setNameAndSurname(ExeID id, String name, String surname) throws NotAccountFoundException{
        Account found = getAccount(id);
        found.setName(name);
        found.setSurname(surname);
        saveDataOnFile();
    }

    public void setNameOrSurname(ExeID id, String name, String surname) throws NotAccountFoundException{
        synchronized (accounts) {
            Account found = getAccount(id);
            if (name == null) {
                found.setSurname(surname);
            } else {
                found.setName(name);
            }
        }
        saveDataOnFile();
    }

    public Transaction withdraw(ExeID id, double amount) 
                                throws NotAccountFoundException, 
                                    InvalidParameterTransactionException,
                                    NotEnoughtBalanceException{
        Account a = getAccount(id);
        Transaction t = Transaction.withdrawTransaction(a, Math.abs(amount));
        synchronized (transactions) {
            transactions.put(t.getUuid(), t);
        }
        saveDataOnFile();
        return t;
    }
    
    public Transaction deposit(ExeID id, double amount) 
                                throws NotAccountFoundException,
                                InvalidParameterTransactionException,
                                NotEnoughtBalanceException{
        Account a = getAccount(id);
        Transaction t = Transaction.depositTransaction(a, amount);
        synchronized (transactions) {
            transactions.put(t.getUuid(), t);
        }
        saveDataOnFile();
        return t;
    }

    public Transaction transfer(ExeID idSender, ExeID idReceiver, double amount) 
                                            throws NotAccountFoundException,
                                                NotEnoughtBalanceException,
                                                InvalidParameterTransactionException {
        Account sender = getAccount(idSender);
        Account receiver = getAccount(idReceiver);
        Transaction t = Transaction.transaction(sender, receiver, amount);
        synchronized (transactions) {
            transactions.put(t.getUuid(), t);
        }
        saveDataOnFile();
        return t;
    }

    public Transaction divert(UUID idTransaction) 
                                throws NotAccountFoundException,
                                    NotEnoughtBalanceException,
                                    InvalidParameterTransactionException,
                                    UnableOperationException {
        Transaction t = null;
        synchronized (transactions) {
            t = transactions.get(idTransaction).divert();
            transactions.put(t.getUuid(), t);
        }
        saveDataOnFile();
        return t;
    }

    private synchronized boolean saveDataOnFile() {
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
            System.out.print(e.getMessage());
            return null;
        }
    }

}
