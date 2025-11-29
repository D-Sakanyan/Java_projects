package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.data.model.Transaction;
import com.skillbox.data.repository.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AggregateMenuController extends AbstractMenuController<AggregateOption> {

    public AggregateMenuController() {
        super(AggregateOption.class, "Выберите способ агрегации транзакций");
    }

    public AggregateOption aggregateOption() {
        TransactionFilterDto filter = SearchMenuController.getFilter();
        while (true) {
            AggregateOption option = selectMenu();
            switch (option) {
                case SUM_CALCULATE:
                    sum(filter);
                    return option;
                case AVG_CALCULATE:
                    avg(filter);
                    return option;
                case COUNT_CALCULATE:
                    count(filter);
                    return option;
            }
        }
    }

    private TransactionFilterDto sum(TransactionFilterDto filter) {
        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();
        if (!filter.getGroupedMap().isEmpty()) {
            filter.getGroupedMap().forEach(((o, transactions) -> {
                transactions = transactions
                        .stream().filter(predicate).peek(transaction -> {
                            transaction.calculateTax();
                            transaction.convertToBaseCurrency(transaction.getSumOfTransaction());
                            transaction.getSumOfTransaction();
                        }).collect(Collectors.toList());
                BigDecimal sum = transactions.stream().map(Transaction::getSumOfTransaction)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2,RoundingMode.HALF_UP);
                System.out.println(o + ": " + sum);
            }));

        } else {

            Map<Integer, BigDecimal> map = transactionRepository.readAll().stream().filter(predicate)
                    .peek(transaction -> {
                        transaction.calculateTax();
                        transaction.convertToBaseCurrency(transaction.getSumOfTransaction());
                        transaction.getSumOfTransaction();
                    })
                    .collect(Collectors.groupingBy(
                            Transaction::getIdOfTransaction,
                            TreeMap::new,
                            Collectors.reducing(
                                    BigDecimal.ZERO,
                                    Transaction::getSumOfTransaction,
                                    BigDecimal::add))
                    );
            map.forEach((integer, bigDecimal) ->
                    System.out.println(integer + ": " + bigDecimal.setScale(2,RoundingMode.HALF_UP)));
        }
        return filter;
    }

    private TransactionFilterDto count(TransactionFilterDto filter) {
        Predicate<Transaction> predicate = filter.buildPredicate();
        TransactionRepository transactionRepository = new TransactionParse();
        if (!filter.getGroupedMap().isEmpty()) {
            filter.getGroupedMap().forEach((o, transactions) -> {
                transactions = transactions.stream().filter(predicate).toList();
                int count = (int) transactions.stream().mapToInt(Transaction::getIdOfTransaction).count();

                System.out.println(o + ": " + count);
            });
        } else {
            Map<Integer, Integer> map = transactionRepository.readAll().stream().filter(predicate)
                    .collect(Collectors.groupingBy(
                            Transaction::getIdOfTransaction,
                            TreeMap::new,
                            Collectors.collectingAndThen(
                                    Collectors.counting(),
                                    Long::intValue
                            )
                    ));
            map.forEach(((integer, integer2) -> System.out.println(integer + ": " + integer2)));
        }

        return filter;
    }

    private TransactionFilterDto avg(TransactionFilterDto filter) {
        Predicate<Transaction> predicate = filter.buildPredicate();
        if (!filter.getGroupedMap().isEmpty()) {
            filter.getGroupedMap().forEach(((o, transactions) -> {
                transactions = transactions
                        .stream().filter(predicate).peek(transaction -> {
                            transaction.calculateTax();
                            transaction.convertToBaseCurrency((transaction.getSumOfTransaction()));
                            transaction.getSumOfTransaction();
                        }).collect(Collectors.toList());
                BigDecimal avg = transactions.stream().map(Transaction::getSumOfTransaction)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(transactions.size()),2, RoundingMode.HALF_UP);

                System.out.println(o + ": " + avg);
            }));
        } else {
            sum(filter);
        }
        return filter;
    }
}
