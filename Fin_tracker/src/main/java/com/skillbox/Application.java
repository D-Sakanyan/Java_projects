package com.skillbox;

import com.skillbox.controller.MainMenuController;
import com.skillbox.controller.dto.AccountParse;
import com.skillbox.controller.dto.ApplicationFiles;
import com.skillbox.controller.dto.TransactionParse;
import com.skillbox.data.model.Analytic;
import com.skillbox.data.model.CalculateAnalytics;
import com.skillbox.data.repository.AccountRepository;
import com.skillbox.data.repository.AnalyticRepository;
import com.skillbox.data.repository.TransactionRepository;
import com.skillbox.service.TransactionService;

public class Application {

    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException(
                    "Необходимо указать имена файлов для входных данных аккаунтов и транзакций," +
                            " а также для выходного файла.");
        }

        String accountFilename = args[0];
        String transactionFilename = args[1];
        String outputFilename = args[2];

        ApplicationFiles applicationFiles = new ApplicationFiles();
        applicationFiles.setFiles(accountFilename,transactionFilename,outputFilename);

        AccountRepository accountReader = new AccountParse();
        TransactionRepository transactionReader = new TransactionParse( );
        TransactionService transactionService = new CalculateAnalytics();
        AnalyticRepository saver = new Analytic();
        new MainMenuController(transactionService, saver).start();
    }

}