import java.util.ArrayList;
import java.util.List;

public class FinancialAccounting {
    private final int capacity;
    private final Transaction[] lastTransactions;
    private int cursor = 0;
    private int size = 0;

    private final List<Transaction> allTransactions = new ArrayList<>();

    public FinancialAccounting(int capacity) {
        this.capacity = capacity;
        this.lastTransactions = new Transaction[capacity];
    }

    public void addTransaction(Transaction transaction) {
        allTransactions.add(transaction);
        lastTransactions[cursor] = transaction;
        cursor = (cursor + 1) % capacity;
        if (size < capacity) size++;
    }

    public double getIncomeSum() {
        return allTransactions.stream()
                .filter(t -> t.type() == TransactionType.INCOME)
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double getExpenseSum() {
        return allTransactions.stream()
                .filter(t -> t.type() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double getBalance() {
        return getIncomeSum() - getExpenseSum();
    }

    public Transaction[] getLastTransactions() {
        Transaction[] result = new Transaction[size];
        for (int i = 0; i < size; i++) {
            int index = (cursor + capacity - 1 - i) % capacity;
            result[i] = lastTransactions[index];
        }
        return result;
    }
}
