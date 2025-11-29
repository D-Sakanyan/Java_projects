package com.skillbox.controller.option;

public enum AggregateOption implements MenuOption {
    SUM_CALCULATE("подсчёт суммы"),
    AVG_CALCULATE("подсчёт среднего значения"),
    COUNT_CALCULATE("подсчёт количества");

    private final String name;

    AggregateOption(String name) {this.name = name;}

    public static AggregateOption of(int option) {
        return OptionUtils.of(AggregateOption.class, option);
    }

    @Override
    public int getOption() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name;
    }
}
