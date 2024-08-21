import java.time.LocalDate;

public class CreditcardPayment implements PaymentMethod {
	String cardNumber;
    String securityCode;
	LocalDate datePayed;

    // Constructor
    public CreditcardPayment(LocalDate datePayed, String cardNumber, String securityCode) {
    	this.cardNumber = cardNumber;
        this.securityCode = securityCode;
    	this.datePayed = datePayed;
        
    }

	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		Receipt receipt = new Receipt(amount, datePayed, fullAddress, "", cardNumber, securityCode);
		return receipt;
	}
}
