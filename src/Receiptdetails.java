import java.time.LocalDate;

public class Receiptdetails {
    private double amount;
    private LocalDate datePayed;
    
    public Receiptdetails(double amount, LocalDate datePayed) {
        this.amount = amount;
        this.datePayed = datePayed;
    }

    public double getAmount() { return amount; }
    public LocalDate getDatePayed() { return datePayed; }
}
