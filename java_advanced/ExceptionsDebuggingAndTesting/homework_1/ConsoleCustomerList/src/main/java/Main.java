package main.java;

import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    private static Logger queryLogger = LogManager.getLogger("queryLogger");
    private static Logger errorLogger = LogManager.getLogger("errorLogger");
    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" +
            COMMAND_EXAMPLES;
    private static final String helpText = "Command examples:\n" + COMMAND_EXAMPLES;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        while (true) {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);
            if (tokens[0].equals("add")) {
                try {
                    if (tokens.length > 1) {
                        executor.addCustomer(tokens[1]);
                        System.out.println("Customer successfully added");
                        queryLogger.info("User successfully added");
                    } else {
                        String msg = "There must be components for option add";
                        System.out.println(msg);
                        errorLogger.error(msg);
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    errorLogger.error(e);
                }
            } else if (tokens[0].equals("list")) {
                executor.listCustomers();
            } else if (tokens[0].equals("remove")) {
                executor.removeCustomer(tokens[1]);
            } else if (tokens[0].equals("count")) {
                System.out.println("There are " + executor.getCount() + " customers");
            } else if (tokens[0].equals("help")) {
                System.out.println(helpText);
            } else {
                System.out.println(COMMAND_ERROR);
            }
        }
    }
}
