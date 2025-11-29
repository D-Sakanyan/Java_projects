package com.skillbox.controller;

import com.skillbox.controller.dto.AccountParse;
import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.model.AccountType;
import com.skillbox.data.model.Transaction;
import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.data.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupMenuController extends AbstractMenuController<GroupOption> {

    public GroupMenuController() {
        super(GroupOption.class, "Выберите опцию группировки транзакции");
    }

    public GroupOption groupOption() {
        TransactionFilterDto filter = SearchMenuController.getFilter();
        while (true) {
            GroupOption option = selectMenu();
            switch (option) {
                case EXIT:
                    return option;
                case BY_MONTHS:
                    month(filter);
                    return option;
                case BY_YEARS:
                    year(filter);
                    return option;
                case BY_DAY_OF_WEEK:
                    dayOfWeek(filter);
                    return option;
                case BY_CATEGORY:
                    category(filter);
                    return option;
                case COUNT_INCOME_EXPENSE:
                    income_expense(filter);
                    return option;
                case BY_ACCOUNT_TYPE:
                    type(filter);
                    return option;
                case BY_ACCOUNT_ID:
                    user(filter);
                    return option;
            }
        }
    }

    private TransactionFilterDto month(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        List<Transaction> transactions = transactionRepository
                .readAll().stream()
                .filter(predicate).toList();
        Map<Integer, List<Transaction>> map = transactions.stream()
                .collect(Collectors.groupingBy(transaction ->
                                transaction.getDateTimeFormatter().getMonthValue(),
                        TreeMap::new,
                        Collectors.toList()
                ));

        map.forEach((month, list) -> {
            System.out.println("Month: " + month);
            list.forEach(System.out::println);
        });

        filter.setGroupedMap(map);
        return filter;
    }

    private TransactionFilterDto year(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        List<Transaction> transactions = transactionRepository.readAll()
                .stream().filter(predicate)
                .toList();

        Map<Integer, List<Transaction>> map = transactions.stream().collect(Collectors.groupingBy(
                transaction ->
                        transaction.getDateTimeFormatter().getYear(),
                TreeMap::new,
                Collectors.toList()
        ));

        map.forEach((year, list) -> {
            System.out.println("Year: " + year);
            list.forEach(System.out::println);
        });

        filter.setGroupedMap(map);
        return filter;
    }

    private TransactionFilterDto dayOfWeek(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        List<Transaction> transactions = transactionRepository.readAll().stream().filter(predicate).toList();
        Map<DayOfWeek, List<Transaction>> map = transactions.stream().collect(Collectors.groupingBy(
                transaction -> transaction.getDateTimeFormatter().getDayOfWeek(),
                TreeMap::new,
                Collectors.toList()
        ));

        map.forEach((day, list) -> {
            System.out.println("Day: " + day);
            list.forEach(System.out::println);
        });

        filter.setGroupedMap(map);
        return filter;
    }

    private TransactionFilterDto category(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        List<Transaction> transactions = transactionRepository.readAll().stream().filter(predicate).toList();
        Map<String, List<Transaction>> map = transactions.stream().collect(Collectors.groupingBy(
                transaction -> transaction.getCategoryOfTransaction(),
                TreeMap::new,
                Collectors.toList()
        ));

        map.forEach((category, list) -> {
            System.out.println("Category: " + category);
            list.forEach(System.out::println);
        });

        filter.setGroupedMap(map);
        return filter;
    }

    private TransactionFilterDto income_expense(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        List<Transaction> transactions = transactionRepository.readAll().stream().filter(predicate).toList();

        Map<String, List<Transaction>> map = transactions.stream()
                .collect(Collectors.groupingBy(transaction
                        -> transaction.getSumOfTransaction().compareTo(BigDecimal.ZERO) > 0 ? "Income" : "Expense"
                ));

        map.forEach(((s, transactions1) -> {
            System.out.println(s + ":");
            transactions1.forEach(System.out::println);
        }));

        filter.setGroupedMap(map);
        return filter;
    }

    private TransactionFilterDto type(TransactionFilterDto filter) {
        AccountParse accountParse = new AccountParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        accountParse.readAll();
        Map<AccountType, List<Transaction>> groupedByType = accountParse.getMap().entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getAccountType(),
                        Collectors.flatMapping(
                                entry -> entry.getValue().stream().filter(predicate),
                                Collectors.toList()
                        )
                ));

        groupedByType.forEach(((account, transactions1) -> {
            System.out.println("Account Type: " + account);
            transactions1.forEach(System.out::println);
        }));

        filter.setGroupedMap(groupedByType);
        return filter;
    }

    private TransactionFilterDto user(TransactionFilterDto filter) {
        AccountParse accountParse = new AccountParse();
        Predicate<Transaction> predicate = filter.buildPredicate();

        accountParse.readAll();
        Map<Integer, List<Transaction>> groupedByUserId = accountParse.getMap()
                .entrySet().stream().collect(Collectors.groupingBy(
                        entry -> entry.getKey().getUserId(),
                        Collectors.flatMapping(
                                entry -> entry.getValue().stream().filter(predicate),
                                Collectors.toList()
                        )
                ));

        groupedByUserId.forEach(((id, transactions) -> {
            System.out.println("User: " + id);
            transactions.forEach(System.out::println);
        }));

        filter.setGroupedMap(groupedByUserId);
        return filter;
    }
}