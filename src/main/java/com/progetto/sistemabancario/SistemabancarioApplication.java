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
	}

}
