package com.progetto.sistemabancario.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.sistemabancario.SistemabancarioApplication;
import com.progetto.sistemabancario.model.Transaction;

@RestController
@RequestMapping("/api/")
public class SistemaBancarioController {
    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(
                            @RequestBody Map<String, String> data
                            ) {
        double amount = 0;
        UUID idAccountSender = null;
        UUID idAccountReceiver = null;
        Transaction res = null;
        if (data == null || 
            data.get("from") == null ||
            data.get("to") == null ||
            data.get("amount") == null ||
            data.get("from").equals(data.get("to"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            amount = Double.parseDouble(data.get("amount"));  
            idAccountSender = UUID.fromString(data.get("from"));  
            idAccountReceiver = UUID.fromString(data.get("to"));  
            res = SistemabancarioApplication.data.transfer(idAccountSender, idAccountReceiver, amount);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> body = new HashMap<>();
        Map<String, String> balanceSender = new HashMap<>();
        balanceSender.put("id", res.getSender().getUuid().toString());
        balanceSender.put("balance", String.valueOf(res.getSender().getBalance()));
        Map<String, String> balanceReceiver = new TreeMap<>();
        balanceReceiver.put("id", res.getReceiver().getUuid().toString());
        balanceReceiver.put("balance", String.valueOf(res.getReceiver().getBalance()));
        body.put("sender", balanceSender);
        body.put("receiver", balanceReceiver);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
    
    @PostMapping("/divert")
    public ResponseEntity<Map<String, Object>> divertTransaction(
                            @RequestBody Map<String, String> data
                            ) {
        if (data == null || 
            data.get("id") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            UUID divert = UUID.fromString(data.get("id"));
            SistemabancarioApplication.data.divert(divert);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
