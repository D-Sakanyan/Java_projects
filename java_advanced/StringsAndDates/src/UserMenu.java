import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class UserMenu {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final FinancialAccounting accounting;
    private final Scanner scanner = new Scanner(System.in);

    public UserMenu(FinancialAccounting accounting) {
        this.accounting = accounting;
    }

    public void start() {
        printHelp();

        while (true) {
            System.out.print("\nВведите команду или транзакцию: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("HELP")) {
                printHelp();
            } else if (input.equalsIgnoreCase("REPORT")) {
                printReport();
            } else if (input.equalsIgnoreCase("EXIT")) {
                printReport();
                System.out.println("Завершение работы.");
                break;
            } else {
                try {
                    Transaction transaction = parseTransaction(input);
                    accounting.addTransaction(transaction);
                    System.out.println("Транзакция добавлена.");
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }

    private void printHelp() {
        System.out.println("\nДоступные команды:");
        System.out.println("HELP — показать инструкцию");
        System.out.println("REPORT — показать отчёт");
        System.out.println("EXIT — показать отчёт и выйти");
        System.out.println("Добавление транзакции:");
        System.out.println("Пример: Билет на Марс; 2499.99; EXPENSE; 24.03.2036");
        System.out.println("Формат: описание; сумма; тип (INCOME или EXPENSE); дата (дд.мм.гггг)");
    }

    private void printReport() {
        System.out.println("\n===== Отчёт по финансам =====");
        System.out.printf(Locale.US, "%-20s %15s%n", "Сумма доходов:", String.format("%.2f", accounting.getIncomeSum()));
        System.out.printf(Locale.US, "%-20s %15s%n", "Сумма расходов:", String.format("%.2f", accounting.getExpenseSum()));
        System.out.printf(Locale.US, "%-20s %15s%n", "Баланс:", String.format("%.2f", accounting.getBalance()));

        System.out.println("\nПоследние операции:");
        System.out.printf("%-25s | %-10s | %-8s | %-12s%n", "Описание", "Сумма", "Тип", "Дата");
        System.out.println("---------------------------+------------+----------+--------------");

        Transaction[] last = accounting.getLastTransactions();
        for (Transaction t : last) {
            System.out.printf("%-25s | %10.2f | %-8s | %-12s%n",
                    truncate(t.description(), 25),
                    t.amount(),
                    t.type(),
                    t.date().format(DATE_FORMATTER));
        }
        System.out.println("=============================\n");
    }

    private Transaction parseTransaction(String input) {
        String[] parts = input.split(";");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Неверный формат: должно быть 4 части через ';'");
        }
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }

        double amount;
        try {
            amount = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Сумма должна быть числом");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[2].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Тип должен быть INCOME или EXPENSE");
        }

        LocalDate date;
        try {
            date = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Дата должна быть в формате дд.мм.гггг");
        }

        return new Transaction(description, amount, type, date);
    }

    private String truncate(String str, int maxLength) {
        return (str.length() <= maxLength) ? str : str.substring(0, maxLength - 3) + "...";
    }

    public static void main(String[] args) {
        FinancialAccounting accounting = new FinancialAccounting(5);
        UserMenu menu = new UserMenu(accounting);
        menu.start();
    }
}
