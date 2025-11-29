package com.skillbox.controller.option;

public enum GroupOption implements MenuOption {
    EXIT("вернуться назад (без группировки)"),
    BY_MONTHS("группировать по месяцам"),
    BY_YEARS("группировать по годам"),
    BY_DAY_OF_WEEK("группировать по дню недели"),
    BY_CATEGORY("группировать по категории"),
    COUNT_INCOME_EXPENSE("считать доходы и расходы"),
    BY_ACCOUNT_TYPE("группировать по типу счёта"),
    BY_ACCOUNT_ID("группировать по ID пользователя");

    private final String name;

    GroupOption(String name) {this.name = name;}

    public static GroupOption of(int option) {
        return OptionUtils.of(GroupOption.class, option);
    }

    @Override
    public int getOption() {
        return ordinal();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
