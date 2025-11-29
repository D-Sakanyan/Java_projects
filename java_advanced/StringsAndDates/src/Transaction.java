import java.time.LocalDate;

public record Transaction(String description, double amount, TransactionType type, LocalDate date) {
}
