package com.skillbox.controller.dto;

import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.TransactionRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionParse implements TransactionRepository {

    @Override
    public List<Transaction> readAll() {
        List<Transaction> list = new ArrayList<>();
        try {
            ApplicationFiles applicationFiles = new ApplicationFiles();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(applicationFiles.getTransactionFilename()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] parts = line.split(",", 7);

                String idOfAccount = parts.length > 0 ? parts[0] : "";
                int idOfTransaction = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
                LocalDateTime dateTimeFormatter = parts.length > 2 && !parts[2].isBlank()
                        ? LocalDateTime.parse(parts[2].trim())
                        : LocalDateTime.now();
                String categoryOfTransaction = parts.length > 3 ? parts[3] : "";
                BigDecimal sumOfTransaction = parts.length > 4 ? new BigDecimal(parts[4]) : BigDecimal.ZERO;
                String typeOfTransaction = parts.length > 5 ? parts[5] : "";
                String patternOfType = parts.length > 6 ? parts[6] : "";
                Transaction transaction = new Transaction(
                        idOfAccount, idOfTransaction, dateTimeFormatter,
                        categoryOfTransaction, sumOfTransaction, typeOfTransaction, patternOfType) {
                };
                list.add(transaction);
            }
        } catch (Exception e) {
            throw
                    new RuntimeException(e);
        }
        return list;
    }
}
