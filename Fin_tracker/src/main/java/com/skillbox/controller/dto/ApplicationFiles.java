package com.skillbox.controller.dto;

public class ApplicationFiles {
    private static String accountFilename;
    private static String transactionFilename;
    private static String outputFilename;

    public void setFiles(String accountFilename, String transactionFilename, String outputFilename) {
        this.accountFilename = accountFilename;
        this.transactionFilename = transactionFilename;
        this.outputFilename = outputFilename;
    }

    public static String getAccountFilename() {
        return accountFilename;
    }

    public static String getTransactionFilename() {
        return transactionFilename;
    }

    public static String getOutputFilename() {
        return outputFilename;
    }
}
