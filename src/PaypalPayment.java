import java.time.LocalDate;

public class PaypalPayment implements PaymentMethod {
	String email;
	LocalDate datePayed;
    
    // Constructor
    public PaypalPayment(LocalDate datePayed, String email) {
        this.datePayed = datePayed;
        this.email = email;
    }

	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		Receipt receipt = new Receipt(amount, datePayed, fullAddress, email, "", "");
		return receipt;
	}
}