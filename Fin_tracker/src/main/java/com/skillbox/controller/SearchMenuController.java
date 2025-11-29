package com.skillbox.controller;

import com.skillbox.controller.dto.TransactionFilterDto;
import com.skillbox.controller.option.SearchOption;
import com.skillbox.data.model.Commentable;
import com.skillbox.data.model.Transaction;
import com.skillbox.controller.dto.TransactionCommentable;
import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.exception.InvalidMinMax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Консольный контроллер для управления навигацией по функционалу поиска транзакций.
 */
public class SearchMenuController extends AbstractMenuController<SearchOption> {

    public SearchMenuController() {
        super(SearchOption.class, "Выберите способ поиска транзакции");
    }

    private static TransactionFilterDto filter = new TransactionFilterDto();

    public TransactionFilterDto getTransactionFilter() {
        while (true) {
            SearchOption option = selectMenu();
            switch (option) {
                case EXIT:
                    return filter;
                case ALL_TRANSACTION:
                    return new TransactionFilterDto();
                case SEARCH_BY_CATEGORY:
                    filter = inputCategory(filter);
                    break;
                case SEARCH_BY_DATES:
                    filter = inputDates(filter);
                    break;
                case SEARCH_BY_AMOUNT:
                    filter = inputAmount(filter);
                    break;
                case SEARCH_BY_COMMENT:
                    filter = inputComment(filter);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    private TransactionFilterDto inputComment(TransactionFilterDto filter) {
        Scanner scanner1 = new Scanner(System.in);
        filter.setCommentDto(scanner1.nextLine());
        HashMap<String, Transaction> hashMap = new HashMap<>();
        Predicate<Transaction> predicate = filter.buildPredicate();
        Commentable commentable = new TransactionCommentable();
        TransactionRepository transactionRepository = new TransactionParse();

        List<String> comments = commentable.getComments();
        List<Transaction> transactions = transactionRepository.readAll().stream().filter(predicate).toList();

        for (Transaction t : transactions) {
            for (String c : comments) {
                String a = c.toLowerCase().trim();
                String b = filter.getCommentDto().toLowerCase().trim();
                if (a.contains(b)) {
                    hashMap.put(c, t);
                }
            }
        }
        hashMap.forEach((c, t) -> System.out.println(t));

        return filter;
    }

    private TransactionFilterDto inputAmount(TransactionFilterDto filter) {
        Scanner scanner1 = new Scanner(System.in);
        try {
            System.out.print("Min:");
            filter.setMin(new BigDecimal(scanner1.nextLine()));
            System.out.print("Max:");
            filter.setMax(new BigDecimal(scanner1.nextLine()));

            if (filter.getMin().compareTo(filter.getMax()) > 0) {
                throw new InvalidMinMax("Max не может быть меньше чем min!");
            }
            TransactionRepository transactionRepository = new TransactionParse();
            Predicate<Transaction> predicate = filter.buildPredicate();
            List<Transaction> list = transactionRepository.readAll()
                    .stream().filter(predicate).peek(transaction -> {
                        transaction.calculateTax();
                        transaction.convertToBaseCurrency(transaction.getSumOfTransaction());
                    }).collect(Collectors.toList());
            list.forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.err.println("Ваш ввод неправильный!");
        } catch (InvalidMinMax e) {
            System.err.println(e.getMessage());
        }

        return filter;
    }

    private TransactionFilterDto inputDates(TransactionFilterDto filter) {
        Scanner scanner1 = new Scanner(System.in);
        String start = scanner1.nextLine();
        if (!start.isBlank()) {
            try {
                filter.setStartDate(LocalDate.parse(start));
            } catch (DateTimeParseException e) {
                System.err.println("Вы неправильно ввели стартовую дату!");
            }
        }
        String end = scanner1.nextLine();
        if (!end.isBlank()) {
            try {
                filter.setEndDate(LocalDate.parse(end));
            } catch (DateTimeParseException e) {
                System.err.println("Вы неправильно ввели конечную дату!");
            }
        }

        TransactionRepository transactionRepository = new TransactionParse();
        Predicate<Transaction> predicate = filter.buildPredicate();
        List<Transaction> list = transactionRepository
                .readAll().stream()
                .filter(predicate).collect(Collectors.toList());
        list.forEach(System.out::println);

        return filter;
    }

    private TransactionFilterDto inputCategory(TransactionFilterDto filter) {
        Scanner scanner1 = new Scanner(System.in);
        filter.setCategoryDto(scanner1.nextLine());

        Predicate<Transaction> predicate = filter.buildPredicate();
        TransactionRepository transactionRepository = new TransactionParse();
        List<Transaction> list = transactionRepository.readAll()
                .stream().filter(predicate).collect(Collectors.toList());
        list.forEach(System.out::println);

        return filter;
    }

    public static TransactionFilterDto getFilter() {
        return filter;
    }
}