package com.skillbox.controller.dto;

import com.skillbox.data.model.Recurring;
import com.skillbox.data.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Класс для хранения фильтра по транзакциям.
 */
@Getter
@Setter
public class TransactionFilterDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String categoryDto;
    private BigDecimal min;
    private BigDecimal max;
    private String commentDto;
    private Map<?, List<Transaction>> groupedMap = new HashMap<>();
    private String filterDescription = "";

    /**
     * Создает предикат для фильтрации транзакций по диапазону дат. Также вернет те Recurring транзакции, которые будут
     * или были выполнены в указанный диапазон дат
     *
     * @return Предикат для фильтрации транзакций по диапазону дат.
     */
    private Predicate<Transaction> datePredicate() {
        return transaction -> {
            LocalDateTime date = transaction.getDateTimeFormatter();
            LocalDateTime start = startDate == null ? null : startDate.atStartOfDay();
            LocalDateTime end = endDate == null ? null : endDate.atStartOfDay();
            return ((start == null || !date.isBefore(start)) && (end == null || !date.isAfter(end)))
                    || (transaction instanceof Recurring &&
                    ((Recurring) transaction).isExecutedBetween(start, end));
        };
    }

    /**
     * Создает предикат для фильтрации транзакций по комментарию или его части. Фильтруются только транзакции,
     * имплементирующие интерфейс Commentable. Если токен пустой или null, то возвращается предикат, который всегда
     * вернет true
     *
     * @return Предикат для фильтрации транзакций по комментарию.
     */
    private Predicate<Transaction> commentPredicate() {
        return transaction -> transaction.getTypeOfTransaction().equals("Commentable")
                && transaction.getPatternOfType().contains(commentDto);
    }

    /**
     * Создает предикат для фильтрации транзакций по диапазону суммы.
     *
     * @return Предикат для фильтрации транзакций по диапазону суммы.
     */
    private Predicate<Transaction> amountPredicate() {
        return transaction -> transaction.getSumOfTransaction().compareTo(min) >= 0 &&
                transaction.getSumOfTransaction().compareTo(max) <= 0;
    }

    /**
     * Создает предикат для фильтрации транзакций по категории.
     *
     * @return Предикат для фильтрации транзакций по категории.
     */
    private Predicate<Transaction> categoryPredicate() {
        return transaction -> transaction.getCategoryOfTransaction().equals(categoryDto);
    }

    /**
     * Собирает предикат для фильтрации транзакции.
     *
     * @return Предикат для фильтрации транзакции.
     */
    public Predicate<Transaction> buildPredicate() {
        StringBuilder stringBuilder = new StringBuilder();
        Predicate<Transaction> predicate = transaction -> true;

        if (categoryDto != null) {
            predicate = predicate.and(categoryPredicate());
            stringBuilder.append("категория —— ").append(categoryDto);
        }
        if (commentDto != null) {
            predicate = predicate.and(commentPredicate());
            stringBuilder.append("комментария —— ").append(commentDto);
        }
        if (min != null && max != null) {
            predicate = predicate.and(amountPredicate());
            stringBuilder.append("минимальная сумма —— ").append(min)
                    .append(" максимальная сумма —— ").append(max);
        }
        if (startDate != null && endDate != null) {
            predicate = predicate.and(datePredicate());
            stringBuilder.append("начальная дата —— ").append(startDate)
                    .append(" конечная дата —— ").append(endDate);
        }
        this.filterDescription = stringBuilder.toString();
        return predicate;
    }
}