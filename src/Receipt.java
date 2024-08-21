import java.time.LocalDate;

public class Receipt {
	double amount;
	String cardNumber;
	String securityCode;
	LocalDate datePayed;
	Address address;
	String email;
	
	
	public Receipt(double amount, LocalDate datePayed, Address address, String email, String cardNumber, String securityCode) {
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.amount = amount;
		this.datePayed = datePayed;
		this.address = address;
		this.email = email;
	}
	
	void printReceipt() {
		if (email == "") {
			System.out.println("Credit Card Receipt");
			System.out.println("Date: " + datePayed);
	        System.out.println("Housenumber: " + address.getHouseNumber());
	        System.out.println("Postcode: " + address.getPostcode());
	        System.out.println("City: " + address.getCity());
	        System.out.println("Cardnumber: " + cardNumber);
	        System.out.println("Total: £" + amount);
		} else {
			System.out.println("PayPal Receipt");
			System.out.println("Date: " + datePayed);
	        System.out.println("Housenumber: " + address.getHouseNumber());
	        System.out.println("Postcode: " + address.getPostcode());
	        System.out.println("City: " + address.getCity());
	        System.out.println("Email: " + email);
	        System.out.println("Total: £" + amount);
		}
	}
}
