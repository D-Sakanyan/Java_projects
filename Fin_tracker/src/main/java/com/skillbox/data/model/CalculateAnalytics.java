package com.skillbox.data.model;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.controller.option.AggregateOption;
import com.skillbox.controller.option.GroupOption;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CalculateAnalytics implements TransactionService {
    private final TransactionRepository transactionRepository;

    public CalculateAnalytics() {
        this.transactionRepository = new TransactionParse();
    }

    private BigDecimal aggregateList(List<Transaction> transactions, AggregateOption option) {
        return switch (option) {
            case SUM_CALCULATE -> transactions.stream()
                    .map(Transaction::getSumOfTransaction).reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
            case AVG_CALCULATE -> transactions.stream()
                    .map(Transaction::getSumOfTransaction).reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(transactions.size()), 2, RoundingMode.HALF_UP);
            case COUNT_CALCULATE -> new BigDecimal(transactions.size());
        };
    }

    @Override
    public Analytic calculateAnalytics(TransactionFilterDto transactionFilter,
                                       GroupOption groupOption,
                                       AggregateOption aggregateOption) {

        if (aggregateOption == null)
            aggregateOption = AggregateOption.SUM_CALCULATE;

        Analytic analytic = new Analytic();
        analytic.setFilterDescription(
                transactionFilter != null ? transactionFilter.getFilterDescription() : "Нет фильтра");
        analytic.setGroupName(groupOption != null ? groupOption.getName() : "Все транзакции");
        analytic.setAggregateOptionName(aggregateOption != null ? aggregateOption.getName() : "Сумма");

        List<Transaction> transactions = transactionRepository.readAll().stream()
                .filter(transactionFilter.buildPredicate()).toList();

        if (groupOption == null) {
            BigDecimal result = aggregateList(transactions, aggregateOption);
            Map<String, BigDecimal> map = new LinkedHashMap<>();
            map.put("Все транзакции: ", result);

            analytic.setAggregation(map);

            return analytic;
        }

        Map<?, List<Transaction>> grouped = transactionFilter.getGroupedMap();
        Map<Object, BigDecimal> aggregation = new LinkedHashMap<>();
        for (var entry : grouped.entrySet()) {
            BigDecimal value = aggregateList(entry.getValue(), aggregateOption);
            aggregation.put(entry.getKey(), value);
        }

        analytic.setAggregation(aggregation);
        return analytic;
    }
}