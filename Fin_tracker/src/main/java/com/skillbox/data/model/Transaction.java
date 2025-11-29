package com.skillbox.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Абстрактный класс, представляющий собой транзакцию
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class Transaction implements Taxable, CurrencyConvertible {
    private String idOfAccount;
    private int idOfTransaction;
    private LocalDateTime dateTimeFormatter;
    private String categoryOfTransaction;
    private BigDecimal sumOfTransaction;
    private String typeOfTransaction;
    private String patternOfType;

    @Override
    public BigDecimal calculateTax() {
        if (typeOfTransaction.equals("Taxable")) {
            BigDecimal pattern = new BigDecimal(patternOfType);
            setSumOfTransaction(getSumOfTransaction().multiply(BigDecimal.ONE.subtract(pattern))
                    .setScale(2, RoundingMode.HALF_UP));
        }
        return sumOfTransaction;
    }

    @Override
    public BigDecimal convertToBaseCurrency(BigDecimal amount) {
        if (typeOfTransaction.equals("ForeignCurrency")) {
            BigDecimal rate = new BigDecimal(patternOfType);
            BigDecimal result = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            setSumOfTransaction(result);
        }
        return amount;
    }

    public String toString() {
        return idOfAccount +
                ", " + idOfTransaction +
                ", " + dateTimeFormatter +
                ", " + categoryOfTransaction +
                ", " + sumOfTransaction +
                ", " + typeOfTransaction +
                ", " + patternOfType;
    }
}
