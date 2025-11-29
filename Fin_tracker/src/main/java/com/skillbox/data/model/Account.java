package com.skillbox.data.model;

import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.data.repository.TransactionRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс представляющий собой счет в банке
 */
@AllArgsConstructor
public class Account implements AccountInfo, BalanceOperations, AccountStatement {
    private String accId;
    private AccountType type;
    private int userId;

    @Override
    public int getAccountId() {
        return Integer.parseInt(accId);
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public AccountType getAccountType() {
        return type;
    }

    @Override
    public List<Transaction> getTransactions() {
        TransactionRepository transactionRepository = new TransactionParse();
        return transactionRepository.readAll()
                .stream()
                .filter(transaction -> transaction.getIdOfAccount().equals(accId))
                .toList();
    }

    @Override
    public BigDecimal getBalance() {
        BigDecimal sum = getTransactions().stream().map(Transaction::getSumOfTransaction)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    @Override
    public void addTransaction(Transaction transaction) {

    }

    @Override
    public String toString() {
        return accId + "," +
                type + "," +
                userId;
    }
}