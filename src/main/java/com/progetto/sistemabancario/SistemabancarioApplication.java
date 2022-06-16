package com.progetto.sistemabancario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.progetto.sistemabancario.model.Account;
import com.progetto.sistemabancario.model.BankDataController;

@SpringBootApplication
public class SistemabancarioApplication {
	public static BankDataController data;
	public static void main(String[] args) {
		SpringApplication.run(SistemabancarioApplication.class, args);
		data = new BankDataController();
		data.addAccounts(new Account("Armando", "Pasta", 2222f));
		data.addAccounts(new Account("Francesco", "Selvino", 2222f));
		data.addAccounts(new Account("Giada", "Corridoni", 2222f));
		data.addAccounts(new Account("Marta", "Terra", 2222f));
		data.addAccounts(new Account("Marco", "Rossi", 2222f));
	}

}
