package com.skillbox.controller.dto;

import com.skillbox.data.model.Commentable;
import com.skillbox.data.repository.TransactionRepository;

import java.util.Arrays;
import java.util.List;

public class TransactionCommentable implements Commentable {
    TransactionRepository transactionRepository = new TransactionParse();

    @Override
    public List<String> getComments() {
        return transactionRepository.readAll()
                .stream()
                .filter(transaction ->
                        transaction
                                .getTypeOfTransaction().equals("Commentable"))
                .flatMap(transaction -> Arrays
                        .stream(transaction.getPatternOfType().split(";")))
                .map(String::trim)
                .toList();
    }
}
