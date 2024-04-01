package model;
import java.sql.Date;

public class Customers {
    private int customerId;
    private String customerName;
    private Date dateOfBirth;
    private String customerSex;
    private String address;
    private String phoneNumber;
    private String email;

    // Constructors
    public Customers() {

    }


    public Customers(int customerId, String customerName, Date dateOfBirth, String customerSex, String address,
			String phoneNumber, String email) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.dateOfBirth = dateOfBirth;
		this.customerSex = customerSex;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}



	// Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(String customerSex) {
        this.customerSex = customerSex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public String toString() {
		return   customerId +" {"+ customerName +"  "+ phoneNumber+"}" ;
	}
	
    
}

