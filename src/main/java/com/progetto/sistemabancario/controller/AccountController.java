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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.progetto.sistemabancario.SistemabancarioApplication;
import com.progetto.sistemabancario.model.Account;
import com.progetto.sistemabancario.model.ExeID;
import com.progetto.sistemabancario.model.NotAccountFoundException;
import com.progetto.sistemabancario.model.Transaction;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @GetMapping
    public ResponseEntity<Map<ExeID, Account>> getAllAccount() {
        return new ResponseEntity<>(SistemabancarioApplication
                                        .data.getAllAccounts(),
                                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody Map<String, String> newResource) {
        if (newResource == null ||
            newResource.get("name") == null || newResource.get("surname") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, ExeID> couple = new HashMap<>();
        try {
            couple.put("id", SistemabancarioApplication.data.addAccounts(
                new Account(newResource.get("name"), 
                            newResource.get("surname"))));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(couple, HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestParam String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ExeID idAccount = null;
        try {
            idAccount = ExeID.fromString(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SistemabancarioApplication.data.deleteAccount(idAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getAccount(@PathVariable("accountId") String id) {
        Map<String, Object> body = null;
        try {
            body = SistemabancarioApplication
                    .data
                    .getAccountInfo(ExeID.fromString(id));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        Account search = null;
        try {
            search = SistemabancarioApplication
                        .data
                        .getAccount(ExeID.fromString(id));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("X-Sistema-Bancario", 
            search.getName() + ";" + search.getSurname());
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<Object> depositOrWithdraw(
                            @PathVariable("accountId") String id,
                            @RequestBody Map<String, String> depositOrWithdraw
                            ) {
        if (depositOrWithdraw == null || 
            depositOrWithdraw.get("amount") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        double amount = 0;
        ExeID idAccount = null;
        try {
            amount = Double.parseDouble(depositOrWithdraw.get("amount"));  
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            idAccount = ExeID.fromString(id);  
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
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> body = new TreeMap<>();
        body.put("idTransaction", res.getUuid());
        body.put("balance", res.getSender().getBalance());
        return new ResponseEntity<>(body, HttpStatus.CREATED);
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
        ExeID idAccount = null;
        try {
            idAccount = ExeID.fromString(id);  
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        try {
            SistemabancarioApplication.data.setNameAndSurname(idAccount, 
                        nameAndSurname.get("name"), 
                        nameAndSurname.get("surname"));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<String> changeNameOrSurnameAccount(
                            @PathVariable("accountId") String id,
                            @RequestBody Map<String, String> nameOrSurname
                            ) {
        if (nameOrSurname == null || 
            (nameOrSurname.get("name") == null && nameOrSurname.get("surname") == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ExeID idAccount = null;
        try {
            idAccount = ExeID.fromString(id);  
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        try {
            SistemabancarioApplication.data.setNameOrSurname(idAccount, 
                        nameOrSurname.get("name"), 
                        nameOrSurname.get("surname"));
        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}