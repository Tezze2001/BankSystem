package com.progetto.sistemabancario.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.sistemabancario.SistemabancarioApplication;
import com.progetto.sistemabancario.model.Account;
import com.progetto.sistemabancario.model.NotAccountFoundException;
import com.progetto.sistemabancario.model.Transaction;

@RestController
@RequestMapping("/api/")
public class SistemaBancarioController {
    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(
                            @RequestBody Map<String, String> data
                            ) {
        if (data == null || 
            data.get("from") == null ||
            data.get("to") == null ||
            data.get("amount") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        double amount = 0;
        UUID idAccountSender = null;
        UUID idAccountReceiver = null;
        try {
            amount = Double.parseDouble(data.get("amount"));  
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            idAccountSender = UUID.fromString(data.get("from"));  
            idAccountReceiver = UUID.fromString(data.get("to"));  
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
        try {
            SistemabancarioApplication.data.transfer(idAccountSender, idAccountReceiver, amount);

        } catch (NotAccountFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Transaction res = Transaction.transaction(sender, receiver, amount);
        Map<String, Object> body = new TreeMap<>();
        body.put("idTransaction", res.getUuid());
        body.put("balance", res.getSender().getBalance());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
}
