// Address class
public class Address {
    private String houseNumber;
    private String postcode;
    private String city;
    
    public Address(String houseNumber, String postcode, String city) {
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
    }

    public String getHouseNumber() { 
    	return houseNumber;
    }
    public String getPostcode() { return postcode; }
    
    public String getCity() { return city; }
    
 // Setters
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostCode(String postCode) {
        this.postcode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


