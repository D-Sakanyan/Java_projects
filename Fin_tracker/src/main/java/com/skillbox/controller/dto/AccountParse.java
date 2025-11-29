package com.skillbox.controller.dto;

import com.skillbox.data.model.Account;
import com.skillbox.data.model.AccountType;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.AccountRepository;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class AccountParse implements AccountRepository {
    private List<Account> accountsList = new ArrayList<>();
    private BufferedReader bufferedReader;
    private String line;
    private String parts[];
    @Getter
    private final Map<Account, List<Transaction>> map = new HashMap<>();

    @Override
    public List<Account> readAll() {
        try {
            ApplicationFiles applicationFiles = new ApplicationFiles();
            bufferedReader = new BufferedReader(new FileReader(applicationFiles.getAccountFilename()));
            while ((line = bufferedReader.readLine()) != null) {
                parts = line.split(",");
                String accId = parts[0];
                AccountType type = AccountType.of(Integer.parseInt(parts[1]));
                int userId = Integer.parseInt(parts[2]);

                Account account = new Account(accId, type, userId);
                accountsList.add(account);

                List<Transaction> accTransactions = account.getTransactions();
                map.put(account, accTransactions);
            }
            return accountsList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}