package com.progetto.sistemabancario.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import com.progetto.sistemabancario.SistemabancarioApplication;
import com.progetto.sistemabancario.model.Account;
import com.progetto.sistemabancario.model.NotAccountFoundException;
import com.progetto.sistemabancario.model.Transaction;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @GetMapping
    public ResponseEntity<Map<UUID, Account>> getAllAccount() {
        return new ResponseEntity<>(SistemabancarioApplication
                                        .data.getAllAccounts(),
                                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createAccount(@RequestBody Map<String, String> newResource) {
        if (newResource.get("name") == null || newResource.get("surname") == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, UUID> couple = new HashMap<>();
        couple.put("id", SistemabancarioApplication.data.addAccounts(
                new Account(newResource.get("name"), 
                            newResource.get("surname"))));
        return new ResponseEntity<>(couple, HttpStatus.CREATED);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody Map<String, String> deleteResource) {
        if (deleteResource == null || deleteResource.get("id") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UUID id = null;
        try {
            id = UUID.fromString(deleteResource.get("id"));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SistemabancarioApplication.data.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable("accountId") String id) {
        Map<String, Object> body = null;
        try {
            body = SistemabancarioApplication
                    .data
                    .getAccountInfo(UUID.fromString(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        Account search = null;
        try {
            search = SistemabancarioApplication
                        .data
                        .getAccount(UUID.fromString(id));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("X-Sistema-Bancario", 
            search.getName() + ";" + search.getSurname());
        return new ResponseEntity<Map<String, Object>>(body, headers, HttpStatus.OK);
    }

    @GetMapping("/transaction/{accountId}")
    public ResponseEntity<Set<Transaction>> getAccountTransaction(@PathVariable("accountId") String id) {
        Set<Transaction> body = null;
        try {
            body = SistemabancarioApplication
                    .data
                    .getAllTransactionFromInfo(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Set<Transaction>>(body, HttpStatus.OK);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> depositOrWithdraw(
                            @PathVariable("accountId") String id,
                            @RequestBody Map<String, String> depositOrWithdraw
                            ) {
        if (depositOrWithdraw == null || 
            depositOrWithdraw.get("amount") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        double amount = 0;
        UUID idAccount = null;
        try {
            amount = Double.parseDouble(depositOrWithdraw.get("amount"));  
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            idAccount = UUID.fromString(id);  
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Transaction res = null;
        try {
            if (amount < 0) {
                res = SistemabancarioApplication
                            .data
                            .withdraw(idAccount, amount);
            } else {
                res = SistemabancarioApplication
                            .data
                            .deposit(idAccount, amount);
            }
            if (res == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> body = new TreeMap<>();
        body.put("idTransaction", res.getUuid());
        body.put("balance", res.getSender().getBalance());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<String> changeNameSurnameAccount(
                            @PathVariable("accountId") String id,
                            @RequestBody Map<String, String> nameAndSurname
                            ) {
        if (nameAndSurname == null || 
            nameAndSurname.get("name") == null ||
            nameAndSurname.get("surname") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UUID idAccount = null;
        try {
            idAccount = UUID.fromString(id);  
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        try {
            SistemabancarioApplication.data.setNameAndSurname(idAccount, 
                        nameAndSurname.get("name"), 
                        nameAndSurname.get("surname"));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<String> changeNameOrSurnameAccount(
                            @PathVariable("accountId") String id,
                            @RequestBody Map<String, String> nameOrSurname
                            ) {
        if (nameOrSurname == null || 
            (nameOrSurname.get("name") == null &&
            nameOrSurname.get("surname") == null) ||
            (nameOrSurname.get("name") != null &&
            nameOrSurname.get("surname") != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UUID idAccount = null;
        try {
            idAccount = UUID.fromString(id);  
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        try {
            SistemabancarioApplication.data.setNameOrSurname(idAccount, 
                        nameOrSurname.get("name"), 
                        nameOrSurname.get("surname"));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}